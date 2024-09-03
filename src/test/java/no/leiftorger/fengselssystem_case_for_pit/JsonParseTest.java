package no.leiftorger.fengselssystem_case_for_pit;

import static org.assertj.core.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.leiftorger.fengselssystem_case_for_pit.model.Fanger;

@SpringBootTest()
public class JsonParseTest {
	
	@Autowired
	ObjectMapper fengselOjectMapper;
	
	private final String fangerJson = """
			{
			  "Fanger": [
			    {
			      "navn": "Anton Chigurh",
			      "alder": 50,
			      "kjonn": "Mann",
			      "celleNummer": 302,
			      "fengslingsDatoFra": "12-02-2007",
			      "fengslingsDatoTil": "07-11-2037"
			    },
			    {
			      "navn": "Henry Hill",
			      "alder": 55,
			      "kjonn": "Mann",
			      "celleNummer": 302,
			      "fengslingsDatoFra": "28-11-2001",
			      "fengslingsDatoTil": "19-08-2031"
			    }
			  ]
			}
			""";
	
	@Test
	void testMapper() {
		try {
			fengselOjectMapper.readValue(fangerJson, Fanger.class);
		} catch (Exception e) {
			fail("Kunne ikke parse json for fanger: " + fangerJson, e);
		}
	}
}
