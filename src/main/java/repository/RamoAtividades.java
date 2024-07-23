package repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import model.RamoAtividade;

public class RamoAtividades implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public RamoAtividades() {
		
	}
	
	public RamoAtividades(EntityManager manager) {
		this.manager = manager;
	}
		
	public List<RamoAtividade> pesquisar(String descricao) {
		CriteriaBuilder criteria = manager.getCriteriaBuilder();
		
		CriteriaQuery<RamoAtividade> criteriaQuery = criteria.createQuery(RamoAtividade.class);
		Root<RamoAtividade> root = criteriaQuery.from(RamoAtividade.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteria.like(root.get("descricao"), descricao + "%"));
		
		TypedQuery<RamoAtividade> query = manager.createQuery(criteriaQuery);
		
		return query.getResultList();
	}	
	
}
