package fr.eni.tp.projet.dal.impl;

import fr.eni.tp.projet.bo.Categories;
import fr.eni.tp.projet.bo.Pickup;
import fr.eni.tp.projet.bo.User;
import fr.eni.tp.projet.dal.PickUpDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Repository
public class PickUpDAOImpl implements PickUpDAO {
    private static final String SELECT_ALL = "SELECT * FROM PICKUPS";
    private static final String SELECT_BY_ID = "SELECT * FROM PICKUPS WHERE :item_id = :item_id";
    private static final String CREATE = "INSERT INTO PICKUPS (item_id, address, city, post_code) VALUES (:item_id, :address, :city, :post_code)";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PickUpDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Pickup FindPickupById(long item_id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("item_id", item_id);

        return namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_ID,
                mapSqlParameterSource,
                new PickUpRowMapper()
        );

    }

    public void createPickUp(Pickup pickup) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("item_id", pickup.getIdPickup());
        mapSqlParameterSource.addValue("address", pickup.getStreet());
        mapSqlParameterSource.addValue("city", pickup.getCity());
        mapSqlParameterSource.addValue("post_code", pickup.getPostalCode());
        namedParameterJdbcTemplate.update(
                CREATE,
                mapSqlParameterSource
        );
    }

}
class PickUpRowMapper implements RowMapper<Pickup> {
    @Override
    public Pickup mapRow(ResultSet rs, int rowNum) throws SQLException {
        Pickup pickup = new Pickup();
        pickup.setIdPickup(rs.getLong("item_id"));
        pickup.setStreet(rs.getString("address"));
        pickup.setCity(rs.getString("city"));
        pickup.setPostalCode(rs.getLong("post_code"));
        return pickup;
    }
}


