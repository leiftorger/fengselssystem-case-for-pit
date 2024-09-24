package no.leiftorger.fengselssystem_case_for_pit.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Ansvar for å lese fengselsdata på JSON-format fra gitt endepunkt 
 *
 */
@Service
@Slf4j
public class ArrestantApiKlient {
	
	@Value("${fengselssystem.data.uri}")
	private String uriString;
	
	@Value("${fengselssystem.data.brukernavn}")
	private String brukernavn;
	
	@Value("${fengselssystem.data.passord}")
	private String passord;
	

	public String hentFangerSomJson() {
		HttpResponse<String> httpResponse = null; 
		try { 
			httpResponse = HttpClient.newHttpClient().send(lagHttpRequest(), BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			log.error("feil fed forsøk på å sende http forespørsel til {}", uriString, e);
			return null;
		}
		if (httpResponse.statusCode() != HttpStatus.OK.value()) {
			throw new RuntimeException(
					String.format("feil fed forsøk på å sende http forespørsel til %s. HttpStatus %s", uriString, httpResponse.statusCode())
					);
			
		}
		return httpResponse.body();
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
	
	public static String basicAuthHeader(String brukernavn, String passord) {
		String brukernavnOgPassord = brukernavn + ":" + passord;
		return "Basic " + Base64.getEncoder().encodeToString(brukernavnOgPassord.getBytes());
	}
}
