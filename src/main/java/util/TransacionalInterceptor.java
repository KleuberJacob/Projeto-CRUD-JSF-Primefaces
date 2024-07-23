package util;

import java.io.Serializable;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Interceptor
@Transacional
@Priority(Interceptor.Priority.APPLICATION)
public class TransacionalInterceptor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private EntityManager manager;
    
    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        EntityTransaction trx = manager.getTransaction();
        boolean criador = false;
        
        try {
            if (!trx.isActive()) {
                // Inicia a transação apenas se não estiver ativa
                trx.begin();
                
                // Marca o criador da transação como verdadeiro
                criador = true;
            }
            
            // Invoca o método que está sendo interceptado
            Object result = context.proceed();
            
            // Se a transação foi criada pelo interceptor e ainda está ativa, faz commit
            if (trx.isActive() && criador) {
                trx.commit();
            }
            
            return result;
        } catch (Exception e) {
            // Se ocorrer uma exceção, faz rollback da transação
            if (trx != null && trx.isActive() && criador) {
                trx.rollback();
            }
            
            // Relança a exceção para que seja tratada pelo chamador
            throw e;
        } finally {
            // Se a transação foi criada pelo interceptor e ainda está ativa, faz commit
            if (trx.isActive() && criador) {
                trx.commit();
            }
        }
    }
}
