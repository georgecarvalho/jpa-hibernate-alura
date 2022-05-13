package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.alura.loja.modelo.Produto;

public class ProdutoDAO {
	private EntityManager entityManager;

	public ProdutoDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void cadastrar(Produto produto) {
		this.entityManager.persist(produto);
	}

	public void atualizar(Produto produto) {
		this.entityManager.merge(produto);
	}

	public void remover(Produto produto) {
		produto = this.entityManager.merge(produto);
		this.entityManager.remove(produto);
	}

	public Produto buscarPor(Long id) {
		return this.entityManager.find(Produto.class, id);
	}

	public List<Produto> buscarTodos() {
		// nome da Entidade e n�o da tabela
		return this.entityManager.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
	}

	public List<Produto> buscarPorNome(String nome) {
		// nome do atributo e n�o da coluna da tabela
		String jqpl = "SELECT p FROM Produto p WHERE p.nome = :nome";
		return this.entityManager.createQuery(jqpl, Produto.class).setParameter("nome", nome).getResultList();
	}

	public List<Produto> buscarPorNomeDaCategoria(String nome) {
		String jqpl = "SELECT p FROM Produto p WHERE p.categoria.nome = :nome";
		return this.entityManager.createQuery(jqpl, Produto.class).setParameter("nome", nome).getResultList();
	}

	public BigDecimal buscarPrecoDoProdutoPorNome(String nome) {
		String jqpl = "SELECT p.preco FROM Produto p WHERE p.nome = :nome";
		return this.entityManager.createQuery(jqpl, BigDecimal.class).setParameter("nome", nome).getSingleResult();
	}

	public List<Produto> buscarPorParametros(String nome, BigDecimal preco, LocalDate dataCadastro) {
		String jpql = " SELECT p FROM Produto p WHERE 1=1 ";
		if (nome != null && !nome.trim().isEmpty()) {
			jpql = jpql.concat(" AND p.nome = :nome ");
		}
		if (preco != null) {
			jpql = jpql.concat(" AND p.preco = :preco ");
		}
		if (dataCadastro != null) {
			jpql = jpql.concat(" AND p.dataCadastro = :dataCadastro ");
		}
		TypedQuery<Produto> query = this.entityManager.createQuery(jpql, Produto.class);
		if (nome != null && !nome.trim().isEmpty()) {
			query.setParameter("nome", nome);
		}
		if (preco != null) {
			query.setParameter("preco", preco);
		}
		if (dataCadastro != null) {
			query.setParameter("dataCadastro", dataCadastro);
		}

		return query.getResultList();
	}

	public List<Produto> buscarPorParametrosComCriteria(String nome, BigDecimal preco, LocalDate dataCadastro) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
		Root<Produto> from = query.from(Produto.class);
		
		Predicate filtros = builder.and();
		
		if (nome != null && !nome.trim().isEmpty()) {
			filtros = builder.and(filtros, builder.equal(from.get("nome"), nome));
		}
		if (preco != null) {
			filtros = builder.and(filtros, builder.equal(from.get("preco"), preco));
		}
		if (dataCadastro != null) {
			filtros = builder.and(filtros, builder.equal(from.get("dataCadastro"), dataCadastro));
		}
		query.where(filtros);
		
		return this.entityManager.createQuery(query).getResultList();

	}
}
