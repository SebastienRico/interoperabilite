package com.interoperability.interoperability;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class Scheduler {
    public static void main (String[] args) {
        GetterInternetPage getPage = new GetterInternetPage();
        
        Timer t = new Timer();
        
        t.scheduleAtFixedRate(getPage, 0, TimeUnit.MINUTES.toMillis(1));
    }
}
