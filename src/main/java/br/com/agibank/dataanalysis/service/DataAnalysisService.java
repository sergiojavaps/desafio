package br.com.agibank.dataanalysis.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.agibank.dataanalysis.constants.AppConstants;
import br.com.agibank.dataanalysis.exception.CalcException;
import br.com.agibank.dataanalysis.exception.CreateOutputFileException;
import br.com.agibank.dataanalysis.exception.FileInvalidException;
import br.com.agibank.dataanalysis.exception.GetInputFilesException;
import br.com.agibank.dataanalysis.exception.ReadingInputFileException;
import br.com.agibank.dataanalysis.exception.SumaryAnalysisException;
import br.com.agibank.dataanalysis.model.Client;
import br.com.agibank.dataanalysis.model.Report;
import br.com.agibank.dataanalysis.model.ReportResume;
import br.com.agibank.dataanalysis.model.Sale;
import br.com.agibank.dataanalysis.model.SaleItem;
import br.com.agibank.dataanalysis.model.Salesman;

/**
 * 
 * class responsible for building the data analysis
 * 
 * @author sergio.melo
 *
 */
@Service
public class DataAnalysisService {
	
	private static Logger logger = LogManager.getLogger(DataAnalysisService.class);
	@Autowired
	private UploadingService uploadingService;
	@Autowired
	private CalculationService calculationService;
	
	@Async
	public void execute() throws GetInputFilesException, FileInvalidException, FileNotFoundException, 
								CreateOutputFileException, IOException, CalcException  {
		List<File> filesIn = getAllInputFiles();
		if(Objects.isNull(filesIn) || filesIn.isEmpty()) {
			Report report = null;
			createOutputFile(report);
		} else {
			createOutputFile(filesIn);
		}
	}
	
	/**
	 * 
	 * Get the summary of the data analysis
	 * 
	 * @author sergio.melo
	 * 
	 * @return
	 * @throws SumaryAnalysisException
	 * @throws IOException
	 */
	public ReportResume getSummaryOfAnalysis() throws SumaryAnalysisException, IOException {
		ReportResume reportResume = null;
		File file = new File(AppConstants.READING_DIR + AppConstants.OUTPUT_FILE_NAME);
		if(file.exists()) {
			reportResume = new ReportResume();
			@SuppressWarnings("resource")
			BufferedReader in = new BufferedReader(
	                new FileReader(file)
	            );
			String linha = null;
			while((linha = in.readLine()) != null) {
			      StringTokenizer st = new StringTokenizer(linha, AppConstants.SEPARATOR);
			      if(!st.hasMoreTokens()) {
			        break;
			      }
			      reportResume.setNumberOfCustomers(Integer.parseInt(st.nextToken()));
			      reportResume.setNumberOfSellers(Integer.parseInt(st.nextToken()));
			      reportResume.setMostExpensiveSaleId(st.nextToken());
			      reportResume.setWorstSeller(st.nextToken());
			}
			return reportResume;
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * creates the output file for the report
	 * 
	 * @author sergio.melo
	 * 
	 * @param files
	 * @throws ReadingInputFileException
	 * @throws IOException
	 * @throws CreateOutputFileException 
	 * @throws CalcException 
	 */
	private void createOutputFile(List<File> files) {
		Report report = new Report();
		List<File> brokenFiles = new ArrayList<File>();
		List<File> validFiles = new ArrayList<File>();
		for(File file : files) {
			try {
				readingInputFile(file, report);
				sendFileToProcessedDirectory(file);
				validFiles.add(file);
			} catch (ReadingInputFileException | IOException | NumberFormatException e) {
				logger.error("the input file could not be processed. The file " + file.getName() + " will be copied to the directory "
						+ "of files with processing failures. Cause: " + e);
				brokenFiles.add(file);
			}
		}
		try {
			if(!brokenFiles.isEmpty()) {
				for(File file : brokenFiles) {
					sendFileToProcessingFailureDirectory(file);
				}
			}
			if(validFiles.isEmpty()) {
				report = null;
			} 
			createOutputFile(report);
		} catch (CreateOutputFileException | IOException | CalcException e) {
			logger.error("it was not possible to create the output file. Cause: " + e);
		}
	}
	
	/**
	 * 
	 * Sends the processed files to the respective directory
	 * 
	 * @author sergio.melo
	 * 
	 * @param file
	 * @throws IOException
	 */
	private void sendFileToProcessedDirectory(File file) throws IOException {
		File originProcessed = new File(file.getAbsolutePath());
		File destinyProcessed = new File(AppConstants.PROCESSED_FILES + file.getName());
		copy(originProcessed, destinyProcessed);
	}
	
	public void sendFileToProcessingFailureDirectory(File file) throws IOException {
		File origin = new File(file.getAbsolutePath());
		File destiny = new File(AppConstants.PROCESSING_FAILURE_DIR + file.getName());
		copy(origin, destiny);
		//origin.delete();
	}
	
	/**
	 * 
	 * Sends the processed files to the respective directory
	 * 
	 * @author sergio.melo
	 * 
	 * @param src
	 * @param dst
	 * @throws IOException
	 */
	private void copy(File src, File dst) throws IOException {
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dst);
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	} 
	
	/**
	 * 
	 * pick up files from the incoming directory
	 * 
	 * @author sergio.melo
	 * 
	 * @return
	 * @throws GetInputFilesException
	 * @throws FileInvalidException 
	 */
	private List<File> getAllInputFiles() throws GetInputFilesException, FileInvalidException {
		File file = new File(AppConstants.UPLOADING_DIR);
		File[] files = file.listFiles();
		List<File> fileList = new ArrayList<File>();
		for(int i = 0; i < files.length; i++) {
			logger.info("::input file: " + files[i].getName());
			if(uploadingService.isValideFileExtension(files[i].getName())) {
				fileList.add(files[i]);
			}
		}
		return fileList;
	}
	
	/**
	 * 
	 * reading input files
	 * 
	 * @author sergio.melo
	 * 
	 * @param file
	 * @param report
	 * @return
	 * @throws ReadingInputFileException
	 * @throws IOException
	 */
	private void readingInputFile(File file, Report report) throws ReadingInputFileException, IOException, NumberFormatException {
		boolean isValidDatFile = false;
		Salesman salesman = null;
		Client client = null;
		Sale sale = null;
		Report reportTemp = new Report();
		@SuppressWarnings("resource")
		BufferedReader in = new BufferedReader(
                new FileReader(file)
            );
		String linha = null;
		while((linha = in.readLine()) != null) {
	      StringTokenizer st = new StringTokenizer(linha, AppConstants.SEPARATOR);
	      if(!st.hasMoreTokens()) {
	        break;
	      }
	      String label = st.nextToken();
	      if(label.equals(AppConstants.SALESMAN_ID)) {
	    	  salesman = createSalesmanObject(st); 
	    	  reportTemp.getSalesmanList().add(salesman);
	    	  isValidDatFile = true;
	      } else if(label.equals(AppConstants.CLIENT_ID)) {
	    	  client = createClientObject(st);
	    	  reportTemp.getClientList().add(client);
	    	  isValidDatFile = true;
	      } else if(label.equals(AppConstants.SALE_ID)) {
	    	  sale = createSaleObject(st);
	    	  reportTemp.getSaleList().add(sale);
	    	  isValidDatFile = true;
	      } else {
	        break;
	      }
	    }
		if(Objects.nonNull(reportTemp.getSalesmanList()) && !reportTemp.getSalesmanList().isEmpty()) {
			report.getSalesmanList().addAll(reportTemp.getSalesmanList());
		}
		if(Objects.nonNull(reportTemp.getClientList()) && !reportTemp.getClientList().isEmpty()) {
			report.getClientList().addAll(reportTemp.getClientList());
		}
		if(Objects.nonNull(reportTemp.getSaleList()) && !reportTemp.getSaleList().isEmpty()) {
			report.getSaleList().addAll(reportTemp.getSaleList());
		}	
		if(!isValidDatFile) {
			throw new ReadingInputFileException();
		}
	}
	
	private Salesman createSalesmanObject(StringTokenizer st) {
		Salesman salesman = new Salesman();
		salesman.setId(AppConstants.SALESMAN_ID);
		salesman.setCpf(st.nextToken());
		salesman.setName(st.nextToken());
		salesman.setSalary(new BigDecimal(st.nextToken()));
		return salesman;
	}
	
	private Client createClientObject(StringTokenizer st) {
		Client cliente = new Client();
		cliente.setId(AppConstants.CLIENT_ID);
		cliente.setCnpj(st.nextToken());
		cliente.setName(st.nextToken());
		cliente.setBusinessArea(st.nextToken());
		return cliente;
	}
	
	private Sale createSaleObject(StringTokenizer st) {
		Sale sale = new Sale();
		sale.setId(AppConstants.SALE_ID);
		sale.setSaleId(st.nextToken());
		treatSaleItemString(st.nextToken(), sale);
		sale.setSalesmanName(st.nextToken());
		return sale;
	}
	
	/**
	 * 
	 * handles sales item data
	 * 
	 * @author sergio.melo
	 * 
	 * @param value
	 * @param sale
	 */
	private void treatSaleItemString(String value, Sale sale) {
		List<SaleItem> saleItemList = new ArrayList<SaleItem>();
		String line = value.replace("[", "").replace("]", "");
        String[] coupledSalesList = line.split(",");
        for(int i = 0; i < coupledSalesList.length; i++) {
        	String[] salesItemValue = coupledSalesList[i].split("-");
        	SaleItem saleItem = new SaleItem();
        	saleItem.setId(salesItemValue[0]);
        	saleItem.setQuantity(Integer.parseInt(salesItemValue[1]));
        	saleItem.setPrice(new BigDecimal(salesItemValue[2]));
        	if(saleItem.getQuantity() > 0 && (saleItem.getPrice().compareTo(new BigDecimal(1)) == 1)) {
        		saleItem.setTotalSaleValue(saleItem.getPrice().multiply(new BigDecimal(saleItem.getQuantity())));
        	} else {
        		saleItem.setTotalSaleValue(saleItem.getPrice());
        	}
        	saleItemList.add(saleItem);
        }
		sale.setSaleItens(saleItemList);	
	}
	
	/**
	 * 
	 * creates the output file
	 * 
	 * @author sergio.melo
	 * 
	 * @param report
	 * @throws CreateOutputFileException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws CalcException 
	 */
	private void createOutputFile(Report report) throws CreateOutputFileException, FileNotFoundException, IOException, CalcException {
		ReportResume reportResume = null;
		if(Objects.isNull(report)) {
			reportResume = new ReportResume();
			reportResume.setMostExpensiveSaleId("none");
			reportResume.setNumberOfCustomers(0);
			reportResume.setNumberOfSellers(0);
			reportResume.setWorstSeller("none");
		} else {
			calculationService.executeCalculation(report);
			reportResume = ReportResume.to(report);
		}
		BufferedWriter buffWrite = new BufferedWriter(new FileWriter(new File(AppConstants.READING_DIR + AppConstants.OUTPUT_FILE_NAME)));
		buffWrite.append(reportResume.getNumberOfCustomers() + "ç" 
					   + reportResume.getNumberOfSellers() 
					   + "ç" + reportResume.getMostExpensiveSaleId() 
					   + "ç" + reportResume.getWorstSeller());
		buffWrite.flush();
		buffWrite.close();
	}
	
	/**
	 * 
	 * Get the processed files
	 * 
	 * @author sergio.melo
	 * 
	 * @return
	 * @throws FileInvalidException
	 */
	public List<File> getAllProcessedFiles() throws FileInvalidException {
		File file = new File(AppConstants.PROCESSED_FILES);
		File[] files = file.listFiles();
		List<File> fileList = new ArrayList<File>();
		for(int i = 0; i < files.length; i++) {
			fileList.add(files[i]);
			logger.info("::processed file: " + files[i].getName());
		}
		return fileList;
	}
	
	/**
	 * 
	 * Get the output sumary processed file 
	 * 
	 * @return
	 * @throws FileInvalidException
	 */
	public List<File> getSumaryProcessedFile() throws FileInvalidException {
		File file = new File(AppConstants.READING_DIR);
		File[] files = file.listFiles();
		List<File> fileList = new ArrayList<File>();
		for(int i = 0; i < files.length; i++) {
			fileList.add(files[i]);
			logger.info("::output file: " + files[i].getName());
		}
		return fileList;
	}
	
	/**
	 * 
	 * get all fail process files
	 * 
	 * @return
	 * @throws FileInvalidException
	 */
	public List<File> getFailProcessedFile() throws FileInvalidException {
		File file = new File(AppConstants.PROCESSING_FAILURE_DIR);
		File[] files = file.listFiles();
		List<File> fileList = new ArrayList<File>();
		for(int i = 0; i < files.length; i++) {
			fileList.add(files[i]);
			logger.info("::fail process files: " + files[i].getName());
		}
		return fileList;
	}
	
	/**
	 * 
	 * Takes the results of the processed data in the output file 
	 * 
	 * @author sergio.melo
	 * 
	 * @return
	 * @throws SumaryAnalysisException
	 * @throws IOException
	 */
	public String getSummaryData() throws SumaryAnalysisException, IOException {
		StringBuilder out = new StringBuilder(System.getProperty("line.separator"));
		ReportResume reportResume = getSummaryOfAnalysis();
		out.append("::Sumary Data::").append(System.getProperty("line.separator"));
		out.append(">> Number Customers: ").append(reportResume.getNumberOfCustomers()).append(System.getProperty("line.separator"));
		out.append(">> Number Sellers: ").append(reportResume.getNumberOfSellers()).append(System.getProperty("line.separator"));
		out.append(">> Most Expensive Sale Id: ").append(reportResume.getMostExpensiveSaleId()).append(System.getProperty("line.separator"));
		out.append(">> Worst Seller: ").append(reportResume.getWorstSeller()).append(System.getProperty("line.separator"));
		return out.toString();
	}
	
	public ReportResume getReportResumeSummaryData() throws SumaryAnalysisException, IOException {
		return getSummaryOfAnalysis();
	}
	
}
