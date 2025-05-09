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
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ArticleDAOImpl implements ArticleDAO {

    private static final String FIND_ALL = "SELECT * FROM ITEMS_SOLD";
    private static final String SELECT_BY_ID = "SELECT * FROM ITEMS_SOLD INNER JOIN CATEGORIES ON ITEMS_SOLD.category_id = CATEGORIES.category_id WHERE item_id = :article_id";
    private static final String SELECT_SALES_BY_USER = "SELECT * FROM ITEMS_SOLD WHERE user_id = :user_id";
    private static final String CREATE_A_SALE = "INSERT INTO ITEMS_SOLD (item_name, description, auction_date_begin, auction_date_end, price_init, price_selling, user_id, category_id, picture_url, status) VALUES\n" +
            "(:item_name, :description, :auction_date_begin, :auction_date_end, :price_init, :price_selling, :user_id, :category_id, :picture_url, :status);";
    private static final String CANCEL_A_SALE = "DELETE FROM ITEMS_SOLD WHERE item_id = ?";

    private static final String FIND_ALL_EC ="SELECT * FROM ITEMS_SOLD WHERE status = 'EC'";
    private static final String SELECT_ARTICLES_BY_USER_AND_STATUS = "SELECT * FROM ITEMS_SOLD WHERE status = :status AND user_id = :user_id;";
    private static final String SELECT_MY_STATUS_USER ="SELECT * FROM ITEMS_SOLD WHERE status = :status AND user_id = :user_id";




    private static final String SELECT_BY_STATUS_AND_START_DATE = "SELECT * FROM ITEMS_SOLD WHERE status = :status AND auction_date_begin = :date;";
    private static final String SELECT_BY_STATUS_AND_END_DATE = "SELECT * FROM ITEMS_SOLD WHERE status = :status AND auction_date_end = :date;";

    private static final String UPDATE_ARTICLE_STATUS_BY_ID = "UPDATE ITEMS_SOLD SET status = :status WHERE item_id = :id_article;";

    private static final String SELECT_FILTER = "SELECT * FROM ITEMS_SOLD WHERE category_id = :category_id AND item_name LIKE '%' + :item_name + '%'";
    private static final String SELECT_FILTER_WITHOUT_NAME = "SELECT * FROM ITEMS_SOLD WHERE category_id = :category_id";
    private static final String SELECT_FILTER_WITHOUT_CATE = "SELECT * FROM ITEMS_SOLD WHERE item_name LIKE '%' + :item_name + '%'";




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
                SELECT_BY_ID,
                mapSqlParameterSource,
                new ArticleCategoryRowMapper()
        );
    }

    /**
     *
     * Trouver les articles avec son IDUser
     */

    @Override
    public List<Article> findByFilter(String item_name,long category_id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("item_name", item_name);
        parameters.addValue("category_id", category_id);

        // SI name est vide ou null mais il y a une catégorie
        if (item_name == null || item_name.isEmpty() && (category_id > 0)) {
            return namedParameterJdbcTemplate.query(
                    SELECT_FILTER_WITHOUT_NAME,
                    parameters,
                    new ArticleRowMapper()
            );
        }
        if (item_name == null || item_name.isEmpty() && (category_id == 0)) {
            return jdbcTemplate.query(
                    FIND_ALL,
                    new ArticleRowMapper()
            );
        }
        // SI name contient qqchose mais pas de catégorie
        if (category_id == 0 && !item_name.isEmpty()) {
            return namedParameterJdbcTemplate.query(
                    SELECT_FILTER_WITHOUT_CATE,
                    parameters,
                    new ArticleRowMapper()
            );
        }
        // SI SI name est vide ou null et pas de catégorie
        if (item_name == null || item_name.isEmpty() && (category_id == 0)){
            return namedParameterJdbcTemplate.query(
                    FIND_ALL,
                    parameters,
                    new ArticleRowMapper()
            );
        }
        // SINON (SI name contient qqchose et il y a une catégorie)
        return namedParameterJdbcTemplate.query(
                SELECT_FILTER,
                parameters,
                new ArticleRowMapper()
        );

    }



    @Override
    public void sellAnArticle(Article article, long user_id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("item_id", article.getIdArticle());
        mapSqlParameterSource.addValue("item_name", article.getName());
        mapSqlParameterSource.addValue("description", article.getDescription());
        mapSqlParameterSource.addValue("auction_date_begin", article.getStartDate());
        mapSqlParameterSource.addValue("auction_date_end", article.getEndDate());
        mapSqlParameterSource.addValue("price_init", article.getBetAPrice());
        mapSqlParameterSource.addValue("price_selling", article.getSalePrice());
        mapSqlParameterSource.addValue("user_id", user_id);
        mapSqlParameterSource.addValue("category_id", article.getCategory().getIdCategory());
        mapSqlParameterSource.addValue("picture_url",article.getPicture());
        mapSqlParameterSource.addValue("status", article.getStatus());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                CREATE_A_SALE,
                mapSqlParameterSource,
                keyHolder,
                new String[]{"item_id"}
        );

        article.setIdArticle(Objects.requireNonNull(keyHolder.getKey()).intValue());
    }


    @Transactional
    public void cancelASell(long id_article) {
        // Supprimer d'abord les lignes dépendantes
        jdbcTemplate.update("DELETE FROM BIDS WHERE item_id = ?", id_article);
        jdbcTemplate.update("DELETE FROM PICKUPS WHERE item_id = ?", id_article);

        // Puis supprimer l'article
        jdbcTemplate.update("DELETE FROM ITEMS_SOLD WHERE item_id = ?", id_article);
    }


    @Override
    public List<Article> findOpenAction() {
        return jdbcTemplate.query(
                FIND_ALL_EC,
                new ArticleRowMapper()
        );
    }

    //MES BID GAGNER ET EN COUR


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
    //MES VENTES EC NC & TR
    @Override
    public List<Article> findAuctionByUserAndStatus(long user_id, String status) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("user_id", user_id);
        parameters.addValue("status", status);

        return namedParameterJdbcTemplate.query(
                SELECT_MY_STATUS_USER,
                parameters,
                new ArticleRowMapper()
        );
    }

    @Override
    public List<Article> findByUserAndStatus(long user_id, String status) {
        List<Article> articles = new ArrayList<>();

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_id", user_id);
        mapSqlParameterSource.addValue("status", status);

        return namedParameterJdbcTemplate.query(
                SELECT_ARTICLES_BY_USER_AND_STATUS,
                mapSqlParameterSource,
                new ArticleRowMapper()
        );
    }

    @Override
    public List<Article> findByStatusAndStartDate(String status, LocalDate startDate) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("status", status);
        mapSqlParameterSource.addValue("date", startDate);

        return namedParameterJdbcTemplate.query(
                SELECT_BY_STATUS_AND_START_DATE,
                mapSqlParameterSource,
                new ArticleRowMapper()
        );
    }

    @Override
    public List<Article> findByStatusAndEndDate(String status, LocalDate endDate) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("status", status);
        mapSqlParameterSource.addValue("date", endDate);

        return namedParameterJdbcTemplate.query(
                SELECT_BY_STATUS_AND_END_DATE,
                mapSqlParameterSource,
                new ArticleRowMapper()
        );
    }

    @Override
    public void updateArticleStatusById(String status, long article_id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("status", status);
        mapSqlParameterSource.addValue("article_id", article_id);

        namedParameterJdbcTemplate.update(
                UPDATE_ARTICLE_STATUS_BY_ID,
                mapSqlParameterSource
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
        article.setStartDate(rs.getDate("auction_date_begin").toLocalDate());
        article.setEndDate(rs.getDate("auction_date_end").toLocalDate());
        article.setBetAPrice(rs.getLong("price_init"));
        article.setSalePrice(rs.getLong("price_selling"));
        article.setUser(rs.getLong("user_id"));
        article.setStatus(rs.getString("status"));
        article.setPicture(rs.getString("picture_url"));

        return article;
    }
}

class ArticleCategoryRowMapper implements RowMapper<Article> {

    @Override
    public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
        Article article = new Article();

        article.setIdArticle(rs.getLong("item_id"));
        article.setName(rs.getString("item_name"));
        article.setDescription(rs.getString("description"));
        article.setStartDate(rs.getDate("auction_date_begin").toLocalDate());
        article.setEndDate(rs.getDate("auction_date_end").toLocalDate());
        article.setBetAPrice(rs.getLong("price_init"));
        article.setSalePrice(rs.getLong("price_selling"));
        article.setUser(rs.getLong("user_id"));
        article.setStatus(rs.getString("status"));
        article.setPicture(rs.getString("picture_url"));

        Categories category = new Categories();
        category.setIdCategory(rs.getLong("category_id"));
        category.setWording(rs.getString("category_desc"));
        article.setCategory(category);

        return article;
    }
}