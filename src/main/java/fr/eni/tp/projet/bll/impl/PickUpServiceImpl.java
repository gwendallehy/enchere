package fr.eni.tp.projet.bll.impl;

import fr.eni.tp.projet.bll.PickUpService;
import fr.eni.tp.projet.bo.Pickup;
import fr.eni.tp.projet.dal.PickUpDAO;
import org.springframework.stereotype.Service;

@Service
public class PickUpServiceImpl implements PickUpService {

    private final PickUpDAO pickUpDAO;

    public PickUpServiceImpl(PickUpDAO pickUpDAO) {
        this.pickUpDAO = pickUpDAO;
    }

    @Override
    public Pickup getPickUpByItemId(long itemId) {
        return pickUpDAO.FindPickupById(itemId);
    }

    @Override
    public void createPickUp(Pickup pickup) {
        pickUpDAO.createPickUp(pickup);
    }
}

