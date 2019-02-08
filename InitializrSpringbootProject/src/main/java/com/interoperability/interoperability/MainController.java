package com.interoperability.interoperability;

import com.interoperability.interoperability.Model.Recherche;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    private static List<Recherche> rech;

    /*@RequestMapping(value = "/...")
    public String goTo...(){
        return "...";
    }*/
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String gotToIndex(Model m) {
        m.addAttribute("rech", new Recherche());
        return "index.html";
    }

    @RequestMapping(value = "/Recherche", method = RequestMethod.GET)
    public String goToRecherche(Model m) {
        m.addAttribute("rech", new Recherche());
        m.addAttribute("rec",rech);

        return "recherche.html";
    }

    @RequestMapping(value = "/addRecherche", method = RequestMethod.GET)
    public String showRecherche(Model m) {
        m.addAttribute("rech", new Recherche());

        return "addRecherche";
    }
    
    @RequestMapping(value="/pageWikidata")
    public String goToPageWikidata(Model m){
        m.addAttribute("rec",rech);
        m.addAttribute("rech", new Recherche());
        return "pageWikidata";
    }

    @RequestMapping(value = "/addRecherche", method = RequestMethod.POST)
    public String addVegetable(Model m, @ModelAttribute("rech") Recherche rec) {
        rech = new ArrayList<Recherche>();
        String champs = rec.getChamps();

        Recherche r = new Recherche(champs);
        rech.add(r);
        return "redirect:/Recherche";

    }

}
