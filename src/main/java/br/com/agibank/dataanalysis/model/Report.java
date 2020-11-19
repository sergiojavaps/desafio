package br.com.agibank.dataanalysis.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

public class Report {

	private int numberOfCustomersInTheInputFile;
	private int salespersonQuantityInInputFile;
	private String mostExpensiveSaleId;
	private String worstSeller;
	private List<Client> clientList;
	private List<Sale> saleList;
	private List<Salesman> salesmanList;
	
	public void startCalculation() {
		this.calcMostExpensiveSaleId();
		this.getTotalSellers();
		this.getTotalCustomers();
		this.getTheWorstSeller();
	}
	
	@SuppressWarnings("unchecked")
	private void calcMostExpensiveSaleId() {
		Map<String, BigDecimal> expensiveSaleMap = new HashMap<String, BigDecimal>();
		for(int i = 0; i < this.saleList.size(); i++) {
			for(int j = 0; j < this.saleList.get(i).getSaleItens().size(); j++) {
				
				if(expensiveSaleMap.containsKey(this.saleList.get(i).getSaleId())) {
					BigDecimal oldValue = expensiveSaleMap.get(this.saleList.get(i).getSaleId()); 
					BigDecimal newValue = this.saleList.get(i).getSaleItens().get(j).getTotalSaleValue();
					newValue = newValue.add(oldValue);
					expensiveSaleMap.replace(this.saleList.get(i).getSaleId(), newValue);
				} else {
					BigDecimal totalSaleValue = this.saleList.get(i).getSaleItens().get(j).getTotalSaleValue();
					expensiveSaleMap.put(this.saleList.get(i).getSaleId(), totalSaleValue);
				}
				
			}
		}
		
		@SuppressWarnings("rawtypes")
		SortedMap reversesAndOrders = new TreeMap();
		 for (@SuppressWarnings("rawtypes")
		Iterator iter = expensiveSaleMap.keySet().iterator(); iter.hasNext();) {
			 Object obj = iter.next();
			 reversesAndOrders.put (expensiveSaleMap.get(obj), obj);
		 }
		
		this.mostExpensiveSaleId = reversesAndOrders.get(reversesAndOrders.lastKey()).toString();
		
	}
	
	@SuppressWarnings("unchecked")
	private void getTheWorstSeller() {
		Map<String, BigDecimal> salesmanMap = new HashMap<String, BigDecimal>();
		for(int i = 0; i < this.saleList.size(); i++) {
			for(int j = 0; j < this.saleList.get(i).getSaleItens().size(); j++) {	
				if(salesmanMap.containsKey(this.saleList.get(i).getSalesmanName())) {
					BigDecimal oldValue = salesmanMap.get(this.saleList.get(i).getSalesmanName());
					BigDecimal newValue = this.saleList.get(i).getSaleItens().get(j).getTotalSaleValue();
					newValue = newValue.add(oldValue);
					salesmanMap.replace(this.saleList.get(i).getSalesmanName(), newValue);
				} else {
					BigDecimal totalSaleValue = this.saleList.get(i).getSaleItens().get(j).getTotalSaleValue();;
					salesmanMap.put(this.saleList.get(i).getSalesmanName(), totalSaleValue);
				}
			}
		}
		@SuppressWarnings("rawtypes")
		SortedMap reversesAndOrders = new TreeMap();
		 for (@SuppressWarnings("rawtypes")
		Iterator iter = salesmanMap.keySet().iterator(); iter.hasNext();) {
			 Object obj = iter.next();
			 reversesAndOrders.put (salesmanMap.get(obj), obj);
		 }
		 worstSeller = reversesAndOrders.get(reversesAndOrders.firstKey()).toString(); 
	}
	
	private void getTotalSellers() {
		Map<String, String> salesmanMap = new HashMap<String, String>();
		for(Salesman salesman : salesmanList) {
			if(!salesmanMap.containsKey(salesman.getCpf())) {
				salesmanMap.put(salesman.getCpf(), salesman.getName());
			} 
		}
		this.salespersonQuantityInInputFile = salesmanMap.size();
	}
	
	private void getTotalCustomers() {
		Map<String, String> clientMap = new HashMap<String, String>();
		for(Client client : clientList) {
			if(!clientMap.containsKey(client.getCnpj())) {
				clientMap.put(client.getCnpj(), client.getName());
			}
		}
		this.numberOfCustomersInTheInputFile = clientMap.size();
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
