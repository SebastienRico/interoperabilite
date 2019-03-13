package com.interoperability.interoperability.objetsDTO;
import com.interoperability.interoperability.ObjectDTO;
import lombok.Data;

@Data
public class RestaurantDTO extends ObjectDTO{

    private String addressRestaurant;
    private String nameRestaurant;
    private String typeRestaurant;
    private Integer capacityRestaurant;
    private ContactDTO contactRestaurant;
    private String menuRestaurant;
    private String scheduleRestaurant; //Voir le format
    private String descriptionRestaurant;

    public RestaurantDTO() {
    }
    
    @Override
    public String toString(){
        return "Restau " + typeRestaurant 
                + " desc: " + descriptionRestaurant
                + " ouvert : " + scheduleRestaurant 
                + " menu : " + menuRestaurant 
                + " capacite : " + capacityRestaurant;
    }
}
