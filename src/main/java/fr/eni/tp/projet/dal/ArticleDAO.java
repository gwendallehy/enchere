package fr.eni.tp.projet.dal;

import fr.eni.tp.projet.bo.Article;

public interface ArticleDAO {
    Article findArticleById(int id);
    Article findAllArticles();
    Article createArticle(Article article);

}
