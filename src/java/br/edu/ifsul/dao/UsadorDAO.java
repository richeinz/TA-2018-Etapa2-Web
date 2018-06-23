
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Usador;
import java.io.Serializable;
import javax.ejb.Stateful;
import javax.persistence.Query;

/**
 *
 * @author Ricardo/Joel
 */
@Stateful
public class UsadorDAO<TIPO> extends DAOGenerico<Usador> implements Serializable{
    
    public UsadorDAO(){
        super();
        classePersistente = Usador.class;
        ordem = "name";
        maximoObjetos = 3;
    }
    
    public Usador getObjectById(Object id) throws Exception {
        Usador obj = em.find(Usador.class, id);
        /*
        A linha obj.getPermissoes().size(); é necessaria para inicializar a coleção para quando ela for exibida na tela não gerar 
        um erro de lazyInicializationException
        */
        obj.getAutorizacoes().size();
        return obj;
    }
    
    public Usador localizaPorNameUsador(String nameUsador){
        Query query = em.createQuery("from Usador where upper(usador) = :nameUsador");
        query.setParameter("nameUsador", nameUsador.toUpperCase());
        Usador obj = (Usador) query.getSingleResult();
        obj.getAutorizacoes().size();
        return obj;
    }
    
    
}
