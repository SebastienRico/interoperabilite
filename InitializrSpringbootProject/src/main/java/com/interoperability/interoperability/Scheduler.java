package com.interoperability.interoperability;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class Scheduler extends Thread {
    
    @Override
    public void run(){
        startScheduler();
    }
    
    public static void startScheduler () {
        GetterInternetPage getPage = new GetterInternetPage();
        
        Timer t = new Timer();
        
        t.scheduleAtFixedRate(getPage, 0, TimeUnit.HOURS.toMillis(1));
    }
}
