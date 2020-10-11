package com.skull.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = { "service.preload.database=true" })
class MpmSrvAuthApplicationWebTest {

	@Value("${security.oauth2.client.id}")
	private String clientid; // NOPMD by skull on 10/11/20, 12:09 AM

	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret; // NOPMD by skull on 10/11/20, 12:09 AM

	private static final String AUTH_URL = "/oauth/token";

	private static final String TEST_USER_EMAIL = "carlos.feitosa.nt@gmail.com";

	private static final String TEST_USER_PASSWORD = "s3cr3t";

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private String endpoint;

	@BeforeEach
	void setupTest() {
		endpoint = String.format("http://localhost:%d%s", port, AUTH_URL);
	}

	@Test
	@DisplayName("Test if can get 200 response trying to get token")
	void testIfCanGet200ForToken() throws Exception {

		TestRestTemplate testTemplate = restTemplate.withBasicAuth(clientid, clientSecret);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type", "password");
		map.add("username", TEST_USER_EMAIL);
		map.add("password", TEST_USER_PASSWORD);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		ResponseEntity<String> response = testTemplate.postForEntity(endpoint, request, String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
