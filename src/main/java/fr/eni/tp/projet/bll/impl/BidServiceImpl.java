package fr.eni.tp.projet.bll.impl;

import fr.eni.tp.projet.bll.BidService;
import fr.eni.tp.projet.bo.Bid;
import fr.eni.tp.projet.dal.BidDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidServiceImpl implements BidService {
    private BidDAO bidDAO;

    @Override
    public List<Bid> findAllBids() {
        return bidDAO.findAllBids();
    }

    @Override
    public Bid findBidByUserId(long id) {
        return bidDAO.findBidByUserId(id);
    }

    @Override
    public Bid findBidByItemId(long id) {
        return bidDAO.findBidByItemId(id);
    }

    @Override
    public void createBid(Bid bid) {
        bidDAO.createBid(bid);
    }

    @Override
    public void deleteBid(long item_id) {
        bidDAO.deleteBid(item_id);
    }
}
