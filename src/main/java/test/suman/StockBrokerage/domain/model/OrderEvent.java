package test.suman.StockBrokerage.domain.model;

import lombok.Builder;
import lombok.Getter;
import test.suman.StockBrokerage.domain.model.Order.OrderStatus;


@Getter
@Builder
public class OrderEvent {
	
	private final String orderId;
	private final String txnId;
	private final OrderStatus currentStatus;
	private final Long createdat;

}
