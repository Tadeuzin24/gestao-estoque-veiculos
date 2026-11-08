package br.com.gestaoveiculos.controller;

import br.com.gestaoveiculos.dto.MarcaRequest;
import br.com.gestaoveiculos.dto.MarcaResponse;
import br.com.gestaoveiculos.service.MarcaService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @PostMapping
    public ResponseEntity<MarcaResponse> cadastrar(
            @Valid @RequestBody MarcaRequest request) {

        MarcaResponse marca = marcaService.cadastrar(request);

        return ResponseEntity
                .created(URI.create("/api/marcas/" + marca.id()))
                .body(marca);
    }

    @GetMapping
    public ResponseEntity<List<MarcaResponse>> listar() {
        return ResponseEntity.ok(marcaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(marcaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody MarcaRequest request) {

        return ResponseEntity.ok(
                marcaService.atualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        marcaService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
