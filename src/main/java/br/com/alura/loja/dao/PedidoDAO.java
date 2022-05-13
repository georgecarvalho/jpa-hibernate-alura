package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

public class PedidoDAO {
	private EntityManager entityManager;

	public PedidoDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void cadastrar(Pedido pedido) {
    	this.entityManager.persist(pedido);
    }
    
    public void atualizar(Pedido pedido) {
        this.entityManager.merge(pedido);
    }
    
    public void remover(Pedido pedido) {
    	pedido = this.entityManager.merge(pedido);
    	this.entityManager.remove(pedido);
    }
    
    public Pedido buscarPor(Long id) {
    	return this.entityManager.find(Pedido.class, id);
    }
    
    public List<Pedido> buscarTodos(){
    	return this.entityManager.createQuery("SELECT p FROM Pedido p", 
    			Pedido.class).getResultList();
    }
    
    public Pedido buscarPedidoComCliente(Long id) {
    	return this.entityManager.createQuery("SELECT p from Pedido p JOIN FETCH p.cliente WHERE p.id = :id", Pedido.class)
    			.setParameter("id", id)
    			.getSingleResult();
    }
    
    public BigDecimal valorTotalVendido() {
    	String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
    	return this.entityManager.createQuery(jpql, BigDecimal.class)
    			.getSingleResult();
    }
    
    public List<RelatorioDeVendasVo> relatorioDeVendas(){
    	String jpql = "SELECT new br.com.alura.loja.vo.RelatorioDeVendasVo("
    			+ "produto.nome, "
    			+ "SUM(item.quantidade), "
    			+ "MAX(pedido.data)) "
    			+ "FROM Pedido pedido "
    			+ "JOIN pedido.itens item "
    			+ "JOIN item.produto produto "
    			+ "GROUP BY produto.nome "
    			+ "ORDER BY item.quantidade DESC";
    	return this.entityManager.createQuery(jpql, RelatorioDeVendasVo.class)
    			.getResultList();
    }
}
