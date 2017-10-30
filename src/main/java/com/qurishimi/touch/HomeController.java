package com.qurishimi.touch;

import java.awt.PageAttributes.MediaType;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.gson.Gson;
import com.qurishimi.touch.dao.DaoUser;
import com.qurishimi.touch.encje.Car;
import com.qurishimi.touch.encje.User;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	DaoUser daoUser;
	
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		
		return "home";
	}
	
	
	
	@RequestMapping(value = "hello")
	public String home(String value) throws SQLException {
		
		
	
		
		
		return "home";
	}
	
	@RequestMapping(value = "query", method= RequestMethod.POST)
	public ModelAndView result(String value) throws SQLException {
		
		List<User> userlist = new ArrayList<User>();
		
		int x = Integer.parseInt(value);
		userlist = daoUser.moreThan(x);
		
		 Gson gson = new Gson();
		
		
		ModelAndView view = new ModelAndView("home");
		view.addObject("userlist", gson.toJson(userlist));
		
		
		
		System.out.println(userlist.toString());
		
		
		return view;
	}
	
	@RequestMapping(value = "session")
	public String session()  {
		
		List<User> userlist = new ArrayList<User>();
		List<Car> carlist = new ArrayList<Car>();
		userlist = daoUser.list();
		carlist = daoUser.listCar();
		
		System.out.println("Samochody: ");
		for(Car cars:carlist)
		{
		System.out.println(cars);
		}
		
		System.out.println("Użytkownicy: ");
		for(User users:userlist)
		{
		System.out.println(users);
		}
		
		
		return "hello";
	}
	
	@RequestMapping(value = "springdata")
	public String springData()  {

		
		
		daoUser.updateValue();
	
		
		
		
		
		return "hello";
	}
	
	@RequestMapping(value = "/api/{id}", method= RequestMethod.GET)
	public @ResponseBody User getUserInJSON(@PathVariable String id)  {

		
		
		List<User> userlist;
		userlist = daoUser.list();
	
		User user = userlist.get(Integer.parseInt(id) - 1);
		
		
		return user;
	}
	@RequestMapping(value = "/api/cars/{id}", method= RequestMethod.GET)
	public @ResponseBody Car getCarInJSON(@PathVariable String id)  {

		
		
		List<Car> carlist;
		carlist = daoUser.listCar();
	
		Car car = carlist.get(Integer.parseInt(id) - 1);
		
		
		return car;
	}
	
	@RequestMapping(value = "list")
	public @ResponseBody List<User> getUsersInJSON()  {

	return daoUser.list();
	}
	
	@RequestMapping(value = "shutdown")
	public String shutdown() throws IOException  {
		String shutdownCommand;
	    String operatingSystem = System.getProperty("os.name");

	    if ("Linux".equals(operatingSystem) || "Mac OS X".equals(operatingSystem)) {
	        shutdownCommand = "shutdown -h now";
	    }
	    else if ("Windows".equals(operatingSystem)) {
	        shutdownCommand = "shutdown.exe -s -t 0";
	    }
	    else {
	        throw new RuntimeException("Unsupported operating system.");
	    }

	    Runtime.getRuntime().exec(shutdownCommand);
	    System.exit(0);
		    return "PA PA ";
	}
	@RequestMapping(value = "shutdown2")
	public String shutdown2() throws IOException  {

		String command;
		String system = System.getProperty("os.name");
		int x = Runtime.getRuntime().availableProcessors();
		Runtime.getRuntime().exec("gedit");
		System.out.println(x);
		
		
		    return "PA PA ";
	}
	   
	@RequestMapping(value= "homepage")
	public ModelAndView homepage() {
		
		
		return new ModelAndView("homepage");
		
	}
	
	@RequestMapping(value= "picture")
	public ModelAndView picture() {
		
		
		return new ModelAndView("addpicture");
		
	}
	
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String handleFileUpload(@RequestParam("plik") MultipartFile file){
	    if (!file.isEmpty()) {
	        try {
	            UUID uuid = UUID.randomUUID();
	            String filename = "/uploads/upload_"+uuid.toString();
	            String bucketName = "nazwaKubelka";
	            String accessKey = "twojAccessKey";
	            String secretKey = "twojSecretKey";
	            byte[] bytes = file.getBytes();
	            InputStream inputStream = new ByteArrayInputStream(bytes);
	            AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey));
	            s3client.putObject(new PutObjectRequest(
	                                     bucketName, filename, inputStream, new ObjectMetadata()));

	            logger.info("File {} has been successfully uploaded as {}", new Object[] {file.getOriginalFilename(), filename});
	        } catch (Exception e) {
	            logger.error("File has not been uploaded", e);
	        }
	    } else {
	        logger.error("Uploaded file is empty");
	    }
	    return "redirect:/";
	}
	

	
}
