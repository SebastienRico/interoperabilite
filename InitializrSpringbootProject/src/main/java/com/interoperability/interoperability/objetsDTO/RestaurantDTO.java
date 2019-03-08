package com.interoperability.interoperability.objetsDTO;
import com.interoperability.interoperability.ObjectDTO;

import lombok.Data;

@Data
public class RestaurantDTO extends ObjectDTO{

    private String nameRestaurant;
    private AddressDTO adresseRestaurant;
    private String typeRestaurant;
    private Integer capaciteRestaurant;
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
