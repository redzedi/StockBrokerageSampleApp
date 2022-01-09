package test.suman.StockBrokerage.domain.model;

public interface PaymentChannel {
	
	PayInOrder pullFunds(String srcAcc , Double Amt , PaymentChannelReq req);
	void payoutFunds(PayoutOrder po, PaymentChannel Req);

}
