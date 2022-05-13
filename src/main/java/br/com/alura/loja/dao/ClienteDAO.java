package br.com.alura.loja.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Cliente;

public class ClienteDAO {
	private EntityManager entityManager;

	public ClienteDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void cadastrar(Cliente cliente) {
    	this.entityManager.persist(cliente);
    }
    
    public void atualizar(Cliente cliente) {
        this.entityManager.merge(cliente);
    }
    
    public void remover(Cliente cliente) {
    	cliente = this.entityManager.merge(cliente);
        this.entityManager.remove(cliente);
    }
    
    public Cliente buscarPor(Long id) {
    	return this.entityManager.find(Cliente.class, id);
    }
    
    public List<Cliente> buscarTodos(){
    	return this.entityManager.createQuery("SELECT cl FROM CLIENTE cl", 
    			Cliente.class).getResultList();
    }
}
