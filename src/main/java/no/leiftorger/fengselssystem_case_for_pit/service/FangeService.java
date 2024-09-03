package no.leiftorger.fengselssystem_case_for_pit.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import no.leiftorger.fengselssystem_case_for_pit.model.Fange;
import no.leiftorger.fengselssystem_case_for_pit.model.Fanger;

/**
 * Ansvar for å tilby forretningsfunksjoner mot for fengselssystemet
 * samt å kunne fylle internt register med initelle data fra egen tjeneste
 *
 */
@Service
@Slf4j
public class FangeService {

	private final FangeApiKlient apiKlient;
	private final ObjectMapper fengselOjectMapper;
	private Fanger fanger;
	
	public FangeService(FangeApiKlient client, ObjectMapper fengselOjectMapper) {
		this.apiKlient = client;
		this.fengselOjectMapper = fengselOjectMapper;
		initierFangedata();
	}
	
	void initierFangedata() {
		this.fanger = hentInitielleFangerFraEksternTjeneste();
		log.info("Hentet og populerte {} fanger fra api", fanger.getFanger().size());
	}
	
	private Fanger hentInitielleFangerFraEksternTjeneste(String fangerJson) {
		try {
			return fengselOjectMapper.readValue(fangerJson, Fanger.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("kunne ikke parse json til liste av type Person", e);
		}
	}
	
	private Fanger hentInitielleFangerFraEksternTjeneste() {
		return hentInitielleFangerFraEksternTjeneste(apiKlient.hentFangerSomJson());
	}
	
	private boolean skulleVærtLøslatt(Fange fange) {
		LocalDate nå = LocalDate.now();
		return nå.isBefore(fange.getFengslingsDatoTil());
	}
	
	public List<Fange> hentFanger() {
		return this.fanger.getFanger();
	}
	
	public List<Fange> hentFangerForCelleNummer(Integer celleNummer) {
		return this.fanger.getFanger().stream()
				.filter(fange -> Objects.equals(fange.getCelleNummer(), celleNummer))
				.toList();
	}

	public void settInn(Fange fange) {
		fanger.leggTil(fange);
	}
	
	public boolean løslat(Fange fange) {
		if (skulleVærtLøslatt(fange)) {
			log.warn("Løslater fange som skulle vært løslatt fra før: {}", fange);
		}
		return fanger.fjern(fange);
	}
	
	public void overfør(Fange fange, Integer celleNummer) {
		fanger.overfør(fange, celleNummer);
	}
	
	/*
	 * Viser antall personer for gitt celle.
	 * Ekskluderer registreringer av fanger hvor løslatt-dato er passert,
	 * da det antas at dette indikerer at de faktisk er løslatt
	 */
	public Long antallFangerICelle(Integer celleNummer) {
		return fanger.getFanger().stream()
				.filter(fange -> Objects.equals(fange.getCelleNummer(), celleNummer))
				.count();
	}
	
	
}
