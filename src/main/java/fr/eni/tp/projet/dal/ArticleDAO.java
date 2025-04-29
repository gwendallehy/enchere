package fr.eni.tp.projet.dal;

import fr.eni.tp.projet.bo.Article;
import fr.eni.tp.projet.bo.Pickup;

public interface ArticleDAO {
    Article findArticleById(int id);
    Article findAllArticles();
    Article createArticle(Article article);


}
