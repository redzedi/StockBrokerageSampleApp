package test.suman.StockBrokerage.domain.model;

import java.util.List;

public interface StockInventory extends StockExchange {

	public List<StockQuoteVO> bulkGetLTP(List<String> stockCodes);
	
	//public void watchStocks(List<String> stockCodes);
	
	public void registerPriceWatcher(StockInventoryObserver watcher);
	
	public void deRegisterPriceWatcher(StockInventoryObserver watcher);
}
