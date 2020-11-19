package br.com.agibank.dataanalysis.job;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.agibank.dataanalysis.constants.AppConstants;
import br.com.agibank.dataanalysis.exception.InvalidFileDirectoryException;
import br.com.agibank.dataanalysis.service.DataAnalysisService;
import br.com.agibank.dataanalysis.service.UploadingService;

/**
 * class responsible for executing the file processing job
 * 
 * @author sergio.melo
 * 
 */
@Component
public class ProcessFilesJob {

	private Logger logger = LoggerFactory.getLogger(ProcessFilesJob.class);
    private final String cronConfig = "*/10 * * * * *";
    private final String timeZone = "America/Sao_Paulo";
    @Autowired
    private DataAnalysisService dataAnalysisService;
    @Autowired
    private UploadingService uploadingService;
    private List<String> dirList = null;
    
    @PostConstruct
    public void ini() {    	
		dirList = new ArrayList<String>();
    	dirList.add(AppConstants.READING_DIR);
    	dirList.add(AppConstants.UPLOADING_DIR);
    	dirList.add(AppConstants.PROCESSED_FILES);		
    }
    
    /**
     * 
     * job scheduling and execution
     * 
     * @author sergio.melo
     * 
     */
    @Scheduled(cron = cronConfig, zone = timeZone)
    @Async
    public void processFilesJob() {
    	try {	
	    	logger.info(">>>> INI JOB");    		  
	    	uploadingService.validateFileDirectory(dirList);
	    	dataAnalysisService.execute();	    	
	    	dataAnalysisService.getAllProcessedFiles();
	    	dataAnalysisService.getSumaryProcessedFile();
	    	logger.info(dataAnalysisService.getSummaryData());
	    	logger.info(">>>> END JOB");    	
    	} catch (InvalidFileDirectoryException e) {
			logger.error("it was not possible to create the directory (s). Cause: " + e);
		} catch (Exception e) {
    		logger.error("job processing error. cause: " + e);
		}    	
    }
     
}
