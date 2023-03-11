package chorbova.velichka.restful.web.service.service;

import chorbova.velichka.restful.web.service.repository.BeverageRepository;
import chorbova.velichka.restful.web.service.repository.FoodRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.mock;

public class ItemServiceImplTest {

    BeverageRepository mockBeverageRepository;

    FoodRepository mockFoodRepository;

    BalanceService mockBalanceService;

    ItemServiceImpl mockItemService;

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

    }

}
