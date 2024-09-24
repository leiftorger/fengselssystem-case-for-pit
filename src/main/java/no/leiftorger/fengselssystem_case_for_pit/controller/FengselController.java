package no.leiftorger.fengselssystem_case_for_pit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import no.leiftorger.fengselssystem_case_for_pit.model.intern.Arrestant;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.ArrestantUtenId;
import no.leiftorger.fengselssystem_case_for_pit.service.ArrestantService;


@RestController()
public class FengselController {
	
	@Autowired
	private ArrestantService arrestantService;
	
	
	@GetMapping("/api/innsatte")
	public ResponseEntity<List<Arrestant>> innsatte() {
		return new ResponseEntity<>(
				arrestantService.hentArrestanter(),
				HttpStatus.OK
				);		
	}
	
	@GetMapping("/api/populer")
	public ResponseEntity<List<Arrestant>> populerFraEksternTjeneste() {
		arrestantService.importerArrestanter();
		return new ResponseEntity<>(
				HttpStatus.OK
				);		
	}
	
	@GetMapping("/api/antallFanger")
	public ResponseEntity<Long> antallArrestanter(@RequestParam Integer celleNummer) {
		return new ResponseEntity<>(
				arrestantService.antallArrestanterICelle(celleNummer),
				HttpStatus.OK
				);		
	}
	
	@PostMapping("/api/overfor")
	public HttpStatus overfor(@RequestBody Arrestant fange, @RequestParam Integer celleNummer) {
		arrestantService.overfør(fange, celleNummer);
		return HttpStatus.OK;
	}
	
	@PostMapping("/api/settInn")
	public HttpStatus settInn(@RequestBody ArrestantUtenId fange) {
		arrestantService.settInn(fange);
		return HttpStatus.OK;
	}
	
	@PostMapping("/api/loslat")
	public HttpStatus løslat(@RequestBody Arrestant arrestant) {
		arrestantService.løslat(arrestant);
		return HttpStatus.OK;
	}
	
}
