
package br.com.gestaoveiculos.service;

import br.com.gestaoveiculos.dto.ModeloRequest;
import br.com.gestaoveiculos.dto.ModeloResponse;
import br.com.gestaoveiculos.entity.Marca;
import br.com.gestaoveiculos.entity.Modelo;

import br.com.gestaoveiculos.repository.MarcaRepository;
import br.com.gestaoveiculos.repository.ModeloRepository;
import br.com.gestaoveiculos.repository.VeiculoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ModeloService {

    private final ModeloRepository modeloRepository;
    private final MarcaRepository marcaRepository;
    private final VeiculoRepository veiculoRepository;

    public ModeloService(
            ModeloRepository modeloRepository,
            MarcaRepository marcaRepository,
            VeiculoRepository veiculoRepository) {

        this.modeloRepository = modeloRepository;
        this.marcaRepository = marcaRepository;
        this.veiculoRepository = veiculoRepository;
    }

    @Transactional(readOnly = true)
    public List<ModeloResponse> listar() {
        return modeloRepository.findAll()
                .stream()
                .map(this::converter)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ModeloResponse> listarPorMarca(Long marcaId) {

        buscarMarca(marcaId);

        return modeloRepository.findByMarca_Id(marcaId)
                .stream()
                .map(this::converter)
                .toList();
    }

    @Transactional(readOnly = true)
    public ModeloResponse buscarPorId(Long id) {
        return converter(buscarEntidade(id));
    }

    @Transactional
    public ModeloResponse cadastrar(ModeloRequest request) {

        String nome = request.nome().trim();
        Marca marca = buscarMarca(request.marcaId());

        verificarDuplicidade(nome, marca.getId());

        Modelo modelo = new Modelo(nome, marca);

        return converter(modeloRepository.save(modelo));
    }

    @Transactional
    public ModeloResponse atualizar(Long id, ModeloRequest request) {

        Modelo modelo = buscarEntidade(id);

        String nome = request.nome().trim();
        Marca marca = buscarMarca(request.marcaId());

        boolean mesmoRegistro =
                modelo.getNome().equalsIgnoreCase(nome)
                        && modelo.getMarca().getId().equals(marca.getId());

        if (!mesmoRegistro) {
            verificarDuplicidade(nome, marca.getId());
        }

        modelo.setNome(nome);
        modelo.setMarca(marca);

        return converter(modeloRepository.save(modelo));
    }

    @Transactional
    public void excluir(Long id) {

        Modelo modelo = buscarEntidade(id);

        if (veiculoRepository.existsByModelo_Id(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Não é possível excluir um modelo que possui veículos cadastrados."
            );
        }

        modeloRepository.delete(modelo);
    }

    private Marca buscarMarca(Long id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Marca não encontrada."
                ));
    }

    private Modelo buscarEntidade(Long id) {
        return modeloRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Modelo não encontrado."
                ));
    }

    private void verificarDuplicidade(String nome, Long marcaId) {

        if (modeloRepository.existsByNomeIgnoreCaseAndMarca_Id(
                nome, marcaId)) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Este modelo já está cadastrado para essa marca."
            );
        }
    }

    private ModeloResponse converter(Modelo modelo) {

        return new ModeloResponse(
                modelo.getId(),
                modelo.getNome(),
                modelo.getMarca().getId(),
                modelo.getMarca().getNome()
        );
    }
}
