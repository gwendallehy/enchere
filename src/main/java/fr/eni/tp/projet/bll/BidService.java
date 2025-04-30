package fr.eni.tp.projet.bll;

import fr.eni.tp.projet.bo.Bid;

import java.util.List;

public interface BidService {
    List<Bid> findAllBids();

    Bid findBidByUserId(long id);

    Bid findBidByItemId(long id);

    void createBid(Bid bid);

    void deleteBid(long item_id);
}
