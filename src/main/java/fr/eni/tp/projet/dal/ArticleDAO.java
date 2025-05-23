package fr.eni.tp.projet.dal;

import fr.eni.tp.projet.bo.Article;
import fr.eni.tp.projet.bo.Pickup;

import java.time.LocalDate;
import java.util.List;

public interface ArticleDAO {
    Article findArticleById(long id);
    List<Article> findAllArticles();
    List<Article> findByFilter(String name, long category_id);

    List<Article> findSalesByUser(long user_id);
    void sellAnArticle(Article article, long user_id);
    void cancelASell(long article_id);

    List<Article> findOpenAction();

    //Vente
    List<Article> findAuctionByUserAndStatus(long user_id, String status);
    List<Article> findByUserAndStatus(long user_id, String status);
    List<Article> findByStatusAndStartDate(String status, LocalDate startDate);
    List<Article> findByStatusAndEndDate(String status, LocalDate endDate);
    void updateArticleStatusById(String status, long article_id);


}
