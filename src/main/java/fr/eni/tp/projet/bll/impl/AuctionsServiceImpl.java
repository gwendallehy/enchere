package fr.eni.tp.projet.bll.impl;

import fr.eni.tp.projet.bll.AuctionsService;
import fr.eni.tp.projet.bo.Categories;
import fr.eni.tp.projet.dal.ArticleDAO;
import fr.eni.tp.projet.dal.AuctionDAO;
import fr.eni.tp.projet.dal.CategoriesDAO;
import fr.eni.tp.projet.dal.UserDAO;
import org.springframework.stereotype.Service;

@Service
public class AuctionsServiceImpl implements AuctionsService {
    private UserDAO userDAO;
    private AuctionDAO auctionDAO;
    private ArticleDAO articleDAO;
    private CategoriesDAO categoriesDAO;

    public AuctionsServiceImpl(UserDAO userDAO, AuctionDAO auctionDAO, ArticleDAO articleDAO, CategoriesDAO categoriesDAO) {
        this.userDAO = userDAO;
        this.auctionDAO = auctionDAO;
        this.articleDAO = articleDAO;
        this.categoriesDAO = categoriesDAO;
    }


}
