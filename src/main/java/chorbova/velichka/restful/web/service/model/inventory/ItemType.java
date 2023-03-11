package chorbova.velichka.restful.web.service.model.inventory;

import lombok.Data;
import lombok.Getter;


public enum ItemType {

    // Food Items
    APPLE("Organic"),
    WAFFLE("Milka"),
    SANDWICH("Grill"),

    // Beverage Items
    WATER("DEVIN"),
    COLA("Coca-Cola"),
    COFFEE("Java");
    @Getter
    private String brandName;

    ItemType(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }

}
