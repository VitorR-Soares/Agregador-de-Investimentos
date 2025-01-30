package com.investment_aggregator.investment_aggregator.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ClienteBrapi", url = "https://brapi.dev/")
public interface BrapiClient {

    @GetMapping("api/quote/{stockId}")
    BrapiDTO getQuote(@RequestParam("token") String token,
                      @PathVariable("stockId") String stockId);

}
