
package br.edu.ifsul.converters;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Ricardo
 */
@FacesConverter(value="converterCalendar")
public class ConverterCalendar implements Converter, Serializable{
  
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        Calendar c = Calendar.getInstance();
        try {
        c.setTime(sdf.parse(string));
        } catch (Exception e ){
            return null;
        }
        return c;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o == null){
            return null;
        }
        Calendar c = (Calendar) o;
        return sdf.format(c.getTime());
    }
    
}

