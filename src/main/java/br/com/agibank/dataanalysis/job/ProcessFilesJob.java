package br.com.agibank.dataanalysis.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.agibank.dataanalysis.service.DataAnalysisService;

@Component
public class ProcessFilesJob {

	private Logger logger = LoggerFactory.getLogger(ProcessFilesJob.class);
    private final String cronConfig = "*/10 * * * * *";
    private final String timeZone = "America/Sao_Paulo";
    @Autowired
    DataAnalysisService dataAnalysisService;
    
    @Scheduled(cron = cronConfig, zone = timeZone)
    @Async
    public void processFilesJob() {
    	try {
	    	logger.info("-------------------------");
	    	logger.info("	INI JOB");    	
	    	logger.info("-------------------------");
	    	
	    	dataAnalysisService.execute();	    	
	    	dataAnalysisService.getAllProcessedFiles();
	    	dataAnalysisService.getSumaryProcessedFile();
	    	dataAnalysisService.getSummaryData();
	    	
	    	logger.info("-------------------------");
	    	logger.info("	FIM JOB");    	
	    	logger.info("-------------------------");
    	} catch (Exception e) {
    		logger.error("job processing error. cause: " + e);
		}
    	
    }
    
    
    
}
