package com.investment_aggregator.investment_aggregator.controllers;

import com.investment_aggregator.investment_aggregator.controllers.dto.AccountStockResponseDTO;
import com.investment_aggregator.investment_aggregator.controllers.dto.AssociateAccountStockDTO;
import com.investment_aggregator.investment_aggregator.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/account")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountId}")
    public ResponseEntity<Void> buyStock(@PathVariable("accountId") String accountId,
                                         @RequestBody AssociateAccountStockDTO associateAccountStockDTO){

        accountService.associateAccountStock(accountId, associateAccountStockDTO);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{accountId}")
    public ResponseEntity<List<AccountStockResponseDTO>> listStocks(@PathVariable("accountId") String accountId){

        var stocksList = accountService.listStocks(accountId);

        return ResponseEntity.ok(stocksList);

    }
}
