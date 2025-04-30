package fr.eni.tp.projet.bll;

import fr.eni.tp.projet.bo.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(long id);
    User getUserByEmail(String email);
    void createUser(User user);
    void deleteUser(long id);
}
