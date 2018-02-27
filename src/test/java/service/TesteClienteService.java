package service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import model.Cliente;
import utilitarios.DAOGenerico;

public class TesteClienteService {
	
	@Mock
	DAOGenerico dao;
	
	 @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@Test
	public void testaListaClientes() {
		ClienteService clienteService = new ClienteService(dao);
		ArrayList<Cliente> retornoFixo = new ArrayList<Cliente>();
		retornoFixo.add(new Cliente());
		retornoFixo.add(new Cliente());
		retornoFixo.add(new Cliente());
		
		when(dao.lista(Cliente.class)).thenReturn(retornoFixo);
		
		List<Cliente> resultado = clienteService.listar();
		
		//A operacao retornou uma lista com 3 clientes?
		Assert.assertEquals("Deveria retornar 3 clientes", 3, resultado.size());
		
		//O metodo lista da classe DAOGenerico foi executado?
		verify(dao).lista(Cliente.class);
	}
	
	@Test
	public void testaIncluirCliente() {
		ClienteService clienteService = new ClienteService(dao);
		Cliente cliente = new Cliente();
		cliente.setId(1l);
		cliente.setNome("John");
		cliente.setIdade(20);
		
		Response resultado = clienteService.cadastrar(cliente);
		
		//a operacao foi executada com sucesso?
		Assert.assertEquals("cadastro realizado.", resultado.getEntity());
		
		//o metodo inserir da classe DAOGenerico foi executado com o parametro cliente?
		verify(dao).inserir(cliente);
	}
	
	//TODO testar os outros métodos da classe ClienteService

}
