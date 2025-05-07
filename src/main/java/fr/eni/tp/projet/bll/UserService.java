package fr.eni.tp.projet.bll;

import fr.eni.tp.projet.bo.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(long id);
    User getUserByEmail(String email);
    User findByUsername(String pseudo);
    void updateUser(User user);
    void createUser(User user);
    void deleteUser(long id);

    void resetPassword(String username, String newPassword);
    void changePassword(String username, String currentPassword, String newPassword);
}
