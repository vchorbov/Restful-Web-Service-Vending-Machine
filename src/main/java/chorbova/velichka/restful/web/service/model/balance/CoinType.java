package chorbova.velichka.restful.web.service.model.balance;

import lombok.Getter;

import java.math.BigDecimal;

public enum CoinType {

    TEN(new BigDecimal(0.1)),
    TWENTY(new BigDecimal(0.2)),
    FIFTY(new BigDecimal(0.5)),
    LEV(new BigDecimal(1)),
    TWO_LEVA(new BigDecimal(2));
    @Getter
    private BigDecimal amount;

    private CoinType(BigDecimal amount) {
        this.amount = amount;
    }


}
