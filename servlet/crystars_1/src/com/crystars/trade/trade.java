package com.crystars.trade;


import java.sql.Timestamp;

public class trade{
	private int trade_num;
	private int tsales_memnum;
	private int tpurchase_memnum;
	private Timestamp trade_date;
	private int trade_price;
	private boolean trade_status;
	private int trade_itemnum;
	private String trade_itemtitle;
	


	

	public trade(){}; // 생성자
	
	
	
	public trade(int trade_num, int tsales_memnum, int tpurchase_memnum, 
			Timestamp trade_date, int trade_price, boolean trade_status, String trade_itemtitle, int trade_itemnum) {
		super();
		this.trade_num = trade_num;
		this.tsales_memnum = tsales_memnum;
		this.tpurchase_memnum = tpurchase_memnum;
		this.trade_date = trade_date;
		this.trade_price = trade_price;
		this.trade_status = trade_status; // 0= 거래완료, 1=  판매중
		this.trade_itemtitle = trade_itemtitle;
		this.trade_itemnum = trade_itemnum;
	}

	public void setTrade_num(int trade_num) {
		this.trade_num = trade_num;
	}



	public String getTrade_itemtitle() {
		return trade_itemtitle;
	}



	public void setTrade_itemtitle(String trade_itemtitle) {
		this.trade_itemtitle = trade_itemtitle;
	}



	public int getTrade_num() {
		return trade_num;
	}
	
	public trade(int GSales_MemberNum, int price){
		this.tsales_memnum =GSales_MemberNum;
		this.trade_price = price;
	}
	public int getTrade_itemnum() {
		return trade_itemnum;
	}

	public void setTrade_itemnum(int trade_itemnum) {
		this.trade_itemnum = trade_itemnum;
	}

	public int getTsales_memnum() {
		return tsales_memnum;
	}

	public void setTsales_memnum(int tsales_memnum) {
		this.tsales_memnum = tsales_memnum;
	}

	public int getTpurchase_memnum() {
		return tpurchase_memnum;
	}

	public void setTpurchase_memnum(int tpurchase_memnum) {
		this.tpurchase_memnum = tpurchase_memnum;
	}

	public Timestamp getTrade_date() {
		return trade_date;
	}

	public void setTrade_date(Timestamp trade_date) {
		this.trade_date = trade_date;
	}

	public int getTrade_price() {
		return trade_price;
	}

	public void setTrade_price(int trade_price) {
		this.trade_price = trade_price;
	}

	public boolean getTrade_status() {
		return trade_status;
	}

	public void setTrade_status(boolean trade_status) {
		this.trade_status = trade_status;
	}


	
	
	
}
