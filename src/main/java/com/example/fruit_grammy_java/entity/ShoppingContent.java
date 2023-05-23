package com.example.fruit_grammy_java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name ="shopppingcontent")
public class ShoppingContent {


	
		
		@Id
		@Column(name = "buyer_shopping_number")
		private String shoppingNumber;
		@Column(name = "item_id")
		private String itemId;
		@Column(name = "item_name")
		private String itemName;
		@Column(name = "sell_account")
		private String sellAccount;
		@Column(name = "per_price")
		private int itemPrice;
		@Column(name = "item_num")
		private int itemNum;
		@Column(name = "discription")
		private String discription;
		@Column(name = "stock")
		private int stock;

		
		public ShoppingContent() {
			super();
			// TODO Auto-generated constructor stub
		}


		public String getShoppingNumber() {
			return shoppingNumber;
		}


		public void setShoppingNumber(String shoppingNumber) {
			this.shoppingNumber = shoppingNumber;
		}


		public String getItemId() {
			return itemId;
		}


		public void setItemId(String itemId) {
			this.itemId = itemId;
		}


		public String getItemName() {
			return itemName;
		}


		public void setItemName(String itemName) {
			this.itemName = itemName;
		}


		public String getSellAccount() {
			return sellAccount;
		}


		public void setSellAccount(String sellAccount) {
			this.sellAccount = sellAccount;
		}


		public int getItemPrice() {
			return itemPrice;
		}


		public void setItemPrice(int itemPrice) {
			this.itemPrice = itemPrice;
		}


		public int getItemNum() {
			return itemNum;
		}


		public void setItemNum(int itemNum) {
			this.itemNum = itemNum;
		}


		public String getDiscription() {
			return discription;
		}


		public void setDiscription(String discription) {
			this.discription = discription;
		}


		public int getStock() {
			return stock;
		}


		public void setStock(int stock) {
			this.stock = stock;
		}
		
			

}
