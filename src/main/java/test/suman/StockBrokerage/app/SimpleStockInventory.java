package test.suman.StockBrokerage.app;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import test.suman.StockBrokerage.domain.model.StockExchange;
import test.suman.StockBrokerage.domain.model.StockExchangeReq;
import test.suman.StockBrokerage.domain.model.StockExchangeTxnSlip;
import test.suman.StockBrokerage.domain.model.StockInventory;
import test.suman.StockBrokerage.domain.model.StockInventoryObserver;
import test.suman.StockBrokerage.domain.model.StockQuoteVO;


@Service
public class SimpleStockInventory implements StockInventory {
	
	@Autowired
	private StockExchange stockExchg;
	
	
	private ConcurrentHashMap<String,StockQuoteVO> stockQuoteCache = new ConcurrentHashMap<>();
	
	private List<StockInventoryObserver> observers = new ArrayList<>();

	@Override
	public StockQuoteVO getLTP(String stockCode) {
		
		return stockQuoteCache.computeIfAbsent(stockCode, stockExchg::getLTP);
		
	}

	@Override
	public StockExchangeTxnSlip doTrade(StockExchangeReq parameterObject) {
		return stockExchg.doTrade(parameterObject);
	}

	@Override
	public List<StockQuoteVO> bulkGetLTP(List<String> stockCodes) {
		return stockCodes.stream().map(this::getLTP).collect(Collectors.toList());
	}

	@Override
	public void registerPriceWatcher(StockInventoryObserver watcher) {
		observers.add(watcher);

	}

	@Override
	public void deRegisterPriceWatcher(StockInventoryObserver watcher) {
		observers.remove(watcher);

	}

	@Scheduled(fixedRate = 2000)
	public void updateStockQuoteCache() {
		
		stockQuoteCache.replaceAll((k,oldV)->{
			if(System.currentTimeMillis() - oldV.getQuoteTime() < 2000) {
				return oldV;
			}else {
				return stockExchg.getLTP(k);
			}
		});
		
		observers.forEach(watcher->watcher.onPriceUpdate(this));
		
	}
}
