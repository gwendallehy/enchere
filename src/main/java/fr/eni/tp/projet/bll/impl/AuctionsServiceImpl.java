package fr.eni.tp.projet.bll.impl;

import fr.eni.tp.projet.bll.AuctionsService;
import fr.eni.tp.projet.bo.Article;
import fr.eni.tp.projet.dal.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionsServiceImpl implements AuctionsService {
    private final ArticleDAO articleDAO;
    private final UserDAO userDAO;
    private final CategoriesDAO categoriesDAO;
    private final PickUpDAO pickUpDAO;
    private final BidDAO bidDAO;

    public AuctionsServiceImpl(ArticleDAO articleDAO, UserDAO userDAO, CategoriesDAO categoriesDAO, PickUpDAO pickUpDAO, BidDAO bidDAO) {
        this.articleDAO = articleDAO;
        this.userDAO = userDAO;
        this.categoriesDAO = categoriesDAO;
        this.pickUpDAO = pickUpDAO;
        this.bidDAO = bidDAO;
    }




    @Override
    public List<Article> OpenAuctions() {
        return articleDAO.findOpenAction();
    }

    @Override
    public List<Article> MyOpenBid(long user_id) {
        return articleDAO.findBidAndWinByUser(user_id, "EC");
    }

    @Override
    public List<Article> MyWinAuctions(long user_id) {
        return articleDAO.findBidAndWinByUser(user_id, "TR");
    }





    @Override
    public List<Article> MyCurrentAuction(long user_id) {
        return articleDAO.findAuctionByUserAndStatus(user_id, "EC");
    }

    @Override
    public List<Article> MyNotStartedAuction(long user_id) {
        return articleDAO.findAuctionByUserAndStatus(user_id, "NC");
    }

    @Override
    public List<Article> MyFinishedAuction(long user_id) {
        return articleDAO.findAuctionByUserAndStatus(user_id, "TR");
    }
}
