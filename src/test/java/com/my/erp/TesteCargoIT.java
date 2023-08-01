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

import com.my.erp.domain.model.Cargo;
import com.my.erp.domain.model.Empresa;
import com.my.erp.domain.model.Loja;
import com.my.erp.domain.service.CargoService;
import com.my.erp.domain.service.EmpresaService;
import com.my.erp.domain.service.LojaService;
import com.my.erp.util.DatabaseCleaner;
import com.my.erp.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class TesteCargoIT {

	@LocalServerPort
	private int port;
	private Cargo cargoGerente;
	private String jsonCargoCorreto;
	private String jsonCargoIncorretoSemTitulo;
	private String jsonCargoIncorretoRemuneracao;
	private String jsonCargoIncorretoSemLoja;
	private String jsonCargoIncorretoLojaInexistente;
	private static final Long idCargoInexistente = 100L;
	private static final String DADOS_INVALIDOS_PROBLEM_TYPE = "Dados inválidos";
	private static final String VIOLACAO_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";
	@Autowired
	private CargoService cargoService;
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
		RestAssured.basePath = "/cargos";

		databaseCleaner.clearTables();
		prepararDados();
		jsonCargoCorreto = ResourceUtils.getContentFromResource("/json/correto/cargo-correto.json");
		jsonCargoIncorretoSemTitulo = ResourceUtils
				.getContentFromResource("/json/incorreto/cargo/cargo-incorreto-sem-titulo.json");
		jsonCargoIncorretoRemuneracao = ResourceUtils
				.getContentFromResource("/json/incorreto/cargo/cargo-incorreto-remuneracao.json");
		jsonCargoIncorretoSemLoja = ResourceUtils
				.getContentFromResource("/json/incorreto/cargo/cargo-incorreto-sem-loja.json");
		jsonCargoIncorretoLojaInexistente = ResourceUtils
				.getContentFromResource("/json/incorreto/cargo/cargo-incorreto-loja-inexistente.json");
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

		cargoGerente = new Cargo();
		cargoGerente.setTitulo("Gerente");
		cargoGerente.setRemuneracao(new BigDecimal(5000));
		cargoGerente.setLoja(loja1);
		cargoService.salvar(cargoGerente);

	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCargos() {
		given().accept(ContentType.JSON).when().get().then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCargoExistente() {
		given().accept(ContentType.JSON).pathParam("id", cargoGerente.getId()).when().get("/{id}").then()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus404_QuandoConsultarCargoInexistente() {
		given().accept(ContentType.JSON).pathParam("id", idCargoInexistente).when().get("/{id}").then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarCargoCorreto() {
		given().body(jsonCargoCorreto).accept(ContentType.JSON).contentType(ContentType.JSON).when().post().then()
				.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarCargoSemTitulo() {
		given().body(jsonCargoIncorretoSemTitulo).accept(ContentType.JSON).contentType(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value()).body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarCargoRemuneracaoincorreta() {
		given().body(jsonCargoIncorretoRemuneracao).accept(ContentType.JSON).contentType(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value()).body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarCargoSemLoja() {
		given().body(jsonCargoIncorretoSemLoja).accept(ContentType.JSON).contentType(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.BAD_REQUEST.value()).body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarCargoLojaInexistente() {
		given().body(jsonCargoIncorretoLojaInexistente).accept(ContentType.JSON).contentType(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(VIOLACAO_NEGOCIO_PROBLEM_TYPE));
	}

}
