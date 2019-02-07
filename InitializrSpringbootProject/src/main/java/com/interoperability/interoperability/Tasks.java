/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interoperability.interoperability;

import java.util.Date;
import java.util.TimerTask;

/**
 *
 * @author Tao
 */
public class Tasks extends TimerTask{
    
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "Yo " + new Date());
    }
    
}
