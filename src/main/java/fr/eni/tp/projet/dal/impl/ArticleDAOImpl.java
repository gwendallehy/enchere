package fr.eni.tp.projet.dal.impl;

import fr.eni.tp.projet.bo.Article;
import fr.eni.tp.projet.bo.Categories;
import fr.eni.tp.projet.bo.Pickup;
import fr.eni.tp.projet.dal.ArticleDAO;
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
public class ArticleDAOImpl implements ArticleDAO {

    private static final String FIND_ALL = "SELECT * FROM ITEMS_SOLD";
    private static final String SELECT_BY_ID = "SELECT * FROM ITEMS_SOLD WHERE article_id = :article_id";
    private static final String SELECT_SALES_BY_USER = "SELECT * FROM ITEMS_SOLD WHERE user_id = :user_id";
    private static final String CREATE_A_SALE = "INSERT INTO ITEMS_SOLD (item_name, description, auction_date_begin, auction_date_end, price_init, price_selling, user_id, category_id, picture_url) VALUES\n" +
            "(:item_name, :description, :auction_date_begin, NULL, :price_init, :price_selling, :user_id, :category_id, :picture_url);";
    private static final String CANCEL_A_SALE = "DELETE FROM ITEMS_SOLD WHERE item_id = :article_id;";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ArticleDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    /**
     *
     * Trouver tous les articles
     */

    @Override
    public List<Article> findAllArticles() {
        return jdbcTemplate.query(
                FIND_ALL,
                new ArticleRowMapper()
        );
    }

    /**
     *
     * Trouver un article avec son ID
     */

    @Override
    public Article findArticleById(long article_id){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("article_id", article_id);

        return namedParameterJdbcTemplate.queryForObject(
                SELECT_SALES_BY_USER,
                mapSqlParameterSource,
                new ArticleRowMapper()
        );
    }

    /**
     *
     * Trouver les articles avec son IDUser
     */

    @Override
    public List<Article> findSalesByUser(long userId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("user_id", userId);

        return namedParameterJdbcTemplate.query(
                SELECT_SALES_BY_USER,
                parameters,
                new ArticleRowMapper()
        );
    }

    @Override
    public void sellAnArticle(Article article, long user_id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("item_id", article.getIdArticle());
        mapSqlParameterSource.addValue("description", article.getDescription());
        mapSqlParameterSource.addValue("auction_date_begin", article.getStartDate());
        mapSqlParameterSource.addValue("price_init", article.getBetAPrice());
        mapSqlParameterSource.addValue("price_selling", article.getSalePrice());
        mapSqlParameterSource.addValue("user_id", user_id);
        mapSqlParameterSource.addValue("category_id", article.getCategory().getIdCategory());
        mapSqlParameterSource.addValue("picture_url", article.getPicture());

        mapSqlParameterSource.addValue("user_id", user_id);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                CREATE_A_SALE,
                mapSqlParameterSource,
                keyHolder,
                new String[]{"item_id"}
        );

        article.setIdArticle(Objects.requireNonNull(keyHolder.getKey()).intValue());
    }

    @Override
    public void cancelASell(long article_id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("article_id", article_id);

        jdbcTemplate.update(
                CANCEL_A_SALE,
                article_id
        );
    }
}

class ArticleRowMapper implements RowMapper<Article> {

    @Override
    public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
        Article article = new Article();

        article.setIdArticle(rs.getLong("item_id"));
        article.setName(rs.getString("item_name"));
        article.setDescription(rs.getString("description"));
        article.setStartDate(rs.getString("auction_date_begin"));
        article.setEndDate(rs.getString("auction_date_end"));
        article.setBetAPrice(rs.getLong("price_init"));
        article.setSalePrice(rs.getLong("price_selling"));
        article.setUser(rs.getLong("user_id"));
        article.setStatus(rs.getString("status"));
        article.setPicture(rs.getString("picture_url"));


        return article;
    }
}