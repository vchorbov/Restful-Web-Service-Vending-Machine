package chorbova.velichka.restful.web.service.service;

import chorbova.velichka.restful.web.service.exceptions.NotSupportedCoinTypeException;
import chorbova.velichka.restful.web.service.model.balance.Balance;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface BalanceService {

    /**
     * This method accepts a String for the type of the coin
     * to be added, which can be no different from the
     * coin types listed in the CoinType Enumeration, otherwise a
     * NotSupportedCoinTypeException is thrown.
     * As the number of accepted coins is finite and each type is unique,
     * adding a new coin increases the amount of coins at hand of the given
     * type by 1.
     *
     * @param type
     * @return Balance
     * @throws NotSupportedCoinTypeException
     */
    Balance addCoin(String type) throws NotSupportedCoinTypeException;

    /**
     * This method returns list of all coins persisted in the Balance Repository.
     * It can show the user the current balance in the Vending Machine
     * after each insertion of coin.
     *
     * @return List<Balance>
     */
    List<Balance> getAllCoinsBalance();

    /**
     * This method clears the current coin Balance and returns a BigDecimal
     * number, which represents the state of the Balance as it was.
     *
     * @return BigDecimal
     */
    void deleteCoinBalance();

    /**
     * The method returns the total current Balance by iteration over
     * the CoinTyps, multiplying amount by quantity and adding up the result.
     * The output is rounded to the second decimal place.
     *
     * @return BigDecimal
     */
    BigDecimal calculateBalance();


}
