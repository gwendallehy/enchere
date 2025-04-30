package fr.eni.tp.projet.bll.impl;

import fr.eni.tp.projet.bll.AuctionsService;
import fr.eni.tp.projet.bo.Article;
import fr.eni.tp.projet.bo.Categories;
import fr.eni.tp.projet.bo.User;
import fr.eni.tp.projet.dal.ArticleDAO;
import fr.eni.tp.projet.dal.UserDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionsServiceImpl implements AuctionsService {
    private final UserDAO userDAO;

    public AuctionsServiceImpl(UserDAO userDAO){
        this.userDAO = userDAO;
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
    public List<Categories> findAllCategories() {
        return null;
    }

    @Override
    public Categories findCategoryById(long id) {
        return null;
    }

    @Override
    public void createAuction(Article article, User user) {

    }

    @Override
    public void cancelAuction(long id) {

    }
}
