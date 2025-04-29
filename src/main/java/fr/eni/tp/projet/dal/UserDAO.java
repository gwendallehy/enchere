package fr.eni.tp.projet.dal;

import fr.eni.tp.projet.bo.User;

import java.util.List;

public interface UserDAO {
    User findById(long id);
    User findByEmail(String email);
    List<User> findAllUsers();


    void createUser(User user);
    void deleteUser(long user_id);

}
