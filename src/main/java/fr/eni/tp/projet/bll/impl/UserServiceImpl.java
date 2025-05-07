package fr.eni.tp.projet.bll.impl;

import fr.eni.tp.projet.bll.UserService;
import fr.eni.tp.projet.bo.User;
import fr.eni.tp.projet.dal.UserDAO;
import fr.eni.tp.projet.dal.impl.ArticleDAOImpl;
import fr.eni.tp.projet.dal.impl.BidDAOImpl;
import fr.eni.tp.projet.exception.BusinessCode;
import fr.eni.tp.projet.exception.BusinessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        boolean isValid = true;
        BusinessException businessException = new BusinessException();

        isValid = isConfirmPasswordValid(user.getPassword(), user.getConfirmPassword(), businessException);
        isValid &= isPseudoBlank(user.getPseudo(), businessException);
        isValid &= isLastNameBlank(user.getName(), businessException);
        isValid &= isFirstNameBlank(user.getFirstName(), businessException);
        isValid &= isPhoneValid(user.getPhone(), businessException);
        isValid &= isPostalCodeValid(user.getPostalCode(), businessException);

        // Vérification que les mots de passe correspondent
        if (isValid) {
            // Encodage du mot de passe avant de le sauvegarder
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);  // Remplacer le mot de passe en clair par le mot de passe encodé

            // Création de l'utilisateur avec le mot de passe encodé
            userDAO.createUser(user);
        } else {
            throw businessException;
        }
    }

    public boolean isConfirmPasswordValid(String password, String confirmPassword, BusinessException businessException) {
        boolean isValid = true;

        if (password != null && !password.equals(confirmPassword)) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_PASSWORD_CONFIRM_PASSWORD);
        }

        // Vérification de la validité du mot de passe (facultatif, mais recommandé)
        if (password.length() < 6) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_PASSWORD_MIN_LENGTH);
        }

        return isValid;
    }

    public boolean isPseudoBlank(String pseudo, BusinessException businessException) {
        boolean isValid = true;

        if (pseudo.isBlank()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_PSEUDO_BLANK);
        }

        return isValid;
    }

    public boolean isLastNameBlank(String lastName, BusinessException businessException) {
        boolean isValid = true;

        if (lastName.isBlank()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_NAME_BLANK);
        }

        return isValid;
    }

    public boolean isFirstNameBlank(String firstName, BusinessException businessException) {
        boolean isValid = true;

        if (firstName.isBlank()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_FIRSTNAME_BLANK);
        }

        return isValid;
    }

    public boolean isPhoneValid(String phoneNumber, BusinessException businessException) {
        boolean isValid = true;

        if (phoneNumber.length() != 10) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_PHONE_INVALID);
        }

        return isValid;
    }

    public boolean isPostalCodeValid(long postalCode, BusinessException businessException) {
        boolean isValid = true;

        String postalCodeStr = String.valueOf(postalCode);

        if (postalCodeStr.isBlank() || postalCodeStr.length() != 5) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_POSTALCODE_INVALID);
        }

        return isValid;
    }

    @Override
    public void deleteUser(long id) {
        if (bidDAOImpl.findBidByUserId(id) == null && articleDAOImpl.findSalesByUser(id) == null){
            userDAO.deleteUser(id);
        }
    }
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void changePassword(String username, String currentPassword, String newPassword) {
        // Trouver l'utilisateur par pseudo
        User user = userDAO.findByPseudo(username);

        // Vérifier si le mot de passe actuel correspond au mot de passe encodé dans la base de données
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new BusinessException("Ancien mot de passe incorrect");
        }

        // Hashage du nouveau mot de passe
        String encodedPassword = passwordEncoder.encode(newPassword);

        // Mettre à jour le mot de passe dans la base de données
        user.setPassword(encodedPassword);

        // Mise à jour complète de l'utilisateur avec le nouveau mot de passe
        userDAO.updatePassword(user.getIdUser(), encodedPassword);
    }



    public void resetPassword(String username, String newPassword) {
        User user = findByUsername(username);
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userDAO.updatePassword(user.getIdUser(), encodedPassword);
    }

}

