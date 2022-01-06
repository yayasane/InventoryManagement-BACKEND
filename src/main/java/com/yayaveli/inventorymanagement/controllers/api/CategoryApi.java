package com.yayaveli.inventorymanagement.controllers.api;

import java.util.List;

import com.yayaveli.inventorymanagement.dto.CategoryDto;
import com.yayaveli.inventorymanagement.utils.Constants;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping(Constants.APP_ROOT
                + "/categories")
@Api(Constants.APP_ROOT + "/categories")
public interface CategoryApi {
        @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        @ApiOperation(value = "Enregistrer catégorie catégorie", notes = "Cette methode permet d'enregistrer ou de modifier catégorie catégorie", response = CategoryDto.class)
        @ApiResponses(value = {
                        @ApiResponse(code = 200, message = "La objet catégorie crée / modifié"),
                        @ApiResponse(code = 400, message = "La objet catégorie n'est pas valide")
        })

        CategoryDto save(@RequestBody CategoryDto categoryDto);

        @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        @ApiOperation(value = "Rehercher une catégorie", notes = "Cette methode permet de chercher une catégorie par son ID", response = CategoryDto.class)
        @ApiResponses(value = {
                        @ApiResponse(code = 200, message = "La catégorie a été trouvé dans la BD"),
                        @ApiResponse(code = 404, message = "Aucune catégorie n'existe dans la bd avec l'ID fourni"),
        })
        CategoryDto findById(@PathVariable Integer id);

        @GetMapping(value = "/{categoryCode}", produces = MediaType.APPLICATION_JSON_VALUE)
        @ApiOperation(value = "Rehercher une catégorie", notes = "Cette methode permet de chercher une catégorie par son code", response = CategoryDto.class)
        @ApiResponses(value = {
                        @ApiResponse(code = 200, message = "La catégorie a été trouvé dans la BD"),
                        @ApiResponse(code = 404, message = "Aucune catégorie n'existe dans la bd avec le CODE fourni"),
        })
        CategoryDto findByCategoryCode(@ApiParam(value = "Accepted Value {CAT1, CAT2, CAT3}") String categoryCode);

        @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
        @ApiOperation(value = "Renvoi la liste des catégories", notes = "Cette methode permet de chercher et renvoyer la liste des catégories qui existe dans la BD")
        @ApiResponses(value = {
                        @ApiResponse(code = 200, message = "La liste des catégories / Une liste vide")
        })
        List<CategoryDto> findAll();

        @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        @ApiOperation(value = "Supprimer une catégorie", notes = "Cette methode permet de supprimer une catégorie par son ID")
        @ApiResponses(value = {
                        @ApiResponse(code = 200, message = "La catégorie a été supprimé")
        })
        void delete(@PathVariable Integer id);

}
