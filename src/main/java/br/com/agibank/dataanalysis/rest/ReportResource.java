package br.com.agibank.dataanalysis.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.agibank.dataanalysis.exception.CreateOutputFileException;
import br.com.agibank.dataanalysis.exception.FileInvalidException;
import br.com.agibank.dataanalysis.exception.GetInputFilesException;
import br.com.agibank.dataanalysis.exception.ReadingInputFileException;
import br.com.agibank.dataanalysis.exception.SumaryAnalysisException;
import br.com.agibank.dataanalysis.model.ReportResume;
import br.com.agibank.dataanalysis.service.DataAnalysisService;

@RestController
public class ReportResource {

	private static Logger logger = LogManager.getLogger(ReportResource.class);
	@Autowired
	private DataAnalysisService dataAnalysisService;
	
	@Async
	@RequestMapping("/report")
	public ModelAndView showReport(Model model) {
		try {
			dataAnalysisService.execute();
			ReportResume reportResume = dataAnalysisService.getSummaryOfAnalysis();
			List<ReportResume> ReportResumeList = new ArrayList<ReportResume>();
			ReportResumeList.add(reportResume);
			model.addAttribute("reportResumeList", ReportResumeList);
		} catch (GetInputFilesException gie) {
			logger.error("it was not possible to get the files from the input directory. Cause: " + gie);
		} catch (FileInvalidException fie) {
			logger.error("it was not possible to validate the file extension. Cause: " + fie);
		} catch (ReadingInputFileException rfe) {
			logger.error("unable to read the input files. Cause: " + rfe);
		} catch (IOException ioe) {
			logger.error("failed or interrupted I/O operations. Cause: " + ioe);
		} catch (CreateOutputFileException coe) {
			logger.error("it was not possible to create the output file. Cause: " + coe);
		} catch (SumaryAnalysisException sae) {
			logger.error("it was not possible to obtain the summary of the analyzed data. Cause: " + sae);
		}
	    return new ModelAndView("report");
	}
	
}
