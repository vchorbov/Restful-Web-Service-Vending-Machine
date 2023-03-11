package chorbova.velichka.restful.web.service.model.balance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Balance extends chorbova.velichka.restful.web.service.model.Entity {
    @Column(unique=true)
    @Enumerated(EnumType.STRING)
    private CoinType amount;

    private Long quantity;
}
