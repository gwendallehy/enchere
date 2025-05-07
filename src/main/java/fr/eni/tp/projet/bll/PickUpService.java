package fr.eni.tp.projet.bll;

import fr.eni.tp.projet.bo.Pickup;

import java.util.List;

public interface PickUpService {
    Pickup getPickUpByItemId(long itemId);
    void createPickUp(Pickup pickup);
    List<Pickup> findAllPickup();
}
