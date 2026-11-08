
package br.com.gestaoveiculos.repository;

import br.com.gestaoveiculos.entity.Veiculo;
import br.com.gestaoveiculos.entity.StatusVeiculo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    boolean existsByModelo_Id(Long modeloId);

    @Query("""
        SELECT v FROM Veiculo v
        WHERE (:marcaId IS NULL OR v.modelo.marca.id = :marcaId)
        AND (:modeloId IS NULL OR v.modelo.id = :modeloId)
        AND (:precoMin IS NULL OR v.preco >= :precoMin)
        AND (:precoMax IS NULL OR v.preco <= :precoMax)
        AND (:anoInicial IS NULL OR v.anoFabricacao >= :anoInicial)
        AND (:anoFinal IS NULL OR v.anoFabricacao <= :anoFinal)
        AND (:status IS NULL OR v.status = :status)
        """)
    List<Veiculo> filtrar(
            @Param("marcaId") Long marcaId,
            @Param("modeloId") Long modeloId,
            @Param("precoMin") BigDecimal precoMin,
            @Param("precoMax") BigDecimal precoMax,
            @Param("anoInicial") Integer anoInicial,
            @Param("anoFinal") Integer anoFinal,
            @Param("status") StatusVeiculo status
    );
}
