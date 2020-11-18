package br.com.agibank.dataanalysis.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Report {

	private int numberOfCustomersInTheInputFile;
	private int salespersonQuantityInInputFile;
	private String mostExpensiveSaleId;
	private String worstSeller;
	private List<Client> clientList;
	private List<Sale> saleList;
	private List<SaleItem> saleItemList;
	private List<Salesman> salesmanList;
	
	public void startCalculation() {
		this.calcMostExpensiveSaleId();
		this.getTotalSellers();
		this.getTotalCustomers();
		this.getTheWorstSeller();
	}
	
	private void getTheWorstSeller() {
		Map<String, BigDecimal> salesmanMap = new HashMap<String, BigDecimal>();
		for(int i = 0; i < this.saleList.size(); i++) {
			for(int j = 0; j < this.saleList.get(i).getSaleItens().size(); j++) {	
				if(salesmanMap.containsKey(this.saleList.get(i).getSalesmanName())) {
					BigDecimal oldValue = salesmanMap.get(this.saleList.get(i).getSalesmanName());
					BigDecimal newValue = this.saleList.get(i).getSaleItens().get(j).getPrice();
					newValue = newValue.add(oldValue);
					salesmanMap.replace(this.saleList.get(i).getSalesmanName(), newValue);
				} else {
					salesmanMap.put(this.saleList.get(i).getSalesmanName(), this.saleList.get(i).getSaleItens().get(j).getPrice());
				}
			}
		}
		BigDecimal smaller = new BigDecimal(999999999);
		for (String key : salesmanMap.keySet()) {
			if(salesmanMap.get(key).compareTo(smaller) == -1) {
				smaller = salesmanMap.get(key);
				worstSeller = key;
			}
		}
	}
	
	private void getTotalSellers() {
		this.salespersonQuantityInInputFile = this.getSalesmanList().size();
	}
	
	private int getTotalCustomers() {
		this.numberOfCustomersInTheInputFile = this.getClientList().size();
		return this.numberOfCustomersInTheInputFile;
	}
	
	private void calcMostExpensiveSaleId() {
		BigDecimal bigger = new BigDecimal(0);
		for(int i = 0; i < this.saleList.size(); i++) {
			BigDecimal addSalePrice = new BigDecimal(0);
			for(int j = 0; j < this.saleList.get(i).getSaleItens().size(); j++) {
				addSalePrice = addSalePrice.add(this.saleList.get(i).getSaleItens().get(j).getPrice());
			}
			if(addSalePrice.compareTo(bigger) == 1) {
				bigger = addSalePrice;
				this.mostExpensiveSaleId = this.saleList.get(i).getSaleId();
			}
		}
	}
	
	public int getNumberOfCustomersInTheInputFile() {
		return numberOfCustomersInTheInputFile;
	}
	public void setNumberOfCustomersInTheInputFile(int numberOfCustomersInTheInputFile) {
		this.numberOfCustomersInTheInputFile = numberOfCustomersInTheInputFile;
	}
	public int getSalespersonQuantityInInputFile() {
		return salespersonQuantityInInputFile;
	}
	public void setSalespersonQuantityInInputFile(int salespersonQuantityInInputFile) {
		this.salespersonQuantityInInputFile = salespersonQuantityInInputFile;
	}
	public String getMostExpensiveSaleId() {
		return mostExpensiveSaleId;
	}
	public void setMostExpensiveSaleId(String mostExpensiveSaleId) {
		this.mostExpensiveSaleId = mostExpensiveSaleId;
	}
	public String getWorstSeller() {
		return worstSeller;
	}
	public void setWorstSeller(String worstSeller) {
		this.worstSeller = worstSeller;
	}

	public List<Client> getClientList() {
		if(Objects.isNull(this.clientList)) {
			this.clientList = new ArrayList<Client>();
		}
		return this.clientList;
	}
	public void setClientList(List<Client> clientList) {
		this.clientList = clientList;
	}
	public List<Sale> getSaleList() {
		if(Objects.isNull(this.saleList)) {
			this.saleList = new ArrayList<Sale>();
		}
		return saleList;
	}
	public void setSaleList(List<Sale> saleList) {
		this.saleList = saleList;
	}
	public List<SaleItem> getSaleItemList() {
		if(Objects.isNull(this.saleItemList)) {
			this.saleItemList = new ArrayList<SaleItem>();
		}
		return this.saleItemList;
	}
	public void setSaleItemList(List<SaleItem> saleItemList) {
		this.saleItemList = saleItemList;
	}
	public List<Salesman> getSalesmanList() {
		if(Objects.isNull(this.salesmanList)) {
			this.salesmanList = new ArrayList<Salesman>();
		}
		return this.salesmanList;
	}
	public void setSalesmanList(List<Salesman> salesmanList) {
		this.salesmanList = salesmanList;
	}
	
	public String toString() {
		String out = "";
		
		return out;
	}
	
}
