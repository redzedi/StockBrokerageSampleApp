package test.suman.StockBrokerage.app;

import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;

import test.suman.StockBrokerage.domain.model.StockExchange;
import test.suman.StockBrokerage.domain.model.StockExchangeReq;
import test.suman.StockBrokerage.domain.model.StockExchangeTxnSlip;
import test.suman.StockBrokerage.domain.model.StockQuoteVO;

@Service
public class SimpleStockExchange implements StockExchange {
	
	private Random rnd = new Random();

	@Override
	public StockQuoteVO getLTP(String stockCode) {
		return  new StockQuoteVO(stockCode,  rnd.nextDouble(), System.currentTimeMillis());
	}

	@Override
	public StockExchangeTxnSlip doTrade(StockExchangeReq req) {
		
		return StockExchangeTxnSlip.builder()
				.orderId(req.getOrderId())
				.txnId(UUID.randomUUID().toString())
				.txnStatus(StockExchangeTxnSlip.Status.EXECUTED)
				.settlementPrice(req.getPrice()!=null? Double.sum(0.50, req.getPrice()):rnd.nextDouble())
				.stockCode(req.getCode())
				.isBuy(req.isBuyOrder())
				.qty(req.getQty())
				.askPrice(req.getPrice())
				.build();
	}

}
