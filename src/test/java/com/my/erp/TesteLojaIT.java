package com.my.erp;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.my.erp.domain.model.Empresa;
import com.my.erp.domain.model.Loja;
import com.my.erp.domain.service.EmpresaService;
import com.my.erp.domain.service.LojaService;
import com.my.erp.util.DatabaseCleaner;
import com.my.erp.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class TesteLojaIT {

	@LocalServerPort
	private int port;
	private Loja loja1;
	private String jsonLojaCorreta;
	private String jsonLojaIncorretaSemNome;
	private String jsonLojaIncorretaSemEmpresa;
	private String jsonLojaIncorretaEmpresaInexistente;
	private static final Long idLojaInexistente = 100L;
	private static final String DADOS_INVALIDOS_PROBLEM_TYPE = "Dados inválidos";
	private static final String VIOLACAO_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";
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
		RestAssured.basePath = "/lojas";

		databaseCleaner.clearTables();
		prepararDados();
		jsonLojaCorreta = ResourceUtils.getContentFromResource("/json/correto/loja-correta.json");
		jsonLojaIncorretaSemNome = ResourceUtils
				.getContentFromResource("/json/incorreto/loja/loja-incorreta-sem-nome.json");
		jsonLojaIncorretaSemEmpresa = ResourceUtils
				.getContentFromResource("/json/incorreto/loja/loja-incorreta-sem-empresa.json");
		jsonLojaIncorretaEmpresaInexistente = ResourceUtils
				.getContentFromResource("/json/incorreto/loja/loja-incorreta-empresa-inexistente.json");
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

	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarLojas() {
		given().accept(ContentType.JSON).when().get().then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarLojaExistente() {
		given().accept(ContentType.JSON).pathParam("id", loja1.getId()).when().get("/{id}").then()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus404_QuandoConsultarLojaInexistente() {
		given().accept(ContentType.JSON).pathParam("id", idLojaInexistente).when().get("/{id}").then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarLojaCorreta() {
		given().body(jsonLojaCorreta).accept(ContentType.JSON).contentType(ContentType.JSON).when().post().then()
				.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarLojaSemNome() {
		given().body(jsonLojaIncorretaSemNome).accept(ContentType.JSON).contentType(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value()).body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarLojaSemEmpresa() {
		given().body(jsonLojaIncorretaSemEmpresa).accept(ContentType.JSON).contentType(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value()).body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarLojaEmpresaInexistente() {
		given().body(jsonLojaIncorretaEmpresaInexistente).accept(ContentType.JSON).contentType(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(VIOLACAO_NEGOCIO_PROBLEM_TYPE));
	}

}
