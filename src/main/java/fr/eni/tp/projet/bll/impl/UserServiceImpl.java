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
        boolean isValid = true;
        BusinessException businessException = new BusinessException();

        isValid = isConfirmPasswordValid(user.getPassword(), user.getConfirmPassword(), businessException);
        isValid &= isPseudoBlank(user.getPseudo(), businessException);
        isValid &= isLastNameBlank(user.getName(), businessException);
        isValid &= isFirstNameBlank(user.getFirstName(), businessException);
        isValid &= isPhoneValid(user.getPhone(), businessException);
        isValid &= isPostalCodeValid(user.getPostalCode(), businessException);

        if (isValid) {
            userDAO.createUser(user);
        } else {
            throw businessException;
        }
    }

    public boolean isConfirmPasswordValid(String password, String confirmPassword, BusinessException businessException) {
        boolean isValid = true;

        if (!password.equals(confirmPassword)) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_USER_PASSWORD_CONFIRM_PASSWORD);
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
}

