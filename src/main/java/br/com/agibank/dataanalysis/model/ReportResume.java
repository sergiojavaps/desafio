package br.com.agibank.dataanalysis.model;

import java.io.Serializable;
import java.util.Objects;

public class ReportResume implements Serializable {

	private static final long serialVersionUID = 1L;
	private int numberOfCustomers;
	private int numberOfSellers;
	private String mostExpensiveSaleId;
	private String worstSeller;
	
	public static ReportResume to(Report report) {
		ReportResume reportResume = new ReportResume();
		if(Objects.isNull(report)) {
			report = new Report();
		}
		reportResume.setNumberOfCustomers(report.getNumberOfCustomersInTheInputFile());
		reportResume.setNumberOfSellers(report.getSalespersonQuantityInInputFile());
		reportResume.setMostExpensiveSaleId(report.getMostExpensiveSaleId());
		reportResume.setWorstSeller(report.getWorstSeller());
		return reportResume;
	}
	
	public int getNumberOfCustomers() {
		return numberOfCustomers;
	}
	public void setNumberOfCustomers(int numberOfCustomers) {
		this.numberOfCustomers = numberOfCustomers;
	}
	public int getNumberOfSellers() {
		return numberOfSellers;
	}
	public void setNumberOfSellers(int numberOfSellers) {
		this.numberOfSellers = numberOfSellers;
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
	
}
