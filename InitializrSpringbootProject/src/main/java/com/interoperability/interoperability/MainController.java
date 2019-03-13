package com.interoperability.interoperability;

import java.util.ArrayList;
import java.util.List;

import org.json.*;

import com.interoperability.interoperability.objetsDTO.RentalFormDTO;
import com.interoperability.interoperability.objetsDTO.RentDTO;
import com.interoperability.interoperability.objetsDTO.OrganizerDTO;
import com.interoperability.interoperability.wikidata.WikidataFacade;
import com.interoperability.interoperability.wikidata.WikidataUtil;
import com.interoperability.interoperability.wikidata.wikidataReader.WikidataRestaurantReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import jdk.nashorn.internal.parser.JSONParser;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wikidata.wdtk.datamodel.json.jackson.JacksonObjectFactory;

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
        WikidataFacade.readPage("Q1569");
        return "index.html";
    }

    @RequestMapping(value = "/Research", method = RequestMethod.GET)
    public String goToResearch(Model m) {
        m.addAttribute("rech", new Research());
        m.addAttribute("rec", research);

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
    public String addResearch(Model m, @ModelAttribute("rech") Research rec) throws IOException {
        research = new ArrayList<>();

        String champs = rec.getChamps();

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

}
