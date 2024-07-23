package repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.Empresa;

public class Empresas implements Serializable {
    private static final long serialVersionUID = 1L;

    @PersistenceContext
    @Inject
    private EntityManager manager;

    public Empresas() {
    }

    public Empresa porId(Long id) {
        return manager.find(Empresa.class, id);
    }
    
    public List<Empresa> todas() {       
        return manager.createQuery("from Empresa", Empresa.class).getResultList();
    }

    public List<Empresa> pesquisar(String nome) {
        String jpqlString = "from Empresa where razaoSocial like :razaoSocial";
        
        TypedQuery<Empresa> query = manager.createQuery(jpqlString, Empresa.class);
        
        query.setParameter("razaoSocial", nome + "%");
        
        return query.getResultList();
    }

    public Empresa guardar(Empresa empresa) {
        return manager.merge(empresa);
    }

    public void remover(Empresa empresa) {
        Empresa response = porId(empresa.getId());
        
        manager.remove(response);
    }
}
