package no.leiftorger.fengselssystem_case_for_pit.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import no.leiftorger.fengselssystem_case_for_pit.model.intern.Arrestant;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.Arrestanter;
import no.leiftorger.fengselssystem_case_for_pit.service.ArrestantService;

@WebMvcTest(FengselController.class)
class FengselControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ArrestantService fangeService;
	
	/* 
	 * Merk: her brukes ISO-8601 for datoer, siden det er Springs controller som deserialiserer 
	 * JSON som mottas av controlleren.
	 */
	private String contentFangeHenry = """
			{
		      "navn": "Henry Hill",
		      "alder": 55,
		      "kjonn": "Mann",
		      "celleNummer": 302,
		      "fengslingsDatoFra": "2001-11-28",
		      "fengslingsDatoTil": "2031-08-19"
		    }
			""";

	@Test
	void testAntallFanger() throws Exception {
		when(fangeService.antallArrestanterICelle(1)).thenReturn(1L);
		this.mockMvc.perform(get("/api/antallFanger").param("celleNummer", "1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("1")));
	}
	
	@Test
	void testInnsatte() throws Exception {
		when(fangeService.hentArrestanter()).thenReturn(Collections.emptyList());
		this.mockMvc.perform(get("/api/innsatte")).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	void testOverfor() throws Exception {
		
		this.mockMvc.perform(
				post("/api/overfor")
				.contentType("application/json")
				.content(contentFangeHenry)
				.param("celleNummer", "1"))
		.andDo(print())
		.andExpect(status().isOk());
	}
}