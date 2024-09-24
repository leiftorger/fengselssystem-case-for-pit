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
import no.leiftorger.fengselssystem_case_for_pit.model.intern.Arrestant;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.ArrestantUtenId;
import no.leiftorger.fengselssystem_case_for_pit.model.intern.ArrestanterUtenId;

@Repository
@Slf4j
public class ArrestantDao {
	@Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Autowired JdbcTemplate jdbcTemplate;

    public void opprett(ArrestantUtenId arrestant) {
        String sql = "INSERT INTO arrestanter (navn, alder, kjonn, fengslingsDatoFra, fengslingsDatoTil, celleNummer) VALUES (:navn, :alder, :kjonn, :fengslingsDatoFra, :fengslingsDatoTil, :celleNummer)";
        namedJdbcTemplate.update(sql, arrestantParameters(arrestant));
    }
    
    public Arrestant hentUtenId(ArrestantUtenId arrestant) {
    	String sql = "SELECT * FROM arrestanter WHERE (navn = :navn AND alder = :alder AND kjonn = :kjonn AND fengslingsDatoFra = :fengslingsDatoFra AND fengslingsDatoTil = :fengslingsDatoTil AND celleNummer = :celleNummer)";
    	return namedJdbcTemplate.queryForObject(sql, arrestantParameters(arrestant), mapArrestant);
    }
    
    public List<Arrestant> hentAlle() {
        String sql = "SELECT * FROM arrestanter";
        return namedJdbcTemplate.query(sql, mapArrestant);
    }
    
    public Arrestant hent(Integer id) {
    	return namedJdbcTemplate.queryForObject("SELECT * FROM arrestanter WHERE id = :id", Map.of("id", id), mapArrestant);
    }

    public void oppdater(Arrestant fange) {
        String sql = "UPDATE arrestanter SET navn = :navn, alder = :alder, kjonn = :kjonn, fengslingsDatoFra = :fengslingsDatoFra, fengslingsDatoTil = :fengslingsDatoTil, celleNummer = :celleNummer WHERE id = :id";
        namedJdbcTemplate.update(sql, arrestantParameters(fange));
    }
    
    private MapSqlParameterSource arrestantParameters(ArrestantUtenId arrestant) {
    	return new MapSqlParameterSource(Map.of(
    			"navn", arrestant.getNavn(),
    			"alder", arrestant.getAlder(),
    			"kjonn", arrestant.getKjonn(),
    			"fengslingsDatoFra", arrestant.getFengslingsDatoFra(),
    			"fengslingsDatoTil", arrestant.getFengslingsDatoTil(),
    			"celleNummer", arrestant.getCelleNummer())
    			);
    }
    
    private MapSqlParameterSource arrestantParameters(Arrestant arrestant) {
    	return new MapSqlParameterSource(Map.of(
    			"id", arrestant.getId(),
    			"navn", arrestant.getNavn(),
    			"alder", arrestant.getAlder(),
    			"kjonn", arrestant.getKjonn(),
    			"fengslingsDatoFra", arrestant.getFengslingsDatoFra(),
    			"fengslingsDatoTil", arrestant.getFengslingsDatoTil(),
    			"celleNummer", arrestant.getCelleNummer())
    			);
    }
    
    private RowMapper<Arrestant> mapArrestant = (resultSet, row) -> {
		return Arrestant.builder()
				.id(resultSet.getInt("id"))
        		.navn(resultSet.getString("navn"))
        		.alder(resultSet.getInt("alder"))
        		.kjonn(resultSet.getString("kjonn"))
        		.fengslingsDatoFra(resultSet.getTimestamp("fengslingsDatoFra").toLocalDateTime().toLocalDate())
        		.fengslingsDatoTil(resultSet.getTimestamp("fengslingsDatoTil").toLocalDateTime().toLocalDate())
        		.celleNummer(resultSet.getInt("celleNummer"))
        		.build();
    };

	public void opprett(ArrestanterUtenId arrestanter) {
		arrestanter.arrestanter.forEach(arrestant -> opprett(arrestant));
		log.info("Opprettet {} fanger.", arrestanter.getArrestanter().size());
	}

	public boolean løslat(Arrestant arrestant) {
		return 1 == namedJdbcTemplate.update("delete from arrestanter where id = :id", new MapSqlParameterSource("id", arrestant.getId()));
	}
	
	public int løslatAlle() {
		return jdbcTemplate.update("delete from arrestanter");
	}
	
	public long antallArrestanter(Integer celleNummer) {
		return namedJdbcTemplate.queryForObject("select count (*) from arrestanter where celleNummer = :celleNummer", new MapSqlParameterSource("celleNummer", celleNummer), Long.class);
	}
}