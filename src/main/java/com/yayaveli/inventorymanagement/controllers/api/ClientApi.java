package com.yayaveli.inventorymanagement.controllers.api;

import java.util.List;

import com.yayaveli.inventorymanagement.dto.ClientDto;
import com.yayaveli.inventorymanagement.utils.Constants;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;

@RequestMapping(Constants.APP_ROOT
        + "/clients")
@Api(Constants.APP_ROOT + "/clients")
public interface ClientApi {
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

    ClientDto save(@RequestBody ClientDto categoryDto);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)

    ClientDto findById(@PathVariable Integer id);

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)

    List<ClientDto> findAll();

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable Integer id);
}
