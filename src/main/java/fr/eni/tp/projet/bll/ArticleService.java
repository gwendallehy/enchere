package fr.eni.tp.projet.bll;

import fr.eni.tp.projet.bo.Article;

import java.util.List;

public interface ArticleService {
    List<Article> findFilter(String name, long category_id);
    Article findArticleById(int id);
    List<Article> findAllArticles();
    List<Article> findSalesByUser (long user_id);
    void sellAnArticle(Article article, int user_id);
    void cancelASell(int article_id);
    List<Article> findSalesByUserAndStatus(long user_id, String status);

}
