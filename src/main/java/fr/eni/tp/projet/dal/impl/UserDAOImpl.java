package fr.eni.tp.projet.dal.impl;

import fr.eni.tp.projet.bo.User;
import fr.eni.tp.projet.dal.UserDAO;
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
public class UserDAOImpl implements UserDAO {

    private static final String SELECT_ALL = "SELECT * FROM USERS";
    private static final String SELECT_BY_ID = "SELECT * FROM USERS WHERE user_id = :user_id";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM USERS WHERE email = :email";
    private static final String CREATE_USER = "INSERT INTO USERS VALUES (:pseudo, :name, :firstName, :email, :phone, :street, :city, :post_code, :password, :credit, :administrator)";
    private static final String DELETE_USER = "DELETE FROM USERS WHERE user_id = :user_id";
    private static final String UPDATE_USER = "";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public User findById(long user_id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_id", user_id);

        return namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_ID,
                mapSqlParameterSource,
                new UserRowMapper()
        );

    }

    @Override
    public User findByEmail(String email) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("email", email);

        return namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_EMAIL,
                mapSqlParameterSource,
                new UserRowMapper()
        );
    }

    @Override
    public List<User> findAllUsers() {
        return jdbcTemplate.query(
                SELECT_ALL,
                new UserRowMapper()
        );
    }

    @Override
    public void createUser(User user) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("pseudo", user.getPseudo());
        mapSqlParameterSource.addValue("name", user.getName());
        mapSqlParameterSource.addValue("firstName", user.getFirstName());
        mapSqlParameterSource.addValue("email", user.getEmail());
        mapSqlParameterSource.addValue("phone", user.getPhone());
        mapSqlParameterSource.addValue("street", user.getStreet());
        mapSqlParameterSource.addValue("city", user.getCity());
        mapSqlParameterSource.addValue("post_code", user.getPostalCode());
        mapSqlParameterSource.addValue("password", user.getPassword());
        mapSqlParameterSource.addValue("credit", user.getCredit());
        mapSqlParameterSource.addValue("administrator", user.getAdministrator());



        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                CREATE_USER,
                mapSqlParameterSource,
                keyHolder,
                new String[]{"user_id"}
        );

        user.setIdUser(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }


    @Override
    public void deleteUser(long user_id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_id", user_id);

        namedParameterJdbcTemplate.update(
                DELETE_USER,
                mapSqlParameterSource
        );

    }
}

class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setIdUser(rs.getLong("id"));
        user.setPseudo(rs.getString("pseudo"));
        user.setName(rs.getString("name"));
        user.setFirstName(rs.getString("first_name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getLong("phone"));
        user.setStreet(rs.getString("street"));
        user.setCity(rs.getString("city"));
        user.setPostalCode(rs.getLong("post_code"));
        user.setPassword(rs.getString("password"));
        user.setCredit(rs.getLong("credit"));
        user.setAdministrator(rs.getInt("administrator"));
        return user;
    }
}