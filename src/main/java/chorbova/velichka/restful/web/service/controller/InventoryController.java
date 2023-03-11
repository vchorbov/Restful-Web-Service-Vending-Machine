package chorbova.velichka.restful.web.service.controller;

import chorbova.velichka.restful.web.service.exceptions.FullCapacityException;
import chorbova.velichka.restful.web.service.exceptions.ItemAlreadyPresentException;
import chorbova.velichka.restful.web.service.exceptions.MissingItemException;
import chorbova.velichka.restful.web.service.exceptions.MissingValuesException;
import chorbova.velichka.restful.web.service.model.inventory.Beverage;
import chorbova.velichka.restful.web.service.model.inventory.Food;
import chorbova.velichka.restful.web.service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    public static final String DELETE_SUCCESS_MESSAGE = "Item with id %s was deleted successfully";
    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/beverage")
    public ResponseEntity<Beverage> createBeverage(@RequestBody final Beverage beverage) {
        Beverage createdBeverage;

        try {

            createdBeverage = inventoryService.addBeverage(beverage);

        } catch (MissingItemException | MissingValuesException | FullCapacityException |
                 ItemAlreadyPresentException ex) {

            return exceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return successResponse(createdBeverage, HttpStatus.CREATED);
    }

    @PostMapping("/food")
    public ResponseEntity<Food> createFood(@RequestBody final Food foodItem) {
        Food createdFoodItem;

        try {

            createdFoodItem = inventoryService.addFood(foodItem);

        } catch (MissingItemException | MissingValuesException | FullCapacityException |
                 ItemAlreadyPresentException ex) {

            return exceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return successResponse(createdFoodItem, HttpStatus.CREATED);
    }

    @PutMapping("/beverage/{id}")
    public ResponseEntity<Beverage> updateBeverage(@PathVariable Long id, @RequestBody Beverage beverage) {
        Beverage updatedBeverage;

        try {

            updatedBeverage = inventoryService.updateBeverage(id, beverage);

        } catch (MissingItemException | MissingValuesException | FullCapacityException ex) {

            return exceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return successResponse(updatedBeverage, HttpStatus.OK);

    }

    @PutMapping("/food/{id}")
    public ResponseEntity<Food> updateBeverage(@PathVariable Long id, @RequestBody Food food) {
        Food updatedFood;

        try {
            updatedFood = inventoryService.updateFood(id, food);

        } catch (MissingItemException | MissingValuesException | FullCapacityException ex) {

            return exceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return successResponse(updatedFood, HttpStatus.OK);

    }

    @DeleteMapping("/beverage/{id}")
    public ResponseEntity<Beverage> updateBeverage(@PathVariable Long id) {

        try {
            inventoryService.deleteBeverage(id);

        } catch (MissingItemException ex) {

            return exceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return successResponse(String.format(DELETE_SUCCESS_MESSAGE, id), HttpStatus.OK);
    }

    @DeleteMapping("/food/{id}")
    public ResponseEntity<Beverage> updateFood(@PathVariable Long id) {

        try {

            inventoryService.deleteFood(id);

        } catch (MissingItemException ex) {

            return exceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return successResponse(String.format(DELETE_SUCCESS_MESSAGE, id), HttpStatus.OK);
    }


    private ResponseEntity successResponse(Object value, HttpStatus status) {
        HashMap<Object, Object> response = new HashMap<>();
        response.put("value", value);
        response.put("status", status.value());
        response.put("message", status.getReasonPhrase());
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    private ResponseEntity exceptionResponse(String errorMessage, HttpStatus status) {
        HashMap<Object, Object> response = new HashMap<>();
        response.put("error", errorMessage);
        response.put("status", status.value());
        response.put("message", status.getReasonPhrase());
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }
}
