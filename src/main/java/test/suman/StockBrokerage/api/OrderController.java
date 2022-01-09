package test.suman.StockBrokerage.api;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import test.suman.StockBrokerage.app.OrderService;
import test.suman.StockBrokerage.domain.model.Order;
import test.suman.StockBrokerage.domain.repo.OrderRepo;

@RequestMapping("/accounts/{userName}")
@RestController
public class OrderController {
	
	@Autowired
	private OrderService ordSvc;
	
	@Autowired
	private OrderRepo ordRepo;
	
	@PostMapping("/orders")
	public ResponseEntity<Void> placeOrder(@RequestBody Order order, HttpServletRequest request) {
		
		String orderId = ordSvc.submitOrder(order);
		
		return ResponseEntity.created(URI.create(request.getRequestURL().append(orderId).toString())).build();
	}
	
	
	@GetMapping("/orders/{orderId}")
	public ResponseEntity<Order> getOrder(@PathVariable String orderId) {
		Order mdl  = ordRepo.findById(UUID.fromString(orderId)).orElseThrow(()-> new HttpClientErrorException(HttpStatus.NOT_FOUND));
		
		return ResponseEntity.ok(mdl);
		
	}
	
	
	@GetMapping("/orders")
	public ResponseEntity<List<Order>> listOrdersForAcc(@PathVariable String userName) {
		
		return ResponseEntity.ok(ordRepo.findByAcctId(userName));
		
	}
	
	//"{\"orderType\":\"market-order\" , \"acctId\":\"sy1\", \"quantity\":\"10\", \"stockCode\":\"INFY\", \"isBuy\":true ,\"orderReqType\":\"1234\" }"

}
