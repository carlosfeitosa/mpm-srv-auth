package com.skull.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * Auth server main class.
 * 
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 * @since 2020-10-04
 *
 */
@SpringBootApplication
@EnableAuthorizationServer
public class MpmSrvAuthApplication {

	/**
	 * Main class.
	 * 
	 * @param args args from command line
	 */
	public static void main(String[] args) {
		SpringApplication.run(MpmSrvAuthApplication.class, args);
	}

}
