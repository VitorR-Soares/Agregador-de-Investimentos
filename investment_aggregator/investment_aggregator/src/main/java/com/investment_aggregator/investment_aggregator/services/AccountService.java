package com.investment_aggregator.investment_aggregator.services;

import com.investment_aggregator.investment_aggregator.client.BrapiClient;
import com.investment_aggregator.investment_aggregator.controllers.dto.AccountStockResponseDTO;
import com.investment_aggregator.investment_aggregator.controllers.dto.AssociateAccountStockDTO;
import com.investment_aggregator.investment_aggregator.entities.AccountStock;
import com.investment_aggregator.investment_aggregator.entities.AccountStockId;
import com.investment_aggregator.investment_aggregator.repositories.AccountRepository;
import com.investment_aggregator.investment_aggregator.repositories.AccountStockRepository;
import com.investment_aggregator.investment_aggregator.repositories.StockRepository;
import com.investment_aggregator.investment_aggregator.utils.ConvertUUID;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AccountService {

    @Value("#{environment.TOKEN}")
    private String token;
    private AccountRepository accountRepository;
    private StockRepository stockRepository;
    private AccountStockRepository accountStockRepository;
    private BrapiClient brapiClient;

    public AccountService(AccountRepository accountRepository, StockRepository stockRepository, AccountStockRepository accountStockRepository, BrapiClient brapiClient) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
        this.brapiClient = brapiClient;
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
    @Transactional
    public List<AccountStockResponseDTO> listStocks(String accountId){

        var uuid = ConvertUUID.fromHexStringToUUID(accountId);

        var accountFound = accountRepository.findById(uuid)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var accountStockList = accountFound.getAccountStocks()
                .stream()
                .map(accountStock -> new AccountStockResponseDTO(accountStock.getStock().getId(),
                        accountStock.getQuantity(),
                        getTotalValue(accountStock.getStock().getId(),accountStock.getQuantity())))
                .toList();

        return accountStockList;


    }
    public double getTotalValue(String stockId, int qtd){

        var response = brapiClient.getQuote(token, stockId);

        if (response.results().isEmpty()){
            return  0;
        }
        var price = response.results().get(0).regularMarketPrice();
        return qtd * price;

    }


}
