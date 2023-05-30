package com.example.fruit_grammy_java.impl;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
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


	// 嚙誼查嚙踝蕭嚙踝蕭X嚙糊嚙褊私嚙踝蕭嚙踝蕭k
	private boolean isValidDate(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(date);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	// 嚙踝蕭a嚙磕嚙稼嚙編嚙諉品
	// 嚙磕嚙稼嚙豎塚蕭嚙確

	// 鞈�摰嗡������
	// 銝��憛怨���

	@Override
	public ProductResponse addProduct(ProductRequest productReq) {
//		// 鋆炎�鞈��ist
		List<Product> productList = productReq.getProductList();
//
		for (Product item : productList) {

			if (!StringUtils.hasText(item.getHsCode())) {
				return new ProductResponse("���楊蝣潔��蝛�");
			}

			if (productDao.existsById(item.getHsCode())) {
				return new ProductResponse("甇文��歇銝");
			}

			if (!StringUtils.hasText(item.getSellerAccount())) {
				return new ProductResponse("鞈�摰嗅董����蝛�");
			}

			if (!StringUtils.hasText(item.getName())) {
				return new ProductResponse("����迂銝�蝛�");
			}

			if (!StringUtils.hasText(item.getType())) {
				return new ProductResponse("蝔桅���蝛�");
			}

			if (!StringUtils.hasText(item.getPlace())) {
				return new ProductResponse("��銝�蝛�");
			}

			if (item.getNumber() <= 0) {
				return new ProductResponse("���������0");
			}

			if (!StringUtils.hasText(item.getDescription())) {
				return new ProductResponse("����酉隤芣���蝛�");
			}

			if (!StringUtils.hasText(item.getDate())) {
				return new ProductResponse("�������蝛�");
			}

			// 瑼Ｘ����蝚血�撘�������
			String date = item.getDate();
			String regex = "\\d{4}-\\d{2}-\\d{2}";
			if (!date.matches(regex)) {
				return new ProductResponse("���撘隤�");
			}


			// 嚙誼查嚙踝蕭嚙踝蕭嚙踝蕭O嚙稻嚙碼嚙糊嚙踝蕭 ex. 4044-04-04 嚙踝蕭嚙箠嚙踝蕭
			if (!isValidDate(item.getDate())) {
                return new ProductResponse("嚙踝蕭嚙踝蕭嚙踝蕭X嚙糊");
            }
	
		// 檢查日期是否為未來日期
			LocalDate currentDate = LocalDate.now();
			LocalDate parsedLocalDate = LocalDate.parse(date);
			if (parsedLocalDate.isAfter(currentDate)) {
				return new ProductResponse("不得為未來日期");
			}
			
		}
		// �憓���鞈�����
		productDao.saveAll(productList);
		return new ProductResponse(productList, "�憓���");
	}

	// 鞈�摰嗡耨�銝����
	@Override
	public ProductResponse updateProduct(ProductRequest productReq) {
		Optional<Product> findProduct = productDao.findById(productReq.getHsCode());
		if (!findProduct.isPresent()) {
			return new ProductResponse("甇文����");
		}

		// 摮���迨������
		Product getProduct = findProduct.get();

		// 靽格����迂
		if (StringUtils.hasText(productReq.getName())) {
			getProduct.setName(productReq.getName());
			productDao.save(getProduct);
		}

		// 靽格蝔桅��
		if (StringUtils.hasText(productReq.getType())) {
			getProduct.setType(productReq.getType());
			productDao.save(getProduct);
		}

		// 靽格��
		if (StringUtils.hasText(productReq.getPlace())) {
			getProduct.setPlace(productReq.getPlace());
			productDao.save(getProduct);
		}

		// 靽格����
		if (productReq.getNumber() > 0) {
			getProduct.setNumber(productReq.getNumber());
			productDao.save(getProduct);
		}

		// 靽格����
		if (StringUtils.hasText(productReq.getDate())) {
			getProduct.setDate(productReq.getDate());
			// 瑼Ｘ����蝚血�撘�
			String date = productReq.getDate();
			String regex = "\\d{4}-\\d{2}-\\d{2}";
			if (!date.matches(regex)) {
				return new ProductResponse("���撘隤�");
			}

			// 檢查此日期是否合法性 ex. 4044-04-04 不可能
			if (!isValidDate(productReq.getDate())) {
				return new ProductResponse("日期不存在");
			}

			// 檢查日期是否為未來日期
			LocalDate currentDate = LocalDate.now();
			LocalDate parsedLocalDate = LocalDate.parse(date);
			if (parsedLocalDate.isAfter(currentDate)) {
				return new ProductResponse("不得為未來日期");
			}

			productDao.save(getProduct);
		}

		// 靽格��
		if (productReq.getPrice() > 0) {
			getProduct.setPrice(productReq.getPrice());
			productDao.save(getProduct);
		}

		// 靽格��膩
		if (StringUtils.hasText(productReq.getDescription())) {
			getProduct.setDescription(productReq.getDescription());
			productDao.save(getProduct);
		}

		return new ProductResponse("靽格����", getProduct);
	}

	// 鞎瑕振���� ��撅交風 -- �������
	@Override
	public ProductResponse searchPlaceProduct(String place) {

		List<Product> searchReq = productDao.findByPlace(place);

		List<ProductResponse> searchAllRes = new ArrayList<>();

		if (!StringUtils.hasText(place)) {
			return new ProductResponse("���摰嫣��蝛�");
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
	public ProductResponse searchSpecificProduct(String name) { // 銝�����

		List<Product> searchAllRes = new ArrayList<>();

		if (!StringUtils.hasText(name)) {// 憒�null嚗�����鞈��

			List<Product> allSearchReq = productDao.findAll();
			return new ProductResponse(allSearchReq, "all info");
		}

		List<Product> searchReq = productDao.findByNameContaining(name);

		for (Product item : searchReq) {

			searchAllRes.add(item);
		}
		return new ProductResponse(searchAllRes, "specific info");
	}

	// 鞈�摰嗅歇銝����
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


//		return new ProductResponse("��������", searchAllRes);
		return new ProductResponse(searchAllRes);
	}

	// ��撌脖�����
	@Override
	public ProductResponse removeProduct(ProductRequest productReq) {
		Optional<Product> findProduct = productDao.findById(productReq.getHsCode());
		if (!findProduct.isPresent()) {
			return new ProductResponse("甇文����");
		}

		try {
			Product product = findProduct.get();
			productDao.delete(product);
			return new ProductResponse("���歇����");
		} catch (Exception e) {
			e.printStackTrace();
			return new ProductResponse("�����仃���");
		}

	}
    //鞈潛頠������������
	@Override
	public ProductResponse searchPeriod(ProductRequest productReq) {
		String firstPosition = productReq.getFirstDay();
		String secondPosition = productReq.getEndDay();
		List<String> periodList = new ArrayList<>();// ����風�����
		
		
		
		 //�LocalDate ��撠�迤蝣箇����
		 String startDateStr = firstPosition;
	        String endDateStr = secondPosition;
	        
	        LocalDate startDate = LocalDate.parse(startDateStr);
	        LocalDate endDate = LocalDate.parse(endDateStr);
	        LocalDate today = LocalDate.now(); //隞予����

	        int firstPositionInt = Integer.parseInt(firstPosition.replaceAll("-", ""));// 20230520
			int secondPositionInt = Integer.parseInt(secondPosition.replaceAll("-", ""));// 20230522
	        
	        if(secondPositionInt<firstPositionInt) {
	        	return new ProductResponse("�隤�!銝銵迨隢����������");
	        }
	        
	        if (endDate.isAfter(today) ) {
	            // endDate ��� startDate 頞��捂���嚗��摨������撘虜��內��蝑��
	        	return new ProductResponse("�隤�!銝銵迨隢��靘��銝���");
	        }
	        
	        if (startDate.isBefore(today.minusDays(30))) {
	            // endDate ��� startDate 頞��捂���嚗��摨������撘虜��內��蝑��
	        	return new ProductResponse("�隤�!銝銵迨隢�韏瑕��潸��������30憭奇銝���");
	        }







	        
	        
//	        if (endDate.isAfter(LocalDate.now())) {
//	            // endDate �隞予銋����摨������撘虜��內��蝑��
//	        	return new ProductResponse("�隤�!銝銵迨隢��靘������");
//	        }
//	        
//	        if (!(startDate.isBefore(endDate) && startDate.plusDays(30).isBefore(endDate))) {
//	            return new ProductResponse("�隤�!銝銵迨隢��閰Ｙ�悅����30憭拐����");
//	        }//��璉��startDate���endDate銋���tartDate����30憭抬�����蝏���endDate銋��

	        while (!startDate.isAfter(endDate)) {
	            periodList.add(startDate.toString());
	            startDate = startDate.plusDays(1);
	            
	            
	        }
		 
	        
	       
		

		
//		int period = secondPositionInt - firstPositionInt;
//		int greatMonthPeriod = (31 - firstPositionInt) + secondPositionInt; // 憭扳����
//		int smallMonthPeriod = (30 - firstPositionInt) + secondPositionInt; // 憭扳����
//		
//		
//		int dayPassThrough = 0;// 閮�����敞��閮�摮�鈭�
//		String periodStr;// 頧��ayPassThrough
//		String wholeDay;// 摰���憭�

	

//		// �������瘜�
//		if (firstPosition.substring(5, 7).equals(secondPosition.substring(5, 7))
//				&& secondPositionInt > firstPositionInt) {
//			for (int i = 0; i <= period; i++) {
//				dayPassThrough = firstPositionInt + i;
//				periodStr = String.valueOf(dayPassThrough);
//				String yearPeriod = periodStr.substring(0, 4);// 20230522 ��僑 2023
//				String monPeriod = periodStr.substring(4, 6);// 20230522 ���� 05
//				String dayPeriod = periodStr.substring(6);// 20230522 �� 22
//				wholeDay = yearPeriod + "-" + monPeriod + "-" + dayPeriod;
//				periodList.add(wholeDay);// ["2023-05-23", "2023-05-24"]
//			}
//		}
//
//		// 頝冽������瘜�
//		// 撠��
//		if (!firstPosition.substring(5, 7).equals(secondPosition.substring(5, 7))
//			    && secondPositionInt > firstPositionInt) {
//			    for (int i = 0; i <= smallMonthPeriod; i++) {
//			        if (( firstPosition.substring(5, 7).equals("04")
//			            || firstPosition.substring(5, 7).equals("06") || firstPosition.substring(5, 7).equals("09")
//			            || firstPosition.substring(5, 7).equals("11"))) {
//
//			            if (firstPosition.substring(8).equals("30") || // �閬絲憪30憭�
//			                (String.valueOf(dayPassThrough).indexOf("3")==6 && String.valueOf(dayPassThrough).indexOf("0")==7)) { // ������30憭�
//			                dayPassThrough = firstPositionInt + 100; // �憓����
//			                dayPassThrough = dayPassThrough - 30; // 20230530 -> 20230600
//			            }
//
//			            dayPassThrough = firstPositionInt + i;// 20230600->20230601
//			            periodStr = String.valueOf(dayPassThrough);
//			            String yearPeriod = periodStr.substring(0, 4);// 20230601 ��僑 2023
//			            String monPeriod = periodStr.substring(4, 6);// 20230601 ���� 06
//			            String dayPeriod = periodStr.substring(6);// 20230601 �� 01
//			            wholeDay = yearPeriod + "-" + monPeriod + "-" + dayPeriod;
//			            periodList.add(wholeDay);// ["2023-05-31", "2023-06-01"]
//			        }
//			    }
//			}
//		//====================================================================================================
//		// 2���
//		 
//
//		
//		
//		//=======================================================
//		//憭扳��
//		if (!firstPosition.substring(5, 7).equals(secondPosition.substring(5, 7))
//			    && secondPositionInt > firstPositionInt) {
//			    for (int i = 0; i <= greatMonthPeriod; i++) {
//			        if ((firstPosition.substring(5, 7).equals("01") || firstPosition.substring(5, 7).equals("03")
//			            || firstPosition.substring(5, 7).equals("05") || firstPosition.substring(5, 7).equals("07")
//			            || firstPosition.substring(5, 7).equals("08") || firstPosition.substring(5, 7).equals("10")
//			            || firstPosition.substring(5, 7).equals("12"))) {
//
//			            if (firstPosition.substring(8).equals("31") || // �閬絲憪31憭�
//			               (String.valueOf(dayPassThrough).indexOf("3")==6 && String.valueOf(dayPassThrough).indexOf("1")==7)) { // ������31憭�
//			                dayPassThrough = firstPositionInt + 100; // �憓����
//			                dayPassThrough = dayPassThrough - 31; // 20230531 -> 20230600
//
//			            }
//
//			            dayPassThrough = firstPositionInt + i;// 20230600->20230601
//			            periodStr = String.valueOf(dayPassThrough);
//			            String yearPeriod = periodStr.substring(0, 4);// 20230601 ��僑 2023
//			            String monPeriod = periodStr.substring(4, 6);// 20230601 ���� 06
//			            String dayPeriod = periodStr.substring(6);// 20230601 �� 01
//			            wholeDay = yearPeriod + "-" + monPeriod + "-" + dayPeriod;
//			            periodList.add(wholeDay);// ["2023-05-31", "2023-06-01"]
//			        }
//			    }
//			}
	        
	    List<Product> productDateList = new ArrayList<>(); // 撌脩��靘����
		for (String item : periodList) {
			List<Product> dateStartList = productDao.findByDate(item);
			for (Product resultItem : dateStartList) {
				Product preInfo= new Product();//�蝑���
				preInfo.setHsCode(resultItem.getHsCode());
				preInfo.setName(resultItem.getName());
				preInfo.setDate(resultItem.getDate());
				preInfo.setDescription(resultItem.getDescription());
				preInfo.setNumber(resultItem.getNumber());
				preInfo.setPlace(resultItem.getPlace());
				preInfo.setPrice(resultItem.getPrice());
				preInfo.setSellerAccount(resultItem.getSellerAccount());
				preInfo.setType(resultItem.getType());
				
				productDateList.add(preInfo); //�憓憭��”

			}
		}
		if(productDateList.isEmpty()) {
			
			return new ProductResponse("�甇斤�����");
		}

		return new ProductResponse(productDateList,"蝭����");

	}

}
