package com.yayaveli.inventorymanagement.controllers.api;

import java.util.List;

import com.yayaveli.inventorymanagement.dto.ItemDto;
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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping(Constants.APP_ROOT
        + "/items")
@Api(Constants.APP_ROOT + "/items")
public interface ItemApi {

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un article", notes = "Cette methode permet d'enregistrer ou de modifier un article", response = ItemDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet article crée / modifié"),
            @ApiResponse(code = 400, message = "L'objet article n'est pas valide")
    })
    ItemDto save(@RequestBody ItemDto itemDto);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rehercher un article", notes = "Cette methode permet de chercher un article par son ID", response = ItemDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article a été trouvé dans la BD"),
            @ApiResponse(code = 404, message = "Aucun article n'existe dans la bd avec l'ID fourni"),
    })
    ItemDto findById(@PathVariable Integer id);

    @GetMapping(value = "/{itemCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rehercher un article", notes = "Cette methode permet de chercher un article par son code", response = ItemDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article a été trouvé dans la BD"),
            @ApiResponse(code = 404, message = "Aucun article n'existe dans la bd avec le CODE fourni"),
    })
    ItemDto findByItemCode(String itemCode);

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des articles", notes = "Cette methode permet de chercher et renvoyer la liste des articles qui existe dans la BD")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des articles / Une liste vide")
    })
    List<ItemDto> findAll();

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Supprimer un article", notes = "Cette methode permet de supprimer un article par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article a été supprimé")
    })
    void delete(@PathVariable Integer id);
}
