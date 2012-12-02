package com.crystars.item;

import java.sql.Timestamp;

public class item{
	private int	goods_num;
	private int	gsales_membernum;
	private String image;
	private int price;
	private Timestamp regist_date; //rs.getTimestamp(...);
	private int category_num;
	private String book_name;
	private String content;
	private String publisher;
	private int quantity;
	private int quality;
	
	public item(){}; // 생성자
	
	public item(int goods_num, int gsales_membernum, String image, int price, Timestamp regist_date, int category_num, String book_name,
			String content, String publisher, int quantity, int quality){
		super();
		this.goods_num = goods_num;
		this.gsales_membernum = gsales_membernum;
		this.image = image;
		this.price = price;
		this.regist_date = regist_date;
		this.category_num = category_num;
		this.book_name = book_name;
		this.content = content;
		this.publisher = publisher;
		this.quantity = quantity;
		this.quality = quality;
	}

	public int getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(int goods_num) {
		this.goods_num = goods_num;
	}

	public int getGsales_membernum() {
		return gsales_membernum;
	}

	public void setGsales_membernum(int gsales_membernum) {
		this.gsales_membernum = gsales_membernum;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Timestamp getRegist_date() {
		return regist_date;
	}

	public void setRegist_date(Timestamp regist_date) {
		this.regist_date = regist_date;
	}

	public int getCategory_num() {
		return category_num;
	}

	public void setCategory_num(int category_num) {
		this.category_num = category_num;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public String getContent() {
		return content;
	}

	public void setContents(String content) {
		this.content = content;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}
	
	
	
}
