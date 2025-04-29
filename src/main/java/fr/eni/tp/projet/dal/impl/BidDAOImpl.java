package fr.eni.tp.projet.dal.impl;

import fr.eni.tp.projet.bo.Bid;
import fr.eni.tp.projet.dal.BidDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BidDAOImpl implements BidDAO {

    private static final String SELECT_ALL = "SELECT * FROM BIDS";
    private static final String SELECT_BY_USER_ID = "SELECT * FROM BIDS WHERE :user_id = :user_id";
    private static final String SELECT_BY_ITEM_ID = "SELECT * FROM BIDS WHERE :item_id = :item_id";
    private static final String CREATE_BID = "INSERT INTO BIDS VALUES (:user_id, :item_id, :bid_date, :bid_price)";
    private static final String DELETE_BID = "DELETE FROM BIDS WHERE item_id = :item_id";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BidDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Bid> findAllBids() {
        return jdbcTemplate.query(
                SELECT_ALL,
                new BidRowMapper()
        );
    }

    @Override
    public Bid findBidByUserId(long user_id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_id", user_id);
        return namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_USER_ID,
                mapSqlParameterSource,
                new BidRowMapper()
        );
    }

    @Override
    public Bid findBidByItemId(long item_id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("item_id", item_id);
        return namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_ITEM_ID,
                mapSqlParameterSource,
                new BidRowMapper()
        );
    }

    @Override
    public void createBid(Bid bid) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("item_id", bid.getBidIdItem());
        mapSqlParameterSource.addValue("user_id", bid.getBidIdUser());
        mapSqlParameterSource.addValue("bid_date", bid.getBidDate());
        mapSqlParameterSource.addValue("bid_price", bid.getBidAmount());
        namedParameterJdbcTemplate.update(
                CREATE_BID,
                mapSqlParameterSource
        );

    }

    @Override
    public void deleteBid(long item_id) {

    }
}

class BidRowMapper implements RowMapper<Bid> {
    @Override
    public Bid mapRow(ResultSet rs, int rowNum) throws SQLException {
        Bid bid = new Bid();

        bid.setBidIdUser(rs.getLong("user_id"));
        bid.setBidIdItem(rs.getLong("item_id"));
        bid.setBidDate(rs.getString("bid_date"));
        bid.setBidAmount(rs.getLong("bid_price"));
        return bid;
    }


}