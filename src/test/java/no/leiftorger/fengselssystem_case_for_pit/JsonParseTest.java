package no.leiftorger.fengselssystem_case_for_pit;

import static org.assertj.core.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import no.leiftorger.fengselssystem_case_for_pit.model.ekstern.FangerEkstern;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.Fanger;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.FangerUtenId;

@SpringBootTest()
public class JsonParseTest {
	
	@Autowired
	ObjectMapper fengselOjectMapper;
	
	@Test
	void testMapper() {
		try {
			fengselOjectMapper.readValue(FangeFactory.fangerJson, FangerEkstern.class);
		} catch (Exception e) {
			fail("Kunne ikke parse json for fanger: " + FangeFactory.fangerJson, e);
		}
	}
}
