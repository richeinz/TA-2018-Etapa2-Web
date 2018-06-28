
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Imagem;
import java.io.Serializable;
import javax.ejb.Stateful;

/**
 *
 * @author Ricardo/Joel
 */
@Stateful
public class ImagemDAO<TIPO> extends DAOGenerico<Imagem> implements Serializable{
    
    public ImagemDAO(){
        super();
        classePersistente = Imagem.class;
        ordem = "name";
        maximoObjetos = 3;
    }
}
