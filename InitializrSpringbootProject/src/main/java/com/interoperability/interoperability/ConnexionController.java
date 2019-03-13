package com.interoperability.interoperability;

import com.interoperability.interoperability.models.Connexion;
import com.interoperability.interoperability.repositories.ConnexionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ConnexionController {
    
    @Autowired
    ConnexionRepository connexionRepository;
    
    @RequestMapping("/connect")
    public String goToConnexion(Model m) {
        Connexion co = new Connexion();
        m.addAttribute("connexion", co);
        if(isGoodConnexion(co)){
            return "connexion";
        } else {
            return "connexionFailed";
        }
    }

    private boolean isGoodConnexion(Connexion connexion) {
        Connexion co = connexionRepository.findConnexionWithLoginAndPassword(connexion.getLogin(), connexion.getPassword());
        if (co == null){
            return false;
        }
        return true;
    }
}
