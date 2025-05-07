package fr.eni.tp.projet.dal.impl;

import fr.eni.tp.projet.bo.Bid;
import fr.eni.tp.projet.dal.BidDAO;
import fr.eni.tp.projet.exception.InsufficientCreditsException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BidDAOImpl implements BidDAO {

    private static final String SELECT_ALL = "SELECT * FROM BIDS";


    private static final String SELECT_BY_USER_ID = "SELECT * FROM BIDS WHERE user_id = :user_id";
    private static final String SELECT_BY_ITEM_ID = "SELECT * FROM BIDS WHERE item_id = :item_id";


    //Bid
    private static final String SELECT_STATUS_USER ="SELECT * FROM BIDS as b INNER JOIN ITEMS_SOLD as i ON b.item_id = i.item_id WHERE b.user_id= :user_id AND status = :status"; //Select BID JOIN Article
    private static final String SELECT_BID_BY_USER = "SELECT * FROM BIDS WHERE user_id= :user_id"; //Select BID JOIN Article


    private static final String CREATE_BID = "INSERT INTO BIDS VALUES (:user_id, :item_id, :bid_date, :bid_price)";
    private static final String DELETE_BID = "DELETE FROM BIDS WHERE item_id = :item_id";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BidDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     *
     * Trouver toutes les enchères (un User enchere sur un Article)
     */

    @Override
    public List<Bid> findAllBids() {
        return jdbcTemplate.query(
                SELECT_ALL,
                new BidRowMapper()
        );
    }
    /**
     *
     * Trouver les encheres par un userId
     */

    @Override
    public List<Bid> findBidByUserId(long user_id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_id", user_id);
        return namedParameterJdbcTemplate.query(
                SELECT_BY_USER_ID,
                mapSqlParameterSource,
                new BidRowMapper()
        );
    }

    /**
     *
     * Trouver les encheres pour un article
     */

    @Override
    public Bid findBidByItemId(long item_id) {
        String sql = "SELECT TOP 1 * FROM bids WHERE item_id = :item_id ORDER BY bid_price DESC";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("item_id", item_id);

        List<Bid> bids = namedParameterJdbcTemplate.query(sql, params, new BidRowMapper());
        return bids.isEmpty() ? null : bids.get(0);
    }



    @Override
    public List<Bid> findBidAndWinByUser(long user_id, String status) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("user_id", user_id);
        parameters.addValue("status", status);

        return namedParameterJdbcTemplate.query(
                SELECT_STATUS_USER,
                parameters,
                new BidRowMapper()
        );

    }


    @Override
    public List<Bid> findBidByUser(long user_id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_id", user_id);

        return namedParameterJdbcTemplate.query(
                SELECT_BID_BY_USER,
                mapSqlParameterSource,
                new BidRowMapper()
        );
    }
    /**
     *
     * Encherir
     */

    @Override
    public void createBid(Bid bid) {
        // Check if a bid already exists for the same item and user
        String sql = "SELECT * FROM bids WHERE item_id = :item_id AND user_id = :user_id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("item_id", bid.getBidIdItem());
        params.addValue("user_id", bid.getBidIdUser());

        List<Bid> existingBids = namedParameterJdbcTemplate.query(sql, params, new BidRowMapper());

        // Check if the user has enough credits
        String userCreditsSql = "SELECT credits FROM users WHERE user_id = :user_id";
        MapSqlParameterSource userCreditsParams = new MapSqlParameterSource();
        userCreditsParams.addValue("user_id", bid.getBidIdUser());

        Integer currentCredits = namedParameterJdbcTemplate.queryForObject(userCreditsSql, userCreditsParams, Integer.class);

        // Check if user has sufficient credits
        if (currentCredits == null || currentCredits < bid.getBidAmount()) {
            throw new InsufficientCreditsException("User does not have enough credits to place the bid.");
        }

        if (!existingBids.isEmpty()) {
            // If a bid exists, update it
            String updateBidQuery = "UPDATE bids SET bid_date = :bid_date, bid_price = :bid_price " +
                    "WHERE user_id = :user_id AND item_id = :item_id";

            MapSqlParameterSource updateParams = new MapSqlParameterSource();
            updateParams.addValue("bid_date", bid.getBidDate());
            updateParams.addValue("bid_price", bid.getBidAmount());
            updateParams.addValue("user_id", bid.getBidIdUser());
            updateParams.addValue("item_id", bid.getBidIdItem());

            namedParameterJdbcTemplate.update(updateBidQuery, updateParams);
        } else {
            // Otherwise, insert a new bid
            String insertBidQuery = "INSERT INTO bids (user_id, item_id, bid_date, bid_price) " +
                    "VALUES (:user_id, :item_id, :bid_date, :bid_price)";

            MapSqlParameterSource insertParams = new MapSqlParameterSource();
            insertParams.addValue("user_id", bid.getBidIdUser());
            insertParams.addValue("item_id", bid.getBidIdItem());
            insertParams.addValue("bid_date", bid.getBidDate());
            insertParams.addValue("bid_price", bid.getBidAmount());

            namedParameterJdbcTemplate.update(insertBidQuery, insertParams);
        }

        // Deduct credits from the user
        String deductCreditsQuery = "UPDATE users SET credits = credits - :credits WHERE user_id = :user_id";
        MapSqlParameterSource deductCreditsParams = new MapSqlParameterSource();
        deductCreditsParams.addValue("credits", bid.getBidAmount());
        deductCreditsParams.addValue("user_id", bid.getBidIdUser());

        // Perform the credit deduction
        namedParameterJdbcTemplate.update(deductCreditsQuery, deductCreditsParams);
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
