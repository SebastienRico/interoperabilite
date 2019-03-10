package com.interoperability.interoperability.objetsDTO;
import com.interoperability.interoperability.ObjectDTO;
import lombok.Data;

@Data
public class RestaurantDTO extends ObjectDTO{

    private AddressDTO addressRestaurant;
    private String nameRestaurant;
    private String typeRestaurant;
    private Integer capacityRestaurant;
    private ContactDTO contactRestaurant;
    private String menuRestaurant;
    private String scheduleRestaurant; //Voir le format
    private String description;

    public RestaurantDTO() {
    }
    
    @Override
    public String toString(){
        return "Restau " + typeRestaurant 
                + " : " + description
                + " ouvert : " + scheduleRestaurant 
                + " menu : " + menuRestaurant 
                + " capacite : " + capacityRestaurant;
    }
}
