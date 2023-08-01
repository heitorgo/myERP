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

import com.my.erp.domain.model.Caixa;
import com.my.erp.domain.model.Empresa;
import com.my.erp.domain.model.Loja;
import com.my.erp.domain.service.CaixaService;
import com.my.erp.domain.service.EmpresaService;
import com.my.erp.domain.service.LojaService;
import com.my.erp.util.DatabaseCleaner;
import com.my.erp.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class TesteCaixaIT {

	@LocalServerPort
	private int port;
	private Caixa caixa1;
	private String jsonCaixaCorreto;
	private String jsonCaixaIncorretoSemNome;
	private String jsonCaixaIncorretoSemLoja;
	private String jsonCaixaIncorretoLojaInexistente;
	private static final Long idCaixaInexistente = 100L;
	private static final String DADOS_INVALIDOS_PROBLEM_TYPE = "Dados inválidos";
	private static final String VIOLACAO_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";
	@Autowired
	private CaixaService caixaService;
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
		RestAssured.basePath = "/caixas";

		databaseCleaner.clearTables();
		prepararDados();
		jsonCaixaCorreto = com.my.erp.util.ResourceUtils.getContentFromResource("/json/correto/caixa-correto.json");
		jsonCaixaIncorretoSemNome = ResourceUtils
				.getContentFromResource("/json/incorreto/caixa/caixa-incorreto-sem-nome.json");
		jsonCaixaIncorretoSemLoja = ResourceUtils
				.getContentFromResource("/json/incorreto/caixa/caixa-incorreto-sem-loja.json");
		jsonCaixaIncorretoLojaInexistente = ResourceUtils
				.getContentFromResource("/json/incorreto/caixa/caixa-incorreto-loja-inexistente.json");
	}

	private void prepararDados() {
		Empresa empresaAutoPecas = new Empresa();
		empresaAutoPecas.setNome("Auto Peças Itu");
		empresaAutoPecas.setRazaoSocial("Auto Peças Itu LTDA");
		empresaService.salvar(empresaAutoPecas);

		Loja loja1 = new Loja();
		loja1.setNome("Loja1");
		loja1.setEmpresa(empresaAutoPecas);
		lojaService.salvar(loja1);

		caixa1 = new Caixa();
		caixa1.setNome("Caixa1");
		caixa1.setSaldo(new BigDecimal(0));
		caixa1.setLoja(loja1);
		caixaService.salvar(caixa1);

	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCaixas() {
		given().accept(ContentType.JSON).when().get().then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCaixaExistente() {
		given().accept(ContentType.JSON).pathParam("id", caixa1.getId()).when().get("/{id}").then()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus404_QuandoConsultarCaixaInexistente() {
		given().accept(ContentType.JSON).pathParam("id", idCaixaInexistente).when().get("/{id}").then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarCaixaCorreto() {
		given().body(jsonCaixaCorreto).accept(ContentType.JSON).contentType(ContentType.JSON).when().post().then()
				.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarCaixaSemNome() {
		given().body(jsonCaixaIncorretoSemNome).accept(ContentType.JSON).contentType(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value()).body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarCaixaSemLoja() {
		given().body(jsonCaixaIncorretoSemLoja).accept(ContentType.JSON).contentType(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value()).body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarCaixaLojaInexistente() {
		given().body(jsonCaixaIncorretoLojaInexistente).accept(ContentType.JSON).contentType(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(VIOLACAO_NEGOCIO_PROBLEM_TYPE));
	}

}
