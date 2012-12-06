package com.crystars.evaluation;

import java.sql.Timestamp;

public class evaluation{
	private int post_num;
	private int esales_memnum;
	private int epurchase_memnum;
	private String evaluation_post;
	private double evaluation_rating;
	//private int trade_num;
	private String name;
	private Timestamp date;
	
	
	public evaluation(){}; // 생성자
	
	public evaluation(int post_num, int esales_memnum, int epurchase_memnum, 
			String evaluation_post, double evaluation_rating, String name, Timestamp date) {
		super();
		this.post_num = post_num;
		this.esales_memnum = esales_memnum;
		this.epurchase_memnum = epurchase_memnum;
		this.evaluation_post = evaluation_post;
		this.evaluation_rating = evaluation_rating;
		//this.trade_num = trade_num;
		this.name = name;
		this.date = date;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
/*
	public int getTrade_num() {
		return trade_num;
	}

	public void setTrade_num(int trade_num) {
		this.trade_num = trade_num;
	}
*/
	public int getPost_num() {
		return post_num;
	}

	public void setPost_num(int post_num) {
		this.post_num = post_num;
	}

	public int getEsales_memnum() {
		return esales_memnum;
	}

	public void setEsales_memnum(int esales_memnum) {
		this.esales_memnum = esales_memnum;
	}

	public int getEpurchase_memnum() {
		return epurchase_memnum;
	}

	public void setEpurchase_memnum(int epurchase_memnum) {
		this.epurchase_memnum = epurchase_memnum;
	}

	public String getEvaluation_post() {
		return evaluation_post;
	}

	public void setEvaluation_post(String evaluation_post) {
		this.evaluation_post =evaluation_post;
	}

	public double getEvaluation_rating() {
		return evaluation_rating;
	}

	public void setEvaluation_rating(double evaluation_rating) {
		this.evaluation_rating = evaluation_rating;
	}

	
}
