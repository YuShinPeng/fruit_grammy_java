package com.example.fruit_grammy_java.impl;

import java.text.SimpleDateFormat;
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

	// 檢查日期合法性私有方法
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
			if (!isValidDate(item.getDate())) {
                return new ProductResponse("日期不存在");
            }
			
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

}
