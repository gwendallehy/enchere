package fr.eni.tp.projet.bll;

import fr.eni.tp.projet.bo.*;

import java.util.List;

public interface AuctionsService {

    //Achat
    List<Article> OpenAuctions();

    List<Bid> MyAllBid(long user_id);
    List<Bid> MyOpenBid(long user_id);
    List<Bid> MyWinAuctions(long user_id);

    //Vente
    List<Article> MyCurrentAuction(long user_id);
    List<Article> MyNotStartedAuction(long user_id);
    List<Article> MyFinishedAuction(long user_id);

}
