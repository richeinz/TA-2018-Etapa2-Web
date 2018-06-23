
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Autorizacao;
import java.io.Serializable;
import javax.ejb.Stateful;

/**
 *
 * @author Ricardo/Joel
 */
@Stateful
public class AutorizacaoDAO<TIPO> extends DAOGenerico<Autorizacao> implements Serializable{
    
    public AutorizacaoDAO(){
        super();
        classePersistente = Autorizacao.class;
        ordem = "name";
        maximoObjetos = 3;
    }
}
