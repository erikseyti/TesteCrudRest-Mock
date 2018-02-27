package service;

import java.util.List;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import com.sun.jersey.core.util.Base64;

import model.Produto;
import utilitarios.DAOGenerico;

@ApplicationPath("/resource")
@Path("/produto")
public class ProdutoService extends Application {
	DAOGenerico dao = new DAOGenerico();

	@POST
	@Path("/inserir")
	@Consumes("application/json")
	public Response inserir(Produto prod, @HeaderParam("token") String token) {
		if (autenticacao(token)) {
			try {
				dao.inserir(prod);
				return Response.status(200).entity("cadastro realizado.").build();
			} catch (Exception e) {
				throw new WebApplicationException(500);
			}
		}
		return Response.status(401).entity("Não autorizado").build();
	}

	@PUT
	@Path("/alterar")
	@Consumes("application/json")
	public Response alterar(Produto prod) {
		try {
			dao.salvar(prod);
			return Response.status(200).entity("cadastro realizado.").build();
		} catch (Exception e) {
			throw new WebApplicationException(500);
		}
	}

	@DELETE
	@Path("/excluir/{id_produto}")
	public Response excluir(@PathParam("id_produto") Long idProduto) {
		try {
			Produto pro = (Produto) dao.recupera(Produto.class, idProduto);
			dao.excluir(pro);
			return Response.status(200).entity("removido com sucesso").build();
		} catch (Exception e) {
			throw new WebApplicationException(500);
		}
	}

	@GET
	@Path("/buscarId/{id_produto}")
	@Produces("application/json")
	public Response buscarId(@PathParam("id_produto") Long idProduto, @HeaderParam("token") String token) {
		if (autenticacaoUsuarioSenha(token)) {
			try {
				Produto pro = (Produto) dao.recupera(Produto.class, idProduto);
				// dao.excluir(pro);
				//return pro;
				 return Response.status(200).entity(pro).build();
			} catch (Exception e) {
				throw new WebApplicationException(500);
			}
		}
		return Response.status(401).entity("{\"error\": \"Não Autorizado\"}").build();
	}

	@GET
	@Path("/listar")
	@Produces("application/json")
	public Response listarTodos(@HeaderParam("token") String token) {
		if (autenticacao(token)) {
			try {
				List<Produto> listaProduto = dao.lista(Produto.class);
				// dao.excluir(pro);
				// return listaProduto;
				return Response.status(200).entity(listaProduto).build();
				// return Response.status(200).entity("removido com
				// sucesso").build();
			} catch (Exception e) {
				throw new WebApplicationException(500);
			}
		}
		return Response.status(401).entity("{\"error\": \"Não Autorizado\"}").build();
	}

	public boolean autenticacao(String token) {
		if (token.equals("123")) {
			return true;
		}
		return false;
	}

	public boolean autenticacaoUsuarioSenha(String usuarioSenha) {
		String decodificado = new Base64().base64Decode(usuarioSenha);
		String[] partes = decodificado.split(":");
		if (partes[0].equals("jose") && partes[1].equals("123")) {
			return true;
		}
		return false;
	}

	// Para o erro de duplicidade de serviços web
	// Go to the Preferences of your project > JAX-RS Validator >
	// JAX-RS Validator > check Enable project specific preferences
	// and then JAX-RS Activators > set No JAX-RS Activator configured
	// to Ignored.

}
