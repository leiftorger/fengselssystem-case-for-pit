package no.leiftorger.fengselssystem_case_for_pit.model;

import java.util.List;
import java.util.Objects;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Fanger {
	public List<Fange> Fanger;
	
	public synchronized void leggTil(Fange fange) {
		Fanger.add(fange);
	}
	public synchronized boolean fjern(Fange fange) {
		return Fanger.remove(fange);
	}
	
	public synchronized void overfør(Fange fange, Integer celleNummer) {
		Fanger.stream()
		.filter(filterFange -> Objects.equals(fange, filterFange)).findFirst()
		.ifPresent(fangeFunnet -> {
			
			Fanger.remove(fangeFunnet);
			
			Fange flyttFange = fangeFunnet.toBuilder()
					.celleNummer(celleNummer)
					.build();
			
			Fanger.add(flyttFange);
		});
	}
}
