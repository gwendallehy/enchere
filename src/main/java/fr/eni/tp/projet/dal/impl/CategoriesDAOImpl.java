package fr.eni.tp.projet.dal.impl;

import fr.eni.tp.projet.bo.Categories;
import fr.eni.tp.projet.dal.CategoriesDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
public class CategoriesDAOImpl implements CategoriesDAO {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SELECT_ALL = "SELECT * FROM CATEGORIES";
    private static final String SELECT_BY_ID = "SELECT * FROM CATEGORIES WHERE category_id = :category_id";
    private static final String CREATE_CAT = "INSERT INTO CATEGORIES (category_id, category_desc) VALUES (:category_id, :category_desc)";

    public CategoriesDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Categories findCategoriesById(int category_id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("category_id", category_id);

        return namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_ID,
                mapSqlParameterSource,
                new CategoriesRowMapper()
        );
    }

    @Override
    public List<Categories> findAllCategories() {
        return jdbcTemplate.query(
                SELECT_ALL,
                new CategoriesRowMapper()
        );
    }

    @Override
    public void createCategories(Categories categories) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("category_desc", categories.getWording());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                CREATE_CAT,
                mapSqlParameterSource,
                keyHolder,
                new String[]{"category_id"}
        );

        categories.setIdCategory(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }
}
class CategoriesRowMapper implements RowMapper<Categories> {
    @Override
    public Categories mapRow(ResultSet rs, int rowNum) throws SQLException {
        Categories category = new Categories();
        category.setIdCategory(rs.getLong("category_id"));
        category.setWording(rs.getString("category_desc"));
        return category;
    }
}