package br.com.gestaoveiculos.service;

import br.com.gestaoveiculos.dto.MarcaRequest;
import br.com.gestaoveiculos.dto.MarcaResponse;
import br.com.gestaoveiculos.entity.Marca;
import br.com.gestaoveiculos.repository.MarcaRepository;
import br.com.gestaoveiculos.repository.ModeloRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MarcaService {

    private final MarcaRepository marcaRepository;
    private final ModeloRepository modeloRepository;

    public MarcaService(
            MarcaRepository marcaRepository,
            ModeloRepository modeloRepository) {
        this.marcaRepository = marcaRepository;
        this.modeloRepository = modeloRepository;
    }

    public List<MarcaResponse> listar() {
        return marcaRepository.findAll()
                .stream()
                .map(this::converter)
                .toList();
    }

    public MarcaResponse buscarPorId(Long id) {
        return converter(buscarEntidade(id));
    }

    @Transactional
    public MarcaResponse cadastrar(MarcaRequest request) {

        String nome = request.nome().trim();

        if (marcaRepository.existsByNomeIgnoreCase(nome)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esta marca já está cadastrada."
            );
        }

        Marca marca = new Marca(nome);

        return converter(marcaRepository.save(marca));
    }

    @Transactional
    public MarcaResponse atualizar(Long id, MarcaRequest request) {

        Marca marca = buscarEntidade(id);
        String nome = request.nome().trim();

        if (!marca.getNome().equalsIgnoreCase(nome)
                && marcaRepository.existsByNomeIgnoreCase(nome)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe uma marca com esse nome."
            );
        }

        marca.setNome(nome);

        return converter(marcaRepository.save(marca));
    }

    @Transactional
    public void excluir(Long id) {

        Marca marca = buscarEntidade(id);

        if (modeloRepository.existsByMarca_Id(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Não é possível excluir uma marca que possui modelos cadastrados."
            );
        }

        marcaRepository.delete(marca);
    }

    private Marca buscarEntidade(Long id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Marca não encontrada."
                ));
    }

    private MarcaResponse converter(Marca marca) {
        return new MarcaResponse(
                marca.getId(),
                marca.getNome()
        );
    }
}
