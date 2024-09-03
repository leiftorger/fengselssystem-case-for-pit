package no.leiftorger.fengselssystem_case_for_pit.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
public class KlientTest {
	@Autowired
	FangeApiKlient klient;
	
	@Test
	void kanLeseDataFraApiUrl() {
		String respons = klient.hentFangerSomJson();
		assertThat(respons).isNotEmpty();
	}
}
