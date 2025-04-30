package fr.eni.tp.projet.bll;

import fr.eni.tp.projet.bo.Pickup;

public interface PickUpService {
    Pickup FindPickupById(long id);
    void createPickUp(Pickup pickup);
}
