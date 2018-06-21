
package br.edu.ifsul.converters;

import br.edu.ifsul.modelo.Carro;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ricardo/Joel
 */
@FacesConverter(value = "converterCarro")
public class ConverterCarro implements Serializable, Converter{
    
     @PersistenceContext(unitName = "TA-2018-Etapa2-WebPU")
    private EntityManager em;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if(string == null || string.equals("Selecione um registro")){
            return null;
        }
        return em.find(Carro.class, Integer.parseInt(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o == null){
            return null;
        }
        Carro obj = (Carro) o;
        return obj.getId().toString();
    }
    
}
