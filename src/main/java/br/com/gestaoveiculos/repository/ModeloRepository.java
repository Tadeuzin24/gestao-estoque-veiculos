package br.com.gestaoveiculos.repository;

import br.com.gestaoveiculos.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {

    List<Modelo> findByMarca_Id(Long marcaId);

    boolean existsByMarca_Id(Long marcaId);

    boolean existsByNomeIgnoreCaseAndMarca_Id(
            String nome,
            Long marcaId
    );
}