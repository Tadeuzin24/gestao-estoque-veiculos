package br.com.gestaoveiculos.controller;

import br.com.gestaoveiculos.dto.ModeloRequest;
import br.com.gestaoveiculos.dto.ModeloResponse;
import br.com.gestaoveiculos.service.ModeloService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/modelos")
public class ModeloController {

    private final ModeloService modeloService;

    public ModeloController(ModeloService modeloService) {
        this.modeloService = modeloService;
    }

    @PostMapping
    public ResponseEntity<ModeloResponse> cadastrar(
            @Valid @RequestBody ModeloRequest request) {

        ModeloResponse modelo = modeloService.cadastrar(request);

        return ResponseEntity
                .created(URI.create("/api/modelos/" + modelo.id()))
                .body(modelo);
    }

    @GetMapping
    public ResponseEntity<List<ModeloResponse>> listar() {
        return ResponseEntity.ok(modeloService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModeloResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(modeloService.buscarPorId(id));
    }

    @GetMapping("/marca/{marcaId}")
    public ResponseEntity<List<ModeloResponse>> listarPorMarca(
            @PathVariable Long marcaId) {

        return ResponseEntity.ok(
                modeloService.listarPorMarca(marcaId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModeloResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ModeloRequest request) {

        return ResponseEntity.ok(
                modeloService.atualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        modeloService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
