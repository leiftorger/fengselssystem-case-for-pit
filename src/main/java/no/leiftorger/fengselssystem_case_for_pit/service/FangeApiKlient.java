package no.leiftorger.fengselssystem_case_for_pit.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Ansvar for å lese fengselsdata på JSON-format fra gitt endepunkt 
 *
 */
@Service
@Slf4j
public class FangeApiKlient {
	
	@Value("${fengselssystem.data.uri}")
	private String uriString;
	
	@Value("${fengselssystem.data.brukernavn}")
	private String brukernavn;
	
	@Value("${fengselssystem.data.passord}")
	private String passord;
	

	public String hentFangerSomJson() {
		String response = null; 
		try { 
			 response = HttpClient.newHttpClient().send(lagHttpRequest(), BodyHandlers.ofString()).body();
		} catch (IOException | InterruptedException e) {
			log.error("feil fed forsøk på å sende http forespørsel til {}", e);
			return null;
		}
		return response;
	}
	
	private HttpRequest lagHttpRequest() {
		URI uri;
		try {
			uri = new URI(uriString);
		} catch (URISyntaxException urie) {
			throw new RuntimeException("Kunne ikke lage URI fra " + uriString, urie);
		}
		return HttpRequest.newBuilder()
		  .GET()
		  .uri(uri)
		  .header("Authorization", basicAuthHeader(brukernavn, passord))
		  .build();
	}
	
	private String basicAuthHeader(String brukernavn, String passord) {
		String brukernavnOgPassord = brukernavn + ":" + passord;
		return "Basic " + Base64.getEncoder().encodeToString(brukernavnOgPassord.getBytes());
	}
}
