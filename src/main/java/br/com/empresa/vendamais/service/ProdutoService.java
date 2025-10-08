package br.com.empresa.vendamais.service;

import br.com.empresa.vendamais.entity.Produto;
import br.com.empresa.vendamais.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    // CREATE - Inserir novo produto
    public Produto insertNew(Produto produto) {
        // Validações de negócio podem ser adicionadas aqui
        if (produto.getId() != null) {
            throw new IllegalArgumentException("ID deve ser nulo para inserção de novo produto");
        }
        return produtoRepository.save(produto);
    }

    // READ - Listar todos os produtos
    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    // READ - Buscar produto por ID
    public Optional<Produto> findById(Long id) {
        return produtoRepository.findById(id);
    }

    // READ - Buscar produtos por nome
    public List<Produto> findByNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    // READ - Buscar produtos com estoque baixo
    public List<Produto> findProdutosComEstoqueBaixo(Integer limite) {
        return produtoRepository.findProdutosComEstoqueBaixo(limite);
    }

    // READ - Buscar produtos por faixa de preço
    public List<Produto> findByFaixaPreco(Double precoMin, Double precoMax) {
        return produtoRepository.findByPrecoBetween(precoMin, precoMax);
    }

    // UPDATE - Atualizar produto existente
    public Produto update(Long id, Produto produtoAtualizado) {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);
        
        if (produtoExistente.isPresent()) {
            Produto produto = produtoExistente.get();
            produto.setNome(produtoAtualizado.getNome());
            produto.setQuantidade(produtoAtualizado.getQuantidade());
            produto.setDescricao(produtoAtualizado.getDescricao());
            produto.setPreco(produtoAtualizado.getPreco());
            return produtoRepository.save(produto);
        } else {
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }
    }

    // DELETE - Deletar produto por ID
    public void deleteById(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }
    }

    // Método auxiliar para verificar se produto existe
    public boolean existsById(Long id) {
        return produtoRepository.existsById(id);
    }

    // Método para contar total de produtos
    public Long countTotalProdutos() {
        return produtoRepository.countTotalProdutos();
    }

    // Método para atualizar estoque
    public Produto atualizarEstoque(Long id, Integer novaQuantidade) {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);
        
        if (produtoExistente.isPresent()) {
            Produto produto = produtoExistente.get();
            produto.setQuantidade(novaQuantidade);
            return produtoRepository.save(produto);
        } else {
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }
    }
}

