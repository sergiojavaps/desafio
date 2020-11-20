package br.com.agibank.dataanalysis.rest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.agibank.dataanalysis.constants.AppConstants;
import br.com.agibank.dataanalysis.exception.FileInvalidException;
import br.com.agibank.dataanalysis.service.UploadingService;

/**
 * 
 * Controller class of upload requests
 * 
 * @author sergio.melo
 *
 */
@RestController
public class UploadingResource {
	
	private static Logger logger = LogManager.getLogger(UploadingResource.class);
	@Autowired
	private UploadingService uploadingService;
	/**
	 * 
	 * Returns template with upload files
	 * 
	 * @author sergio.melo
	 * 
	 * @param model
	 * @return
	 */
	@Async
	@RequestMapping("/")
    public ModelAndView uploading(Model model) {
		try {
			File file = new File(AppConstants.UPLOADING_DIR);
			File procFile = new File(AppConstants.PROCESSED_FILES);
			File resultFile = new File(AppConstants.READING_DIR);
			model.addAttribute("files", file.listFiles());
			model.addAttribute("procFiles", procFile.listFiles());
			model.addAttribute("result", resultFile.listFiles());
			return new ModelAndView("uploading");
		} catch(Exception e) {
			model.addAttribute("errorMessage", e);
			return new ModelAndView("/error");
		}
    }
	
	/**
	 * 
	 * Upload files
	 * 
	 * @author sergio.melo
	 * 
	 * @param uploadingFiles
	 * @return
	 */
	@Async
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView uploadingPost(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles, Model model) { 
		try {	    	
	        uploadingService.uploadingPost(uploadingFiles);	     
	    	logger.info("upload realizado com sucesso");	    
	    	return new ModelAndView("redirect:/");	  
    	} catch(FileNotFoundException e) {
    		logger.error("File Not Found. Cause: " + e);
    		model.addAttribute("errorMessage", "File Not Found. Cause: " + e);
    		return new ModelAndView("/error");
    	} catch(IOException e) {
    		logger.error("file to upload or IO error. Cause: " + e);
    		model.addAttribute("file to upload or IO error. Cause: " + e);
    		return new ModelAndView("/error");
    	}  catch(FileInvalidException e) {
    		logger.error("File is invalid. Cause: " + e);
    		model.addAttribute("errorMessage", "File is invalid. Cause: " + e);
    		return new ModelAndView("/error");
    	}  
		
    }
	
}
