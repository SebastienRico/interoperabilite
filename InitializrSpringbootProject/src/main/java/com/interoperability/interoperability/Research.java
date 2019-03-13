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
        String command = "curl --data \"query=activite kb=http://qanswer-svc1.univ-st-etienne.fr/wiki\" http://qanswer-core1.univ-st-etienne.fr/gerbsil";
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        // get the qId
        return qId;
    }
}
