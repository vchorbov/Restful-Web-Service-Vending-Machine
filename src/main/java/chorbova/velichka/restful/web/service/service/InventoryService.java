package chorbova.velichka.restful.web.service.service;

import chorbova.velichka.restful.web.service.exceptions.FullCapacityException;
import chorbova.velichka.restful.web.service.exceptions.ItemAlreadyPresentException;
import chorbova.velichka.restful.web.service.exceptions.MissingItemException;
import chorbova.velichka.restful.web.service.exceptions.MissingValuesException;
import chorbova.velichka.restful.web.service.model.inventory.Beverage;
import chorbova.velichka.restful.web.service.model.inventory.Food;
import org.springframework.stereotype.Service;

@Service
public interface InventoryService {
    /**
     * The method adds new Item of type Beverage to the Vending Machine.
     * All the Beverage parameters should be provided for successful
     * persistence of the item.
     * The type of the Beverage is unique, and should be predefined
     * in the ItemType enumeration.
     * For now, it is assumed that the users of the method will satisfy this
     * condition.
     *
     * @param beverage
     * @return Beverage
     * @throws MissingItemException
     * @throws MissingValuesException
     * @throws FullCapacityException
     * @throws ItemAlreadyPresentException
     */
    Beverage addBeverage(Beverage beverage) throws MissingItemException, MissingValuesException,
            FullCapacityException, ItemAlreadyPresentException;

    /**
     * The method adds new Item of type Food to the Vending Machine.
     * All the Food parameters should be provided for successful
     * persistence of the item.
     * The type of the Food is unique, and should be predefined
     * in the ItemType enumeration.
     * For now, it is assumed that the users of the method will satisfy this
     * condition.
     *
     * @param food
     * @return Food
     * @throws MissingItemException
     * @throws MissingValuesException
     * @throws FullCapacityException
     * @throws ItemAlreadyPresentException
     */
   Food addFood(Food food) throws MissingItemException, MissingValuesException,
           FullCapacityException, ItemAlreadyPresentException;

    /**
     * The method retrieves persisted Beverage by id and if provided,
     * updates its values, by replacing them with those of the
     * passed Beverage instance.
     * If no Beverage with this id exists, an Exception is thrown.
     *
     * @param id
     * @param beverage
     * @return Beverage
     * @throws MissingItemException
     * @throws MissingValuesException
     * @throws FullCapacityException
     */
   Beverage updateBeverage(Long id, Beverage beverage) throws MissingItemException,
           MissingValuesException, FullCapacityException;

    /**
     * The method retrieves persisted Food item by id and if provided,
     * updates its values, by replacing them with those of the
     * passed Food instance.
     * If no Food with this id exists, an Exception is thrown.
     *
     * @param id
     * @param food
     * @return Food
     * @throws MissingItemException
     * @throws MissingValuesException
     * @throws FullCapacityException
     */
    Food updateFood(Long id, Food food) throws MissingItemException,
            MissingValuesException, FullCapacityException;

    /**
     * The method deletes Beverage by id,
     * and if no Beverage with that id is found,
     * throws an Exception.
     *
     * @param id
     * @throws MissingItemException
     */
    void deleteBeverage(Long id) throws MissingItemException;

    /**
     * The method deletes Food item by id,
     * if no Food with that id is found,
     * throws an Exception.
     * @param id
     * @throws MissingItemException
     */
    void deleteFood(Long id) throws MissingItemException;


}
