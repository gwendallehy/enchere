package fr.eni.tp.projet.bll.impl;

import fr.eni.tp.projet.bll.PickUpService;
import fr.eni.tp.projet.bo.Pickup;
import fr.eni.tp.projet.dal.PickUpDAO;
import org.springframework.stereotype.Service;

@Service
public class PickUpServiceImpl implements PickUpService {
    private PickUpDAO pickUpDAO;

    public PickUpServiceImpl(PickUpDAO pickUpDAO) {
        this.pickUpDAO = pickUpDAO;
    }

    @Override
    public Pickup FindPickupById(long id) {
        return pickUpDAO.FindPickupById(id);
    }

    @Override
    public void createPickUp(Pickup pickup) {
        pickUpDAO.createPickUp(pickup);
    }
}
