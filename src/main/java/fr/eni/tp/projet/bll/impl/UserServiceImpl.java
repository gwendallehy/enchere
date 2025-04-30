package fr.eni.tp.projet.bll.impl;

import fr.eni.tp.projet.bll.UserService;
import fr.eni.tp.projet.bo.User;
import fr.eni.tp.projet.dal.UserDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User findUserById(long id) {
        return userDAO.findById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userDAO.findAllUsers();
    }

    @Override
    public void createUser(User user) {
        userDAO.createUser(user);
    }

    @Override
    public void deleteUser(long user_id) {
        userDAO.deleteUser(user_id);
    }
}
