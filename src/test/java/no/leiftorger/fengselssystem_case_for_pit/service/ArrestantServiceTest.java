package no.leiftorger.fengselssystem_case_for_pit.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import no.leiftorger.fengselssystem_case_for_pit.ArrestantFactory;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.Arrestant;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.ArrestantUtenId;

@SpringBootTest()
public class ArrestantServiceTest {
	
	@Autowired
	ArrestantService fangeService;
	
	@Autowired
	ObjectMapper fengselOjectMapper;
	
	Arrestant fangeAnton;
	
	@BeforeEach
	public void importerFanger() {
		fangeService.løslatAlle();
		ArrestantFactory.fanger(fengselOjectMapper).arrestanter.forEach(arrestant -> fangeService.settInn(arrestant));
		fangeAnton = fangeService.hentUtenId(fangeAntonUtenId);
	}
	
	ArrestantUtenId fangeAntonUtenId = ArrestantUtenId.builder()
			.navn("Anton Chigurh")
			.alder(50)
			.kjonn("Mann")
			.celleNummer(302)
			.fengslingsDatoFra(LocalDate.of(2007, 02, 12))
			.fengslingsDatoTil(LocalDate.of(2037, 11, 07))
			.build();
	
	
	ArrestantUtenId fangeSomIkkeFinnesIInitieltRegisterOgHeterLucy = ArrestantUtenId.builder()
			.navn("Lucy Smith")
			.alder(80)
			.kjonn("Kvinne")
			.celleNummer(302)
			.fengslingsDatoFra(LocalDate.of(2007, 02, 12))
			.fengslingsDatoTil(LocalDate.of(2037, 11, 07))
			.build();
		
	@Test
	void rettAntallFanger() {
		List<Arrestant> fanger = fangeService.hentArrestanter();
		assertThat(fanger).size()
		.as("Forventet 5 fanger i initielle data")
		.isEqualTo(5);
	}
	
	@Test
	void fangeSkalKunneOverføres() {
		Arrestant overførFange = fangeAnton;
		
		Integer overførFangeFraCelle = 302;
		Integer overførFangeTilCelle = 102;
		
		assertThat(fangeService.antallArrestanterICelle(overførFangeFraCelle))
		.as("Forventet to fanger i cellen vi overfører fanger fra " + overførFangeFraCelle)
		.isEqualTo(2);
		
		assertThat(fangeService.antallArrestanterICelle(overførFangeTilCelle))
		.as("Forventet to fanger i cellen vi overfører fanger til " + overførFangeTilCelle)
		.isEqualTo(2);
		
		fangeService.overfør(overførFange, overførFangeTilCelle);
		
		assertThat(fangeService.antallArrestanterICelle(overførFangeFraCelle))
		.as("Forventet en fange i cellen som en fange ble overført fra")
		.isEqualTo(1);
		
		assertThat(fangeService.antallArrestanterICelle(overførFangeTilCelle))
		.as("Forventet tre fanger i cellen vi overførte fangen til")
		.isEqualTo(3);
	}
	
	@Test
	void fangeSkalKunneSettesInn() {
		
		Integer settInnICelleNummer = fangeSomIkkeFinnesIInitieltRegisterOgHeterLucy.getCelleNummer();
		
		assertThat(fangeService.antallArrestanterICelle(settInnICelleNummer))
		.as("Forventet 2 fanger basert på initielle fangedata")
		.isEqualTo(2);
		
		fangeService.settInn(fangeSomIkkeFinnesIInitieltRegisterOgHeterLucy);
		
		assertThat(fangeService.antallArrestanterICelle(settInnICelleNummer))
		.as("Forventet tre fanger i celle etter å ha satt inn ny arrestant")
		.isEqualTo(3);
		
		Arrestant nyArrestantLucy = fangeService.hentUtenId(fangeSomIkkeFinnesIInitieltRegisterOgHeterLucy);
		
		Long forventerEnFunnetFange = fangeService.hentFangerForCelleNummer(settInnICelleNummer)
		.stream().filter(fange -> Objects.equals(fange, nyArrestantLucy))
		.count();
		assertThat(1L)
		.as("Forventet at innsatt fange var på angitt celle med nummer " + settInnICelleNummer)
		.isEqualTo(forventerEnFunnetFange);
	}
	
	@Test
	void fangeSkalKunneLøslates() {
		
		Integer løslatFraCellenummer = fangeAnton.getCelleNummer();
		
		assertThat(fangeService.antallArrestanterICelle(løslatFraCellenummer))
		.as("Forventet 2 fanger basert på initielle fangedata")
		.isEqualTo(2);
		
		fangeService.løslat(fangeAnton);
		
		assertThat(fangeService.antallArrestanterICelle(løslatFraCellenummer))
		.as("Forventet at kun en fange var igjen i celle etter å ha løslatt en arrestant")
		.isEqualTo(1);
		
		Long forventerEnFunnetFange = fangeService.hentFangerForCelleNummer(løslatFraCellenummer)
		.stream().filter(fange -> Objects.equals(fange, fangeSomIkkeFinnesIInitieltRegisterOgHeterLucy))
		.count();
		assertThat(0L)
		.as("Forventet ikke at løslatt fange var på angitt celle med nummer " + løslatFraCellenummer)
		.isEqualTo(forventerEnFunnetFange);
	}
	
}