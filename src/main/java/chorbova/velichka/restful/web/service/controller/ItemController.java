package chorbova.velichka.restful.web.service.controller;


import chorbova.velichka.restful.web.service.exceptions.MissingItemException;
import chorbova.velichka.restful.web.service.exceptions.NotEnoughFundsException;
import chorbova.velichka.restful.web.service.model.inventory.Beverage;
import chorbova.velichka.restful.web.service.model.inventory.Food;
import chorbova.velichka.restful.web.service.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping("/beverage")
    public ResponseEntity getAllBeverages() {
        List<Beverage> beverages;
        try {
            beverages = itemService.getAllBeverages();
        } catch (MissingItemException ex) {
            return exceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return successResponse(beverages, HttpStatus.OK);
    }

    @GetMapping("/food")
    public ResponseEntity getAllFoods() {
        List<Food> foods;
        try {
            foods = itemService.getAllFoods();
        } catch (MissingItemException ex) {
            return exceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return successResponse(foods, HttpStatus.OK);
    }

    @GetMapping("/beverage/{beverageType}")
    public ResponseEntity getBeverageByType(@PathVariable String beverageType) {
        Beverage beverage;
        try {
            beverage = itemService.buyBeverageByType(beverageType);
        } catch (NotEnoughFundsException | MissingItemException ex) {
            return exceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return successResponse(beverage, HttpStatus.OK);
    }

    @GetMapping("/food/{foodType}")
    public ResponseEntity getFoodByType(@PathVariable String foodType) {
        Food foodItem;
        try {
            foodItem = itemService.buyFoodItemByType(foodType);
        } catch (NotEnoughFundsException | MissingItemException ex) {
            return exceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return successResponse(foodItem, HttpStatus.OK);
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
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(response);
    }
}
