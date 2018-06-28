
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Carro;
import java.io.Serializable;
import javax.ejb.Stateful;
import javax.persistence.Query;

/**
 *
 * @author Ricardo/Joel
 */
@Stateful
public class CarroDAO<TIPO> extends DAOGenerico<Carro> implements Serializable{
    
    public CarroDAO(){
        super();
        classePersistente = Carro.class;
        ordem = "id";
        maximoObjetos = 3;
    }
    
    public Carro localizaPorIdCarro(Integer id){
        Query query = em.createQuery("from Carro where id = :id");
        query.setParameter("id", id);
        Carro obj = (Carro) query.getSingleResult();
        obj.getImagens().size();
        return obj;
    }
}
