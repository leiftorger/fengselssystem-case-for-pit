package no.leiftorger.fengselssystem_case_for_pit.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.Fange;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.FangeUtenId;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.FangerUtenId;

@Repository
@Slf4j
public class ArrestantDao {
	@Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Autowired JdbcTemplate jdbcTemplate;

    public void opprett(FangeUtenId fange) {
        String sql = "INSERT INTO arrestanter (navn, alder, kjonn, fengslingsDatoFra, fengslingsDatoTil, celleNummer) VALUES (:navn, :alder, :kjonn, :fengslingsDatoFra, :fengslingsDatoTil, :celleNummer)";
        namedJdbcTemplate.update(sql, fangeParameters(fange));
    }
    
    public Fange hentUtenId(FangeUtenId fange) {
    	String sql = "SELECT * FROM arrestanter WHERE (navn = :navn AND alder = :alder AND kjonn = :kjonn AND fengslingsDatoFra = :fengslingsDatoFra AND fengslingsDatoTil = :fengslingsDatoTil AND celleNummer = :celleNummer)";
    	return namedJdbcTemplate.queryForObject(sql, fangeParameters(fange), mapFange);
    }
    
    public List<Fange> hentAlle() {
        String sql = "SELECT * FROM arrestanter";
        return namedJdbcTemplate.query(sql, mapFange);
    }

    public void oppdater(Fange fange) {
        String sql = "UPDATE arrestanter SET navn = :navn, alder = :alder, kjonn = :kjonn, fengslingsDatoFra = :fengslingsDatoFra, fengslingsDatoTil = :fengslingsDatoTil, celleNummer = :celleNummer WHERE id = :id";
        namedJdbcTemplate.update(sql, fangeParameters(fange));
    }
    
    private MapSqlParameterSource fangeParameters(FangeUtenId fange) {
    	return new MapSqlParameterSource(Map.of(
    			"navn", fange.getNavn(),
    			"alder", fange.getAlder(),
    			"kjonn", fange.getKjonn(),
    			"fengslingsDatoFra", fange.getFengslingsDatoFra(),
    			"fengslingsDatoTil", fange.getFengslingsDatoTil(),
    			"celleNummer", fange.getCelleNummer())
    			);
    }
    
    private MapSqlParameterSource fangeParameters(Fange fange) {
    	return new MapSqlParameterSource(Map.of(
    			"id", fange.getId(),
    			"navn", fange.getNavn(),
    			"alder", fange.getAlder(),
    			"kjonn", fange.getKjonn(),
    			"fengslingsDatoFra", fange.getFengslingsDatoFra(),
    			"fengslingsDatoTil", fange.getFengslingsDatoTil(),
    			"celleNummer", fange.getCelleNummer())
    			);
    }
    
    private RowMapper<Fange> mapFange = (resultSet, row) -> {
		return Fange.builder()
				.id(resultSet.getInt("id"))
        		.navn(resultSet.getString("navn"))
        		.alder(resultSet.getInt("alder"))
        		.kjonn(resultSet.getString("kjonn"))
        		.fengslingsDatoFra(resultSet.getTimestamp("fengslingsDatoFra").toLocalDateTime().toLocalDate())
        		.fengslingsDatoTil(resultSet.getTimestamp("fengslingsDatoTil").toLocalDateTime().toLocalDate())
        		.celleNummer(resultSet.getInt("celleNummer"))
        		.build();
    };

	public void opprett(FangerUtenId fanger) {
		fanger.fanger.forEach(fange -> opprett(fange));
		log.info("Opprettet {} fanger.", fanger.getFanger().size());
	}

	public boolean løslat(Fange fange) {
		return 1 == namedJdbcTemplate.update("delete from arrestanter where id = :id", new MapSqlParameterSource("id", fange.getId()));
	}
	
	public int løslatAlle() {
		return jdbcTemplate.update("delete from arrestanter");
	}
	
	public long antallFanger(Integer celleNummer) {
		return namedJdbcTemplate.queryForObject("select count (*) from arrestanter where celleNummer = :celleNummer", new MapSqlParameterSource("celleNummer", celleNummer), Long.class);
	}
}