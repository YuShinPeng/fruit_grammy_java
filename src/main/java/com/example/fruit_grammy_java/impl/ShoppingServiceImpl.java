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
		// 購物車內容
		ShoppingContent shoppingContentList = new ShoppingContent();
		// 購物車
		Shopping shoppingBasic = new Shopping();

		// 品項購買數量
		int shoppingAddNum = req.getNumber();

		// 請求買家帳號
		String buyerAccount = req.getBuyerAccount();

// 確認表內是否已經加入過 叫出全部資料
		// 建立所有購物車內容的 List
		List<ShoppingContent> allList = shoppingContentDao.findAll();

		// 產品編號
		String productId = req.getProduct();
		if(!StringUtils.hasText(productId)) {
			return new ShoppingResponse("沒有資料");
		}
		// 購物車內各品項的詳細資料
		Optional<Product> ProductResult = productDao.findById(productId);
		Product productContext = ProductResult.get();
		// 商品編號, ItemID = productId
		String ItemID = productContext.getHsCode();

		// 若有下單，會顯示買家帳號
		if (StringUtils.hasText(buyerAccount)) {
			// allList = 購物車內容資料庫所有資料
			for (ShoppingContent item : allList) {
				// 若該買家的購物車內已有該品項則回傳"重複"
				if (item.getItemId().contains(ItemID) && item.getShoppingNumber().contains(buyerAccount)) {
					return new ShoppingResponse("重複");
				}
			}
		}

		// 防止買家需求數量大於庫存,判斷式前者：買家需求數量，判斷式後者：商品庫存量
		if (shoppingAddNum > productContext.getNumber()) {
			return new ShoppingResponse("超過賣家數量，請重下");
		}

		// 防呆，若沒送入基本資料則報錯
		if (!StringUtils.hasText(buyerAccount) && !StringUtils.hasText(productId)) {
			return new ShoppingResponse("錯誤!無基本資訊");
		}

		// 不曾登入過
		String code1 = "";
		// 取得該買家的購物車資訊
		Optional<Shopping> ShoppingResult = shoppingDao.findById(buyerAccount);
		// 若沒有該買家的購物車，則新增一筆該買家的購物車資訊
		if (!ShoppingResult.isPresent()) {
			shoppingBasic.setBuyerAccount(buyerAccount);
			code1 = buyerAccount + "_001";
			shoppingBasic.setBuyerContent(code1);
			shoppingDao.save(shoppingBasic);

			// 購物車內容資料庫也要跟著新增，因為是連動的
			shoppingContentList.setShoppingNumber(code1);
			shoppingContentList.setItemId(productId);
			shoppingContentDao.save(shoppingContentList);

		}

		// 如果已經存在資料，拉出既有資料做往上加
		if (ShoppingResult.isPresent()) {

			Shopping alreadyChart = ShoppingResult.get();

			// 將多數的購物車內容資訊逐一加入陣列
			String[] ChartArr = alreadyChart.getBuyerContent().split(",");
			if (ChartArr.length >= 5) {
				return new ShoppingResponse("新增超過五筆，請先結帳，或刪除購物車");
			}

			// collection
			List<Integer> intArr = new ArrayList<>();

			for (int i = 0; i < ChartArr.length; i++) {
				// ex:as555@gmail.com_001 => as555@gmail.com & 001
				// numStr[0] = as555@gmail.com, numStr[1] = 001
				String[] numStr = ChartArr[i].split("_");
				int orderNum = Integer.valueOf(numStr[1]);
				intArr.add(orderNum);
			}

			// 曾經登入過
			// DSEC
			Collections.sort(intArr, Collections.reverseOrder());
			// 因為進行倒序，所以 [0] 會是最大數字，取出來 +1 就會是最新一筆
			String orderStr = String.valueOf(intArr.get(0) + 1);
			// ex as555@gmail.com + _00 + 最新數字
			String nextInfo = (buyerAccount + "_00" + orderStr);
			// 最新的購物車內容資料加上已存在的
			String updateContent = nextInfo + "," + alreadyChart.getBuyerContent();
			// 新的一筆資料
			alreadyChart.setBuyerContent(updateContent);
			shoppingDao.save(alreadyChart);

			shoppingContentList.setShoppingNumber(nextInfo);
			shoppingContentList.setItemId(productId);
			shoppingContentDao.save(shoppingContentList);

		}

		shoppingContentList.setItemNum(shoppingAddNum);

		// productContext = 品項的詳細資料
		shoppingContentList.setItemName(productContext.getName());
		shoppingContentList.setDiscription(productContext.getDescription());
		shoppingContentList.setItemPrice(productContext.getPrice());
		shoppingContentList.setSellAccount(productContext.getSellerAccount());
		// 庫存量
		shoppingContentList.setStock(productContext.getNumber());
		shoppingContentList.setShop_condition(false);
		//
		shoppingContentDao.save(shoppingContentList);

		return new ShoppingResponse("成功加入");
	}

	// ===================================================================
	private ShoppingResponse updateOrDelete(ShoppingRequest req, boolean isUpdate) {
		// 使用者為誰
		String userAccount = req.getBuyerAccount();
		// 買方預購序號
		String shoppingCode = req.getShoppingCode(); 

		if (!StringUtils.hasText(userAccount)) {
			return new ShoppingResponse("無使用之權限");
		}

		// 根據提供的shoppingCode找到要更新刪除的資料

		Optional<ShoppingContent> ShoppingContentResult = shoppingContentDao.findById(shoppingCode);

		ShoppingContent getShoppingContent = ShoppingContentResult.get();
		
		// 更新情況
		if (isUpdate) { 
			// 產品數量
			int reqNum = req.getNumber(); 
			// 產品ID
			String productId = req.getProduct(); 
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
			return new ShoppingResponse("無使用者之權限");
		}

		userAllList = shoppingContentDao.findByShoppingNumberContaining(userAccount);
		if (userAllList.size() <= 0) {

			return new ShoppingResponse("歡迎到瀏覽搜尋選購~");
		}
		return new ShoppingResponse(userAllList);

	}

}