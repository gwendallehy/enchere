package fr.eni.tp.projet.bll.impl;

import fr.eni.tp.projet.bll.AuctionsService;
import fr.eni.tp.projet.bo.Article;
import fr.eni.tp.projet.bo.Auction;
import fr.eni.tp.projet.bo.Categories;
import fr.eni.tp.projet.bo.User;
import fr.eni.tp.projet.dal.ArticleDAO;
import fr.eni.tp.projet.dal.AuctionDAO;
import fr.eni.tp.projet.dal.CategoriesDAO;
import fr.eni.tp.projet.dal.UserDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionsServiceImpl implements AuctionsService {
    private UserDAO userDAO;
//    private AuctionDAO auctionDAO;
//    private ArticleDAO articleDAO;
    private CategoriesDAO categoriesDAO;

    public AuctionsServiceImpl(UserDAO userDAO//, AuctionDAO auctionDAO, ArticleDAO articleDAO
                                , CategoriesDAO categoriesDAO) {
        this.userDAO = userDAO;
//        this.auctionDAO = auctionDAO;
//        this.articleDAO = articleDAO;
        this.categoriesDAO = categoriesDAO;
    }


    @Override
    public List<Auction> consultAuctions() {
        return null;
    }

    @Override
    public Auction consultAuctionById(long id) {
        return null;
    }

    @Override
    public List<User> consultUsers() {
        return userDAO.findAllUsers();
    }

    @Override
    public User consultUserById(long id) {
        return userDAO.findById(id);
    }

    @Override
    public void createAuction(Article article, User user) {

    }

    @Override
    public void cancelAuction(long id) {

    }
}
