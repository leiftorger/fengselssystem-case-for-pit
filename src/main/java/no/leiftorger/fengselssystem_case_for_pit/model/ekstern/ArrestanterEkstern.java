package no.leiftorger.fengselssystem_case_for_pit.model.ekstern;

import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class ArrestanterEkstern {
	// denne heter "Fanger" her med stor F fordi eksternt API krever dette i mapping 
	public List<ArrestantEkstern> Fanger;
}
