/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interoperability.interoperability;

import java.util.Date;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author Tao
 */
public class Scheduler {
    public static void main (String[] args) {
        Tasks t1 = new Tasks();
        
        Timer t = new Timer();
        
        t.scheduleAtFixedRate(t1, 0, TimeUnit.MINUTES.toMillis(1));
    }
}
