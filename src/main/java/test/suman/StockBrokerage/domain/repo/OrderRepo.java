package test.suman.StockBrokerage.domain.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import test.suman.StockBrokerage.domain.model.Order;


@Repository
public interface OrderRepo extends CrudRepository<Order,UUID> {

List<Order> findByAcctId(String acctId);

}
