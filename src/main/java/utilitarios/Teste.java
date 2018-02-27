package utilitarios;

import model.Cliente;

public class Teste {
public static void main(String[] args) {
	DAOGenerico dao=new DAOGenerico();
	
	Cliente c=new Cliente();
	c.setNome("Frank");
	dao.inserir(c);
}
}
