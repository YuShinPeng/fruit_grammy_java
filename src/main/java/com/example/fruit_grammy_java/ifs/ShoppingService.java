package com.example.fruit_grammy_java.ifs;


import com.example.fruit_grammy_java.vo.ProductRequest;
import com.example.fruit_grammy_java.vo.ShoppingRequest;
import com.example.fruit_grammy_java.vo.ShoppingResponse;

public interface ShoppingService {
	
	
	
	public ShoppingResponse addData(ShoppingRequest req); //�U�ȱN��ƫضi�ʪ���
	public ShoppingResponse modiData(ShoppingRequest req); //�w�i�J�ʪ�����ƭק�
	public ShoppingResponse deleteData(ShoppingRequest req); //�w�i�J�ʪ�����ƧR��
	public ShoppingResponse getShoppingData(ShoppingRequest req); //���X�ʪ������
}
