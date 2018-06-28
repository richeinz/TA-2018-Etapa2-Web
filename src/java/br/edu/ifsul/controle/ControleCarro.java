package br.edu.ifsul.controle;

import br.edu.ifsul.dao.CarroDAO;
import br.edu.ifsul.dao.ImagemDAO;
import br.edu.ifsul.dao.MarcaDAO;
import br.edu.ifsul.modelo.Carro;
import br.edu.ifsul.modelo.Imagem;
import br.edu.ifsul.modelo.Marca;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Ricardo/Joel
 */
@Named(value = "controleCarro")
@SessionScoped
public class ControleCarro implements Serializable {

    @EJB
    private CarroDAO<Carro> dao;
    private Carro objeto;
    private Boolean editando;
    @EJB
    private MarcaDAO daoMarca;
    @EJB
    private ImagemDAO<Imagem> daoImagem;
    private Imagem img;
    private Boolean editandoImagem;
    private byte[] byimg;

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

    public void enviarImagem(FileUploadEvent event) {
        try {
            this.byimg = IOUtils.toByteArray(event.getFile().getInputstream());
        } catch (Exception e) {
            Util.mensagemErro("Erro ao enviar arquivo:"
                    + Util.getMensagemErro(e));
        }
    }

    public void novaImagem() {
        if (objeto.getImagens().size() == 1) {
            Util.mensagemErro("Permitido somente uma imagem");
        } else {
            editandoImagem = true;
        }
    }

    public void salvarImagem() {
        if (this.byimg != null) {
            Imagem img = new Imagem();
            img.setCarro(objeto);
            img.setArquivo(this.byimg);
            objeto.getImagens().add(img);
            Util.mensagemInformacao("Imagem adicionada com sucesso");
        }
        editandoImagem = false;
    }
    
    public void removerImagem(Imagem obj){
        objeto.getImagens().remove(obj);
        Util.mensagemInformacao("Imagem removida com sucesso");
    }

    public StreamedContent getImagemDinamica() {
        String strid = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get("id_imagem");
        if (strid != null) {
            Integer id = Integer.parseInt(strid);
            Carro obj = dao.localizaPorIdCarro(id);
            List<Imagem> i = new ArrayList<>();
            i = obj.getImagens();
            for (int j = 0; j < i.size(); j++) {
                return new DefaultStreamedContent(new ByteArrayInputStream(i.get(j).getArquivo()));
            }
        }
        return new DefaultStreamedContent();
    }

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

    public ImagemDAO<Imagem> getDaoImagem() {
        return daoImagem;
    }

    public void setDaoImagem(ImagemDAO<Imagem> daoImagem) {
        this.daoImagem = daoImagem;
    }

    public Imagem getImg() {
        return img;
    }

    public void setImg(Imagem img) {
        this.img = img;
    }

    public Boolean getEditandoImagem() {
        return editandoImagem;
    }

    public void setEditandoImagem(Boolean editandoImagem) {
        this.editandoImagem = editandoImagem;
    }

    public byte[] getByimg() {
        return byimg;
    }

    public void setByimg(byte[] byimg) {
        this.byimg = byimg;
    }

}
