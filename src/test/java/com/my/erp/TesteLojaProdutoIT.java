package com.my.erp;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.my.erp.domain.model.Empresa;
import com.my.erp.domain.model.Loja;
import com.my.erp.domain.model.Produto;
import com.my.erp.domain.service.EmpresaService;
import com.my.erp.domain.service.LojaService;
import com.my.erp.domain.service.ProdutoService;
import com.my.erp.util.DatabaseCleaner;
import com.my.erp.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class TesteLojaProdutoIT {

	@LocalServerPort
	private int port;
	private Produto produto1;
	private Loja loja1;
	private String jsonProdutoCorreto;
	private String jsonProdutoIncorretoSemNome;
	private String jsonProdutoIncorretoSemDescricao;
	private String jsonProdutoIncorretoSemQuantidade;
	private String jsonProdutoIncorretoSemValor;
	private String jsonProdutoIncorretoLojaInexistente;
	private static final Long idProdutoInexistente = 100L;
	private static final Long idLojaInexistente = 100L;
	private static final String DADOS_INVALIDOS_PROBLEM_TYPE = "Dados inválidos";
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private LojaService lojaService;
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private DatabaseCleaner databaseCleaner;

	@BeforeEach
	public void SetUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "lojas/{lojaId}/produtos";

		databaseCleaner.clearTables();
		prepararDados();
		jsonProdutoCorreto = ResourceUtils.getContentFromResource("/json/correto/produto-correto.json");
		jsonProdutoIncorretoSemNome = ResourceUtils
				.getContentFromResource("/json/incorreto/produto/produto-incorreto-sem-nome.json");
		jsonProdutoIncorretoSemDescricao = ResourceUtils
				.getContentFromResource("/json/incorreto/produto/produto-incorreto-sem-descricao.json");
		jsonProdutoIncorretoSemQuantidade = ResourceUtils
				.getContentFromResource("/json/incorreto/produto/produto-incorreto-sem-quantidade.json");
		jsonProdutoIncorretoSemValor = ResourceUtils
				.getContentFromResource("/json/incorreto/produto/produto-incorreto-sem-valor.json");
		jsonProdutoIncorretoLojaInexistente = ResourceUtils
				.getContentFromResource("/json/incorreto/produto/produto-incorreto-loja-inexistente.json");
	}

	private void prepararDados() {
		Empresa empresaAutoPecas = new Empresa();
		empresaAutoPecas.setNome("Auto Peças Itu");
		empresaAutoPecas.setRazaoSocial("Auto Peças Itu LTDA");
		empresaService.salvar(empresaAutoPecas);

		loja1 = new Loja();
		loja1.setNome("Loja1");
		loja1.setEmpresa(empresaAutoPecas);
		lojaService.salvar(loja1);

		produto1 = new Produto();
		produto1.setNome("Oleo Shell Helix 5w-30");
		produto1.setDescricao("óleo lubrificante.");
		produto1.setQuantidade(20);
		produto1.setValor(new BigDecimal(24.20));
		produto1.setLoja(loja1);
		produtoService.salvar(produto1);
		
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarProdutos() {
		given().accept(ContentType.JSON).pathParam("lojaId", loja1.getId()).when().get().then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarProdutoExistente() {
		given().accept(ContentType.JSON).pathParam("lojaId", loja1.getId()).pathParam("produtoId", produto1.getId())
				.when().get("/{produtoId}").then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus404_QuandoConsultarProdutoInexistente() {
		given().accept(ContentType.JSON).pathParam("lojaId", loja1.getId()).pathParam("produtoId", idProdutoInexistente)
				.when().get("/{produtoId}").then().statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarProdutoCorreto() {
		given().body(jsonProdutoCorreto).accept(ContentType.JSON).pathParam("lojaId", loja1.getId())
				.contentType(ContentType.JSON).when().post().then()
				.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarProdutoSemNome() {
		given().body(jsonProdutoIncorretoSemNome).accept(ContentType.JSON).pathParam("lojaId", loja1.getId())
				.contentType(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarProdutoSemDescricao() {
		given().body(jsonProdutoIncorretoSemDescricao).accept(ContentType.JSON).pathParam("lojaId", loja1.getId())
				.contentType(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarProdutoSemValor() {
		given().body(jsonProdutoIncorretoSemValor).accept(ContentType.JSON).pathParam("lojaId", loja1.getId())
				.contentType(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarProdutoSemQuantidade() {
		given().body(jsonProdutoIncorretoSemQuantidade).accept(ContentType.JSON).pathParam("lojaId", loja1.getId())
				.contentType(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarProdutoLojaInexistente() {
		given().body(jsonProdutoIncorretoLojaInexistente).accept(ContentType.JSON).pathParam("lojaId", idLojaInexistente)
				.contentType(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.NOT_FOUND.value());
	}

}
