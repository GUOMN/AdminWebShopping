package com.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {
	private int orderID = 0, userID = 0, goodsID = 0, InvoiceID = 0;
	private int deliver_address = 0;
	private String pay_method = null;
	private double total_price = 0;
	private String time = null;
	private String status = null;
	private String remark = null;

	public Order() {
		super();
	}

	public Order(int userID, int invoiceID, int deliver_address,
			String pay_method, double total_price) {
		super();
		this.userID = userID;
		InvoiceID = invoiceID;
		this.deliver_address = deliver_address;
		this.pay_method = pay_method;
		this.total_price = total_price;
		// this.time = time;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(int goodsID) {
		this.goodsID = goodsID;
	}

	public int getInvoiceID() {
		return InvoiceID;
	}

	public void setInvoiceID(int invoiceID) {
		InvoiceID = invoiceID;
	}

	public int getDeliver_address() {
		return deliver_address;
	}

	public void setDeliver_address(int deliver_address0) {
		this.deliver_address = deliver_address0;
	}

	public String getPay_method() {
		return pay_method;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}

	public double getTotal_price() {
		return total_price;
	}

	public void setTotal_price(double total_price) {
		this.total_price = total_price;
		// GoodsDAO goodsDAO=new GoodsDAO();
		// double total= goodsDAO.queryGoods(goodsID).getPriceCommon();
		// if(total==total_price){
		// this.total_price = total_price;
		// }else{
		// throw new ELException("价格不符，提交订单未成功！");
		// }

	}

	public String getTime() {
		return time;
	}

	public void setTime(Date time) {
		// this.time = time;
		this.time = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(time);
	}

}
