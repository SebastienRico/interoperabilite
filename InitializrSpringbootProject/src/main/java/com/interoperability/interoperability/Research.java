package com.interoperability.interoperability;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONException;
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
    
    private String formatChamps(){
        return champs.replaceAll(" ", "%20");
    }

    public List<String> requestQAnswer() {
        try {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/octet-stream");
            RequestBody body = RequestBody.create(mediaType, "test");
            
            String champsForQuery = formatChamps();
            
            System.out.println("champsForQuery : " + champsForQuery);
            
            Request request = new Request.Builder()
                    .url("http://qanswer-core1.univ-st-etienne.fr/api/gerbil?query=" +champsForQuery +"kb=students")
                    .post(body)
                    .addHeader("authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI5MCIsImlhdCI6MTU1MzI2MzEwOCwiZXhwIjoxNTUzODY3OTA4fQ.VX54-6i4ninDrA_0CNRgdH4G0vX47OiguoY29SkK1Ge1Hfperv2KF5N5fXPBbZwwdAToHkvuViGUGwAXH7DQsg")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "bfede952-3409-42f3-88cd-2ac5f6eb75d2")
                    .build();
            
            Response response = client.newCall(request).execute();
            String urlInResponse = response.body().string();
            
            System.out.println("urlInResponse : " + urlInResponse);
            
            JSONArray head = new JSONObject(new JSONObject(urlInResponse).getJSONArray("questions").getJSONObject(0).getJSONObject("question").get("answers").toString()).getJSONObject("head").getJSONArray("vars");
            System.out.println("head " + head.toString());
            JSONArray bindings = new JSONObject(new JSONObject(urlInResponse).getJSONArray("questions").getJSONObject(0).getJSONObject("question").get("answers").toString()).getJSONObject("results").getJSONArray("bindings");
            System.out.println("bind " + bindings.toString());
            
            for (int i = 0; i < bindings.length(); i++) {
                String bindInResponse = bindings.getJSONObject(i).getJSONObject(head.get(0).toString()).get("value").toString();
                System.out.println("bind : " + bindInResponse);
                // parser les bindings pour récupérer les Qids et ajouter les résultats dans la liste
                String[] urlInResponseSplited = bindInResponse.split("entity/");
                qIds.add(urlInResponseSplited[1]);
            }
        } catch (IOException ex) {
            Logger.getLogger(Research.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return qIds;
    }
}
