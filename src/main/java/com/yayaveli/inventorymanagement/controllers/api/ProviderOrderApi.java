package com.yayaveli.inventorymanagement.controllers.api;

import java.util.List;

import com.yayaveli.inventorymanagement.dto.ProviderOrderDto;
import com.yayaveli.inventorymanagement.utils.Constants;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.Api;

@Api(Constants.PROVIDER_ORDER_ENDPOINT + "")
public interface ProviderOrderApi {
    @PostMapping(Constants.PROVIDER_ORDER_ENDPOINT + "")
    ResponseEntity<ProviderOrderDto> save(@RequestBody ProviderOrderDto providerOrderDto);

    @GetMapping(Constants.PROVIDER_ORDER_ENDPOINT + "/{id}")
    ResponseEntity<ProviderOrderDto> findById(Integer id);

    @GetMapping(Constants.PROVIDER_ORDER_ENDPOINT + "/{OrderCode}")
    ResponseEntity<ProviderOrderDto> findByOrderCode(@PathVariable(name = "OrderCode") String orderCode);

    @GetMapping(Constants.PROVIDER_ORDER_ENDPOINT)
    ResponseEntity<List<ProviderOrderDto>> findAll();

    @DeleteMapping(Constants.PROVIDER_ORDER_ENDPOINT + "/{id}")
    ResponseEntity delete(@PathVariable Integer id);
}
