package no.leiftorger.fengselssystem_case_for_pit.model;

import no.leiftorger.fengselssystem_case_for_pit.model.ekstern.FangeEkstern;
import no.leiftorger.fengselssystem_case_for_pit.model.ekstern.FangerEkstern;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.Fange;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.FangeUtenId;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.Fanger;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.FangerUtenId;

public class ModelMapper {
	
	public static Fange map(FangeUtenId fangeEkstern) {
		return Fange.builder()
				.alder(fangeEkstern.getAlder())
				.celleNummer(fangeEkstern.getCelleNummer())
				.fengslingsDatoFra(fangeEkstern.getFengslingsDatoFra())
				.fengslingsDatoTil(fangeEkstern.getFengslingsDatoTil())
				.kjonn(fangeEkstern.getKjonn())
				.navn(fangeEkstern.getNavn())
				.build();
	}
	
	public static Fanger map(FangerUtenId fangerEkstern) {
		return Fanger.builder()
				.fanger(fangerEkstern.getFanger().stream().map(fangeEkstern -> map(fangeEkstern)).toList())
				.build();
	}
	
	public static FangeUtenId map(FangeEkstern fangeEkstern) {
		return FangeUtenId.builder()
				.alder(fangeEkstern.getAlder())
				.celleNummer(fangeEkstern.getCelleNummer())
				.fengslingsDatoFra(fangeEkstern.getFengslingsDatoFra())
				.fengslingsDatoTil(fangeEkstern.getFengslingsDatoTil())
				.kjonn(fangeEkstern.getKjonn())
				.navn(fangeEkstern.getNavn())
				.build();
	}
	
	public static FangerUtenId map(FangerEkstern fangerEkstern) {
		return FangerUtenId.builder()
				.fanger(fangerEkstern.getFanger().stream().map(fangeEkstern -> map(fangeEkstern)).toList())
				.build();
	}
}
