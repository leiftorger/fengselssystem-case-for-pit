package no.leiftorger.fengselssystem_case_for_pit.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

import no.leiftorger.fengselssystem_case_for_pit.ArrestantFactory;

@SpringBootTest()
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.properties"})
public class KlientTest {
	@Autowired
	ArrestantApiKlient klient;
	
	@RegisterExtension
	WireMockExtension wme = WireMockExtension.newInstance()
	    .options(WireMockConfiguration.wireMockConfig().port(9090)
	    .notifier(new ConsoleNotifier(true)))
	    .build();
	
	@Test
	void kanLeseDataFraApiUrl() {
		
		wme.stubFor(WireMock.get(WireMock.urlPathEqualTo("/fanger")).withHost(WireMock.equalTo("localhost"))
	            .willReturn(WireMock.aResponse()
	                .withStatus(200)
	                .withHeader("Authorization", ArrestantApiKlient.basicAuthHeader("test-brukernavn", "test-passordet"))
	                .withHeader("Content-Type", "application/json")
	                .withBody(ArrestantFactory.arrestanterJson)
	                )
	            );
		
		String respons = klient.hentFangerSomJson();
		assertThat(respons).isNotEmpty();
		assertThat(respons).isEqualTo(ArrestantFactory.arrestanterJson);
	}
}
