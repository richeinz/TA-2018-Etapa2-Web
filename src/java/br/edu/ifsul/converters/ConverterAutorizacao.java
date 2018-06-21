
package br.edu.ifsul.converters;

import br.edu.ifsul.modelo.Autorizacao;
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
@FacesConverter(value = "converterAutorizacao")
public class ConverterAutorizacao implements Serializable, Converter{
    
    @PersistenceContext(unitName = "TA-2018-Etapa2-WebPU")
    private EntityManager em;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if(string == null || string.equals("Selecione um registro")){
            return null;
        }
        return em.find(Autorizacao.class, string);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o == null){
            return null;
        }
        Autorizacao obj = (Autorizacao) o;
        return obj.getName();
    }
    
}
