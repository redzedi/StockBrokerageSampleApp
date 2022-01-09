package test.suman.StockBrokerage.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import test.suman.StockBrokerage.api.dtos.BrokerageAccountDto;
import test.suman.StockBrokerage.domain.model.BrokerageAccount;
import test.suman.StockBrokerage.domain.repo.BrokerageAccountRepo;

@RequestMapping("/accounts")
@RestController
public class AccountController {
	
	@Autowired
	private BrokerageAccountRepo acctRepo;
	
	
	
	@PostMapping()
	public ResponseEntity<Void> createAccount(@RequestBody BrokerageAccountDto dto,HttpServletRequest request) {
		
		BrokerageAccount mdl = dto2Mdl(dto);
		acctRepo.save(mdl);
		
		return ResponseEntity.created(URI.create(request.getRequestURL()+"/"+mdl.getUserName())).build();
		
	}

	
	
	@GetMapping("/{acctId}")
	public ResponseEntity<BrokerageAccountDto> getAccount(@PathVariable String acctId){
		
		BrokerageAccount mdl = acctRepo.findById(acctId).orElseThrow(()-> new HttpClientErrorException(HttpStatus.NOT_FOUND));
		
		BrokerageAccountDto dto = mdl2dto(mdl);
		
		return ResponseEntity.ok(dto);
		
	}
	
	@DeleteMapping("/{acctId}")
	public ResponseEntity<Void> deleteAccount(@PathVariable String acctId){
		
		 acctRepo.deleteById(acctId);
		
		return ResponseEntity.noContent().build();
		
	}
	
	@PutMapping("/{acctId}")
	public ResponseEntity<BrokerageAccountDto> updateAccount(@PathVariable String acctId,@RequestBody BrokerageAccountDto dto){
		
		BrokerageAccount mdl = dto2Mdl(dto);
		mdl.setUserName(acctId);
		mdl.setForUpdate(true);
		
		mdl = acctRepo.save(mdl);
		
		return ResponseEntity.ok(mdl2dto(mdl));
		
	}
	
	@GetMapping()
	public ResponseEntity<List<BrokerageAccountDto>> listAccounts(){
		
		Iterable<BrokerageAccount> mdls = acctRepo.findAll();
		
		List<BrokerageAccountDto> dtos = new ArrayList<>();
				
		mdls.forEach(dto-> dtos.add(this.mdl2dto(dto)));
		
		return ResponseEntity.ok(dtos);
		
	}

	protected BrokerageAccountDto mdl2dto(BrokerageAccount mdl) {
		BrokerageAccountDto dto = new BrokerageAccountDto();
		dto.setCashBalance(mdl.getCashBalance());
		dto.setUserName(mdl.getUserName());
		dto.setCreatedat(mdl.getCreatedat());
		dto.setFullName(mdl.getFullName());
		return dto;
	}
	
	protected BrokerageAccount dto2Mdl(BrokerageAccountDto dto) {
		BrokerageAccount mdl = new BrokerageAccount();
		mdl.setUserName(dto.getUserName());
		mdl.setFullName(dto.getFullName());
		return mdl;
	}
	

}
