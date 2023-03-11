package chorbova.velichka.restful.web.service.service;

import chorbova.velichka.restful.web.service.exceptions.NotSupportedCoinTypeException;
import chorbova.velichka.restful.web.service.model.balance.Balance;
import chorbova.velichka.restful.web.service.model.balance.CoinType;
import chorbova.velichka.restful.web.service.repository.BalanceRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


//@RunWith(MockitoJUnitRunner.class)
public class BalanceServiceImplTest {

    private BalanceRepository mockBalanceRepository;
    private BalanceServiceImpl mockBalanceService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void mockTheService() {
        mockBalanceRepository = mock(BalanceRepository.class);
        mockBalanceService = new BalanceServiceImpl(mockBalanceRepository);
    }

    @Test
    public void testAddCoinValidCoinType() throws NotSupportedCoinTypeException {
        // arrange
        String coinType = "TEN";
        Long initialBalanceQuantity = 5L;
        Balance balance = new Balance(CoinType.TEN, initialBalanceQuantity);
        Balance increasedQuantityBalance = new Balance(CoinType.TEN, 6l);
        Mockito.when(mockBalanceRepository.findByType(CoinType.TEN)).thenReturn(Collections.singletonList(balance));
        Mockito.when(mockBalanceRepository.save(balance)).thenReturn(balance);

        // act
        Balance updatedBalance = mockBalanceService.addCoin(coinType);

        // assert
        Mockito.verify(mockBalanceRepository, Mockito.times(1)).findByType(CoinType.TEN);
        Mockito.verify(mockBalanceRepository, Mockito.times(1)).save(balance);
        Assert.assertEquals(initialBalanceQuantity + 1, updatedBalance.getQuantity().intValue());
    }

    @Test
    public void testAddCoinThrowsNotSupportedCoinTypeException() {
        // arrange
        when(mockBalanceRepository.findByType(any(CoinType.class))).thenReturn(Collections.emptyList());

        // act and assert that the NotSupportedCoinTypeException is thrown
        String invalidCoinType = "INVALID";
        Assert.assertThrows(NotSupportedCoinTypeException.class, () -> {
            mockBalanceService.addCoin(invalidCoinType);
        });
    }

    @Test
    public void testGetAllCoinsBalance() {
        // arrange
        List<Balance> mockBalanceList = Arrays.asList(
                new Balance(CoinType.TEN, 5L),
                new Balance(CoinType.TWENTY, 10L)
        );
        when(mockBalanceRepository.findAll()).thenReturn(mockBalanceList);

        // act
        List<Balance> result = mockBalanceService.getAllCoinsBalance();

        //assert
        Assert.assertEquals(mockBalanceList, result);
    }

    @Test
    public void testDeleteCoinBalance() {
        // arrange
        List<Balance> mockBalanceList = Arrays.asList(
                new Balance(CoinType.TEN, 5L),
                new Balance(CoinType.TWENTY, 10L)
        );
        when(mockBalanceRepository.findAll()).thenReturn(mockBalanceList);

        // act
        mockBalanceService.deleteCoinBalance();

        // assert
        List<Balance> result = mockBalanceService.getAllCoinsBalance();
        Assert.assertEquals(Optional.of(0L), Optional.of(result.get(0).getQuantity()));
        Assert.assertEquals(Optional.of(0L), Optional.of(result.get(1).getQuantity()));
    }

    @Test
    public void testCalculateBalance() {
        // arrange
        List<Balance> mockBalanceList = Arrays.asList(
                new Balance(CoinType.TEN, 5L),
                new Balance(CoinType.TWENTY, 10L)
        );
        when(mockBalanceRepository.findAll()).thenReturn(mockBalanceList);

        // act
        BigDecimal actualBalance = mockBalanceService.calculateBalance();

        // assert
        Assert.assertEquals(new BigDecimal("2.50"), actualBalance);

    }

}
