package com.example.fruit_grammy_java.impl;

import java.util.ArrayList;
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
		ShoppingContent ShoppingList = new ShoppingContent(); //
		Shopping shoppingBasic = new Shopping();
		String nextInfo = "";// 第二筆
		String code1 = ""; //如果沒有登入過
		String code2 = ""; //如果有
		
		String productId = req.getProduct();// 請求產品編碼
		String shoppingAccount = req.getBuyerAccount(); // 請求賣家帳號

		// 基本資料
		if (!StringUtils.hasText(shoppingAccount) && !StringUtils.hasText(productId)) {
			return new ShoppingResponse("錯誤!無基本資訊");
		}
		
	

		List<ShoppingContent> allList = shoppingContentDao.findAll(); // 確認表內是否已經加入過 叫出全部資料
		Optional<Product> ProductResult = productDao.findById(productId);
		Product productContext = ProductResult.get();
		String ItemID = productContext.getHs_code();
        if(StringUtils.hasText(shoppingAccount)) {
		for (ShoppingContent item : allList) {
			if (item.getItemId().contains(ItemID) && item.getShoppingNumber().contains(shoppingAccount)) {
				return new ShoppingResponse("重複");
			}
		}}

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
		if (ShoppingResult.isPresent()) {

			alreadyChart = ShoppingResult.get();
			String[] ChartArr = alreadyChart.getBuyerContent().split(",");
			int ArrLong = ChartArr.length;
			String[] numStr = ChartArr[0].split("_");
			
			int orderNum = Integer.valueOf(numStr[1]);
            String  orderStr =String.valueOf(orderNum+1);
			updateContent = shoppingAccount + "_00" + orderStr + "," + alreadyChart.getBuyerContent();
			nextInfo =  (shoppingAccount + "_00" + orderStr);// 新的一筆資料
			alreadyChart.setBuyerContent(updateContent);
			shoppingDao.save(alreadyChart);
			code2 = nextInfo;
			

			ShoppingList.setShoppingNumber(code2);
			ShoppingList.setItemId(productId);
			shoppingContentDao.save(ShoppingList);
			

		}


	

		ShoppingList.setItemName(productContext.getName());
		ShoppingList.setDiscription(productContext.getDescription());
		ShoppingList.setItemPrice(productContext.getPrice());
		ShoppingList.setSellAccount(productContext.getSeller_account());
		ShoppingList.setStock(productContext.getNumber());
//
		shoppingContentDao.save(ShoppingList);

		return new ShoppingResponse("成功加入");
	}

//	===================================================================
	private ShoppingResponse updateOrDelete(ShoppingRequest req, boolean isUpdate) {
		String userAccount = req.getBuyerAccount();
		int reqNum = req.getNumber();
		String shoppingCode = req.getShoppingCode();

		if (!StringUtils.hasText(userAccount)) {
			return new ShoppingResponse("無使用之權限");
		}

		// 根據提供的shoppingCode找到要更新刪除的資料

		Optional<ShoppingContent> ShoppingContentResult = shoppingContentDao.findById(shoppingCode);

		ShoppingContent getShoppingContent = ShoppingContentResult.get();

		if (isUpdate) { // 更新情況

			if (reqNum <= 0 && StringUtils.hasText(shoppingCode)) {
				return new ShoppingResponse("請使用移除功能，或請更正為大於零的數字");
			} else {
				getShoppingContent.setItemNum(reqNum);
				shoppingContentDao.save(getShoppingContent);
				return new ShoppingResponse("更新完成");
			}

		}

		// 刪除情況
		String stringShoppningCode = "";
		if (StringUtils.hasText(shoppingCode)) {

			shoppingContentDao.deleteById(shoppingCode);

			Optional<Shopping> ShoppingResult = shoppingDao.findById(userAccount);
			Shopping getShoppingResult = ShoppingResult.get();
			String[] shoppingOredrArr = getShoppingResult.getBuyerContent().split(",");
			List<String> result = new ArrayList<>();// collection
            if(shoppingOredrArr.length==1) {
            	 shoppingDao.deleteById(userAccount);
             }
			
			
			for (int i = 0; i < shoppingOredrArr.length; i++) {

				String item = shoppingOredrArr[i];
				if (shoppingOredrArr[i].equals(shoppingCode)) {
					continue;
				} 
					
//					if(i==shoppingOredrArr.length-1 && shoppingOredrArr.length>=2) {
//						stringShoppningCode += item;
//					}
				stringShoppningCode += item+",";
				

				getShoppingResult.setBuyerContent(stringShoppningCode);
				shoppingDao.save(getShoppingResult);

			}
		}

		return new ShoppingResponse("刪除完成");

	}
//			if(i==shoppingOredrArr.length-1) {
//				stringShoppningCode=shoppingOredrArr[i]+stringShoppningCode;
//				}
//               
//				else {
//				
//				stringShoppningCode=","+shoppingOredrArr[i]+stringShoppningCode;}
//				getShoppingResult.setBuyerContent(stringShoppningCode);

	// 確認是否只有一筆 就不要，
//				String textrefresh="";
//				String[] concern=getShoppingResult.getBuyerContent().split(",");
//				if(concern.length==1) {
//					for(String item :concern) {
//						textrefresh=getShoppingResult.getBuyerAccount()+"_001"+textrefresh;
//						getShoppingResult.setBuyerContent(textrefresh);
//						break;
//					}
//					
//				}

	@Override
	public ShoppingResponse modiData(ShoppingRequest req) {

		return updateOrDelete(req, true);
	}

	@Override
	public ShoppingResponse deleteData(ShoppingRequest req) {

		return updateOrDelete(req, false);
	}

}
