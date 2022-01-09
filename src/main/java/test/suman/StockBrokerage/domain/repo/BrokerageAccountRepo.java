package test.suman.StockBrokerage.domain.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import test.suman.StockBrokerage.domain.model.BrokerageAccount;

@Repository
public interface BrokerageAccountRepo extends CrudRepository<BrokerageAccount,String>{


 
}