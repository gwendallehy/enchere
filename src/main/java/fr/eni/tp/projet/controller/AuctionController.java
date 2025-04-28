package fr.eni.tp.projet.controller;

import fr.eni.tp.projet.bo.User;
import fr.eni.tp.projet.dal.UserDAO;
import fr.eni.tp.projet.dal.impl.UserDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class AuctionController {
    private final static Logger logger = LoggerFactory.getLogger(AuctionController.class);
    private final UserDAOImpl userDAOImpl;
}
