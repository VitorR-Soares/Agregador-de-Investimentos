package com.investment_aggregator.investment_aggregator.services;

import com.investment_aggregator.investment_aggregator.controllers.dto.AccountStockResponseDTO;
import com.investment_aggregator.investment_aggregator.controllers.dto.AssociateAccountStockDTO;
import com.investment_aggregator.investment_aggregator.entities.AccountStock;
import com.investment_aggregator.investment_aggregator.entities.AccountStockId;
import com.investment_aggregator.investment_aggregator.repositories.AccountRepository;
import com.investment_aggregator.investment_aggregator.repositories.AccountStockRepository;
import com.investment_aggregator.investment_aggregator.repositories.StockRepository;
import com.investment_aggregator.investment_aggregator.utils.ConvertUUID;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private StockRepository stockRepository;
    private AccountStockRepository accountStockRepository;

    public AccountService(AccountRepository accountRepository, StockRepository stockRepository, AccountStockRepository accountStockRepository) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
    }

    @Transactional
    public void associateAccountStock(String accountId, AssociateAccountStockDTO dto){

        var uuid = ConvertUUID.fromHexStringToUUID(accountId);

        var accountFound = accountRepository.findById(uuid)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var stockFound = stockRepository.findById(dto.stockId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        AccountStockId accountStockId =
                new AccountStockId(stockFound.getId(),
                        accountFound.getId());

        AccountStock accountStock = new AccountStock(
                accountStockId,
                accountFound,
                stockFound,
                dto.qtd()
        );

        accountStockRepository.save(accountStock);

    }


}
