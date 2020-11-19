package br.com.agibank.dataanalysis.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import br.com.agibank.dataanalysis.model.Client;
import br.com.agibank.dataanalysis.model.Report;
import br.com.agibank.dataanalysis.model.Salesman;

/**
 * 
 * class responsible for obtaining the result of the analyzed data
 * 
 * @author sergio.melo
 *
 */
@Service
public class CalculationService {

	public void executeCalculation(Report report) {
		getMostExpensiveSaleId(report);
		getTotalSellers(report);
		getTotalCustomers(report);
		getTheWorstSeller(report);
	}
	
	/**
	 * 
	 * Get the most expensive sale id
	 * 
	 * @author sergio.melo
	 * 
	 * @param report
	 */
	@SuppressWarnings("unchecked")
	private void getMostExpensiveSaleId(Report report) {
		Map<String, BigDecimal> expensiveSaleMap = new HashMap<String, BigDecimal>();
		for(int i = 0; i < report.getSaleList().size(); i++) {
			for(int j = 0; j < report.getSaleList().get(i).getSaleItens().size(); j++) {
				
				if(expensiveSaleMap.containsKey(report.getSaleList().get(i).getSaleId())) {
					BigDecimal oldValue = expensiveSaleMap.get(report.getSaleList().get(i).getSaleId()); 
					BigDecimal newValue = report.getSaleList().get(i).getSaleItens().get(j).getTotalSaleValue();
					newValue = newValue.add(oldValue);
					expensiveSaleMap.replace(report.getSaleList().get(i).getSaleId(), newValue);
				} else {
					BigDecimal totalSaleValue = report.getSaleList().get(i).getSaleItens().get(j).getTotalSaleValue();
					expensiveSaleMap.put(report.getSaleList().get(i).getSaleId(), totalSaleValue);
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
		report.setMostExpensiveSaleId(reversesAndOrders.get(reversesAndOrders.lastKey()).toString());
	}
	
	/**
	 * 
	 * Get the worst seller
	 * 
	 * @author sergio.melo
	 * 
	 * @param report
	 */
	@SuppressWarnings("unchecked")
	private void getTheWorstSeller(Report report) {
		Map<String, BigDecimal> salesmanMap = new HashMap<String, BigDecimal>();
		for(int i = 0; i < report.getSaleList().size(); i++) {
			for(int j = 0; j < report.getSaleList().get(i).getSaleItens().size(); j++) {	
				if(salesmanMap.containsKey(report.getSaleList().get(i).getSalesmanName())) {
					BigDecimal oldValue = salesmanMap.get(report.getSaleList().get(i).getSalesmanName());
					BigDecimal newValue = report.getSaleList().get(i).getSaleItens().get(j).getTotalSaleValue();
					newValue = newValue.add(oldValue);
					salesmanMap.replace(report.getSaleList().get(i).getSalesmanName(), newValue);
				} else {
					BigDecimal totalSaleValue = report.getSaleList().get(i).getSaleItens().get(j).getTotalSaleValue();;
					salesmanMap.put(report.getSaleList().get(i).getSalesmanName(), totalSaleValue);
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
		 report.setWorstSeller(reversesAndOrders.get(reversesAndOrders.firstKey()).toString()); 
	}
	
	/**
	 * 
	 * Get total sellers
	 * 
	 * @author sergio
	 * 
	 * @param report
	 */
	private void getTotalSellers(Report report) {
		Map<String, String> salesmanMap = new HashMap<String, String>();
		for(Salesman salesman : report.getSalesmanList()) {
			if(!salesmanMap.containsKey(salesman.getCpf())) {
				salesmanMap.put(salesman.getCpf(), salesman.getName());
			} 
		}
		report.setSalespersonQuantityInInputFile(salesmanMap.size()); 
	}
	
	/**
	 * 
	 * Get the total customers
	 * 
	 * @author sergio.melo
	 * 
	 * @param report
	 */
	private void getTotalCustomers(Report report) {
		Map<String, String> clientMap = new HashMap<String, String>();
		for(Client client : report.getClientList()) {
			if(!clientMap.containsKey(client.getCnpj())) {
				clientMap.put(client.getCnpj(), client.getName());
			}
		}
		report.setNumberOfCustomersInTheInputFile(clientMap.size());
	}
	
}
