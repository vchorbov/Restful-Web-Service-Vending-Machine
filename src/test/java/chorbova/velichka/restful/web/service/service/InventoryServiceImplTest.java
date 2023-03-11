package chorbova.velichka.restful.web.service.service;

import chorbova.velichka.restful.web.service.exceptions.FullCapacityException;
import chorbova.velichka.restful.web.service.exceptions.ItemAlreadyPresentException;
import chorbova.velichka.restful.web.service.exceptions.MissingItemException;
import chorbova.velichka.restful.web.service.exceptions.MissingValuesException;
import chorbova.velichka.restful.web.service.model.inventory.Beverage;
import chorbova.velichka.restful.web.service.model.inventory.Food;
import chorbova.velichka.restful.web.service.model.inventory.ItemType;
import chorbova.velichka.restful.web.service.repository.BalanceRepository;
import chorbova.velichka.restful.web.service.repository.BeverageRepository;
import chorbova.velichka.restful.web.service.repository.FoodRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

public class InventoryServiceImplTest {
    private BeverageRepository mockBeverageRepository;

    private FoodRepository mockFoodRepository;

    private InventoryServiceImpl mockInventoryService;

    private Integer vendingMachineCapacity;

    private Beverage uniqueBeverage;

    private Beverage exceptionBeverage;

    private Food uniqueFood;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void mockTheService() {
        vendingMachineCapacity = 10;
        mockBeverageRepository = mock(BeverageRepository.class);
        mockFoodRepository = mock(FoodRepository.class);
        mockInventoryService = new InventoryServiceImpl(mockBeverageRepository, mockFoodRepository);
        mockInventoryService.setVendingMachineCapacity(vendingMachineCapacity);

        uniqueBeverage = new Beverage();
        uniqueBeverage.setPrice(new BigDecimal("1.20"));
        uniqueBeverage.setQuantity(9);
        uniqueBeverage.setAvailable(true);
        uniqueBeverage.setIsFizzyDrink(false);
        uniqueBeverage.setType(ItemType.WATER);

        exceptionBeverage = new Beverage();
        exceptionBeverage.setPrice(null);
        exceptionBeverage.setQuantity(20);
        exceptionBeverage.setAvailable(true);
        exceptionBeverage.setIsFizzyDrink(false);
        exceptionBeverage.setType(ItemType.COFFEE);

        uniqueFood = new Food();
        uniqueFood.setQuantity(2);
        uniqueFood.setPrice(new BigDecimal("1.20"));
        uniqueFood.setAvailable(true);
        uniqueFood.setType(ItemType.APPLE);

    }

    @Test
    public void testAddBeverage() throws MissingItemException, MissingValuesException,
            FullCapacityException, ItemAlreadyPresentException {
        // arrange
        when(mockBeverageRepository.findByType(ItemType.WATER)).thenReturn(Collections.emptyList());
        when(mockBeverageRepository.save(uniqueBeverage)).thenReturn(uniqueBeverage);

        // act

        Beverage persistedBeverage = mockInventoryService.addBeverage(uniqueBeverage);

        //assert
        Assert.assertEquals(persistedBeverage, uniqueBeverage);
        verify(mockBeverageRepository, times(1)).findByType(ItemType.WATER);
        verify(mockBeverageRepository, times(1)).save(uniqueBeverage);

    }

    @Test
    public void testAddBeverageThrowsItemAlreadyPresentException() throws MissingItemException,
            MissingValuesException, FullCapacityException {
        // arrange
        when(mockBeverageRepository.findByType(ItemType.WATER)).thenReturn(Arrays.asList(uniqueBeverage));

        //act and assert
        assertThrows(ItemAlreadyPresentException.class, () -> mockInventoryService.addBeverage(uniqueBeverage));
        verify(mockBeverageRepository, times(1)).findByType(ItemType.WATER);
        verify(mockBeverageRepository, never()).save(uniqueBeverage);
    }

    @Test
    public void testAddBeverageThrowsMissingItemException() throws MissingItemException,
            MissingValuesException, FullCapacityException {
        // arrange
        when(mockBeverageRepository.findByType(ItemType.WATER)).thenReturn(Arrays.asList(uniqueBeverage));

        // assert
        assertThrows(MissingItemException.class, () -> mockInventoryService.addBeverage(null));

        // verify that if null is passed as value for Beverage, the following methods are never executed
        verify(mockBeverageRepository, never()).findByType(ItemType.WATER);
        verify(mockBeverageRepository, never()).save(any(Beverage.class));
    }

    @Test
    public void testAddBeverageThrowsMissingValuesException() {
        // act and assert
        assertThrows(MissingValuesException.class, () -> mockInventoryService.addBeverage(exceptionBeverage));
        verify(mockBeverageRepository, never()).findByType(ItemType.COFFEE);
        verify(mockBeverageRepository, never()).save(any(Beverage.class));
    }

    @Test
    public void testAddBeverageThrowsFullCapacityException() {
        //arrange
        exceptionBeverage.setPrice(new BigDecimal("2.10"));

        // act and assert
        assertThrows(FullCapacityException.class, () -> mockInventoryService.addBeverage(exceptionBeverage));
        verify(mockBeverageRepository, never()).findByType(ItemType.COFFEE);
        verify(mockBeverageRepository, never()).save(any(Beverage.class));
    }

    @Test
    public void testAddFood() throws MissingItemException, MissingValuesException,
            FullCapacityException, ItemAlreadyPresentException {
        // arrange
        when(mockFoodRepository.findByType(ItemType.APPLE)).thenReturn(Collections.emptyList());
        when(mockFoodRepository.save(uniqueFood)).thenReturn(uniqueFood);

        // act

        Food persistedFood = mockInventoryService.addFood(uniqueFood);

        //assert
        Assert.assertEquals(persistedFood, uniqueFood);
        verify(mockFoodRepository, times(1)).findByType(ItemType.APPLE);
        verify(mockFoodRepository, times(1)).save(uniqueFood);

    }

    @Test
    public void testUpdateBeverage() throws MissingItemException,
            MissingValuesException, FullCapacityException {
        // arrange
        Long id = 1L;

        when(mockBeverageRepository.save(uniqueBeverage)).thenReturn(uniqueBeverage);
        when(mockBeverageRepository.findById(id)).thenReturn(Optional.of(uniqueBeverage));

        //act
        Beverage updatedBeverage = mockInventoryService.updateBeverage(id, uniqueBeverage);

        //assert
        Assert.assertFalse(updatedBeverage.getIsFizzyDrink());
        Assert.assertEquals(uniqueBeverage.getQuantity(), updatedBeverage.getQuantity());
        Assert.assertTrue(updatedBeverage.isAvailable());
        Assert.assertEquals(uniqueBeverage.getPrice(), updatedBeverage.getPrice());
        Assert.assertEquals(uniqueBeverage.getType(), updatedBeverage.getType());
    }

    @Test
    public void testUpdateBeverageThrowsMissingItemException() throws MissingItemException,
            MissingValuesException, FullCapacityException {
        // arrange
        Long id = 1L;
        when(mockBeverageRepository.findById(id)).thenReturn(Optional.empty());

        // act and assert
        Assert.assertThrows(MissingItemException.class, () -> mockInventoryService.updateBeverage(id, uniqueBeverage));

    }

    @Test
    public void testUpdateFood() throws MissingItemException,
            MissingValuesException, FullCapacityException {
        // arrange
        Long id = 1L;

        when(mockFoodRepository.save(uniqueFood)).thenReturn(uniqueFood);
        when(mockFoodRepository.findById(id)).thenReturn(Optional.of(uniqueFood));

        //act
        Food updatedFood = mockInventoryService.updateFood(id, uniqueFood);

        //assert
        Assert.assertEquals(uniqueFood.getQuantity(), updatedFood.getQuantity());
        Assert.assertTrue(updatedFood.isAvailable());
        Assert.assertEquals(uniqueFood.getPrice(), updatedFood.getPrice());
        Assert.assertEquals(uniqueFood.getType(), updatedFood.getType());
    }

    @Test
    public void testUpdateFoodThrowsMissingItemException() throws MissingItemException,
            MissingValuesException, FullCapacityException {
        // arrange
        Long id = 1L;
        when(mockFoodRepository.findById(id)).thenReturn(Optional.empty());

        // act and assert
        Assert.assertThrows(MissingItemException.class, () -> mockInventoryService.updateFood(id, uniqueFood));

    }

    @Test
    void testDeleteBeverage() throws MissingItemException {
        // arrange
        Long id = 1L;
        Beverage beverage = new Beverage();
        Mockito.when(mockBeverageRepository.findById(id)).thenReturn(Optional.of(beverage));

        // act
        mockInventoryService.deleteBeverage(id);

        // assert
        Mockito.verify(mockBeverageRepository, Mockito.times(1)).delete(beverage);
    }

    @Test
    void testDeleteBeverageThrowsMissingItemException() {
        // arrange
        Long id = 1L;
        Mockito.when(mockBeverageRepository.findById(id)).thenReturn(Optional.empty());

        // act and assert
        assertThrows(MissingItemException.class, () -> mockInventoryService.deleteBeverage(id));
        Mockito.verify(mockBeverageRepository, Mockito.never()).delete(Mockito.any(Beverage.class));
    }


    @Test
    void testDeleteFood() throws MissingItemException {
        // arrange
        Long id = 1L;
        Food food = new Food();
        Mockito.when(mockFoodRepository.findById(id)).thenReturn(Optional.of(food));

        // act
        mockInventoryService.deleteFood(id);

        // assert
        Mockito.verify(mockFoodRepository, Mockito.times(1)).delete(food);
    }

    @Test
    void testDeleteFoodThrowsMissingItemException() {
        // arrange
        Long id = 1L;
        Mockito.when(mockFoodRepository.findById(id)).thenReturn(Optional.empty());

        // act and assert
        assertThrows(MissingItemException.class, () -> mockInventoryService.deleteFood(id));
        Mockito.verify(mockFoodRepository, Mockito.never()).delete(Mockito.any(Food.class));
    }
}
