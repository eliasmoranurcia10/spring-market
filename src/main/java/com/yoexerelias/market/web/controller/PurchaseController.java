package com.yoexerelias.market.web.controller;

import com.yoexerelias.market.domain.Purchase;
import com.yoexerelias.market.domain.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
@Tag(name = "Compras", description = "Operaciones relacionadas con compras")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;



    @GetMapping("/all")
    @Operation(summary = "Obtener todas las compras")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<List<Purchase>> getAll() {
        return new ResponseEntity<>(purchaseService.getAll(), HttpStatus.OK);
    }


    @GetMapping("/client/{id}")
    @Operation(
            summary = "Buscar compras por id del cliente",
            description = "Devuelve los datos de las compras que realizó un cliente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Compra no encontrada"),
    })
    public ResponseEntity<List<Purchase>> getByClient(
            @Parameter(description = "id del cliente", required = true, example = "4546221")
            @PathVariable("id") String clientId
    ) {
        return purchaseService.getByClient(clientId)
                .map(purchases -> new ResponseEntity<>(purchases, HttpStatus.OK))
                .orElse( new ResponseEntity<>(HttpStatus.NOT_FOUND) );
    }


    @PostMapping()
    @Operation(
            summary = "Save a purchase",
            description = "Guarda una compra con productos"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Compra realizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    public ResponseEntity<Purchase> save(@RequestBody Purchase purchase) {
        return new ResponseEntity<>(purchaseService.save(purchase), HttpStatus.CREATED);
    }

}
