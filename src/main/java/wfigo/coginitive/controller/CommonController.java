package wfigo.coginitive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommonController {

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "This service is responsible for the back end processing of the line bot";
	}
	
	@RequestMapping("/Hello")
	@ResponseBody
	String hello() {
		return "Hello World!!";
	}
	
}
