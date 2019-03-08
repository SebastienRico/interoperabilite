package com.interoperability.interoperability;

import java.util.ArrayList;
import java.util.List;

import com.interoperability.interoperability.objetsDTO.AddressDTO;
import com.interoperability.interoperability.objetsDTO.RentalFormDTO;
import com.interoperability.interoperability.objetsDTO.RentDTO;
import com.interoperability.interoperability.objetsDTO.OrganizerDTO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    private static List<Research> research;

    /*@RequestMapping(value = "/...")
    public String goTo...(){
        return "...";
    }*/
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String gotToIndex(Model m) {
        m.addAttribute("rech", new Research());
        return "index.html";
    }

    @RequestMapping(value = "/Research", method = RequestMethod.GET)
    public String goToResearch(Model m) {
        m.addAttribute("rech", new Research());
        m.addAttribute("rec",research);

        return "recherche.html";
    }

    @RequestMapping(value = "/addResearch", method = RequestMethod.GET)
    public String showResearch(Model m) {
        m.addAttribute("rech", new Research());

        return "addResearch";
    }
    @RequestMapping(value="/pageWikidata")
    public String goToPageWikidata(Model m){
        m.addAttribute("rec",research);
        m.addAttribute("rech", new Research());
        return "pageWikidata";
    }

    @RequestMapping(value = "/addResearch", method = RequestMethod.POST)
    public String addResearch(Model m, @ModelAttribute("rech") Research rec){
        research = new ArrayList<>();
        String champs = rec.getChamps();
        
        String command = "curl --data \"query="+champs+" http://qanswer-core1.univ-st-etienne.fr/gerbil --kb \"http://qanswer-svc1.univ-st-etienne.fr/wiki/Main_Page\"";
        System.out.println(command);
        /*
        try {
            Process process = Runtime.getRuntime().exec(command);
        } catch (IOException ex) {
        }*/
        
        Research r = new Research(champs);
        research.add(r);
        return "redirect:/Research";
    }
    
    @RequestMapping("/addLocation")
    public String addLocation(Model m) {
        m.addAttribute("location", new RentalFormDTO());
        return "formulaireLocation";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/addLocation")
    public String addNewLocation(@ModelAttribute("location") RentalFormDTO location) {
        AddressDTO adresseLocation = new AddressDTO();
        adresseLocation.setNumeroRue(location.getNumeroRue());
        adresseLocation.setNomRue(location.getNomRue());
        adresseLocation.setVille(location.getVille());
        
        OrganizerDTO organisateurLocation = new OrganizerDTO();
        organisateurLocation.setNomPersonne(location.getNomPersonne());
        organisateurLocation.setPrenomPersonne(location.getPrenomPersonne());
        organisateurLocation.setNomContact(location.getNomPersonne() + " " + location.getPrenomPersonne());
        organisateurLocation.setTelephoneContact(location.getTelephoneContact());
        organisateurLocation.setEmailContact(location.getEmailContact());
        organisateurLocation.setSiteWebContact(location.getSiteWebContact());
        
        
        RentDTO locationDTO = new RentDTO();
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
