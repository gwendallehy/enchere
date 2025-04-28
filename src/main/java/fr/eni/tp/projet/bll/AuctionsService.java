package fr.eni.tp.projet.bll;

import fr.eni.tp.projet.bo.*;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AuctionsService {
    //Listez les enchères
    List<Auction> consultAuctions();
    // Consulter une enchère
    Auction consultAuctionById(long id);

    //Consulter les utilisateurs
    List<User> consultUsers();
    //Consulter un utilisateur
    User consultUserById(long id);

    //Vendre un objet
    void createAuction(Article article, User user);
    //Annuler la vente
    void cancelAuction(long id);

}
