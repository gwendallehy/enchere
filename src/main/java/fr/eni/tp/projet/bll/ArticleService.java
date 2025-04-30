package fr.eni.tp.projet.bll;

import fr.eni.tp.projet.bo.Article;

import java.util.List;

public interface ArticleService {
    Article findArticleById(int id);
    List<Article> findAllArticles();
    void createArticle(Article article, long user_id);
    List<Article> findSalesByUser (int userId);
    void sellAnArticle(Article article, int user_id);
    void cancelASell(int article_id);
}
