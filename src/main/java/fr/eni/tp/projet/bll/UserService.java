package fr.eni.tp.projet.bll;

import fr.eni.tp.projet.bo.User;

import java.util.List;

public interface UserService {
    User findUserById(long id);
    User findUserByEmail(String email);
    List<User> findAllUsers();
    void createUser(User user);
    void deleteUser(long user_id);
}
