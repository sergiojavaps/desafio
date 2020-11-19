package br.com.agibank.dataanalysis.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.agibank.dataanalysis.constants.AppConstants;
import br.com.agibank.dataanalysis.exception.CalcException;
import br.com.agibank.dataanalysis.exception.CreateOutputFileException;
import br.com.agibank.dataanalysis.exception.FileInvalidException;
import br.com.agibank.dataanalysis.exception.GetInputFilesException;
import br.com.agibank.dataanalysis.exception.InvalidFileDirectoryException;
import br.com.agibank.dataanalysis.exception.ReadingInputFileException;
import br.com.agibank.dataanalysis.exception.SumaryAnalysisException;
import br.com.agibank.dataanalysis.model.ReportResume;
import br.com.agibank.dataanalysis.service.DataAnalysisService;
import br.com.agibank.dataanalysis.service.UploadingService;

/**
 * 
 * class responsible for data reporting resources
 * 
 * @author sergio.melo
 *
 */
@RestController
public class ReportResource {

	private static Logger logger = LogManager.getLogger(ReportResource.class);
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
	 * Responsible for displaying the data analysis report on the web.
	 * Responsible for creating the output file.
	 * 
	 * @author sergio.melo
	 * 
	 * @param model
	 * @return
	 */
	@Async
	@RequestMapping("/report")
	public ModelAndView showReport(Model model) {
		try {
			uploadingService.validateFileDirectory(dirList);
			dataAnalysisService.execute();
			ReportResume reportResume = dataAnalysisService.getSummaryOfAnalysis();
			List<ReportResume> ReportResumeList = new ArrayList<ReportResume>();
			ReportResumeList.add(reportResume);
			model.addAttribute("reportResumeList", ReportResumeList);
		} catch (GetInputFilesException gie) {
			logger.error("it was not possible to get the files from the input directory. Cause: " + gie);
			model.addAttribute("errorMessage", "it was not possible to get the files from the input directory. Cause: " + gie);
			return new ModelAndView("/error");
		} catch (FileInvalidException fie) {
			logger.error("it was not possible to validate the file extension. Cause: " + fie);
			model.addAttribute("errorMessage", "it was not possible to validate the file extension. Cause: " + fie);
			return new ModelAndView("/error");
		} catch (ReadingInputFileException rfe) {
			logger.error("unable to read the input files. Cause: " + rfe);
			model.addAttribute("errorMessage", "unable to read the input files. Cause: " + rfe);
			return new ModelAndView("/error");
		} catch (IOException ioe) {
			logger.error("failed or interrupted I/O operations. Cause: " + ioe);
			model.addAttribute("errorMessage", "failed or interrupted I/O operations. Cause: " + ioe);
			return new ModelAndView("/error");
		} catch (CreateOutputFileException coe) {
			logger.error("it was not possible to create the output file. Cause: " + coe);
			model.addAttribute("errorMessage", "it was not possible to create the output file. Cause: " + coe);
			return new ModelAndView("/error");
		} catch (SumaryAnalysisException sae) {
			logger.error("it was not possible to obtain the summary of the analyzed data. Cause: " + sae);
			model.addAttribute("errorMessage", "it was not possible to obtain the summary of the analyzed data. Cause: " + sae);
			return new ModelAndView("/error");
		} catch (InvalidFileDirectoryException ifde) {
			logger.error("it was not possible to create the directory (s). Cause: " + ifde);
			model.addAttribute("errorMessage", "it was not possible to create the directory (s). Cause: " + ifde);
			return new ModelAndView("/error");
		} catch (CalcException ce) {
			logger.error("it was not possible to perform the calculation of the file data. Cause: " + ce);
			model.addAttribute("errorMessage", "it was not possible to perform the calculation of the file data. Cause: " + ce);
			return new ModelAndView("/error");
		}
	    return new ModelAndView("report");
	}
	
}
