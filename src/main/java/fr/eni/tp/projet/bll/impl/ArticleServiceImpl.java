package fr.eni.tp.projet.bll.impl;

import fr.eni.tp.projet.bll.ArticleService;
import fr.eni.tp.projet.bo.Article;
import fr.eni.tp.projet.dal.ArticleDAO;
import fr.eni.tp.projet.exception.BusinessCode;
import fr.eni.tp.projet.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleDAO articleDAO;

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
    public List<Article> findSalesByUser(long user_id) {
        return articleDAO.findSalesByUser(user_id);
    }

    @Override
    public void sellAnArticle(Article article, int user_id) {
        LocalDate now = LocalDate.now();
        BusinessException businessException = new BusinessException();

        if (article.getStartDate().isAfter(article.getEndDate())) {
            businessException.addKey(BusinessCode.VALID_ARTICLE_STARTDATE_IS_AFTER_ENDDATE);
        }

        if (article.getStartDate().isBefore(now)) {
            businessException.addKey(BusinessCode.VALID_ARTICLE_STARTDATE_INVALID);
        }

        if (!businessException.getKeys().isEmpty()) {
            throw businessException;
        }

        if (article.getStartDate().equals(now)) {
            article.setStatus("EC");
            articleDAO.sellAnArticle(article, user_id);
        } else if (article.getStartDate().isAfter(now)) {
            article.setStatus("NC");
            articleDAO.sellAnArticle(article, user_id);
        }
    }

    @Override
    public void cancelASell(int article_id) {
        articleDAO.cancelASell(article_id);
    }

    @Override
    public List<Article> findSalesByUserAndStatus(long user_id, String status) {
        return articleDAO.findByUserAndStatus(user_id, status);
    }

    @Override
    public List<Article> findFilter(String name, long category_id) {
        return articleDAO.findByFilter(name , category_id);
    }
}
