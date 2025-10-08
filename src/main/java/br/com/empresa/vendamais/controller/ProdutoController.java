package br.com.empresa.vendamais.controller;

import br.com.empresa.vendamais.entity.Produto;
import br.com.empresa.vendamais.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*") // Permite CORS para todas as origens
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // CREATE - POST /api/produtos
    @PostMapping
    public ResponseEntity<Produto> criarProduto(@Valid @RequestBody Produto produto) {
        try {
            Produto novoProduto = produtoService.insertNew(produto);
            return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - GET /api/produtos (Listar todos)
    @GetMapping
    public ResponseEntity<List<Produto>> listarTodosProdutos() {
        try {
            List<Produto> produtos = produtoService.findAll();
            if (produtos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(produtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - GET /api/produtos/{id} (Buscar por ID)
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable("id") Long id) {
        try {
            Optional<Produto> produto = produtoService.findById(id);
            if (produto.isPresent()) {
                return new ResponseEntity<>(produto.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - GET /api/produtos/buscar?nome={nome} (Buscar por nome)
    @GetMapping("/buscar")
    public ResponseEntity<List<Produto>> buscarProdutosPorNome(@RequestParam("nome") String nome) {
        try {
            List<Produto> produtos = produtoService.findByNome(nome);
            if (produtos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(produtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - GET /api/produtos/estoque-baixo?limite={limite}
    @GetMapping("/estoque-baixo")
    public ResponseEntity<List<Produto>> buscarProdutosEstoqueBaixo(@RequestParam("limite") Integer limite) {
        try {
            List<Produto> produtos = produtoService.findProdutosComEstoqueBaixo(limite);
            return new ResponseEntity<>(produtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ - GET /api/produtos/preco?min={min}&max={max}
    @GetMapping("/preco")
    public ResponseEntity<List<Produto>> buscarProdutosPorFaixaPreco(
            @RequestParam("min") Double precoMin, 
            @RequestParam("max") Double precoMax) {
        try {
            List<Produto> produtos = produtoService.findByFaixaPreco(precoMin, precoMax);
            return new ResponseEntity<>(produtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // UPDATE - PUT /api/produtos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable("id") Long id, @Valid @RequestBody Produto produto) {
        try {
            Produto produtoAtualizado = produtoService.update(id, produto);
            return new ResponseEntity<>(produtoAtualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // UPDATE - PATCH /api/produtos/{id}/estoque
    @PatchMapping("/{id}/estoque")
    public ResponseEntity<Produto> atualizarEstoque(@PathVariable("id") Long id, @RequestParam("quantidade") Integer quantidade) {
        try {
            Produto produtoAtualizado = produtoService.atualizarEstoque(id, quantidade);
            return new ResponseEntity<>(produtoAtualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE - DELETE /api/produtos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletarProduto(@PathVariable("id") Long id) {
        try {
            produtoService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET /api/produtos/count (Contar total de produtos)
    @GetMapping("/count")
    public ResponseEntity<Long> contarProdutos() {
        try {
            Long total = produtoService.countTotalProdutos();
            return new ResponseEntity<>(total, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

