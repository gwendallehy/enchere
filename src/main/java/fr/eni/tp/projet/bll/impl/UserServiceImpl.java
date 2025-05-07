package fr.eni.tp.projet.bll.impl;

import fr.eni.tp.projet.bll.UserService;
import fr.eni.tp.projet.bo.User;
import fr.eni.tp.projet.dal.UserDAO;
import fr.eni.tp.projet.dal.impl.ArticleDAOImpl;
import fr.eni.tp.projet.dal.impl.BidDAOImpl;
import fr.eni.tp.projet.exception.BusinessCode;
import fr.eni.tp.projet.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final BidDAOImpl bidDAOImpl;
    private final ArticleDAOImpl articleDAOImpl;

    public UserServiceImpl(UserDAO userDAO, BidDAOImpl bidDAOImpl, ArticleDAOImpl articleDAOImpl) {
        this.userDAO = userDAO;
        this.bidDAOImpl = bidDAOImpl;
        this.articleDAOImpl = articleDAOImpl;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAllUsers();
    }

    @Override
    public User getUserById(long id) {
        return userDAO.findById(id);
    }

    @Override
    public User findByUsername(String pseudo) {
        return userDAO.findByPseudo(pseudo);
    }

    @Override
    public void updateUser(User user) {
         userDAO.updateUser(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public void createUser(User user) {
        BusinessException businessException = new BusinessException();

        if (user.getPassword() != null && user.getPassword().equals(user.getConfirmPassword())) {
            userDAO.createUser(user);
        } else {
            businessException.addKey(BusinessCode.VALID_USER_PASSWORD_CONFIRM_PASSWORD);
            throw businessException;
        }
    }

    @Override
    public void deleteUser(long id) {
        if (bidDAOImpl.findBidByUserId(id) == null && articleDAOImpl.findSalesByUser(id) == null){
            userDAO.deleteUser(id);
        }
    }
}

