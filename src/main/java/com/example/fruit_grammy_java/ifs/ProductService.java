package com.example.fruit_grammy_java.ifs;

import com.example.fruit_grammy_java.vo.ProductRequest;
import com.example.fruit_grammy_java.vo.ProductResponse;

public interface ProductService {

	// ��a�W�[�s�ӫ~
	public ProductResponse addProduct(ProductRequest productReq);

	// ��a�ק�W�[�ӫ~
	public ProductResponse updateProduct(ProductRequest productReq);

	// ��a�U�[�ӫ~
	public ProductResponse removeProduct(ProductRequest productReq);

	// ��a�w�W�[�ӫ~ -- �z�L��a�b���j�M
	public ProductResponse searchSellerProduct(String sellerAccount);
	
	// ��a�j�M�W�[�ӫ~ --�S�w�j�M
	public ProductResponse searchSpecificProduct(String name);
	
	// �R�a�j�M �Ͳ��i�� -- �z�L���a�j�M
	public ProductResponse searchPlaceProduct(String place);
 	
}
