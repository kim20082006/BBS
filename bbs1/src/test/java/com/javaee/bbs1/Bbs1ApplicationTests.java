package com.javaee.bbs1;

import com.javaee.bbs1.controller.ExController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@ContextConfiguration(classes = BBS1Application.class)
class Bbs1ApplicationTests {


	@Autowired

	private ExController exController;

	@Autowired

	private SimpleDateFormat simpleDateFormat;

	@Test
	public void testBeanDI() {
		System.out.println(exController);
	}

	@Test
	public void testBeanConfig() {
		System.out.println(simpleDateFormat.format(new Date()));
	}
}
