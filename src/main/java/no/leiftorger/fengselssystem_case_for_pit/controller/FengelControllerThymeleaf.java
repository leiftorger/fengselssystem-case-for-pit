package no.leiftorger.fengselssystem_case_for_pit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import no.leiftorger.fengselssystem_case_for_pit.service.FangeService;

@Controller
public class FengelControllerThymeleaf {
	
	@Autowired
	FangeService fangeService;

    @GetMapping("/arrestanter")
    public String arrestanter(Model model) {
        model.addAttribute("arrestanter", fangeService.hentFanger());
        return "arrestanter";
    }
}
