
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
        ordem = "nome";
        maximoObjetos = 3;
    }
}
