package fr.eni.tp.projet.bll;

import fr.eni.tp.projet.bo.Bid;

import java.util.List;

public interface BidService {
    List<Bid> getAllBids();
    List<Bid> getBidsByUserId(long userId);
    Bid getBidByItemId(long itemId);
    void createBid(Bid bid);
    void deleteBid(long itemId);
}

