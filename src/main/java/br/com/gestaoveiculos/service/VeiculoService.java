
package br.com.gestaoveiculos.service;

import br.com.gestaoveiculos.dto.VeiculoRequest;
import br.com.gestaoveiculos.dto.VeiculoResponse;
import br.com.gestaoveiculos.entity.Modelo;
import br.com.gestaoveiculos.entity.StatusVeiculo;
import br.com.gestaoveiculos.entity.Veiculo;

import br.com.gestaoveiculos.repository.ModeloRepository;
import br.com.gestaoveiculos.repository.VeiculoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ModeloRepository modeloRepository;

    public VeiculoService(
            VeiculoRepository veiculoRepository,
            ModeloRepository modeloRepository) {

        this.veiculoRepository = veiculoRepository;
        this.modeloRepository = modeloRepository;
    }

    @Transactional(readOnly = true)
    public List<VeiculoResponse> listar(
            Long marcaId,
            Long modeloId,
            BigDecimal precoMin,
            BigDecimal precoMax,
            Integer anoInicial,
            Integer anoFinal,
            StatusVeiculo status) {

        validarFiltros(precoMin, precoMax, anoInicial, anoFinal);

        return veiculoRepository.filtrar(
                        marcaId,
                        modeloId,
                        precoMin,
                        precoMax,
                        anoInicial,
                        anoFinal,
                        status
                ).stream()
                .map(this::converter)
                .toList();
    }

    @Transactional(readOnly = true)
    public VeiculoResponse buscarPorId(Long id) {
        return converter(buscarEntidade(id));
    }

    @Transactional
    public VeiculoResponse cadastrar(VeiculoRequest request) {

        Modelo modelo = buscarModelo(request.modeloId());

        Veiculo veiculo = new Veiculo();

        aplicarDados(veiculo, request, modelo);

        return converter(veiculoRepository.save(veiculo));
    }

    @Transactional
    public VeiculoResponse atualizar(
            Long id,
            VeiculoRequest request) {

        Veiculo veiculo = buscarEntidade(id);

        Modelo modelo = buscarModelo(request.modeloId());

        aplicarDados(veiculo, request, modelo);

        return converter(veiculoRepository.save(veiculo));
    }

    @Transactional
    public void excluir(Long id) {

        Veiculo veiculo = buscarEntidade(id);

        veiculoRepository.delete(veiculo);
    }

    private Modelo buscarModelo(Long id) {
        return modeloRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Modelo não encontrado."
                ));
    }

    private Veiculo buscarEntidade(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Veículo não encontrado."
                ));
    }

    private void aplicarDados(
            Veiculo veiculo,
            VeiculoRequest request,
            Modelo modelo) {

        veiculo.setModelo(modelo);
        veiculo.setAnoFabricacao(request.anoFabricacao());
        veiculo.setCor(request.cor());
        veiculo.setPreco(request.preco());
        veiculo.setQuilometragem(request.quilometragem());
        veiculo.setStatus(request.status());
    }

    private void validarFiltros(
            BigDecimal precoMin,
            BigDecimal precoMax,
            Integer anoInicial,
            Integer anoFinal) {

        if (precoMin != null && precoMax != null
                && precoMin.compareTo(precoMax) > 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O preço mínimo não pode ser maior que o máximo."
            );
        }

        if (anoInicial != null && anoFinal != null
                && anoInicial > anoFinal) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O ano inicial não pode ser maior que o ano final."
            );
        }
    }

    private VeiculoResponse converter(Veiculo veiculo) {

        return new VeiculoResponse(
                veiculo.getId(),
                veiculo.getModelo().getId(),
                veiculo.getModelo().getNome(),
                veiculo.getModelo().getMarca().getNome(),
                veiculo.getAnoFabricacao(),
                veiculo.getCor(),
                veiculo.getPreco(),
                veiculo.getQuilometragem(),
                veiculo.getStatus()
        );
    }
}
