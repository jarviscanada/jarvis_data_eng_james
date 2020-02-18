package ca.jrvs.apps.trading.service;


import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceUnitTest {

    //capture parameter when calling securityOrderDao.save
    @Captor
    ArgumentCaptor<SecurityOrder> captorSecurityOrder;

    //mock all dependencies
    @Mock
    private AccountDao accountDao;
    @Mock
    private SecurityOrderDao securityOrderDao;
    @Mock
    private QuoteDao quoteDao;
    @Mock
    private PositionDao positionDao;

    //injecting mocker dependencies to the testing class via constructor
    @InjectMocks
    private OrderService orderService;

    @Before
    public void setup() {

        Account savedAccount = new Account();
        savedAccount.setTraderId(1);
        savedAccount.setAmount(100d);
        savedAccount.setId(1);
        when(accountDao.findById(any())).thenReturn(Optional.of(savedAccount));

        Quote savedQuote = new Quote();
        savedQuote.setAskPrice(10d);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(10.2d);
        savedQuote.setBidSize(10);
        savedQuote.setTicker("aapl");
        savedQuote.setLastPrice(10.1d);
        when(quoteDao.findById(any())).thenReturn(Optional.of(savedQuote));

        Position savedPosition = new Position();
        savedPosition.setPosition(10);
        savedPosition.setAccountId(1);
        savedPosition.setTicker("aapl");
        List<Position> positions = new ArrayList<>();
        positions.add(savedPosition);
        when(positionDao.findByIdList(any())).thenReturn(positions);

        SecurityOrder savedSecurityOrder = new SecurityOrder();
        savedSecurityOrder.setAccountId(1);
        savedSecurityOrder.setNotes("Good");
        savedSecurityOrder.setPrice(10d);
        savedSecurityOrder.setSize(10);
        savedSecurityOrder.setTicker(savedQuote.getTicker());
        savedSecurityOrder.setStatus("FILLED");
        savedSecurityOrder.setId(1);
        when(securityOrderDao.save(any())).thenReturn(savedSecurityOrder);
    }

    @Test
    public void buyOrder() {
        MarketDataDto marketDataDto = new MarketDataDto();
        marketDataDto.setAccountId(1);
        marketDataDto.setTicker("aapl");
        marketDataDto.setSize(5);
        orderService.executeMarketOrder(marketDataDto);
        verify(securityOrderDao).save(captorSecurityOrder.capture());
        assertEquals(captorSecurityOrder.getValue().getStatus(), "FILLED");
    }

    @Test
    public void sellOrder() {
        MarketDataDto marketDataDto = new MarketDataDto();
        marketDataDto.setAccountId(1);
        marketDataDto.setTicker("aapl");
        marketDataDto.setSize(-5);
        orderService.executeMarketOrder(marketDataDto);
        verify(securityOrderDao).save(captorSecurityOrder.capture());
        assertEquals(captorSecurityOrder.getValue().getStatus(), "FILLED");
    }

}