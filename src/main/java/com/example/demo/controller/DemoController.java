package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Controller
public class DemoController {
	
	
	private static final String MAIN_SERVICE = "getInvoiceCB";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Bean
	private RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@RequestMapping("/")
	public @ResponseBody String getGreetings() {
		return "Have a Nice Day!!";
		
	}
	
	@GetMapping("/api/offers")
	@ResponseStatus(HttpStatus.OK)
	@CircuitBreaker(name=MAIN_SERVICE, fallbackMethod="offersInSecondary")
	public ResponseEntity<String> getOffersPrimary(){
		
		String str = restTemplate.getForObject("http://localhost:7070/api/offers", String.class);
		return new ResponseEntity<String>(str,HttpStatus.OK);
		
//		String str = getSomeStringValue(); // generating the exception
//		return new ResponseEntity<String>(str,HttpStatus.OK);
		
		
	}
	
	
//	private String getSomeStringValue() {
//        throw new RuntimeException("boo!");
//    }
	
	
	public ResponseEntity<String> offersInSecondary(Throwable tx){
		
		String str = restTemplate.getForObject("http://localhost:9090/api/offers", String.class);
		return new ResponseEntity<String>(str,HttpStatus.OK);
		
//		System.out.println("in fallback method...");
//		
//		return new ResponseEntity<String>("In fallback method", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	

}
