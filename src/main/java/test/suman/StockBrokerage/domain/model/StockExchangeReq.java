package test.suman.StockBrokerage.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StockExchangeReq {
	
	private final String orderId;
	
	private final boolean isBuyOrder;
	private final String code;
	private final Integer qty;
	
	//if this is a market order - what will be the price ??
	private final  Double price;
	private final Long createdat;

	
}