package no.leiftorger.fengselssystem_case_for_pit.model.intern;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder(toBuilder = true)
@Jacksonized
public class ArrestantUtenId {
	String navn;
	Integer alder;
	String kjonn;
	Integer celleNummer;
	LocalDate fengslingsDatoFra;
	LocalDate fengslingsDatoTil;
}
