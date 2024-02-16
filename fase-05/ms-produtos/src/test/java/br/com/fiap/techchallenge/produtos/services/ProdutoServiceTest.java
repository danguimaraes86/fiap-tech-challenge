package br.com.fiap.techchallenge.produtos.services;

import br.com.fiap.techchallenge.produtos.exceptions.domain.EstoqueInsuficienteException;
import br.com.fiap.techchallenge.produtos.exceptions.domain.ProdutoJaCadastradoException;
import br.com.fiap.techchallenge.produtos.exceptions.domain.ProdutoNaoEncontradoException;
import br.com.fiap.techchallenge.produtos.models.Produto;
import br.com.fiap.techchallenge.produtos.models.dtos.ProdutoDTO;
import br.com.fiap.techchallenge.produtos.repositories.ProdutoRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.fiap.techchallenge.produtos.exceptions.domain.MensagensErro.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ProdutoServiceTest {

    AutoCloseable mocks;
    private ProdutoService produtoService;
    @Mock
    private ProdutoRepository produtoRepository;

    private static Produto getProdutoMock(Double preco, Long estoque) {
        return new Produto(RandomString.make(), RandomString.make(), preco, estoque);
    }

    @BeforeEach
    void setup() {
        mocks = openMocks(this);
        produtoService = new ProdutoService(produtoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Nested
    class BuscarProdutos {

        @Test
        void deveBuscarProdutos_RetornaPageableVazio() {
            Page<Produto> produtoPageFake = new PageImpl<>(Collections.emptyList());
            when(produtoRepository.findAll(any(Pageable.class))).thenReturn(produtoPageFake);

            Page<Produto> produtoPage = produtoService.findAllProdutos(Pageable.unpaged());
            verify(produtoRepository, times(1)).findAll(Pageable.unpaged());

            assertThat(produtoPage).isInstanceOf(Page.class);
            assertThat(produtoPage.getContent()).isEmpty();
        }

        @Test
        void deveBuscarProdutos_RetornaPageable() {
            Page<Produto> produtoPageFake = new PageImpl<>(List.of(
                    mock(Produto.class),
                    mock(Produto.class),
                    mock(Produto.class)));
            when(produtoRepository.findAll(any(Pageable.class))).thenReturn(produtoPageFake);

            Page<Produto> produtoPage = produtoService.findAllProdutos(Pageable.unpaged());
            verify(produtoRepository, times(1)).findAll(Pageable.unpaged());

            assertThat(produtoPage).isInstanceOf(Page.class);
            assertThat(produtoPage.getTotalElements()).isEqualTo(produtoPageFake.getTotalElements());
        }

        @Test
        void deveBuscarProdutoPorId() {
            String id = UUID.randomUUID().toString();
            Produto produtoMock = getProdutoMock(7.0, 7L);
            when(produtoRepository.findById(anyString())).thenReturn(Optional.of(produtoMock));

            Produto produto = produtoService.findProdutoById(id);
            verify(produtoRepository, times(1)).findById(id);

            assertThat(produto).isEqualTo(produtoMock);
            assertThat(produto.getNome()).isEqualTo(produtoMock.getNome());
            assertThat(produto.getDescricao()).isEqualTo(produtoMock.getDescricao());
            assertThat(produto.getPreco()).isEqualTo(produtoMock.getPreco());
            assertThat(produto.getEstoque()).isEqualTo(produtoMock.getEstoque());
        }

        @Test
        void deveBuscarProdutoPor_NomeOuDescricao_RetornaPageableVazio() {
            Page<Produto> produtoPageFake = new PageImpl<>(Collections.emptyList());
            when(produtoRepository.findByNomeContainsIgnoreCaseAndDescricaoContainsIgnoreCase(
                    any(Pageable.class), anyString(), anyString())).thenReturn(produtoPageFake);

            Page<Produto> produtoPage = produtoService.findProdutosNomeDescricao(
                    Pageable.unpaged(), "", "");
            verify(produtoRepository, times(1))
                    .findByNomeContainsIgnoreCaseAndDescricaoContainsIgnoreCase(
                            Pageable.unpaged(), "", "");

            assertThat(produtoPage).isInstanceOf(Page.class);
            assertThat(produtoPage.getContent()).isEmpty();
        }

        @Test
        void deveBuscarProdutosPor_NomeOuDescricao_RetornaPageable() {
            Page<Produto> produtoPageFake = new PageImpl<>(List.of(
                    mock(Produto.class),
                    mock(Produto.class),
                    mock(Produto.class)));
            when(produtoRepository.findByNomeContainsIgnoreCaseAndDescricaoContainsIgnoreCase(
                    any(Pageable.class), anyString(), anyString())).thenReturn(produtoPageFake);

            Page<Produto> produtoPage = produtoService.findProdutosNomeDescricao(
                    Pageable.unpaged(), "", "");
            verify(produtoRepository, times(1))
                    .findByNomeContainsIgnoreCaseAndDescricaoContainsIgnoreCase(
                            Pageable.unpaged(), "", "");

            assertThat(produtoPage).isInstanceOf(Page.class);
            assertThat(produtoPage.getTotalElements()).isEqualTo(produtoPageFake.getTotalElements());
        }
    }

    @Nested
    class InserirProdutos {

        @Test
        void deveInserirProduto_comSucesso() {
            Produto produtoMock = getProdutoMock(10.0, 0L);
            ProdutoDTO produtoDTO = produtoMock.toProdutoDTO();
            when(produtoRepository.save(any(Produto.class))).thenReturn(produtoMock);

            Produto produto = produtoService.insertProduto(produtoDTO);
            verify(produtoRepository, times(1)).save(any(Produto.class));

            assertThat(produto).isInstanceOf(Produto.class);
            assertThat(produto.getId()).isEqualTo(produtoMock.getId());
            assertThat(produto.getNome()).isEqualTo(produtoDTO.nome());
            assertThat(produto.getDescricao()).isEqualTo(produtoDTO.descricao());
            assertThat(produto.getPreco()).isEqualTo(produtoDTO.preco().orElseThrow());
            assertThat(produto.getEstoque()).isEqualTo(produtoDTO.estoque().orElseThrow());
        }
    }

    @Nested
    class AlterarEstoque {

        @Test
        void deveAlterarEstoque_comSucesso() {
            String id = UUID.randomUUID().toString();
            Produto produtoMock = getProdutoMock(10.0, 10L);
            Long alteracaoEstoque = (-7L);
            when(produtoRepository.findById(anyString())).thenReturn(Optional.of(produtoMock));
            when(produtoRepository.save(any(Produto.class))).thenReturn(produtoMock);

            Produto produto = produtoService.updateProdutoEstoque(id, alteracaoEstoque);
            verify(produtoRepository, times(1)).findById(anyString());
            verify(produtoRepository, times(1)).save(any(Produto.class));

            assertThat(produto.getEstoque()).isEqualTo(produtoMock.getEstoque());
        }
    }

    @Nested
    class Exceptions {

        @Test
        void deveLancarExcecao_buscarProdutoPorId_produtoNaoEncontrado() {
            String id = UUID.randomUUID().toString();
            when(produtoRepository.findById(anyString())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> produtoService.findProdutoById(id))
                    .isInstanceOf(ProdutoNaoEncontradoException.class)
                    .hasMessage(String.format(PRODUTO_NAO_ENCONTRADO.getMensagem(), id));
            verify(produtoRepository, times(1)).findById(anyString());
        }

        @Test
        void deveLancarExcecao_inserirProduto_nomeJaCadastrado() {
            Produto produtoMock = getProdutoMock(10.0, 10L);
            ProdutoDTO produtoDTOMock = produtoMock.toProdutoDTO();
            when(produtoRepository.findByNomeIgnoreCase(anyString())).thenReturn(Optional.of(produtoMock));

            assertThatThrownBy(() -> produtoService.insertProduto(produtoDTOMock))
                    .isInstanceOf(ProdutoJaCadastradoException.class)
                    .hasMessage(String.format(NOME_JA_CADASTRADO.getMensagem(), produtoDTOMock.nome()));
            verify(produtoRepository, times(1)).findByNomeIgnoreCase(anyString());
            verify(produtoRepository, never()).save(produtoMock);
        }

        @Test
        void deveLancarExcecao_alterarEstoque_produtoSemEstoque() {
            String id = UUID.randomUUID().toString();
            Produto produtoMock = getProdutoMock(10.0, 10L);
            Long alterarEstoque = (-13L);
            when(produtoRepository.findById(anyString())).thenReturn(Optional.of(produtoMock));

            assertThatThrownBy(() -> produtoService.updateProdutoEstoque(id, alterarEstoque))
                    .isInstanceOf(EstoqueInsuficienteException.class)
                    .hasMessage(String.format(ESTOQUE_INSUFICIENTE.getMensagem(),
                            alterarEstoque, produtoMock.getEstoque()));
            verify(produtoRepository, times(1)).findById(anyString());
            verify(produtoRepository, never()).save(produtoMock);
        }
    }
}
