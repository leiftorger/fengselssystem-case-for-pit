package no.leiftorger.fengselssystem_case_for_pit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.leiftorger.fengselssystem_case_for_pit.model.ModelMapper;
import no.leiftorger.fengselssystem_case_for_pit.model.ekstern.ArrestanterEkstern;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.ArrestanterUtenId;

public class ArrestantFactory {
	
	public static ArrestanterUtenId fanger(ObjectMapper fengselOjectMapper) {
		ArrestanterUtenId fanger = null;
		try {
			fanger = ModelMapper.map(fengselOjectMapper.readValue(arrestanterJson, ArrestanterEkstern.class));
		} catch (JsonProcessingException e) {
			throw new RuntimeException("kunne ikke parse json til Fange", e);
		}
		return fanger;
	}
	
	public static final String arrestanterJson = """
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
			    	},
			    	{
						"navn": "Navn Navnesen",
						"alder": "42",
						"kjonn": "Mann",
						"celleNummer": "101",
						"fengslingsDatoFra": "24-04-2002",
						"fengslingsDatoTil": "04-02-2042"
					},
			    	{
						"navn": "Donald Duck",
						"alder": "50",
						"kjonn": "Mann",
						"celleNummer": "102",
						"fengslingsDatoFra": "12-02-2007",
						"fengslingsDatoTil": "07-11-2037"
					},
			    	{
						"navn": "Dolly Duck",
						"alder": "50",
						"kjonn": "Kvinne",
						"celleNummer": "102",
						"fengslingsDatoFra": "12-02-2007",
						"fengslingsDatoTil": "07-11-2037"
					}
			  	]
			}
			""";
	
	
}
