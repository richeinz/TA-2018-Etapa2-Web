
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
        ordem = "nome";
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
    
    public Usador localizaPorNomeUsador(String nomeUsador){
        Query query = em.createQuery("from Usador where upper(usador) = :nomeUsador");
        query.setParameter("nomeUsador", nomeUsador.toUpperCase());
        Usador obj = (Usador) query.getSingleResult();
        obj.getAutorizacoes().size();
        return obj;
    }
    
    
}
