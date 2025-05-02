package fr.eni.tp.projet.dal;

import fr.eni.tp.projet.bo.User;

import java.util.List;

public interface UserDAO {
    public User findByEmail(String email);
    User findById(long user_id);
    List<User> findAllUsers();
    void createUser(User user);
    void deleteUser(long user_id);
    User findByPseudo(String pseudo);
}
