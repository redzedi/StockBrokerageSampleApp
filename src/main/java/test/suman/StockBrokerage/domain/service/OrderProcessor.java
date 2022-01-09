package test.suman.StockBrokerage.domain.service;

import test.suman.StockBrokerage.app.OrderAsyncExecutor;
import test.suman.StockBrokerage.domain.model.StockInventory;

@FunctionalInterface
public interface OrderProcessor {
	
	void processOrder(OrderAsyncExecutor svc , StockInventory inv);

}
