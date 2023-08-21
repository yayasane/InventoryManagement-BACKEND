package com.yayaveli.inventorymanagement.controllers.api;

import java.util.List;

import com.yayaveli.inventorymanagement.dto.CompanyDto;
import com.yayaveli.inventorymanagement.utils.Constants;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;

@RequestMapping(Constants.COMPANY_ENDPOINT)
@Api(Constants.COMPANY_ENDPOINT)
public interface CompanyApi {
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CompanyDto save(@RequestBody CompanyDto categoryDto);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    CompanyDto findById(@PathVariable Integer id);

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    List<CompanyDto> findAll();

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable Integer id);
}
