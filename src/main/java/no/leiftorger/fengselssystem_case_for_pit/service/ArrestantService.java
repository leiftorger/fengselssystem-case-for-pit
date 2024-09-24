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
import no.leiftorger.fengselssystem_case_for_pit.model.ekstern.ArrestanterEkstern;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.Arrestant;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.ArrestantUtenId;

/**
 * Ansvar for å tilby forretningsfunksjoner mot for fengselssystemet
 * samt å kunne fylle internt register med initelle data fra egen tjeneste
 *
 */
@Service
@Slf4j
public class ArrestantService {

	private final ArrestantApiKlient apiKlient;
	private final ObjectMapper fengselOjectMapper;
	private ArrestantDao arrestantDao;
	
	public ArrestantService(ArrestantApiKlient client, ObjectMapper fengselOjectMapper, ArrestantDao arrestantDao) {
		this.apiKlient = client;
		this.fengselOjectMapper = fengselOjectMapper;
		this.arrestantDao = arrestantDao;
	}
	
	private ArrestanterEkstern fangerFraJson(String fangerJson) {
		try {
			return fengselOjectMapper.readValue(fangerJson, ArrestanterEkstern.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("kunne ikke parse json til Fange", e);
		}
	}
	
	public void importerArrestanter() {
		arrestantDao.opprett(
				ModelMapper.map(
						fangerFraJson(apiKlient.hentFangerSomJson())
						)
				);
	}
	
	private boolean skulleVærtLøslatt(Arrestant fange) {
		LocalDate nå = LocalDate.now();
		return nå.isAfter(fange.getFengslingsDatoTil());
	}
	
	public List<Arrestant> hentArrestanter() {
		return arrestantDao.hentAlle();
	}
	
	public Arrestant hentUtenId(ArrestantUtenId fange) {
		return arrestantDao.hentUtenId(fange);
	}
	
	public List<Arrestant> hentFangerForCelleNummer(Integer celleNummer) {
		return arrestantDao.hentAlle().stream()
				.filter(fange -> Objects.equals(fange.getCelleNummer(), celleNummer))
				.toList();
	}

	public void settInn(ArrestantUtenId arrestant) {
		arrestantDao.opprett(arrestant);
	}
	
	public boolean løslat(Arrestant arrestant) {
		if (skulleVærtLøslatt(arrestant)) {
			log.warn("Løslater fange som skulle vært løslatt fra før: {}", arrestant);
		}
		return arrestantDao.løslat(arrestant);
	}
	
	public int løslatAlle() {
		return arrestantDao.løslatAlle();
	}
	
	@Transactional
	public void overfør(Arrestant arrestant, Integer celleNummer) {
		arrestantDao.oppdater(arrestant.toBuilder().celleNummer(celleNummer).build());
	}
	
	public Arrestant hent(Integer id) {
		return arrestantDao.hent(id);
	}
	
	/*
	 * Viser antall personer for gitt celle.
	 * Ekskluderer registreringer av fanger hvor løslatt-dato er passert,
	 * da det antas at dette indikerer at de faktisk er løslatt
	 */
	public Long antallArrestanterICelle(Integer celleNummer) {
		return arrestantDao.antallArrestanter(celleNummer);
	}
	
	
}
