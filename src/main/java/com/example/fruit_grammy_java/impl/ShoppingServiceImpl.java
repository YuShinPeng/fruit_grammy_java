package com.example.fruit_grammy_java.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.fruit_grammy_java.entity.Product;
import com.example.fruit_grammy_java.entity.Shopping;
import com.example.fruit_grammy_java.entity.ShoppingContent;
import com.example.fruit_grammy_java.ifs.ShoppingService;

import com.example.fruit_grammy_java.repository.ProductDao;
import com.example.fruit_grammy_java.repository.ShoppingContentDao;
import com.example.fruit_grammy_java.repository.ShoppingDao;

import com.example.fruit_grammy_java.vo.ShoppingRequest;
import com.example.fruit_grammy_java.vo.ShoppingResponse;

@Service
public class ShoppingServiceImpl implements ShoppingService {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private ShoppingDao shoppingDao;
	@Autowired
	private ShoppingContentDao shoppingContentDao;

	@Override
	public ShoppingResponse addData(ShoppingRequest req) {
		//購物車內容
		ShoppingContent ShoppingList = new ShoppingContent();
		//購物車
		Shopping shoppingBasic = new Shopping();
		// 第二筆
		String nextInfo = "";
		// 不曾登入過
		String code1 = "";
		// 曾經登入過
		String code2 = "";										

<<<<<<< HEAD
		String productId = req.getProduct();// 請求產品編碼
		String shoppingAccount = req.getBuyerAccount(); // 請求賣家帳號

		int shoppingAddNum = req.getNumber(); // 請求買家需要加入數量


		List<ShoppingContent> allList = shoppingContentDao.findAll(); // 確認表內是否已經加入過 叫出全部資料
=======
		// 請求產品編碼
		String productId = req.getProduct();
		// 請求買家帳號
		String shoppingAccount = req.getBuyerAccount(); 
		
		// 確認表內是否已經加入過 叫出全部資料
		// 建立放購物車內容的 List
		List<ShoppingContent> allList = shoppingContentDao.findAll(); 
		// 購物車內各品項的詳細資料
>>>>>>> Tung3
		Optional<Product> ProductResult = productDao.findById(productId);
		Product productContext = ProductResult.get();
		// 商品編號
		String ItemID = productContext.getHsCode();
		if (StringUtils.hasText(shoppingAccount)) {
			for (ShoppingContent item : allList) {
				if (item.getItemId().contains(ItemID) && item.getShoppingNumber().contains(shoppingAccount)) {
					return new ShoppingResponse("重複");
				}
			}
		}


		if (shoppingAddNum > productContext.getNumber()) {
			return new ShoppingResponse("超過賣家數量，請重下");
		}


		// 基本資料
		if (!StringUtils.hasText(shoppingAccount) && !StringUtils.hasText(productId)) {
			return new ShoppingResponse("錯誤!無基本資訊");
		}

		Optional<Shopping> ShoppingResult = shoppingDao.findById(shoppingAccount);
		if (!ShoppingResult.isPresent()) {
			shoppingBasic.setBuyerAccount(shoppingAccount);
			shoppingBasic.setBuyerContent(shoppingAccount + "_001");
			shoppingDao.save(shoppingBasic);
			code1 = shoppingAccount + "_001";

			ShoppingList.setShoppingNumber(code1);
			ShoppingList.setItemId(productId);
			shoppingContentDao.save(ShoppingList);

		}

		String updateContent = "";
		Shopping alreadyChart = null;

		// 如果已經存在資料，拉出既有資料做往上加
		if (ShoppingResult.isPresent()) {

			alreadyChart = ShoppingResult.get();

			String[] ChartArr = alreadyChart.getBuyerContent().split(",");
			if (ChartArr.length >= 5) {
				return new ShoppingResponse("新增超過五筆，請先結帳，或刪除購物車");
			}

			List<Integer> intArr = new ArrayList<>();// collection

			for (int i = 0; i < ChartArr.length; i++) {
				String[] numStr = ChartArr[i].split("_");
				int orderNum = Integer.valueOf(numStr[1]);
				intArr.add(orderNum);
			}


			// Collections.sort(intArr); //ASC

//			Collections.sort(intArr); //ASC

			Collections.sort(intArr, Collections.reverseOrder()); // DSEC

			String orderStr = String.valueOf(intArr.get(0) + 1);
			updateContent = shoppingAccount + "_00" + orderStr + "," + alreadyChart.getBuyerContent();
			nextInfo = (shoppingAccount + "_00" + orderStr);// 新的一筆資料
			alreadyChart.setBuyerContent(updateContent);
			shoppingDao.save(alreadyChart);
			code2 = nextInfo;

			ShoppingList.setShoppingNumber(code2);
			ShoppingList.setItemId(productId);
			shoppingContentDao.save(ShoppingList);

		}


		ShoppingList.setItemNum(shoppingAddNum);

		ShoppingList.setItemName(productContext.getName());
		ShoppingList.setDiscription(productContext.getDescription());
		ShoppingList.setItemPrice(productContext.getPrice());
		ShoppingList.setSellAccount(productContext.getSellerAccount());
		ShoppingList.setStock(productContext.getNumber());
		ShoppingList.setShop_condition(false);
		//
		shoppingContentDao.save(ShoppingList);

		return new ShoppingResponse("成功加入");
	}

	// ===================================================================
	private ShoppingResponse updateOrDelete(ShoppingRequest req, boolean isUpdate) {
		String userAccount = req.getBuyerAccount(); //使用者為誰
		String shoppingCode = req.getShoppingCode(); //買方預購序號
	

		if (!StringUtils.hasText(userAccount)) {
			return new ShoppingResponse("無使用之權限");
		}

		// 根據提供的shoppingCode找到要更新刪除的資料
		
		Optional<ShoppingContent> ShoppingContentResult = shoppingContentDao.findById(shoppingCode);

		ShoppingContent getShoppingContent = ShoppingContentResult.get();

		if (isUpdate) { // 更新情況
			int reqNum = req.getNumber(); //產品數量
			String productId = req.getProduct(); //產品ID
			Optional<Product> ProductList = productDao.findById(productId);
			Product produtInfo = ProductList.get();

			if (reqNum <= 0 && StringUtils.hasText(shoppingCode)) {
				return new ShoppingResponse("請使用移除功能，或請更正為大於零的數字");
			} else if (reqNum > produtInfo.getNumber()) {
				return new ShoppingResponse("已超過賣家提供數量上限，請調整，最大值為庫存數量");
			} else {
				getShoppingContent.setItemNum(reqNum);
				shoppingContentDao.save(getShoppingContent);
				return new ShoppingResponse("更新完成");
			}

		}

		// 刪除情況
	
		if (StringUtils.hasText(shoppingCode)) {

			shoppingContentDao.deleteById(shoppingCode);

			Optional<Shopping> ShoppingResult = shoppingDao.findById(userAccount);
			Shopping getShoppingResult = ShoppingResult.get();
			String[] shoppingOredrArr = getShoppingResult.getBuyerContent().split(",");

			if (shoppingOredrArr.length == 1) {
				shoppingDao.deleteById(userAccount);
			}
			List<String> resultArr = new ArrayList<>();// collection

			String textSet = "";
			for (int i = 0; i < shoppingOredrArr.length; i++) {

				String item = shoppingOredrArr[i];
				if (shoppingOredrArr[i].equals(shoppingCode)) {
					continue;
				}
				resultArr.add(item);
			}


//	     if(i==shoppingOredrArr.length-1 && shoppingOredrArr.length>=2) {
//	      stringShoppningCode += item;
//	     }



//					if(i==shoppingOredrArr.length-1 && shoppingOredrArr.length>=2) {
//						stringShoppningCode += item;
//					}


			for (int i = 0; i < resultArr.size(); i++) {
				String item = resultArr.get(i);

				if (i == resultArr.size() - 1) {
					textSet = item + textSet;
				} else {
					textSet = textSet + "," + item;
				}

				getShoppingResult.setBuyerContent(textSet);
				shoppingDao.save(getShoppingResult);
			}
		}

		return new ShoppingResponse("刪除完成");

	}

	@Override
	public ShoppingResponse modiData(ShoppingRequest req) {

		return updateOrDelete(req, true);
	}

	@Override
	public ShoppingResponse deleteData(ShoppingRequest req) {

		return updateOrDelete(req, false);
	}

	// ======================================================
	@Override
	public ShoppingResponse getShoppingData(ShoppingRequest req) {
		List<ShoppingContent> userAllList = new ArrayList<>();

		String userAccount = req.getBuyerAccount();
		if (!StringUtils.hasText(userAccount)) {
<<<<<<< HEAD
			return new ShoppingResponse("無使用者之權限");

=======
			return new ShoppingResponse("無使用者之權限_無讀取到值");
>>>>>>> Tung3
		}

		userAllList = shoppingContentDao.findByShoppingNumberContaining(userAccount);
		if (userAllList.size() <= 0) {

			return new ShoppingResponse("歡迎到瀏覽搜尋選購~");
		}
		return new ShoppingResponse(userAllList);

	
	}

}