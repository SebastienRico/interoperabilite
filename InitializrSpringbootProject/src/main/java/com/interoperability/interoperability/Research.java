package com.interoperability.interoperability;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Data
public class Research {

    private String champs;

    @Id
    @GeneratedValue
    private long id;

    public Research() {
    }

    public Research(String champs) {
        this.champs = champs;
    }

    public String requestQAnswer(String request) {
        String qId = null;
        // request the QAnswer service with the good one curl function
        String command = "curl -o test.html --data \"query=barack obama\" http://qanswer-core1.univ-st-etienne.fr/api/gerbils";
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        // get the qId
        return qId;
    }
}
