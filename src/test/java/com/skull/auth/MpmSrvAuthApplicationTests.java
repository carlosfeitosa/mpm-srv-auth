package com.skull.auth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.skull.auth.service.CustomUserDetailsService;

@SpringBootTest(properties = { "service.preload.database=false" })
class MpmSrvAuthApplicationTests {

	@Autowired
	private CustomUserDetailsService service;

	@Test
	void contextLoads() {

		MpmSrvAuthApplication.main(new String[] {});

		assertThat(service).isNotNull();
	}

}
