package fr.eni.tp.projet.dal.impl;

import fr.eni.tp.projet.bo.Bid;
import fr.eni.tp.projet.dal.BidDAO;
import fr.eni.tp.projet.exception.InsufficientCreditsException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public void createBid(Bid bid) {
        // Vérifie s'il existe déjà une enchère pour cet article
        String sql = "SELECT * FROM bids WHERE item_id = :item_id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("item_id", bid.getBidIdItem());

        List<Bid> existingBids = namedParameterJdbcTemplate.query(sql, params, new BidRowMapper());

        long bidAmount = bid.getBidAmount();
        long newUserId = bid.getBidIdUser();

        // Vérifie les crédits du nouvel utilisateur
        String creditQuery = "SELECT credit FROM users WHERE user_id = :user_id";
        MapSqlParameterSource creditParams = new MapSqlParameterSource().addValue("user_id", newUserId);
        Integer newUserCredits = namedParameterJdbcTemplate.queryForObject(creditQuery, creditParams, Integer.class);

        if (newUserCredits == null || newUserCredits < bidAmount) {
            throw new InsufficientCreditsException("User does not have enough credits to place the bid.");
        }

        if (!existingBids.isEmpty()) {
            Bid existingBid = existingBids.get(0);
            long oldUserId = existingBid.getBidIdUser();
            long oldBidAmount = existingBid.getBidAmount();

            // Rembourse l'ancien utilisateur
            String refundSql = "UPDATE users SET credit = credit + :amount WHERE user_id = :user_id";
            namedParameterJdbcTemplate.update(refundSql,
                    new MapSqlParameterSource()
                            .addValue("amount", oldBidAmount)
                            .addValue("user_id", oldUserId));

            // Met à jour le bid existant avec le nouvel utilisateur
            String updateBidSql = "UPDATE bids SET user_id = :user_id, bid_price = :bid_price, bid_date = :bid_date " +
                    "WHERE item_id = :item_id";
            MapSqlParameterSource updateParams = new MapSqlParameterSource()
                    .addValue("user_id", newUserId)
                    .addValue("bid_price", bidAmount)
                    .addValue("bid_date", bid.getBidDate())
                    .addValue("item_id", bid.getBidIdItem());

            namedParameterJdbcTemplate.update(updateBidSql, updateParams);
        } else {
            // Aucune enchère : insère une nouvelle ligne
            String insertSql = "INSERT INTO bids (user_id, item_id, bid_date, bid_price) " +
                    "VALUES (:user_id, :item_id, :bid_date, :bid_price)";
            MapSqlParameterSource insertParams = new MapSqlParameterSource()
                    .addValue("user_id", newUserId)
                    .addValue("item_id", bid.getBidIdItem())
                    .addValue("bid_date", bid.getBidDate())
                    .addValue("bid_price", bidAmount);

            namedParameterJdbcTemplate.update(insertSql, insertParams);
        }

        // Déduit les crédits du nouvel utilisateur
        String deductSql = "UPDATE users SET credit = credit - :amount WHERE user_id = :user_id";
        namedParameterJdbcTemplate.update(deductSql,
                new MapSqlParameterSource()
                        .addValue("amount", bidAmount)
                        .addValue("user_id", newUserId));
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
