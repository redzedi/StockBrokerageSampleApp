package test.suman.StockBrokerage.domain.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class PaymentOrder {

	//TODO: javax validation honoured in lombok
	Double amount;
	String currency;
	String authDate;
	
	String acctId;
	

}
