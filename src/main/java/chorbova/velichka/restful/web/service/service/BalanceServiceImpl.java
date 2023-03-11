package chorbova.velichka.restful.web.service.service;

import chorbova.velichka.restful.web.service.exceptions.NotSupportedCoinTypeException;
import chorbova.velichka.restful.web.service.model.balance.Balance;
import chorbova.velichka.restful.web.service.model.balance.CoinType;
import chorbova.velichka.restful.web.service.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

@Service
public class BalanceServiceImpl implements BalanceService {

    private static final Integer INCREASED_BY_ONE = 1;
    @Autowired
    BalanceRepository balanceRepository;

    public BalanceServiceImpl(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @Override
    public Balance addCoin(String type) throws NotSupportedCoinTypeException {
        CoinType coinType;
        try {
            // attempt to convert the passed String to CoinType
            coinType = CoinType.valueOf(type);
        } catch (IllegalArgumentException ex) {

            // hiding the generic Exception with one from the domain
            throw new NotSupportedCoinTypeException("This Vending Machine does not support the inserted coin type: " + type);
        }
        Balance balanceToUpdate = balanceRepository.findByType(coinType).get(0);
        // at this point, it is guaranteed that such coin type is persisted in the Balance repo,
        // so we increase its quantity by one. Only one coin insertion at a time is supported for the moment;
        balanceToUpdate.setQuantity(balanceToUpdate.getQuantity() + INCREASED_BY_ONE);

        return balanceRepository.save(balanceToUpdate);
    }

    @Override
    public List<Balance> getAllCoinsBalance() {
        // we use it to return the total Balance after each coin insertion
        return balanceRepository.findAll();
    }

    @Override
    public void deleteCoinBalance() {
        // reset the quantity of each coin to 0, as it was initially
        balanceRepository.findAll().stream().forEach(balance1 -> balance1.setQuantity(0L));
        balanceRepository.saveAll(balanceRepository.findAll()); // save changes to database
    }

    @Override
    public BigDecimal calculateBalance() {
        MathContext precision = new MathContext(3); // 3 decimal precision
        BigDecimal balance = BigDecimal.ZERO;

        for (Balance b : getAllCoinsBalance()) {
            CoinType coinType = b.getAmount();
            Long quantity = b.getQuantity();

            balance = balance.add(coinType.getAmount().multiply(BigDecimal.valueOf(quantity)));
        }

        return balance.round(precision);
    }
}
