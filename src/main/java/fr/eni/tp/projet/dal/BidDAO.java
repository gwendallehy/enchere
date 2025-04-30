package fr.eni.tp.projet.dal;

import fr.eni.tp.projet.bo.Bid;

import java.util.List;

public interface BidDAO {
    List<Bid> findAllBids();

    List<Bid> findBidByUserId(long id);

    Bid findBidByItemId(long id);

    void createBid(Bid bid);

    void deleteBid(long item_id);
}
