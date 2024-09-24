package no.leiftorger.fengselssystem_case_for_pit.model;

import no.leiftorger.fengselssystem_case_for_pit.model.ekstern.ArrestantEkstern;
import no.leiftorger.fengselssystem_case_for_pit.model.ekstern.ArrestanterEkstern;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.Arrestant;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.ArrestantUtenId;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.Arrestanter;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.ArrestanterUtenId;

public class ModelMapper {
	
	public static Arrestant map(ArrestantUtenId fangeEkstern) {
		return Arrestant.builder()
				.alder(fangeEkstern.getAlder())
				.celleNummer(fangeEkstern.getCelleNummer())
				.fengslingsDatoFra(fangeEkstern.getFengslingsDatoFra())
				.fengslingsDatoTil(fangeEkstern.getFengslingsDatoTil())
				.kjonn(fangeEkstern.getKjonn())
				.navn(fangeEkstern.getNavn())
				.build();
	}
	
	public static Arrestanter map(ArrestanterUtenId fangerEkstern) {
		return Arrestanter.builder()
				.fanger(fangerEkstern.getArrestanter().stream().map(fangeEkstern -> map(fangeEkstern)).toList())
				.build();
	}
	
	public static ArrestantUtenId map(ArrestantEkstern fangeEkstern) {
		return ArrestantUtenId.builder()
				.alder(fangeEkstern.getAlder())
				.celleNummer(fangeEkstern.getCelleNummer())
				.fengslingsDatoFra(fangeEkstern.getFengslingsDatoFra())
				.fengslingsDatoTil(fangeEkstern.getFengslingsDatoTil())
				.kjonn(fangeEkstern.getKjonn())
				.navn(fangeEkstern.getNavn())
				.build();
	}
	
	public static ArrestanterUtenId map(ArrestanterEkstern fangerEkstern) {
		return ArrestanterUtenId.builder()
				.arrestanter(fangerEkstern.getFanger().stream().map(fangeEkstern -> map(fangeEkstern)).toList())
				.build();
	}
}
