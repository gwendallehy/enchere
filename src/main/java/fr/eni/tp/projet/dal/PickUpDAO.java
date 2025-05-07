package fr.eni.tp.projet.dal;

import fr.eni.tp.projet.bo.Article;
import fr.eni.tp.projet.bo.Pickup;

import java.util.List;

public interface PickUpDAO {

    Pickup FindPickupById(long id);
    void createPickUp(Pickup pickup);
    List<Pickup> findAllPickup();

}
