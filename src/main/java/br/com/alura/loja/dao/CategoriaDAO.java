package br.com.alura.loja.dao;

import java.util.List;

import javax.persistence.EntityManager;
import br.com.alura.loja.modelo.Categoria;

public class CategoriaDAO {
private EntityManager entityManager;
	
    public CategoriaDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
    
    public void cadastrar(Categoria categoria) {
    	this.entityManager.persist(categoria);
    }
    
    public void atualizar(Categoria categoria) {
        this.entityManager.merge(categoria);
    }
    
    public void remover(Categoria categoria) {
    	categoria = this.entityManager.merge(categoria);
        this.entityManager.remove(categoria);
    }
    
    public List<Categoria> buscarTodos(){
    	return this.entityManager.createQuery("SELECT c FROM CATEGORIA c", 
    			Categoria.class).getResultList();
    }
}