package test.suman.StockBrokerage.domain.model;

import javax.persistence.Entity;

import test.suman.StockBrokerage.domain.service.OrderProcessor;

@Entity
public class LimitOrder extends Order {

	

	protected LimitOrder(String orderReqId, String acctId, String stockCode, Integer quantity,
			Boolean isBuy) {
		super(orderReqId, acctId, stockCode, quantity, null, isBuy);
		// TODO Auto-generated constructor stub
	}

	@Override
	public StockExchangeReq createStockExchangeReq() {
		return StockExchangeReq.builder()
					.code(getStockCode())
					.isBuyOrder(this.getIsBuy())
					.orderId(getOrderId().toString())
					.qty(getQuantity())
					.build();
	}

	@Override
	public OrderProcessor createOrderProcessor() {
		return (svc, inv)->svc.processOrderTransactional(this);
	}

}
