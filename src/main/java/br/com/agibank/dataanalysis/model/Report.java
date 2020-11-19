package br.com.agibank.dataanalysis.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Report {

	private int numberOfCustomersInTheInputFile;
	private int salespersonQuantityInInputFile;
	private String mostExpensiveSaleId;
	private String worstSeller;
	private List<Client> clientList;
	private List<Sale> saleList;
	private List<Salesman> salesmanList;
	
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
	
	@Deprecated
	public String toString(Report report) {
		String out = "";
		@SuppressWarnings("unused")
		StringBuilder entity = new StringBuilder();
		return out;
	}
	
}
