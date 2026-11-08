
package br.com.gestaoveiculos.controller;

import br.com.gestaoveiculos.dto.VeiculoRequest;
import br.com.gestaoveiculos.dto.VeiculoResponse;
import br.com.gestaoveiculos.entity.StatusVeiculo;
import br.com.gestaoveiculos.service.VeiculoService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @PostMapping
    public ResponseEntity<VeiculoResponse> cadastrar(
            @Valid @RequestBody VeiculoRequest request) {

        VeiculoResponse veiculo =
                veiculoService.cadastrar(request);

        return ResponseEntity
                .created(URI.create("/api/veiculos/" + veiculo.id()))
                .body(veiculo);
    }

    @GetMapping
    public ResponseEntity<List<VeiculoResponse>> listar(
            @RequestParam(required = false) Long marcaId,
            @RequestParam(required = false) Long modeloId,
            @RequestParam(required = false) BigDecimal precoMin,
            @RequestParam(required = false) BigDecimal precoMax,
            @RequestParam(required = false) Integer anoInicial,
            @RequestParam(required = false) Integer anoFinal,
            @RequestParam(required = false) StatusVeiculo status) {

        return ResponseEntity.ok(
                veiculoService.listar(
                        marcaId,
                        modeloId,
                        precoMin,
                        precoMax,
                        anoInicial,
                        anoFinal,
                        status
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                veiculoService.buscarPorId(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody VeiculoRequest request) {

        return ResponseEntity.ok(
                veiculoService.atualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        veiculoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
