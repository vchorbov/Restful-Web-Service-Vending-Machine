package chorbova.velichka.restful.web.service.model.inventory;

import chorbova.velichka.restful.web.service.model.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@MappedSuperclass
@Data
public abstract class Item extends Entity {
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private ItemType type;
    private BigDecimal price;
    private Boolean available;
    private Integer quantity;
}
