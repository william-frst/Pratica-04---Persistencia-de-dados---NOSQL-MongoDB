package br.com.empresa.vendamais.repository;

import br.com.empresa.vendamais.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Método para buscar produtos por nome (case insensitive)
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    // Método para buscar produtos por quantidade
    List<Produto> findByQuantidadeGreaterThan(Integer quantidade);

    // Método para buscar produtos por faixa de preço
    List<Produto> findByPrecoBetween(Double precoMin, Double precoMax);

    // Query customizada para buscar produtos com estoque baixo
    @Query("SELECT p FROM Produto p WHERE p.quantidade <= :limite")
    List<Produto> findProdutosComEstoqueBaixo(@Param("limite") Integer limite);

    // Query nativa para estatísticas
    @Query(value = "SELECT COUNT(*) FROM produtos", nativeQuery = true)
    Long countTotalProdutos();
}

