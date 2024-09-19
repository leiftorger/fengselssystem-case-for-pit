package no.leiftorger.fengselssystem_case_for_pit.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import no.leiftorger.fengselssystem_case_for_pit.dao.ArrestantDao;
import no.leiftorger.fengselssystem_case_for_pit.model.ModelMapper;
import no.leiftorger.fengselssystem_case_for_pit.model.ekstern.FangerEkstern;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.Fange;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.FangeUtenId;

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
	private ArrestantDao arrestantDao;
	
	public FangeService(FangeApiKlient client, ObjectMapper fengselOjectMapper, ArrestantDao arrestantDao) {
		this.apiKlient = client;
		this.fengselOjectMapper = fengselOjectMapper;
		this.arrestantDao = arrestantDao;
	}
	
	private FangerEkstern fangerFraJson(String fangerJson) {
		try {
			return fengselOjectMapper.readValue(fangerJson, FangerEkstern.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("kunne ikke parse json til Fange", e);
		}
	}
	
	public void importerFanger() {
		arrestantDao.opprett(
				ModelMapper.map(
						fangerFraJson(apiKlient.hentFangerSomJson())
						)
				);
	}
	
	private boolean skulleVærtLøslatt(Fange fange) {
		LocalDate nå = LocalDate.now();
		return nå.isBefore(fange.getFengslingsDatoTil());
	}
	
	public List<Fange> hentFanger() {
		return arrestantDao.hentAlle();
	}
	
	public Fange hentUtenId(FangeUtenId fange) {
		return arrestantDao.hentUtenId(fange);
	}
	
	public List<Fange> hentFangerForCelleNummer(Integer celleNummer) {
		return arrestantDao.hentAlle().stream()
				.filter(fange -> Objects.equals(fange.getCelleNummer(), celleNummer))
				.toList();
	}

	public void settInn(FangeUtenId fange) {
		arrestantDao.opprett(fange);
	}
	
	public boolean løslat(Fange fange) {
		if (skulleVærtLøslatt(fange)) {
			log.warn("Løslater fange som skulle vært løslatt fra før: {}", fange);
		}
		return arrestantDao.løslat(fange);
	}
	
	public int løslatAlle() {
		return arrestantDao.løslatAlle();
	}
	
	@Transactional
	public void overfør(Fange fange, Integer celleNummer) {
		arrestantDao.oppdater(fange.toBuilder().celleNummer(celleNummer).build());
	}
	
	/*
	 * Viser antall personer for gitt celle.
	 * Ekskluderer registreringer av fanger hvor løslatt-dato er passert,
	 * da det antas at dette indikerer at de faktisk er løslatt
	 */
	public Long antallFangerICelle(Integer celleNummer) {
		return arrestantDao.antallFanger(celleNummer);
	}
	
	
}
