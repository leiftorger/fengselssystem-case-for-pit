package no.leiftorger.fengselssystem_case_for_pit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import no.leiftorger.fengselssystem_case_for_pit.model.intern.Arrestant;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.ArrestantUtenId;
import no.leiftorger.fengselssystem_case_for_pit.service.ArrestantService;

@Controller
public class FengelControllerThymeleaf {
	
	@Autowired
	ArrestantService arrestantService;

    @GetMapping("/arrestanter")
    public String arrestanter(Model model) {
        model.addAttribute("arrestanter", arrestantService.hentArrestanter());
        model.addAttribute("arrestantUtenId", ArrestantUtenId.builder().build());
        return "arrestanter";
    }
    
    @GetMapping("/overfor")
    public String overfor(@RequestParam Integer celleNummer, @RequestParam Integer arrestantId, Model model) {
    	arrestantService.overfør(arrestantService.hent(arrestantId), celleNummer);
    	return "redirect:/arrestanter";
    }
    
    @GetMapping("/loslat")
    public String loslat(@RequestParam Integer arrestantId, Model model) {
    	arrestantService.løslat(arrestantService.hent(arrestantId));
    	return "redirect:/arrestanter";
    }
    
    @GetMapping("/importer")
    public String importer(Model model) {
    	System.out.println("importerer arrestanter");
    	arrestantService.importerArrestanter();
    	return "redirect:/arrestanter";
    }
    
    @PostMapping("/settInn")
    public String settInn(@ModelAttribute ArrestantUtenId arrestant) {
    	System.out.println("setter inn arrestant " + arrestant);
    	arrestantService.settInn(arrestant);
    	return "redirect:/arrestanter";
    }
}