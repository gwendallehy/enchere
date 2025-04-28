package fr.eni.tp.projet.controller;

import fr.eni.tp.projet.bll.AuctionsService;
import fr.eni.tp.projet.dal.impl.UserDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    private final UserDAOImpl userDAOImpl;
    private final AuctionsService auctionsService;

    public UserController(UserDAOImpl userDAOImpl, AuctionsService auctionsService) {
        this.userDAOImpl = userDAOImpl;
        this.auctionsService = auctionsService;
    }


    //Mapping user/profile?id

    @GetMapping("/users/profile")
    public String profile() {
        return "/users/profile";
    }

    @GetMapping("/users/viewProfile")
    public String checkProfile() {
        return "/users/viewProfile";
    }
    //Mapping user/create

    @GetMapping("/users/create")
    public String create() {
        return "/users/create";
    }

    //Mapping user/

}
