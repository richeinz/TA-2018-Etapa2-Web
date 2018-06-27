package br.edu.ifsul.controle;

import br.edu.ifsul.dao.CarroDAO;
import br.edu.ifsul.dao.MarcaDAO;
import br.edu.ifsul.modelo.Carro;
import br.edu.ifsul.modelo.Marca;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Ricardo/Joel
 */
@Named(value = "controleCarro")
@ViewScoped
public class ControleCarro implements Serializable {

    @EJB
    private CarroDAO<Carro> dao;
    private Carro objeto;
    private Boolean editando;
    @EJB
    private MarcaDAO daoMarca;

    public ControleCarro() {
        editando = false;
    }
    
    public String listarPublico() {
        editando = false;
        return "/index/listar?faces-redirect=true";
    }

    public String listar() {
        editando = false;
        return "/privado/carro/listar?faces-redirect=true";
    }

    public void novo() {
        editando = true;
        objeto = new Carro();
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
    
    /*
    public void enviarImagem(FileUploadEvent event) {
        try {
            byte[] imagem = IOUtils.toByteArray(event.getFile().getInputstream());
            objeto.setImagens(imagem);
            Util.mensagemInformacao("Arquivo enviado com sucesso! "
                    + event.getFile().getFileName());
        } catch (Exception e) {
            Util.mensagemErro("Erro ao enviar arquivo:"
                    + Util.getMensagemErro(e));
        }
    }

    public StreamedContent getImagemDinamica() {
        String strid = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get("id_imagem");
        if (strid != null) {
            Integer id = Integer.parseInt(strid);
            Carro obj = dao.localizar(id);
            return obj.getImagens();
        }
        return new DefaultStreamedContent();
    }
    */

    public CarroDAO<Carro> getDao() {
        return dao;
    }

    public void setDao(CarroDAO<Carro> dao) {
        this.dao = dao;
    }

    public Carro getObjeto() {
        return objeto;
    }

    public void setObjeto(Carro objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public MarcaDAO getDaoMarca() {
        return daoMarca;
    }

    public void setDaoMarca(MarcaDAO daoMarca) {
        this.daoMarca = daoMarca;
    }


}
