package com.interoperability.interoperability;

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
import com.interoperability.interoperability.wikidata.WikidataFacade;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
    private static List<HostelDTO> hostelDTO;
    private static List<PersonDTO> personDTO;
    private static List<RestaurantDTO> restaurantDTO;


    /*@RequestMapping(value = "/...")
    public String goTo...(){
        return "...";
    }*/
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String gotToIndex(Model m) {
        m.addAttribute("rech", new Research());
        WikidataFacade.readPage("Q1580");
        return "index.html";
    }

    @RequestMapping(value = "/Research", method = RequestMethod.GET)
    public String goToResearch(Model m) {
        m.addAttribute("rech", new Research());

        m.addAttribute("activitesDTO", activitesDTO);
        m.addAttribute("contactDTO", contactDTO);
        m.addAttribute("eventDTO", eventDTO);
        m.addAttribute("housingDTO", hostelDTO);
        m.addAttribute("personDTO", personDTO);
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
    public String addResearch(Model m, @ModelAttribute("rech") Research rec) throws IOException {

        research = new ArrayList<>();
        restaurantDTO = new ArrayList<>();
        activitesDTO = new ArrayList<>();
        contactDTO = new ArrayList<>();
        eventDTO = new ArrayList<>();
        hostelDTO = new ArrayList<>();
        personDTO = new ArrayList<>();

        String champs = rec.getChamps();

        ObjectDTO object = WikidataFacade.readPage("Q2109");
        String command = "curl --data \"query=" + champs + "\" http://qanswer-core1.univ-st-etienne.fr/api/gerbil";
        Process process = Runtime.getRuntime().exec(command);

        try {
            System.out.println(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            String result = builder.toString();

            JSONArray head = new JSONObject(new JSONObject(result.toString()).getJSONArray("questions").getJSONObject(0).getJSONObject("question").get("answers").toString()).getJSONObject("head").getJSONArray("vars");
            System.out.println(head.toString());
            JSONArray bindings = new JSONObject(new JSONObject(result.toString()).getJSONArray("questions").getJSONObject(0).getJSONObject("question").get("answers").toString()).getJSONObject("results").getJSONArray("bindings");
            System.out.println(bindings.toString());
            for (int i = 0; i < bindings.length(); i++) {
                System.out.println(bindings.getJSONObject(i).getJSONObject(head.get(0).toString()).get("value").toString());
            }

        } catch (Exception e) {

        }
        Research r = new Research(champs);

        if (object instanceof RestaurantDTO) {
            System.out.println("restaurant" + object);

            restaurantDTO.add((RestaurantDTO) object);
            return "redirect:/restaurant";
        } else if (object instanceof PersonDTO) {
            System.out.println("personne" + object);

            personDTO.add((PersonDTO) object);
            return "redirect:/person";
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
        } else if (object instanceof ActivitesDTO){
            System.out.println("activité" + object);

            activitesDTO.add((ActivitesDTO) object);
            return "redirect:/activites";
        }

        research.add(r);
        return "redirect:/Research";
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
        contactRent.setNamePerson(rentalForm.getNamePerson() + " " + rentalForm.getFirstnamePerson());
        contactRent.setPhoneContact(rentalForm.getPhoneContact());
        contactRent.setMailContact(rentalForm.getMailContact());
        contactRent.setWebsiteContact(rentalForm.getWebsiteContact());

        RentDTO locationDTO = new RentDTO();
        locationDTO.setAddressRent(rentalForm.getAdressRent());
        locationDTO.setContactRent(contactRent);
        locationDTO.setDateStartRent(rentalForm.getDateStartRent());
        locationDTO.setDateEndRent(rentalForm.getDateEndRent());
        locationDTO.setCapacityRent(rentalForm.getCapacityRent());
        locationDTO.setDisponibilityRent(rentalForm.getDisponibilityRent());
        locationDTO.setPriceRent(rentalForm.getPriceRent());
        locationDTO.setDescriptionRent(rentalForm.getDescriptionRent());

        //Envoyer locationDTO au BOT qui écrit dans la WikiBase
        return "redirect:/";
    }

    @RequestMapping(value = "redirectResearch", method = RequestMethod.GET)
    public String redirectResearch() {
        return null;

    }

}
