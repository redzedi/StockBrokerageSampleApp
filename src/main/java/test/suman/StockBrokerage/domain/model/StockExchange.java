package test.suman.StockBrokerage.domain.model;

public interface StockExchange {
	
	public StockQuoteVO getLTP(String stockCode);
	
	//bid
	public StockExchangeTxnSlip doTrade(StockExchangeReq parameterObject);
	
	//public CompletionStage<StockExchangeTxnSlip> getTradeStatus(String txnId);
	

}
