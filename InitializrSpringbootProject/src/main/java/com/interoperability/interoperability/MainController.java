package com.interoperability.interoperability;

import com.interoperability.interoperability.objetsDTO.ActivitesDTO;
import com.interoperability.interoperability.objetsDTO.ContactDTO;
import com.interoperability.interoperability.objetsDTO.EventDTO;
import com.interoperability.interoperability.objetsDTO.HousingDTO;
import java.util.ArrayList;
import java.util.List;

import com.interoperability.interoperability.objetsDTO.RentalFormDTO;
import com.interoperability.interoperability.objetsDTO.RentDTO;
import com.interoperability.interoperability.objetsDTO.OrganizerDTO;
import com.interoperability.interoperability.objetsDTO.PersonDTO;
import com.interoperability.interoperability.objetsDTO.RestaurantDTO;
import com.interoperability.interoperability.wikidata.WikidataFacade;
import com.interoperability.interoperability.wikidata.WikidataUtil;
import com.interoperability.interoperability.wikidata.wikidataReader.WikidataRestaurantReader;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    private static List<Research> research;
    private static List<ActivitesDTO> activitesDTO;
    private static List<ContactDTO> contactDTO;
    private static List<EventDTO> eventDTO;
    private static List<HousingDTO> housingDTO;
    private static List<OrganizerDTO> organizerDTO;
    private static List<PersonDTO> personDTO;
    private static List<RestaurantDTO> restaurantDTO;


    /*@RequestMapping(value = "/...")
    public String goTo...(){
        return "...";
    }*/
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String gotToIndex(Model m) {
        m.addAttribute("rech", new Research());
        WikidataFacade.readPage("Q1569");
        return "index.html";
    }

    @RequestMapping(value = "/Research", method = RequestMethod.GET)
    public String goToResearch(Model m) {
        m.addAttribute("rech", new Research());

        m.addAttribute("activitesDTO",activitesDTO);
        m.addAttribute("contactDTO",contactDTO);
        m.addAttribute("eventDTO",eventDTO);
        m.addAttribute("housingDTO",housingDTO);
        m.addAttribute("organizerDTO",organizerDTO);
        m.addAttribute("personDTO",personDTO);
        m.addAttribute("restaurantDTO",restaurantDTO);

        return "research.html";
    }

    @RequestMapping(value = "/addResearch", method = RequestMethod.GET)
    public String showResearch(Model m) {
        m.addAttribute("rech", new Research());

        return "addResearch";
    }

    @RequestMapping(value = "/wikidataPage")
    public String goToPageWikidata(Model m) {
        m.addAttribute("rec", research);
        m.addAttribute("rech", new Research());
        return "wikidataPage";
    }

    @RequestMapping(value = "/locationForm", method = RequestMethod.GET)
    public String goToLocationForm(Model m) {
        m.addAttribute("location", new RentalFormDTO());
        m.addAttribute("rech", new Research());
        return "locationForm";
    }

    @RequestMapping(value = "/addResearch", method = RequestMethod.POST)
    public String addResearch(Model m, @ModelAttribute("rech") Research rec) {
        research = new ArrayList<>();

        String champs = rec.getChamps();

        String command = "curl --data \"query=" + champs + " kb=http://qanswer-svc1.univ-st-etienne.fr/wiki/Main_Page http://qanswer-core1.univ-st-etienne.fr/gerbil";
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
        return "locationForm";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/addLocation")
    public String addNewLocation(@ModelAttribute("location") RentalFormDTO location) {
        OrganizerDTO organisateurLocation = new OrganizerDTO();
        organisateurLocation.setNamePerson(location.getNamePerson());
        organisateurLocation.setFirstnamePerson(location.getFirstnamePerson());
        organisateurLocation.setNamePerson(location.getNamePerson() + " " + location.getFirstnamePerson());
        organisateurLocation.setPhoneContact(location.getPhoneContact());
        organisateurLocation.setMailContact(location.getMailContact());
        organisateurLocation.setWebsiteContact(location.getWebsiteContact());

        RentDTO locationDTO = new RentDTO();
        locationDTO.setAddressRent(location.getAdressRent());
        locationDTO.setOrganizerRent(organisateurLocation);
        locationDTO.setDateStartRent(location.getDateStartRent());
        locationDTO.setDateEndRent(location.getDateEndRent());
        locationDTO.setCapacityRent(location.getCapacityRent());
        locationDTO.setDisponibilityRent(location.getDisponibilityRent());
        locationDTO.setPriceRent(location.getPriceRent());
        locationDTO.setDescriptionRent(location.getDescriptionRent());

        //Envoyer locationDTO au BOT qui écrit dans la WikiBase
        return "redirect:/";
    }

    @RequestMapping(value = "redirectResearch", method = RequestMethod.GET)
    public String redirectResearch() {

    }

}
