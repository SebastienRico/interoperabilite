package com.interoperability.interoperability;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

/**
 *
 * @author qbiss
 */
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
        String command = "curl -X GET https://postman-echo.com/get?foo1=bar1&foo2=bar2";
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        // get the qId
        return qId;
    }
}
