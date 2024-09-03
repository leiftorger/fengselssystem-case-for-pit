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

import no.leiftorger.fengselssystem_case_for_pit.model.Fange;
import no.leiftorger.fengselssystem_case_for_pit.service.FangeService;


@RestController()
public class FengselController {
	
	@Autowired
	private FangeService fangeService;
	
	
	@GetMapping("/api/innsatte")
	public ResponseEntity<List<Fange>> innsatte() {
		return new ResponseEntity<>(
				fangeService.hentFanger(),
				HttpStatus.OK
				);		
	}
	
	@GetMapping("/api/antallFanger")
	public ResponseEntity<Long> antallFanger(@RequestParam Integer celleNummer) {
		return new ResponseEntity<>(
				fangeService.antallFangerICelle(celleNummer),
				HttpStatus.OK
				);		
	}
	
	@PostMapping("/api/overfor")
	public HttpStatus overfør(@RequestBody Fange fange, @RequestParam Integer celleNummer) {
		fangeService.overfør(fange, celleNummer);
		return HttpStatus.OK;
	}
	
	@PostMapping("/api/settInn")
	public HttpStatus settInn(@RequestBody Fange fange) {
		fangeService.settInn(fange);
		return HttpStatus.OK;
	}
	
	@PostMapping("/api/loslat")
	public HttpStatus løslat(@RequestBody Fange fange) {
		fangeService.løslat(fange);
		return HttpStatus.OK;
	}
	
}
