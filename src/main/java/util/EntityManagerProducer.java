package util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class EntityManagerProducer {
    
    private EntityManagerFactory factory;
    
    public EntityManagerProducer() {
        // Inicializa o EntityManagerFactory com a unidade de persistência 'AlgaWorksPU'
        this.factory = Persistence.createEntityManagerFactory("AlgaWorksPU");
    }
    
    @Produces
    @RequestScoped
    public EntityManager createEntityManager() {
        // Cria um novo EntityManager a partir do EntityManagerFactory
        return this.factory.createEntityManager();
    }
    
    public void closeEntityManager(@Disposes EntityManager manager) {
        // Fecha o EntityManager quando ele é descartado pelo CDI
        if (manager.isOpen()) {
            manager.close();
        }
    }
}
