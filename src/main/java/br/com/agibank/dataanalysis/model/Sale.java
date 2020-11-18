package br.com.agibank.dataanalysis.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sale {

	private String id;
	private String saleId;
	private String salesmanName;
	private List<SaleItem> saleItens;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSaleId() {
		return saleId;
	}
	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}
	public String getSalesmanName() {
		return salesmanName;
	}
	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}
	public List<SaleItem> getSaleItens() {
		if(Objects.isNull(this.saleItens)) {
			this.saleItens = new ArrayList<SaleItem>();
		}
		return saleItens;
	}
	public void setSaleItens(List<SaleItem> saleItens) {
		this.saleItens = saleItens;
	}
	
}
