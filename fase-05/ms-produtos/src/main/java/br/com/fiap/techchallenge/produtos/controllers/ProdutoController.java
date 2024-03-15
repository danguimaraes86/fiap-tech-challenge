package br.com.fiap.techchallenge.produtos.controllers;

import br.com.fiap.techchallenge.produtos.models.Produto;
import br.com.fiap.techchallenge.produtos.models.dtos.ProdutoDTO;
import br.com.fiap.techchallenge.produtos.models.dtos.ProdutoRequestDTO;
import br.com.fiap.techchallenge.produtos.services.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> findAllProdutos(
            @SortDefault(sort = "nome") Pageable pageable) {
        Page<Produto> produtos = produtoService.findAllProdutos(pageable);
        return ResponseEntity.ok(produtos.map(Produto::toProdutoDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> findProdutoById(@PathVariable String id) {
        Produto produto = produtoService.findProdutoById(id);
        return ResponseEntity.ok(produto.toProdutoDTO());
    }

    @GetMapping("/busca")
    public ResponseEntity<Page<ProdutoDTO>> findProdutoByNomeByDescricao(
            @SortDefault(sort = "nome") Pageable pageable,
            @RequestParam(required = false, defaultValue = "") String nome,
            @RequestParam(required = false, defaultValue = "") String descricao) {
        Page<Produto> produtos = produtoService.findProdutosNomeDescricao(pageable, nome, descricao);
        return ResponseEntity.ok(produtos.map(Produto::toProdutoDTO));
    }

    @PostMapping("/checarsetemestoque")
    public ResponseEntity<Boolean> checarSeHaEstoque(@RequestBody @Valid ProdutoRequestDTO produtoDTO) {
        return ResponseEntity.ok(produtoService.checarSeHaEstoque(produtoDTO));
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> insertProduto(@RequestBody @Valid ProdutoDTO produtoDTO) {
        Produto produto = produtoService.insertProduto(produtoDTO);
        return ResponseEntity.ok(produto.toProdutoDTO());
    }

    @PutMapping("/estoque/{id}")
    public ResponseEntity<ProdutoDTO> updateEstoqueProduto(
            @PathVariable String id, @RequestParam Long alteracaoEstoque) {
        Produto produto = produtoService.updateProdutoEstoque(id, alteracaoEstoque);
        return ResponseEntity.ok(produto.toProdutoDTO());
    }
}
