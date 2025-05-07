package fr.eni.tp.projet.dal;

import fr.eni.tp.projet.bo.Article;
import fr.eni.tp.projet.bo.Bid;

import java.util.List;

public interface BidDAO {
    List<Bid> findAllBids();

    Bid findBidByItemId(long id);

    List<Bid> findBidByUserId(long id);
    List<Bid> findBidAndWinByUser(long user_id, String status);
    List<Bid> findBidByUser(long user_id);


    void createBid(Bid bid);

    void deleteBid(long item_id);
}
