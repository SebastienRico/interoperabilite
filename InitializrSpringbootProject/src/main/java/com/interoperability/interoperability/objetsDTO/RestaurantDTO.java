package com.interoperability.interoperability.objetsDTO;

import lombok.Data;

@Data
public class RestaurantDTO {

    private AdresseDTO adresseRestaurant;
    private String typeRestaurant;
    private int capaciteRestaurant;
    private ContactDTO contactRestaurant;
    private String menuRestaurant;
    private String horaireOuvertureRestaurant; //Voir le format

    public RestaurantDTO() {
    }
    
    @Override
    public String toString(){
        return "Restau " + typeRestaurant 
                + " ouvert : " + horaireOuvertureRestaurant 
                + " menu : " + menuRestaurant 
                + " capacite : " + capaciteRestaurant;
    }
}
