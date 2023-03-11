package chorbova.velichka.restful.web.service.service;

import chorbova.velichka.restful.web.service.exceptions.MissingItemException;
import chorbova.velichka.restful.web.service.exceptions.NotEnoughFundsException;
import chorbova.velichka.restful.web.service.model.inventory.Beverage;
import chorbova.velichka.restful.web.service.model.inventory.Food;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemService {

    List<Beverage> getAllBeverages() throws MissingItemException;

    List<Food> getAllFoods() throws MissingItemException;

    /**
     * The following method buys a beverage by its type, but throws exceptions
     * if the item is missing or the user does not have enough funds.
     * The method also checks if the item is buyable and reduces its quantity after the purchase.
     * Finally, it saves the change to the database,
     * clears the balance for the next user, and returns the purchased beverage.
     *
     * @param itemType
     * @return Beverage
     * @throws MissingItemException
     * @throws NotEnoughFundsException
     */
    Beverage buyBeverageByType(String itemType) throws MissingItemException, NotEnoughFundsException;

    /**
     * The following method buys a food item by its type, but throws exceptions
     * if the item is missing or the user does not have enough funds.
     * The method also checks if the item is buyable and reduces its quantity after the purchase.
     * Finally, it saves the change to the database,
     * clears the balance for the next user, and returns the purchased beverage.
     *
     * @param itemType
     * @return Food
     * @throws MissingItemException
     * @throws NotEnoughFundsException
     */
    Food buyFoodItemByType(String itemType) throws MissingItemException, NotEnoughFundsException;
}
