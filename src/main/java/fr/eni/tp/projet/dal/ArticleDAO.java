package fr.eni.tp.projet.dal;

import fr.eni.tp.projet.bo.Article;
import fr.eni.tp.projet.bo.Pickup;

import java.util.List;

public interface ArticleDAO {
    Article findArticleById(int id);
    List<Article> findAllArticles();

    List<Article> findSalesByUser(long user_id);

    void sellAnArticle(Article article, long user_id);
    void cancelASell(long article_id);
}
