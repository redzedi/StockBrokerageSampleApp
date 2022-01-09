package test.suman.StockBrokerage.api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrokerageAccountDto {
	private  String userName;
	private  String fullName;
	private  Double cashBalance;
	private  Long createdat;
	
}
