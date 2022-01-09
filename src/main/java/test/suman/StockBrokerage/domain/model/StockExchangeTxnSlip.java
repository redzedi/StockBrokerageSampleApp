package test.suman.StockBrokerage.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class StockExchangeTxnSlip {
	
	//TODO: this can be absorped in the inheritance
	public static enum Status { ACCEPTED, REJECTED, EXPIRED, EXECUTED };
	
	private String txnSlipId;
	
   // uk --> txnId+txnStatus
	
	private final String txnId;
	
	private final String orderId;

	//txn details - should it be there
	private final String stockCode;
	private final Integer qty;
	private final boolean isBuy;
	private final long createdTime;
	private final Double askPrice;
	
	
	private final Status txnStatus;
	
	//executed
	private  Double settlementPrice;
	
	//rejected
	private String rejectionCode;
	private String rejectionMsg;
	
	//expired
	private Long expiredTime;
	

}
