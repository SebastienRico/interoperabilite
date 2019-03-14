package com.interoperability.interoperability;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

@Data
public class Research {

    private String champs;
    List<String> qIds;

    @Id
    @GeneratedValue
    private long id;

    public Research() {
        this.qIds = new ArrayList<>();
    }

    public Research(String champs) {
        this.qIds = new ArrayList<>();
        this.champs = champs;
    }

    public List<String> requestQAnswer() {
        try {
            // search "children barack obama" to have several Qids
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
                //System.out.println("head " + head.toString());
                JSONArray bindings = new JSONObject(new JSONObject(result.toString()).getJSONArray("questions").getJSONObject(0).getJSONObject("question").get("answers").toString()).getJSONObject("results").getJSONArray("bindings");
                //System.out.println("bind " + bindings.toString());
                for (int i = 0; i < bindings.length(); i++) {
                    String url = bindings.getJSONObject(i).getJSONObject(head.get(0).toString()).get("value").toString();
                    System.out.println("bind : " + url);
                    // parser les bindings pour récupérer les Qids et ajouter les résultats dans la liste
                    String[] urlSplited = url.split("entity/");
                    qIds.add(urlSplited[1]);
                }
                
            } catch (Exception e) {
            }
        } catch (IOException ex) {
            Logger.getLogger(Research.class.getName()).log(Level.SEVERE, null, ex);

        }
        qIds.forEach(id ->{
            System.out.println("id : " + id);
        });
        return qIds;
    }
}
