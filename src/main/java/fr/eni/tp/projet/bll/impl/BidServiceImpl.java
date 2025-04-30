package fr.eni.tp.projet.bll.impl;

import fr.eni.tp.projet.bll.BidService;
import fr.eni.tp.projet.bo.Bid;
import fr.eni.tp.projet.dal.BidDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidServiceImpl implements BidService {

    private final BidDAO bidDAO;

    public BidServiceImpl(BidDAO bidDAO) {
        this.bidDAO = bidDAO;
    }

    @Override
    public List<Bid> getAllBids() {
        return bidDAO.findAllBids();
    }

    @Override
    public List<Bid> getBidsByUserId(long userId) {
        return bidDAO.findBidByUserId(userId);
    }

    @Override
    public Bid getBidByItemId(long itemId) {
        return bidDAO.findBidByItemId(itemId);
    }

    @Override
    public void createBid(Bid bid) {
        bidDAO.createBid(bid);
    }

    @Override
    public void deleteBid(long itemId) {
        bidDAO.deleteBid(itemId);
    }
}
