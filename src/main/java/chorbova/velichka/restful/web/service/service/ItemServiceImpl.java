package chorbova.velichka.restful.web.service.service;

import chorbova.velichka.restful.web.service.exceptions.MissingItemException;
import chorbova.velichka.restful.web.service.exceptions.NotEnoughFundsException;
import chorbova.velichka.restful.web.service.model.inventory.Beverage;
import chorbova.velichka.restful.web.service.model.inventory.Food;
import chorbova.velichka.restful.web.service.model.inventory.Item;
import chorbova.velichka.restful.web.service.model.inventory.ItemType;
import chorbova.velichka.restful.web.service.repository.BeverageRepository;
import chorbova.velichka.restful.web.service.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    BeverageRepository beverageRepository;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    BalanceService balanceService;

    public ItemServiceImpl(BeverageRepository beverageRepository, FoodRepository foodRepository,
                           BalanceService balanceService) {
        this.beverageRepository = beverageRepository;
        this.foodRepository = foodRepository;
        this.balanceService = balanceService;
    }

    @Override
    public List<Beverage> getAllBeverages() throws MissingItemException {
        List<Beverage> beveragesList = beverageRepository.findAll();
        if (beveragesList.isEmpty())
            throw new MissingItemException("The inventory of the machine is empty. Please contact the staff!");
        return beveragesList;
    }

    @Override
    public List<Food> getAllFoods() throws MissingItemException {
        List<Food> foodsList = foodRepository.findAll();
        if (foodsList.isEmpty()) {
            throw new MissingItemException("The inventory of the machine is empty. Please contact the staff!");
        }
        return foodsList;
    }

    @Override
    public Beverage buyBeverageByType(String itemType) throws MissingItemException, NotEnoughFundsException {
        ItemType type = checkifValidItemType(itemType); // check if valid item type
        List<Beverage> beverages = beverageRepository.findByType(type);
        Beverage beverage = !beverages.isEmpty() ? beverages.get(0) : null; // if no item of that type provided, pass null

        checkIfBuyable(beverage); // validates that the item satisfies to conditions to be bought

        checkIfEnoughFundsToBuy(beverage); // checks if the balance is enough to cover the cost of the item

        beverage.setQuantity(beverage.getQuantity() - 1); // decrease the quantity of the item at hand

        beverageRepository.save(beverage); // persist the change in the db

        balanceService.deleteCoinBalance(); // clear the balance for the next user

        return beverage;
    }

    @Override
    public Food buyFoodItemByType(String itemType) throws MissingItemException, NotEnoughFundsException {
        ItemType type = checkifValidItemType(itemType); // check if valid item
        List<Food> foods = foodRepository.findByType(type);
        Food food = !foods.isEmpty() ? foods.get(0) : null; // if no item of that type provided, pass null

        checkIfBuyable(food); // validates that the item satisfies to conditions to be bought

        checkIfEnoughFundsToBuy(food); // checks if the balance is enough to cover the cost of the item

        food.setQuantity(food.getQuantity() - 1); // decrease the quantity of the item at hand

        foodRepository.save(food); // persist the change in the db

        balanceService.deleteCoinBalance(); // clear the balance for the next user

        return food;
    }

    /**
     * Check if the balance at hand is enough to cover the cost
     * of the product yet to be purchased.
     *
     * @param item
     * @throws NotEnoughFundsException
     */
    private void checkIfEnoughFundsToBuy(Item item) throws NotEnoughFundsException {
        BigDecimal balance = balanceService.calculateBalance();
        // if the price of the item is greater than the balance at hand, throw exception
        if (item.getPrice().compareTo(balance) > 0) {
            throw new NotEnoughFundsException();
        }
    }


    /**
     * This method verifies that an Item is buyable.
     * For an item to be buyable it must satisfy the following conditions:
     * it shouldn't be null, its availability flag should be set to true and
     * there should be at least one product at hand.
     * If an item is not buyable and exception is thrown.
     *
     * @param item
     */
    private void checkIfBuyable(Item item) throws MissingItemException {
        if (item == null || !item.isAvailable() || item.getQuantity().compareTo(0) <= 0) {
            throw new MissingItemException("The operation cannot be performed successfully. " +
                    "Either the product is missing, or there is not enough stock. Please refer to the menu.");
        }
    }

    /**
     * This method validates that a passed String object is a valid
     * ItemType instance.
     *
     * @param type
     * @return ItemType
     * @throws MissingItemException
     */
    private ItemType checkifValidItemType(String type) throws MissingItemException {
        ItemType itemType;
        try {
            // attempt to convert the passed String to ItemType
            itemType = ItemType.valueOf(type);
        } catch (IllegalArgumentException ex) {

            // hiding the generic Exception with one from the domain
            throw new MissingItemException("This Vending Machine does not offer items of type : " + type);
        }
        return itemType;
    }


}
