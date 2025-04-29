package fr.eni.tp.projet.dal.impl;

import fr.eni.tp.projet.bo.Article;
import fr.eni.tp.projet.dal.ArticleDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class ArticleDAOImpl implements ArticleDAO {

    //    ATTRIBUTS
    private static final String SELECT_ALL = "SELECT * FROM ITEMS_SOLD";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    //CONSTRUCTORS
    public ArticleDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public Article findArticleById(int id) {
        return null;
    }

    @Override
    public List<Article> findAllArticles() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        return jdbcTemplate.query(
                SELECT_ALL,
                new ArticleRowMapper()
        );
    }

    @Override
    public void createArticle(Article article) {
    }


}



