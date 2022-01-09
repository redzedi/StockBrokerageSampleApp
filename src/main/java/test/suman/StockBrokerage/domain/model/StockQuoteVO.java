package test.suman.StockBrokerage.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StockQuoteVO {
	
	private final String stockCode;
	private final Double price;
	private final long quoteTime;

}
