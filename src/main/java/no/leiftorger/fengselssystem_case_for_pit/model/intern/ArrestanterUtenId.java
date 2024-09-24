package no.leiftorger.fengselssystem_case_for_pit.model.intern;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder(toBuilder = true)
public class ArrestanterUtenId {
	public List<ArrestantUtenId> arrestanter;
}
