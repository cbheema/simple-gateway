package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.controller.DemoController;

@SpringBootTest
class DemoApplicationTests {
	
	
	@Autowired
	private DemoController demoController;

	@Test
	void contextLoads() {
		
		assertNotNull(demoController);
		String str = demoController.getGreetings();
		
		//expecting the "Have a Nice Day!!" from the server.
		assertEquals(str, "Have a Nice Day!!");
	}

}
