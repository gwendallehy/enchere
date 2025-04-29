package fr.eni.tp.projet.dal;

import fr.eni.tp.projet.bo.Article;
import fr.eni.tp.projet.bo.Pickup;

import java.util.List;

public interface ArticleDAO {
    Article findArticleById(int id);
    List<Article> findAllArticles();
    Article createArticle(Article article);
    Article findSalesByUser (int userId);
    void sellAnArticle(Article article, int user_id);
    void cancelASell(int article_id);


}
