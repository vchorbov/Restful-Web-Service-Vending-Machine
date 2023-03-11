package chorbova.velichka.restful.web.service.service;

import chorbova.velichka.restful.web.service.exceptions.MissingItemException;
import chorbova.velichka.restful.web.service.exceptions.NotEnoughFundsException;
import chorbova.velichka.restful.web.service.model.inventory.Beverage;
import chorbova.velichka.restful.web.service.model.inventory.Food;
import chorbova.velichka.restful.web.service.model.inventory.ItemType;
import chorbova.velichka.restful.web.service.repository.BeverageRepository;
import chorbova.velichka.restful.web.service.repository.FoodRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;

public class ItemServiceImplTest {

    private BeverageRepository mockBeverageRepository;

    private FoodRepository mockFoodRepository;

    private BalanceService mockBalanceService;

    private ItemServiceImpl mockItemService;

    private Beverage uniqueBeverage;

    private Food uniqueFood;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void mockTheService() {
        mockBeverageRepository = mock(BeverageRepository.class);
        mockFoodRepository = mock(FoodRepository.class);
        mockBalanceService = mock(BalanceService.class);
        mockItemService = new ItemServiceImpl(mockBeverageRepository,
                mockFoodRepository, mockBalanceService);

        uniqueBeverage = new Beverage();
        uniqueBeverage.setPrice(new BigDecimal("1.20"));
        uniqueBeverage.setQuantity(9);
        uniqueBeverage.setAvailable(true);
        uniqueBeverage.setIsFizzyDrink(false);
        uniqueBeverage.setType(ItemType.WATER);

        uniqueFood = new Food();
        uniqueFood.setQuantity(2);
        uniqueFood.setPrice(new BigDecimal("1.20"));
        uniqueFood.setAvailable(true);
        uniqueFood.setType(ItemType.APPLE);


    }

    @Test
    public void testGetAllBeverages() throws MissingItemException {
        // arrange
        List<Beverage> mockBeveragesList = new ArrayList<>();
        mockBeveragesList.add(new Beverage());
        mockBeveragesList.add(new Beverage());
        Mockito.when(mockBeverageRepository.findAll()).thenReturn(mockBeveragesList);

        // act
        List<Beverage> actualBeveragesList = mockItemService.getAllBeverages();

        // assert
        Assert.assertEquals(mockBeveragesList, actualBeveragesList);
    }

    @Test
    public void testGetAllBeveragesThrowsMissingItemException() {
        // arrange
        List<Beverage> mockBeveragesList = new ArrayList<>();
        Mockito.when(mockBeverageRepository.findAll()).thenReturn(mockBeveragesList);

        // act and assert
        Assert.assertThrows(MissingItemException.class, () -> {
            mockItemService.getAllBeverages();
        });
    }

    @Test
    public void testBuyBeverageByTypeWhenAvailableAndEnoughFunds() throws MissingItemException, NotEnoughFundsException {
        // arrange
        List<Beverage> mockBeveragesList = new ArrayList<>();
        mockBeveragesList.add(uniqueBeverage);
        Mockito.when(mockBeverageRepository.findByType(ItemType.WATER)).thenReturn(mockBeveragesList);
        Mockito.when(mockBalanceService.calculateBalance()).thenReturn(BigDecimal.valueOf(1.20));

        //act
        Beverage actualBeverage = mockItemService.buyBeverageByType("WATER");

        // assert
        Assert.assertEquals(mockBeveragesList.get(0), actualBeverage);
    }

    @Test
    public void testBuyBeverageByTypeThrowsMissingItemException() {
        //  arrange
        List<Beverage> mockBeveragesList = new ArrayList<>();
        Mockito.when(mockBeverageRepository.findByType(ItemType.COFFEE)).thenReturn(mockBeveragesList);
        Mockito.when(mockBalanceService.calculateBalance()).thenReturn(BigDecimal.valueOf(3.00));

        // act and assert
        Assert.assertThrows(MissingItemException.class, () -> {
            mockItemService.buyBeverageByType("COFFEE");
        });
    }

    @Test
    public void testBuyBeverageByTypeThrowsNotEnoughFundsException() {
        // arrange
        List<Beverage> mockBeveragesList = new ArrayList<>();
        mockBeveragesList.add(uniqueBeverage);
        Mockito.when(mockBeverageRepository.findByType(ItemType.WATER)).thenReturn(mockBeveragesList);
        Mockito.when(mockBalanceService.calculateBalance()).thenReturn(BigDecimal.valueOf(1.00));

        // act and assert
        Assert.assertThrows(NotEnoughFundsException.class, () -> {
            mockItemService.buyBeverageByType("WATER");
        });
    }

    @Test
    public void testBuyFoodItemByTypeWhenAvailableAndEnoughFunds() throws MissingItemException, NotEnoughFundsException {
        // arrange
        List<Food> mockFoodsList = new ArrayList<>();
        mockFoodsList.add(uniqueFood);
        Mockito.when(mockFoodRepository.findByType(ItemType.APPLE)).thenReturn(mockFoodsList);
        Mockito.when(mockBalanceService.calculateBalance()).thenReturn(BigDecimal.valueOf(1.20));

        //act
        Food actualBeverage = mockItemService.buyFoodItemByType("APPLE");

        // assert
        Assert.assertEquals(mockFoodsList.get(0), actualBeverage);
    }

    @Test
    public void testBuyFoodItemByTypeThrowsMissingItemException() {
        //  arrange
        List<Food> mockFoodsList = new ArrayList<>();
        Mockito.when(mockFoodRepository.findByType(ItemType.SANDWICH)).thenReturn(mockFoodsList);
        Mockito.when(mockBalanceService.calculateBalance()).thenReturn(BigDecimal.valueOf(3.00));

        // act and assert
        Assert.assertThrows(MissingItemException.class, () -> {
            mockItemService.buyBeverageByType("SANDWICH");
        });
    }

    @Test
    public void testBuyBuyFoodItemByTypeThrowsNotEnoughFundsException() {
        // arrange
        List<Food> mockFoodsList = new ArrayList<>();
        mockFoodsList.add(uniqueFood);
        Mockito.when(mockFoodRepository.findByType(ItemType.APPLE)).thenReturn(mockFoodsList);
        Mockito.when(mockBalanceService.calculateBalance()).thenReturn(BigDecimal.valueOf(1.00));

        // act and assert
        Assert.assertThrows(NotEnoughFundsException.class, () -> {
            mockItemService.buyFoodItemByType("APPLE");
        });
    }
}
