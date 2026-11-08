package br.com.gestaoveiculos.repository;

import br.com.gestaoveiculos.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, Long> {

    boolean existsByNomeIgnoreCase(String nome);

}
