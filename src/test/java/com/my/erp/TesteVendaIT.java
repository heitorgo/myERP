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
import com.my.erp.domain.model.Cargo;
import com.my.erp.domain.model.Empresa;
import com.my.erp.domain.model.FormaPagamento;
import com.my.erp.domain.model.Funcionario;
import com.my.erp.domain.model.ItemVenda;
import com.my.erp.domain.model.Loja;
import com.my.erp.domain.model.Produto;
import com.my.erp.domain.model.Venda;
import com.my.erp.domain.service.CaixaService;
import com.my.erp.domain.service.CargoService;
import com.my.erp.domain.service.EmpresaService;
import com.my.erp.domain.service.FormaPagamentoService;
import com.my.erp.domain.service.FuncionarioService;
import com.my.erp.domain.service.LojaService;
import com.my.erp.domain.service.ProdutoService;
import com.my.erp.domain.service.VendaService;
import com.my.erp.util.DatabaseCleaner;
import com.my.erp.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class TesteVendaIT {

	@LocalServerPort
	private int port;
	private Venda venda1;
	private String jsonVendaCorreto;
	private String jsonVendaIncorretoSemDescricao;
	private String jsonVendaIncorretoSemCaixa;
	private String jsonVendaIncorretoCaixaInexistente;
	private String jsonVendaIncorretoSemFuncionario;
	private String jsonVendaIncorretoFuncionarioInexistente;
	private String jsonVendaIncorretoItemVendaProdutoInexistente;
	private String jsonVendaIncorretoItemVendaQuantidadeIncorreta;
	private String jsonVendaIncorretoItemVendaSemProduto;
	private String jsonVendaIncorretoItemVendaSemQuantidade;
	private static final Long idVendaInexistente = 100L;
	private static final String DADOS_INVALIDOS_PROBLEM_TYPE = "Dados inválidos";
	private static final String VIOLACAO_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";
	@Autowired
	private VendaService vendaService;
	@Autowired
	private CaixaService caixaService;
	@Autowired
	private CargoService cargoService;
	@Autowired
	private FuncionarioService funcionarioService;
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private LojaService lojaService;
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private DatabaseCleaner databaseCleaner;

	@BeforeEach
	public void SetUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/vendas";

		databaseCleaner.clearTables();
		prepararDados();
		jsonVendaCorreto = ResourceUtils.getContentFromResource("/json/correto/venda-correto.json");
		jsonVendaIncorretoSemDescricao = ResourceUtils
				.getContentFromResource("/json/incorreto/venda/venda-incorreto-sem-descricao.json");
		jsonVendaIncorretoSemCaixa = ResourceUtils
				.getContentFromResource("/json/incorreto/venda/venda-incorreto-sem-caixa.json");
		jsonVendaIncorretoCaixaInexistente = ResourceUtils
				.getContentFromResource("/json/incorreto/venda/venda-incorreto-caixa-inexistente.json");
		jsonVendaIncorretoSemFuncionario = ResourceUtils
				.getContentFromResource("/json/incorreto/venda/venda-incorreto-sem-funcionario.json");
		jsonVendaIncorretoFuncionarioInexistente = ResourceUtils
				.getContentFromResource("/json/incorreto/venda/venda-incorreto-funcionario-inexistente.json");
		jsonVendaIncorretoItemVendaProdutoInexistente = ResourceUtils
				.getContentFromResource("/json/incorreto/venda/venda-incorreto-itemVenda-produto-inexistente.json");
		jsonVendaIncorretoItemVendaQuantidadeIncorreta = ResourceUtils
				.getContentFromResource("/json/incorreto/venda/venda-incorreto-itemVenda-quantidade-incorreta.json");
		jsonVendaIncorretoItemVendaSemProduto = ResourceUtils
				.getContentFromResource("/json/incorreto/venda/venda-incorreto-itemVenda-sem-produto.json");
		jsonVendaIncorretoItemVendaSemQuantidade = ResourceUtils
				.getContentFromResource("/json/incorreto/venda/venda-incorreto-itemVenda-sem-quantidade.json");
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

		Caixa caixa1 = new Caixa();
		caixa1.setNome("Caixa1");
		caixa1.setSaldo(new BigDecimal(0));
		caixa1.setLoja(loja1);
		caixaService.salvar(caixa1);

		Cargo cargo1 = new Cargo();
		cargo1.setTitulo("Gerente");
		cargo1.setRemuneracao(new BigDecimal(7000));
		cargo1.setLoja(loja1);
		cargoService.salvar(cargo1);

		Funcionario funcionario1 = new Funcionario();
		funcionario1.setNome("Pedro");
		funcionario1.setCargo(cargo1);
		funcionarioService.salvar(funcionario1);
		
		FormaPagamento formaPagamento1 = new FormaPagamento();
		formaPagamento1.setTitulo("Dinheiro");
		formaPagamentoService.salvar(formaPagamento1);
		
		Produto produto1 = new Produto();
		produto1.setNome("Parafuso");
		produto1.setDescricao("parafuso para venda");
		produto1.setQuantidade(100);
		produto1.setValor(new BigDecimal(2.5));
		produto1.setLoja(loja1);
		produtoService.salvar(produto1);

		venda1 = new Venda();
		venda1.setValor(new BigDecimal(1000));
		venda1.setDescricao("Venda de Produtos");
		venda1.setCaixa(caixa1);
		venda1.setFuncionario(funcionario1);
		venda1.setFormaPagamento(formaPagamento1);
		
		ItemVenda itemVenda1 = new ItemVenda();
		itemVenda1.setProduto(produto1);
		itemVenda1.setQuantidade(1);
		itemVenda1.setVenda(venda1);
		
		vendaService.salvar(venda1);
		
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarVendas() {
		given().accept(ContentType.JSON).when().get().then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarVendaExistente() {
		given().accept(ContentType.JSON).pathParam("id", venda1.getId()).when().get("/{id}").then()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus404_QuandoConsultarVendaInexistente() {
		given().accept(ContentType.JSON).pathParam("id", idVendaInexistente).when().get("/{id}").then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarVendaCorreta() {
		given().body(jsonVendaCorreto).accept(ContentType.JSON).contentType(ContentType.JSON).when().post().then()
				.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarVendaSemDescricao() {
		given().body(jsonVendaIncorretoSemDescricao).accept(ContentType.JSON).contentType(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarVendaSemCaixa() {
		given().body(jsonVendaIncorretoSemCaixa).accept(ContentType.JSON).contentType(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarVendaSemFuncionario() {
		given().body(jsonVendaIncorretoSemFuncionario).accept(ContentType.JSON).contentType(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarVendaFuncionarioInexistente() {
		given().body(jsonVendaIncorretoFuncionarioInexistente).accept(ContentType.JSON).contentType(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(VIOLACAO_NEGOCIO_PROBLEM_TYPE));
	}

	@Test
	public void deveRetornarStatus400_QuandoCadastrarVendaCaixaInexistente() {
		given().body(jsonVendaIncorretoCaixaInexistente).accept(ContentType.JSON).contentType(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(VIOLACAO_NEGOCIO_PROBLEM_TYPE));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoCadastrarVendaItemVendaProdutoInexistente() {
		given().body(jsonVendaIncorretoItemVendaProdutoInexistente).accept(ContentType.JSON).contentType(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarVendaItemVendaSemProduto() {
		given().body(jsonVendaIncorretoItemVendaSemProduto).accept(ContentType.JSON).contentType(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarVendaItemVendaQuantidadeIncorreta() {
		given().body(jsonVendaIncorretoItemVendaQuantidadeIncorreta).accept(ContentType.JSON).contentType(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarVendaItemVendaSemQuantidade() {
		given().body(jsonVendaIncorretoItemVendaSemQuantidade).accept(ContentType.JSON).contentType(ContentType.JSON).when()
				.post().then().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
	}

}
