package com.example.fruit_grammy_java.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.fruit_grammy_java.entity.Product;
import com.example.fruit_grammy_java.ifs.ProductService;
import com.example.fruit_grammy_java.repository.ProductDao;
import com.example.fruit_grammy_java.vo.ProductRequest;
import com.example.fruit_grammy_java.vo.ProductResponse;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	// 賣家上架新商品
	// 上架需填資訊
	@Override
	public ProductResponse addProduct(ProductRequest productReq) {
		// 裝檢查資料的List
		List<Product> productList = productReq.getProductList();

		for (Product item : productList) {

			if (!StringUtils.hasText(item.getHsCode())) {
				return new ProductResponse("商品編碼不得為空");
			}

			if (productDao.existsById(item.getHsCode())) {
				return new ProductResponse("此商品已上架");
			}

			if (!StringUtils.hasText(item.getSellerAccount())) {
				return new ProductResponse("賣家帳號不得為空");
			}

			if (!StringUtils.hasText(item.getName())) {
				return new ProductResponse("商品名稱不得為空");
			}

			if (!StringUtils.hasText(item.getType())) {
				return new ProductResponse("種類不得為空");
			}

			if (!StringUtils.hasText(item.getPlace())) {
				return new ProductResponse("產地不得為空");
			}

			if (item.getNumber() <= 0) {
				return new ProductResponse("數量不得小於或等於0");
			}

			if (!StringUtils.hasText(item.getDescription())) {
				return new ProductResponse("商品備註說明不得為空");
			}

			if (!StringUtils.hasText(item.getDate())) {
				return new ProductResponse("採收日期不得為空");
			}

			// 檢查日期是否符合格式且具有合法性
			String date = item.getDate();
			String regex = "\\d{4}-\\d{2}-\\d{2}";
			if (!date.matches(regex)) {
				return new ProductResponse("日期格式錯誤");
			}

			// 檢查此日期是否合法性 ex. 4044-04-04 不可能
		}
		// 新增成功回傳資訊和成功訊息
		productDao.saveAll(productList);
		return new ProductResponse(productList, "新增成功");
	}

	// 賣家修改上架商品
	@Override
	public ProductResponse updateProduct(ProductRequest productReq) {
		Optional<Product> findProduct = productDao.findById(productReq.getHsCode());
		if (!findProduct.isPresent()) {
			return new ProductResponse("此商品不存在");
		}

		// 存在取得此產品資訊
		Product getProduct = findProduct.get();

		// 修改產品名稱
		if (StringUtils.hasText(productReq.getName())) {
			getProduct.setName(productReq.getName());
			productDao.save(getProduct);
		}

		// 修改種類
		if (StringUtils.hasText(productReq.getType())) {
			getProduct.setType(productReq.getType());
			productDao.save(getProduct);
		}

		// 修改產地
		if (StringUtils.hasText(productReq.getPlace())) {
			getProduct.setPlace(productReq.getPlace());
			productDao.save(getProduct);
		}

		// 修改數量
		if (productReq.getNumber() > 0) {
			getProduct.setNumber(productReq.getNumber());
			productDao.save(getProduct);
		}

		// 修改日期
		if (StringUtils.hasText(productReq.getDate())) {
			getProduct.setDate(productReq.getDate());
			// 檢查日期是否符合格式
			String date = productReq.getDate();
			String regex = "\\d{4}-\\d{2}-\\d{2}";
			if (!date.matches(regex)) {
				return new ProductResponse("日期格式錯誤");
			}
			productDao.save(getProduct);
		}

		// 修改價格
		if (productReq.getPrice() > 0) {
			getProduct.setPrice(productReq.getPrice());
			productDao.save(getProduct);
		}

		// 修改敘述
		if (StringUtils.hasText(productReq.getDescription())) {
			getProduct.setDescription(productReq.getDescription());
			productDao.save(getProduct);
		}

		return new ProductResponse("修改成功", getProduct);
	}

	// 買家搜尋 生產履歷 -- 透過產地搜尋
	@Override
	public ProductResponse searchPlaceProduct(String place) {

		List<Product> searchReq = productDao.findByPlace(place);

		List<ProductResponse> searchAllRes = new ArrayList<>();

		if (!StringUtils.hasText(place)) {
			return new ProductResponse("搜尋內容不得為空");
		}

		for (Product item : searchReq) {
			ProductResponse searchRes = new ProductResponse();
			searchRes.setPlace(place);
			searchRes.setHsCode(item.getHsCode());
			searchRes.setName(item.getName());
			searchRes.setDate(item.getDate());
			searchRes.setType(item.getType());
			searchRes.setNumber(item.getNumber());
			searchRes.setPrice(item.getPrice());
			searchRes.setDescription(item.getDescription());
			searchAllRes.add(searchRes);
		}
		return new ProductResponse(searchAllRes);

	}

	@Override
	public ProductResponse searchSpecificProduct(String name) { // 上架的搜尋

		List<Product> searchAllRes = new ArrayList<>();

		if (!StringUtils.hasText(name)) {// 如果為null，直接提供全部資料

			List<Product> allSearchReq = productDao.findAll();
			return new ProductResponse(allSearchReq, "all info");
		}

		List<Product> searchReq = productDao.findByNameContaining(name);

		for (Product item : searchReq) {

			searchAllRes.add(item);
		}
		return new ProductResponse(searchAllRes, "specific info");
	}

	// 賣家已上架商品
	@Override
	public ProductResponse searchSellerProduct(String sellerAccount) {
		List<Product> searchReq = productDao.findBySellerAccount(sellerAccount);

		List<ProductResponse> searchAllRes = new ArrayList<>();

		for (Product item : searchReq) {
			ProductResponse searchRes = new ProductResponse();
			searchRes.setHsCode(item.getHsCode());
			searchRes.setPlace(item.getPlace());
			searchRes.setName(item.getName());
			searchRes.setDate(item.getDate());
			searchRes.setType(item.getType());
			searchRes.setNumber(item.getNumber());
			searchRes.setPrice(item.getPrice());
			searchRes.setDescription(item.getDescription());
			searchAllRes.add(searchRes);
		}

		return new ProductResponse("搜尋結果如下", searchAllRes);
	}

	// 刪除已上架商品
	@Override
	public ProductResponse removeProduct(ProductRequest productReq) {
		Optional<Product> findProduct = productDao.findById(productReq.getHsCode());
		if (!findProduct.isPresent()) {
			return new ProductResponse("此商品不存在");
		}

		try {
			Product product = findProduct.get();
			productDao.delete(product);
			return new ProductResponse("商品已成功刪除");
		} catch (Exception e) {
			e.printStackTrace();
			return new ProductResponse("刪除商品失敗");
		}

	}
    //購物車針對商品做日期範圍搜尋
	@Override
	public ProductResponse searchPeriod(ProductRequest productReq) {
		String firstPosition = productReq.getFirstDay();
		String secondPosition = productReq.getEndDay();
		List<String> periodList = new ArrayList<>();// 收集經歷的日期
		List<Product> productDateList = new ArrayList<>(); // 已經跑出來的結果
		
		
		 //用LocalDate 直接對應正確的時間
		 String startDateStr = firstPosition;
	        String endDateStr = secondPosition;
	        
	        LocalDate startDate = LocalDate.parse(startDateStr);
	        LocalDate endDate = LocalDate.parse(endDateStr);
	        LocalDate today = LocalDate.now(); //今天時間

	        int firstPositionInt = Integer.parseInt(firstPosition.replaceAll("-", ""));// 20230520
			int secondPositionInt = Integer.parseInt(secondPosition.replaceAll("-", ""));// 20230522
	        
	        if(secondPositionInt<firstPositionInt) {
	        	return new ProductResponse("錯誤!不執行此請求_時間順序不合理");
	        }
	        
	        if (endDate.isAfter(today) ) {
	            // endDate 或 startDate 超过允许的范围，进行相应处理（例如抛出异常、提示用户等）
	        	return new ProductResponse("錯誤!不執行此請求_未來時間_不合理");
	        }
	        
	        if (startDate.isBefore(today.minusDays(30))) {
	            // endDate 或 startDate 超过允许的范围，进行相应处理（例如抛出异常、提示用户等）
	        	return new ProductResponse("錯誤!不執行此請求_起始值超越今日回朔的30天_不合理");
	        }







	        
	        
//	        if (endDate.isAfter(LocalDate.now())) {
//	            // endDate 在今天之后，进行相应处理（例如抛出异常、提示用户等）
//	        	return new ProductResponse("錯誤!不執行此請求_未來時間不合理");
//	        }
//	        
//	        if (!(startDate.isBefore(endDate) && startDate.plusDays(30).isBefore(endDate))) {
//	            return new ProductResponse("錯誤!不執行此請求_查詢生鮮間隔30天不合理");
//	        }//判断检查startDate是否在endDate之前。将startDate加上30天，然后检查结果是否在endDate之內。

	        while (!startDate.isAfter(endDate)) {
	            periodList.add(startDate.toString());
	            startDate = startDate.plusDays(1);
	            
	            
	        }
		 
	        
	       
		

		
//		int period = secondPositionInt - firstPositionInt;
//		int greatMonthPeriod = (31 - firstPositionInt) + secondPositionInt; // 大月期間
//		int smallMonthPeriod = (30 - firstPositionInt) + secondPositionInt; // 大月期間
//		
//		
//		int dayPassThrough = 0;// 記錄初始日期累加到訖的日子有哪些
//		String periodStr;// 轉換的dayPassThrough
//		String wholeDay;// 完整的一天

	

//		// 同一個月的狀況
//		if (firstPosition.substring(5, 7).equals(secondPosition.substring(5, 7))
//				&& secondPositionInt > firstPositionInt) {
//			for (int i = 0; i <= period; i++) {
//				dayPassThrough = firstPositionInt + i;
//				periodStr = String.valueOf(dayPassThrough);
//				String yearPeriod = periodStr.substring(0, 4);// 20230522 的年 2023
//				String monPeriod = periodStr.substring(4, 6);// 20230522 的月 05
//				String dayPeriod = periodStr.substring(6);// 20230522 的日 22
//				wholeDay = yearPeriod + "-" + monPeriod + "-" + dayPeriod;
//				periodList.add(wholeDay);// ["2023-05-23", "2023-05-24"]
//			}
//		}
//
//		// 跨月一個月的狀況
//		// 小月
//		if (!firstPosition.substring(5, 7).equals(secondPosition.substring(5, 7))
//			    && secondPositionInt > firstPositionInt) {
//			    for (int i = 0; i <= smallMonthPeriod; i++) {
//			        if (( firstPosition.substring(5, 7).equals("04")
//			            || firstPosition.substring(5, 7).equals("06") || firstPosition.substring(5, 7).equals("09")
//			            || firstPosition.substring(5, 7).equals("11"))) {
//
//			            if (firstPosition.substring(8).equals("30") || // 只要起始在30天
//			                (String.valueOf(dayPassThrough).indexOf("3")==6 && String.valueOf(dayPassThrough).indexOf("0")==7)) { // 或是運算出現30天
//			                dayPassThrough = firstPositionInt + 100; // 新增一個月
//			                dayPassThrough = dayPassThrough - 30; // 20230530 -> 20230600
//			            }
//
//			            dayPassThrough = firstPositionInt + i;// 20230600->20230601
//			            periodStr = String.valueOf(dayPassThrough);
//			            String yearPeriod = periodStr.substring(0, 4);// 20230601 的年 2023
//			            String monPeriod = periodStr.substring(4, 6);// 20230601 的月 06
//			            String dayPeriod = periodStr.substring(6);// 20230601 的日 01
//			            wholeDay = yearPeriod + "-" + monPeriod + "-" + dayPeriod;
//			            periodList.add(wholeDay);// ["2023-05-31", "2023-06-01"]
//			        }
//			    }
//			}
//		//====================================================================================================
//		// 2月
//		 
//
//		
//		
//		//=======================================================
//		//大月
//		if (!firstPosition.substring(5, 7).equals(secondPosition.substring(5, 7))
//			    && secondPositionInt > firstPositionInt) {
//			    for (int i = 0; i <= greatMonthPeriod; i++) {
//			        if ((firstPosition.substring(5, 7).equals("01") || firstPosition.substring(5, 7).equals("03")
//			            || firstPosition.substring(5, 7).equals("05") || firstPosition.substring(5, 7).equals("07")
//			            || firstPosition.substring(5, 7).equals("08") || firstPosition.substring(5, 7).equals("10")
//			            || firstPosition.substring(5, 7).equals("12"))) {
//
//			            if (firstPosition.substring(8).equals("31") || // 只要起始在31天
//			               (String.valueOf(dayPassThrough).indexOf("3")==6 && String.valueOf(dayPassThrough).indexOf("1")==7)) { // 或是運算出現31天
//			                dayPassThrough = firstPositionInt + 100; // 新增一個月
//			                dayPassThrough = dayPassThrough - 31; // 20230531 -> 20230600
//
//			            }
//
//			            dayPassThrough = firstPositionInt + i;// 20230600->20230601
//			            periodStr = String.valueOf(dayPassThrough);
//			            String yearPeriod = periodStr.substring(0, 4);// 20230601 的年 2023
//			            String monPeriod = periodStr.substring(4, 6);// 20230601 的月 06
//			            String dayPeriod = periodStr.substring(6);// 20230601 的日 01
//			            wholeDay = yearPeriod + "-" + monPeriod + "-" + dayPeriod;
//			            periodList.add(wholeDay);// ["2023-05-31", "2023-06-01"]
//			        }
//			    }
//			}
		
		
		for (String item : periodList) {
			List<Product> dateStartList = productDao.findByDate(item);
			for (Product resultItem : dateStartList) {
				productDateList.add(resultItem);

			}
		}
		if(productDateList.isEmpty()) {
			
			return new ProductResponse("無此範圍資料");
		}

		return new ProductResponse(productDateList,"範圍日期");

	}

}
