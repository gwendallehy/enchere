package fr.eni.tp.projet.bll;

import fr.eni.tp.projet.bo.*;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AuctionsService {

    //Consulter les utilisateurs
    List<User> consultUsers();

    //Consulter un utilisateur
    User consultUserById(long id);

    List<Categories> findAllCategories();

    Categories findCategoryById(long id);


    //Vendre un objet
    void createAuction(Article article, User user);

    //Annuler la vente
    void cancelAuction(long id);



}
