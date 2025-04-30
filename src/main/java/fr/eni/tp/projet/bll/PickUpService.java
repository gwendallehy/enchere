package fr.eni.tp.projet.bll;

import fr.eni.tp.projet.bo.Pickup;

public interface PickUpService {
    Pickup getPickUpByItemId(long itemId);
    void createPickUp(Pickup pickup);
}
