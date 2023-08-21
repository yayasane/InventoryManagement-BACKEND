package com.yayaveli.inventorymanagement.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yayaveli.inventorymanagement.dto.CategoryDto;
import com.yayaveli.inventorymanagement.exceptions.EntityNotFoundException;
import com.yayaveli.inventorymanagement.exceptions.ErrorCodes;
import com.yayaveli.inventorymanagement.exceptions.InvalidEntityException;
import com.yayaveli.inventorymanagement.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.yayaveli.inventorymanagement.utils.Constants.APP_ROOT;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @WithMockUser
    @Test
    void PostCategory_thenShouldCreatedSuccess() throws Exception {
        //Given
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1)
                .categoryCode("Cat test")
                .designation("Designation test")
                .build();
        given(categoryService.save(any(CategoryDto.class))).willReturn(categoryDto);

        //When
        ResultActions resultActions = this.mockMvc.perform(post("/" + APP_ROOT
                        + "/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectToJson(categoryDto)))
        )
                .andDo(print());

        //Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("categoryCode").value("Cat test"))
                .andExpect(jsonPath("designation").value("Designation test"));
    }

    @WithMockUser
    @Test
    void PostCategory_thenShouldThrowInvalidEntityException() throws Exception {
        //Given
        CategoryDto categoryDto = CategoryDto.builder()
                .build();
        given(categoryService.save(any(CategoryDto.class))).willThrow(new InvalidEntityException("La catégorie n'est pas valide", ErrorCodes.CATEGORY_NOT_VALID, Collections.emptyList()));

        //When
        ResultActions resultActions = mockMvc.perform(post("/" + APP_ROOT + "/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectToJson(categoryDto)))
        ).andDo(print());

        //Then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("httpCode").value(400))
                .andExpect(jsonPath("code").value(ErrorCodes.CATEGORY_NOT_VALID.getCode()))
                .andExpect(jsonPath("message").value("La catégorie n'est pas valide"));
        then(categoryService).should().save(any(CategoryDto.class));
    }

    @WithMockUser
    @Test
    void GetCategoryById_thenShouldGetSuccess() throws Exception {
        //Given
        CategoryDto categoryDto = CategoryDto.builder()
                .id(55)
                .categoryCode("Cat test")
                .designation("Designation test")
                .build();
        given(categoryService.findById(55)).willReturn(categoryDto);

        //When
        ResultActions resultActions = this.mockMvc.perform(get("/" + APP_ROOT
                        + "/categories/"+categoryDto.getId())
        )
                .andDo(print());

        //Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("id").value(55))
                .andExpect(jsonPath("categoryCode").value("Cat test"))
                .andExpect(jsonPath("designation").value("Designation test"));
        then(categoryService).should().findById(55);
    }

    @WithMockUser
    @Test
    void GetCategoryById_thenShouldReturnStatus404() throws Exception {
        //Given
        int categoryId = 55;
        given(categoryService.findById(categoryId)).willThrow(new EntityNotFoundException("Aucune catégorie avec l'id = " + categoryId, ErrorCodes.CATEGORY_NOT_FOUND));

        //When
        ResultActions resultActions = this.mockMvc.perform(get("/" + APP_ROOT
                + "/categories/"+categoryId)
        )
                .andDo(print());

        //Then
        resultActions.andExpect(status().isNotFound())
                .andExpect(jsonPath("httpCode").value(404))
                .andExpect(jsonPath("code").value(ErrorCodes.CATEGORY_NOT_FOUND.getCode()))
                .andExpect(jsonPath("message").value("Aucune catégorie avec l'id = " + categoryId));
        then(categoryService).should().findById(categoryId);
    }

    @WithMockUser
    @Test
    void GetCategoryByCode_thenShouldGetSuccess() throws Exception {
        //Given
        CategoryDto categoryDto = CategoryDto.builder()
                .id(55)
                .categoryCode("CAT55")
                .designation("Designation test")
                .build();
        given(categoryService.findByCategoryCode("CAT55")).willReturn(categoryDto);

        //When
        ResultActions resultActions = this.mockMvc.perform(get("/" + APP_ROOT
                + "/categories/byCategoryCode/"+categoryDto.getCategoryCode())
        )
                .andDo(print());

        //Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("id").value(55))
                .andExpect(jsonPath("categoryCode").value("CAT55"))
                .andExpect(jsonPath("designation").value("Designation test"));
        then(categoryService).should().findByCategoryCode("CAT55");
    }

    @WithMockUser
    @Test
    void GetCategoryByCode_thenShouldReturnStatus404() throws Exception {
        //Given
        String categoryCode = "CAT1";
        given(categoryService.findByCategoryCode(categoryCode)).willThrow(new EntityNotFoundException("Aucune catégorie avec le code = " + categoryCode, ErrorCodes.CATEGORY_NOT_FOUND));

        //When
        ResultActions resultActions = this.mockMvc.perform(get("/" + APP_ROOT
                + "/categories/byCategoryCode/"+categoryCode)
        )
                .andDo(print());

        //Then
        resultActions.andExpect(status().isNotFound())
                .andExpect(jsonPath("httpCode").value(404))
                .andExpect(jsonPath("code").value(ErrorCodes.CATEGORY_NOT_FOUND.getCode()))
                .andExpect(jsonPath("message").value("Aucune catégorie avec le code = " + categoryCode));
        then(categoryService).should().findByCategoryCode(categoryCode);
    }

    @WithMockUser
    @Test
    void GetCategories_thenShouldReturnStatus200() throws Exception {
        //Given
        List<CategoryDto> categoryDtos = new ArrayList<>();
        given(categoryService.findAll()).willReturn(categoryDtos);
        //When
        ResultActions resultActions = this.mockMvc.perform(get("/" + APP_ROOT + "/categories"))
                .andDo(print());

        //Then
        resultActions.andExpect(status().isOk());
        then(categoryService).should().findAll();

    }

    @WithMockUser
    @Test
    void DeleteCategoryById_thenShouldReturnStatus204() throws Exception {
        //Given
        Integer categoryId = 1;
        willDoNothing().given(categoryService).delete(categoryId);

        //When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.delete("/" + APP_ROOT + "/categories/02"))
                .andDo(print());
        //Then
        resultActions.andExpect(status().isNoContent());
    }
    @WithMockUser
    @Test
    void DeleteCategoryById_thenShouldReturnStatus404() throws Exception {
        //Given
        Integer categoryId = 5555;
        willThrow(new EntityNotFoundException("Aucune catégorie avec l'id = " + categoryId,
                ErrorCodes.CATEGORY_NOT_FOUND)).given(categoryService).delete(categoryId);

        //When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.delete("/" + APP_ROOT + "/categories/5555"))
                .andDo(print());
        //Then
        resultActions.andExpect(status().isNotFound())
                .andExpect(jsonPath("httpCode").value(404))
                .andExpect(jsonPath("code").value(ErrorCodes.CATEGORY_NOT_FOUND.getCode()))
                .andExpect(jsonPath("message").value("Aucune catégorie avec l'id = " + categoryId));
        then(categoryService).should().delete(categoryId);
    }

    private String objectToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            fail("Failed to convert object to json");
            return null;
        }
    }
}