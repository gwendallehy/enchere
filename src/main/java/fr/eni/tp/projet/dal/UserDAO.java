package fr.eni.tp.projet.dal;

import fr.eni.tp.projet.bo.User;

public interface UserDAO {
    User findById(long id);
    User findByEmail(String email);
    User findAllUsers();
    User createUser(User user);
}
