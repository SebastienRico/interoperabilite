package com.interoperability.interoperability;

import java.util.ArrayList;
import java.util.List;
import com.interoperability.interoperability.objetsDTO.AdresseDTO;
import com.interoperability.interoperability.objetsDTO.LocationFormDTO;
import com.interoperability.interoperability.objetsDTO.LocationsDTO;
import com.interoperability.interoperability.objetsDTO.OrganisateurDTO;
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
    
    @RequestMapping("/addLocation")
    public String addLocation(Model m) {
        m.addAttribute("location", new LocationFormDTO());
        return "formulaireLocation";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/addLocation")
    public String addNewLocation(@ModelAttribute("location") LocationFormDTO location) {
        AdresseDTO adresseLocation = new AdresseDTO();
        adresseLocation.setNumeroRue(location.getNumeroRue());
        adresseLocation.setNomRue(location.getNomRue());
        adresseLocation.setVille(location.getVille());
        
        OrganisateurDTO organisateurLocation = new OrganisateurDTO();
        organisateurLocation.setNomPersonne(location.getNomPersonne());
        organisateurLocation.setPrenomPersonne(location.getPrenomPersonne());
        organisateurLocation.setNomContact(location.getNomPersonne() + " " + location.getPrenomPersonne());
        organisateurLocation.setTelephoneContact(location.getTelephoneContact());
        organisateurLocation.setEmailContact(location.getEmailContact());
        organisateurLocation.setSiteWebContact(location.getSiteWebContact());
        
        
        LocationsDTO locationDTO = new LocationsDTO();
        locationDTO.setAdresseLocation(adresseLocation);
        locationDTO.setOrganisateurLocation(organisateurLocation);
        locationDTO.setDateDebutLocation(location.getDateDebutLocation());
        locationDTO.setDateFinLocation(location.getDateFinLocation());
        locationDTO.setCapaciteLocation(location.getCapaciteLocation());
        locationDTO.setDisponibiliteLocation(location.getDisponibiliteLocation());
        locationDTO.setTarifLocation(location.getTarifLocation());
        locationDTO.setDescriptionLocation(location.getDescriptionLocation());
        
       //Envoyer locationDTO au BOT qui écrit dans la WikiBase
       
        return "redirect:/";
    }

}
