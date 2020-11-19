package br.com.agibank.dataanalysis.model;

import java.math.BigDecimal;

public class SaleItem {

	private String id;
	private int quantity;
	private BigDecimal price;
	private BigDecimal totalSaleValue;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getTotalSaleValue() {
		return totalSaleValue;
	}
	public void setTotalSaleValue(BigDecimal totalSaleValue) {
		this.totalSaleValue = totalSaleValue;
	}
	
}
