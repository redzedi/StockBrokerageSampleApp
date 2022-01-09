package test.suman.StockBrokerage.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.suman.StockBrokerage.domain.model.StockInventory;

@Service
public class StockExchangeFactory {
	
	@Autowired
	private SimpleStockInventory exchg;
	
	public StockInventory getExchangeForCode(String stockCode) {
		return exchg;
	}

}
