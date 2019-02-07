package com.interoperability.interoperability;

import com.interoperability.interoperability.ObjetsDTO.AdresseDTO;
import com.interoperability.interoperability.ObjetsDTO.LocationForm;
import com.interoperability.interoperability.ObjetsDTO.LocationsDTO;
import com.interoperability.interoperability.ObjetsDTO.OrganisateurDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    /*@RequestMapping(value = "/...")
    public String goTo...(){
        return "...";
    }*/
    
    
    @RequestMapping("/addLocation")
    public String addLocation(Model model) {
        model.addAttribute("location", new LocationForm());
        return "formulaireLocation";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/addLocation")
    public String addNewLocation(@ModelAttribute("location") LocationForm location) {
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
