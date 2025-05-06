package fr.eni.tp.projet.bll.scheduler;

import fr.eni.tp.projet.bo.Article;
import fr.eni.tp.projet.dal.ArticleDAO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AuctionStatusScheduler {
    private final ArticleDAO articleDAO;

    public AuctionStatusScheduler(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    @Transactional
    // seconde / minute / heure / jour du mois / mois / jour de la semaine
    @Scheduled(cron = "0 0 10 * * *")
    public void updateAuctionStatus() {
        LocalDate now = LocalDate.now();

        List<Article> toStart = articleDAO.findByStatusAndStartDate("NC", now);
        for (Article article : toStart) {
            article.setStatus("EC");
            articleDAO.updateArticleStatusById(article.getStatus(), article.getIdArticle());
        }

        List<Article> toFinish = articleDAO.findByStatusAndEndDate("EC", now);
        for (Article article : toFinish) {
            article.setStatus("TR");
            articleDAO.updateArticleStatusById(article.getStatus(), article.getIdArticle());
        }
    }
}
