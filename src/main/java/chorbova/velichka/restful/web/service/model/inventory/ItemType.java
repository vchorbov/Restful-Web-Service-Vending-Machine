package chorbova.velichka.restful.web.service.model.inventory;

import lombok.Data;


public enum ItemType {
        APPLE("Organic"),
        WAFFLE("Milka"),
        SANDWICH("Grill"),
        WATER("DEVIN"),
        COLA("Coca-Cola"),
        COFFEE("Java");

        private String brandName;

        ItemType(String brandName) {
            this.brandName = brandName;
        }

        public String getBrandName() {
            return brandName;
        }

}
