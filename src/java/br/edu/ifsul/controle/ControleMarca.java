package br.edu.ifsul.controle;

import br.edu.ifsul.dao.MarcaDAO;
import br.edu.ifsul.modelo.Marca;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Ricardo/Joel
 */
@Named(value = "controleMarca")
@ViewScoped
public class ControleMarca implements Serializable {

    @EJB
    private MarcaDAO<Marca> dao;
    private Marca objeto;
    private Boolean editando;

    public ControleMarca() {
        editando = false;
    }
    

    public String listar() {
        editando = false;
        return "/privado/marca/listar?faces-redirect=true";
    }

    public void novo() {
        editando = true;
        objeto = new Marca();
    }

    public void alterar(Object id) {
        try {
            objeto = dao.getObjectById(id);
            editando = true;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao recuperar objeto: " + Util.getMensagemErro(e));
        }
    }

    public void excluir(Object id) {
        try {
            objeto = dao.getObjectById(id);
            dao.remover(objeto);
            Util.mensagemInformacao("Objeto removido com sucesso!");
        } catch (Exception e) {
            Util.mensagemErro("Erro ao remover objeto: " + Util.getMensagemErro(e));
        }
    }

    public void salvar() {
        try {
            if (objeto.getId() == null) {
                dao.persist(objeto);
            } else {
                dao.merge(objeto);
            }
            Util.mensagemInformacao("Objeto perdistido com sucesso!");
            editando = false;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao persistir objeto " + Util.getMensagemErro(e));
        }
    }
   

    public MarcaDAO<Marca> getDao() {
        return dao;
    }

    public void setDao(MarcaDAO<Marca> dao) {
        this.dao = dao;
    }

    public Marca getObjeto() {
        return objeto;
    }

    public void setObjeto(Marca objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

}
