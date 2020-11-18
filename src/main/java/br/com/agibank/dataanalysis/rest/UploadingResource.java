package br.com.agibank.dataanalysis.rest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.agibank.dataanalysis.constants.AppConstants;
import br.com.agibank.dataanalysis.model.FileNotAllowed;

/**
 * 
 * Controller class of upload requests
 * 
 * @author sergio.melo
 *
 */
@RestController
public class UploadingResource {
	
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
			model.addAttribute("files", file.listFiles());
			return new ModelAndView("uploading");
		} catch(Exception e) {
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
    public ModelAndView uploadingPost(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles) { 
    	HashMap<String, Object> params = new HashMap<String, Object>();
    	List<FileNotAllowed> fileNotAllowedList = new ArrayList<FileNotAllowed>();
		try {
	    	for(MultipartFile uploadedFile : uploadingFiles) {
	            File file = new File(AppConstants.UPLOADING_DIR + uploadedFile.getOriginalFilename());
	            uploadedFile.transferTo(file);	       	            	            	      	      
	        }
	    	params.put("files", fileNotAllowedList);
	    	return new ModelAndView("redirect:/");
    	} catch(FileNotFoundException e) {
    		return new ModelAndView("redirect:/");
    	} catch(IOException e) {
    		return new ModelAndView("redirect:/");
    	}  
		
    }
	
}
