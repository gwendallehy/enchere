package fr.eni.tp.projet.bll.impl;

import fr.eni.tp.projet.bll.ArticleService;
import fr.eni.tp.projet.bo.Article;
import fr.eni.tp.projet.dal.ArticleDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleDAO articleDAO;

    public ArticleServiceImpl(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    @Override
    public Article findArticleById(int id) {
        return articleDAO.findArticleById(id);
    }

    @Override
    public List<Article> findAllArticles() {
        return articleDAO.findAllArticles();
    }

    @Override
    public void createArticle(Article article, long user_id) {
        articleDAO.sellAnArticle(article, user_id);
    }

    @Override
    public List<Article> findSalesByUser(int userId) {
        return articleDAO.findSalesByUser(userId);
    }

    @Override
    public void sellAnArticle(Article article, int user_id) {
        articleDAO.sellAnArticle(article, user_id);
    }

    @Override
    public void cancelASell(int article_id) {
        articleDAO.cancelASell(article_id);
    }
}
