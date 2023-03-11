package chorbova.velichka.restful.web.service.service;

import chorbova.velichka.restful.web.service.exceptions.FullCapacityException;
import chorbova.velichka.restful.web.service.exceptions.ItemAlreadyPresentException;
import chorbova.velichka.restful.web.service.exceptions.MissingItemException;
import chorbova.velichka.restful.web.service.exceptions.MissingValuesException;
import chorbova.velichka.restful.web.service.model.inventory.Beverage;
import chorbova.velichka.restful.web.service.model.inventory.Food;
import chorbova.velichka.restful.web.service.model.inventory.Item;
import chorbova.velichka.restful.web.service.repository.BeverageRepository;
import chorbova.velichka.restful.web.service.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class InventoryServiceImpl extends MachineService implements InventoryService {

    // A global variable for the entire application, defined in application.properties
    @Value("${vending.machine.capacity}")
    private Integer vendingMachineCapacity;
    @Autowired
    BeverageRepository beverageRepository;

    @Autowired
    FoodRepository foodRepository;

    @Override
    public Beverage addBeverage(Beverage beverage) throws MissingItemException, MissingValuesException,
            FullCapacityException, ItemAlreadyPresentException {

        // Perform some internal checks common for all Item Types
        internalChecks(beverage);


        // A beverage of that type is already present in the Vending Machine
        if (!beverageRepository.findByType(beverage.getType()).isEmpty()) {
            throw new ItemAlreadyPresentException();
        }

        /*
         * TODO: Implement a check for typesafty for the itemtype,
         * but for now we assume that the user will pass only the allowed types,
         * which are listed in the readme
         */
        return beverageRepository.save(beverage);

    }

    @Override
    public Food addFood(Food food) throws MissingItemException, MissingValuesException,
            FullCapacityException, ItemAlreadyPresentException {

        //Perform some internal checks common for all Item Types
        internalChecks(food);

        // A beverage of that type is already present in the Vending Machine
        if (!foodRepository.findByType(food.getType()).isEmpty()) {
            throw new ItemAlreadyPresentException();
        }

        return foodRepository.save(food);

    }

    @Override
    public Beverage updateBeverage(Long id, Beverage beverage) throws MissingItemException,
            MissingValuesException, FullCapacityException {

        // Perform some internal checks common for all Item Types
        internalChecks(beverage);

        // Check if a beverage with that id exists, and if not throw custom exception
        Beverage updateBeverage = beverageRepository.findById(id)
                .orElseThrow(() -> new MissingItemException(String
                        .format("Beverage with id %s does not exist!", id)));

        updateBeverage.setIsFizzyDrink(beverage.getIsFizzyDrink());
        updateBeverage.setQuantity(beverage.getQuantity());
        updateBeverage.setAvailable(beverage.getAvailable());
        updateBeverage.setPrice(beverage.getPrice());
        updateBeverage.setType(beverage.getType());

        return beverageRepository.save(updateBeverage);
    }

    @Override
    public Food updateFood(Long id, Food food) throws MissingItemException,
            MissingValuesException, FullCapacityException {


        // Perform some internal checks common for all Item Types
        internalChecks(food);

        // Check if food item with that id exists, and if not throw custom exception
        Food updateFood = foodRepository.findById(id)
                .orElseThrow(() -> new MissingItemException(String
                        .format("Food item with id %s does not exist!", id)));

        updateFood.setQuantity(food.getQuantity());
        updateFood.setAvailable(food.getAvailable());
        updateFood.setPrice(food.getPrice());
        updateFood.setType(food.getType());

        return foodRepository.save(updateFood);
    }

    @Override
    public void deleteBeverage(Long id) throws MissingItemException {

        /*
         * Check if a beverage with that id exists, and if not throw custom exception
         */
        Beverage deleteBeverage = beverageRepository.findById(id)
                .orElseThrow(() -> new MissingItemException(String
                        .format("Beverage with id %s does not exist!", id)));

        beverageRepository.delete(deleteBeverage);
    }

    @Override
    public void deleteFood(Long id) throws MissingItemException {

        // Check if food item with that id exists, and if not throw custom exception
        Food deleteFood = foodRepository.findById(id).orElseThrow(()
                -> new MissingItemException(String
                .format("Food item with id %s does not exist!", id)));

        foodRepository.delete(deleteFood);
    }

    /**
     * The method performs standard checks applicable for every instance
     * of class extending Item.
     * If not satisfactory, the appropriate Exception is thrown.
     *
     * @param item
     * @throws MissingItemException
     * @throws MissingValuesException
     * @throws FullCapacityException
     */
    private void internalChecks(Item item) throws MissingItemException,
            MissingValuesException, FullCapacityException {

        /*
         * The method is called without a value for Beverage
         */
        if (item == null) {
            throw new MissingItemException();
        }
        /*
         * The method is called with a Beverage instance without enough information to persist it successfully
         */
        if (item.getPrice() == null || item.getType() == null || item.getQuantity() == null) {
            throw new MissingValuesException();
        }

        /*
         * The Beverage is being added with grater quantity than possible in the machine
         */
        if (item.getQuantity().compareTo(vendingMachineCapacity) > 0) {
            throw new FullCapacityException();
        }
    }
}
