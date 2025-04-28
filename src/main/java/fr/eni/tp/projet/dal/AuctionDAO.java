package fr.eni.tp.projet.dal;

import fr.eni.tp.projet.bo.Auction;

public interface AuctionDAO {
    Auction createAuction(Auction auction);
    Auction findAuction(long id);
    Auction findAllAuctions();
}
