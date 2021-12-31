package com.yayaveli.inventorymanagement.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.yayaveli.inventorymanagement.dto.CompanyDto;
import com.yayaveli.inventorymanagement.exceptions.EntityNotFoundException;
import com.yayaveli.inventorymanagement.exceptions.ErrorCodes;
import com.yayaveli.inventorymanagement.exceptions.InvalidEntityException;
import com.yayaveli.inventorymanagement.models.Company;
import com.yayaveli.inventorymanagement.repositories.CompanyRepository;
import com.yayaveli.inventorymanagement.services.CompanyService;
import com.yayaveli.inventorymanagement.validators.CompanyValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public CompanyDto save(CompanyDto companyDto) {
        List<String> errors = CompanyValidator.validate(companyDto);
        if (!errors.isEmpty()) {
            log.error("Company i not valid {}", companyDto);
            throw new InvalidEntityException("L'artcle n'est pas valide", ErrorCodes.COMPANY_NOT_VALID, errors);
        }
        return CompanyDto.fromEntity(companyRepository.save(CompanyDto.toEntity(companyDto)));
    }

    @Override
    public CompanyDto findById(Integer id) {
        if (id == null) {
            log.error("Company is null");
            return null;
        }
        Optional<Company> company = companyRepository.findById(id);

        CompanyDto companyDto = CompanyDto.fromEntity(company.get());

        return Optional.of(companyDto)
                .orElseThrow(() -> new EntityNotFoundException("Aucun article avec l'id = " + id,
                        ErrorCodes.COMPANY_NOT_FOUND));
    }

    @Override
    public CompanyDto findByCompanyCode(String companyCode) {
        if (!StringUtils.hasLength(companyCode)) {
            log.error("companyCode is null");
            return null;
        }
        Optional<Company> company = companyRepository.findByName(companyCode);

        CompanyDto companyDto = CompanyDto.fromEntity(company.get());

        return Optional.of(companyDto)
                .orElseThrow(() -> new EntityNotFoundException("Aucun article avec le code = " + companyCode,
                        ErrorCodes.COMPANY_NOT_FOUND));
    }

    @Override
    public List<CompanyDto> findAll() {
        return companyRepository.findAll().stream().map(CompanyDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Company is null");
            return;
        }
        companyRepository.deleteById(id);
    }

}
