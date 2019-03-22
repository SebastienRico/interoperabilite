package com.interoperability.interoperability;

import com.interoperability.interoperability.models.Connexion;
import com.interoperability.interoperability.objetsDTO.ActivitesDTO;
import com.interoperability.interoperability.objetsDTO.ContactDTO;
import com.interoperability.interoperability.objetsDTO.EventDTO;
import com.interoperability.interoperability.objetsDTO.HostelDTO;
import java.util.ArrayList;
import java.util.List;

import org.json.*;

import com.interoperability.interoperability.objetsDTO.RentalFormDTO;
import com.interoperability.interoperability.objetsDTO.RentDTO;
import com.interoperability.interoperability.objetsDTO.PersonDTO;
import com.interoperability.interoperability.objetsDTO.RestaurantDTO;
import com.interoperability.interoperability.repositories.ConnexionRepository;
import com.interoperability.interoperability.wikidata.WikidataFacade;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    @Autowired
    ConnexionRepository connexionRepository;

    private static List<Research> researches;
    private static List<ActivitesDTO> activitesDTO;
    private static List<ContactDTO> contactDTO;
    private static List<EventDTO> eventDTO;
    private static List<HostelDTO> hostelDTO;
    private static List<RestaurantDTO> restaurantDTO;


    /*@RequestMapping(value = "/...")
    public String goTo...(){
        return "...";
    }*/
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String goToIndex(Model m) {
        m.addAttribute("rech", new Research());
        WikidataFacade.readPage("Q2109");
        return "index.html";
    }

    @RequestMapping(value = "/Research", method = RequestMethod.GET)
    public String goToResearch(Model m) {
        m.addAttribute("rech", new Research());

        m.addAttribute("activitesDTO", activitesDTO);
        m.addAttribute("contactDTO", contactDTO);
        m.addAttribute("eventDTO", eventDTO);
        m.addAttribute("housingDTO", hostelDTO);
        m.addAttribute("restaurantDTO", restaurantDTO);

        return "research.html";
    }

    /* @RequestMapping(value = "/addResearch", method = RequestMethod.GET)
    public String showResearch(Model m) {
        m.addAttribute("rech", new Research());

        return "addResearch";
    }
     */
    @RequestMapping(value = "/restaurant", method = RequestMethod.GET)
    public String goToRestaurant(Model m) {
        m.addAttribute("restaurant", restaurantDTO);
        m.addAttribute("rech", new Research());
        return "restaurant";
    }

    @RequestMapping(value = "/activites", method = RequestMethod.GET)
    public String goToActivites(Model m) {
        m.addAttribute("activites", activitesDTO);
        m.addAttribute("rech", new Research());
        return "activites";
    }

    @RequestMapping(value = "/wikidataPage")
    public String goToPageWikidata(Model m) {
        m.addAttribute("rec", researches);
        m.addAttribute("rech", new Research());
        return "wikidataPage";
    }

    @RequestMapping(value = "/locationForm", method = RequestMethod.GET)
    public String goToLocationForm(Model m) {
        m.addAttribute("location", new RentalFormDTO());
        m.addAttribute("rech", new Research());
        return "locationForm";
    }

    @RequestMapping(value = "/event", method = RequestMethod.GET)
    public String goToEvent(Model m) {
        m.addAttribute("event", eventDTO);
        m.addAttribute("rech", new Research());
        return "event";
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String goToContact(Model m) {
        m.addAttribute("contact", contactDTO);
        m.addAttribute("rech", new Research());
        return "contact";
    }

    @RequestMapping(value = "/hostel", method = RequestMethod.GET)
    public String goToHostel(Model m) {
        m.addAttribute("hostel", hostelDTO);
        m.addAttribute("rech", new Research());
        return "hostel";
    }

    @RequestMapping(value = "/addResearch", method = RequestMethod.POST)
    public String addResearch(Model m, @ModelAttribute("rech") Research rec) throws IOException {

        restaurantDTO = new ArrayList<>();
        activitesDTO = new ArrayList<>();
        contactDTO = new ArrayList<>();
        eventDTO = new ArrayList<>();
        hostelDTO = new ArrayList<>();

        String champs = rec.getChamps();

        ObjectDTO object = new ObjectDTO();// = WikidataFacade.readPage("Q2310");

        Research research = new Research(champs);

        List<String> qIds = new ArrayList<>();
        qIds = research.requestQAnswer();

        if (qIds.isEmpty()) {
            return "redirect:/noData";
        } else if (qIds.size() == 1) {
            object = WikidataFacade.readPage(qIds.get(0));
            if (object instanceof RestaurantDTO) {
                System.out.println("restaurant" + object);

                restaurantDTO.add((RestaurantDTO) object);
                return "redirect:/restaurant";
            } else if (object instanceof HostelDTO) {
                System.out.println("hotel" + object);

                hostelDTO.add((HostelDTO) object);
                return "redirect:/hostel";
            } else if (object instanceof EventDTO) {
                System.out.println("événement" + object);

                eventDTO.add((EventDTO) object);
                return "redirect:/event";
            } else if (object instanceof ContactDTO) {
                System.out.println("contact" + object);

                contactDTO.add((ContactDTO) object);
                return "redirect:/contact";
            } else if (object instanceof ActivitesDTO) {
                System.out.println("activité" + object);

                activitesDTO.add((ActivitesDTO) object);
                return "redirect:/activites";
            }
        } else {
            // if la liste est plus grande qu'un seul résultat alors on appelle l'affichage d'une liste de résultat*
            for (int i = 0; i < qIds.size(); i++) {
                object = WikidataFacade.readPage(qIds.get(i));
                if (object instanceof RestaurantDTO) {
                    System.out.println("restaurant" + object);

                    restaurantDTO.add((RestaurantDTO) object);
                } else if (object instanceof HostelDTO) {
                    System.out.println("hotel" + object);

                    hostelDTO.add((HostelDTO) object);
                } else if (object instanceof EventDTO) {
                    System.out.println("événement" + object);

                    eventDTO.add((EventDTO) object);
                } else if (object instanceof ContactDTO) {
                    System.out.println("contact" + object);

                    contactDTO.add((ContactDTO) object);
                } else if (object instanceof ActivitesDTO) {
                    System.out.println("activité" + object);

                    activitesDTO.add((ActivitesDTO) object);
                }
            }

            return "Research";
        }
        return null;
    }

    @RequestMapping("/addLocation")
    public String addLocation(Model m) {
        m.addAttribute("location", new RentalFormDTO());
        return "locationForm";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/addLocation")
    public String addNewLocation(@ModelAttribute("location") RentalFormDTO rentalForm) {
        ContactDTO contactRent = new ContactDTO();
        contactRent.setNamePerson(rentalForm.getNamePerson());
        contactRent.setFirstnamePerson(rentalForm.getFirstnamePerson());
        contactRent.setPhoneContact(rentalForm.getPhoneContact());
        contactRent.setMailContact(rentalForm.getMailContact());
        contactRent.setWebsiteContact(rentalForm.getWebsiteContact());

        RentDTO locationDTO = new RentDTO();
        locationDTO.setAddressRent(rentalForm.getAddressRent());
        locationDTO.setContactRent(contactRent);
        locationDTO.setDateStartRent(rentalForm.getDateStartRent());
        locationDTO.setDateEndRent(rentalForm.getDateEndRent());
        locationDTO.setCapacityRent(rentalForm.getCapacityRent());
        locationDTO.setDisponibilityRent(rentalForm.getDisponibilityRent());
        locationDTO.setPriceRent(rentalForm.getPriceRent());
        locationDTO.setDescriptionRent(rentalForm.getDescriptionRent());
        locationDTO.setNameRent(rentalForm.getNameRent());

        //Envoyer locationDTO au BOT qui écrit dans la WikiBase
        WikidataFacade.writePage(locationDTO);
        return "redirect:/";
    }

    @RequestMapping(value = "redirectResearch", method = RequestMethod.GET)
    public String redirectResearch() {
        return null;

    }

    @RequestMapping(value = "/connect", method = RequestMethod.POST)
    public String goToConnexion(Model m) {
        m.addAttribute("co", new Connexion());
        m.addAttribute("rech", new Research());
        m.addAttribute("file", new ImportCSV());
        return "connexion";
    }

    @RequestMapping(value = "/tryConnexion", method = RequestMethod.POST)
    public String tryConnexion(@ModelAttribute("co") Connexion co, Model m) {
        m.addAttribute("rech", new Research());
        Connexion tryConnexion = connexionRepository.findConnexionWithLoginAndPassword(co.getLogin(), co.getPassword());
        if (tryConnexion == null) {
            return "connexionFailed";
        }
        m.addAttribute("file", new ImportCSV());
        return "connexionSuccess";
    }

    @RequestMapping(value = "/connexionFailed", method = RequestMethod.GET)
    public String FailedConnexion(Model m) {
        m.addAttribute("rech", new Research());
        return "connexionFailed";
    }

    @RequestMapping(value = "/connexionSuccess", method = RequestMethod.POST)
    public void connexionOk(@ModelAttribute("co") Connexion co, Model m) {
        m.addAttribute("rech", new Research());
        m.addAttribute("file", new ImportCSV());
    }

    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public void copyFile(@ModelAttribute("file") ImportCSV f, Model m) throws IOException {
        m.addAttribute("rech", new Research());
        m.addAttribute("file", new ImportCSV());
        f.copyFile();
    }

}
