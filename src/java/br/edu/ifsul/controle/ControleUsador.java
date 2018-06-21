
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.AutorizacaoDAO;
import br.edu.ifsul.dao.UsadorDAO;
import br.edu.ifsul.modelo.Autorizacao;
import br.edu.ifsul.modelo.Usador;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Ricardo/Joel
 */
@Named(value = "controleUsador")
@ViewScoped
public class ControleUsador implements Serializable{
    
    @EJB
    private UsadorDAO<Usador> dao;
    private Usador objeto;
    private Boolean editando;
    @EJB
    private AutorizacaoDAO<Autorizacao> daoAutorizacao;
    private Autorizacao autorizacao;
    private Boolean editandoAutorizacao;

    public ControleUsador() {
        editando = false;
    }
    
    public String listar(){
        editando = false;
        return "/privado/usador/listar?faces-redirect=true";
    }
    
    public void novo(){
        editando = true;
        editandoAutorizacao = false;
        objeto = new Usador();
    }
    
    public void alterar(Object id){
        try{
            objeto = dao.getObjectById(id);
            editando = true;
            editandoAutorizacao = false;
        }catch (Exception e){
            Util.mensagemErro("Erro ao recuperar objeto: " + Util.getMensagemErro(e));
        }
    }
    
    public void excluir(Object id){
        try{
            objeto = dao.getObjectById(id);
            dao.remover(objeto);
            Util.mensagemInformacao("Objeto removido com sucesso!");
        }catch (Exception e){
            Util.mensagemErro("Erro ao remover objeto: " + Util.getMensagemErro(e));
        }
    }
    
    public void salvar(){
        try{
            if(objeto.getId() == null){
                dao.persist(objeto);
            }else{
                dao.merge(objeto);
            }
            Util.mensagemInformacao("Objeto perdistido com sucesso!");
            editando = false;
        }catch(Exception e){
            Util.mensagemErro("Erro ao persistir objeto " + Util.getMensagemErro(e));
        }
    }
    
    public void novaAutorizacao(){
        editandoAutorizacao = true;
    }
    
    public void salvarAutorizacao(){
        if(objeto.getAutorizacoes().contains(autorizacao)){
            Util.mensagemErro("Este usuador já possui esta autorizacão!");
        }else{
            objeto.getAutorizacoes().add(autorizacao);
            Util.mensagemInformacao("Autorização adicionada com sucesso!");
        }
        editandoAutorizacao = false;
    }
    
    public void removerAutorizacao(Autorizacao obj){
        objeto.getAutorizacoes().remove(obj);
        Util.mensagemInformacao("Autorizacao removida com sucesso!");
    }
    
    
    public UsadorDAO<Usador> getDao() {
        return dao;
    }

    public void setDao(UsadorDAO<Usador> dao) {
        this.dao = dao;
    }

    public Usador getObjeto() {
        return objeto;
    }

    public void setObjeto(Usador objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public AutorizacaoDAO<Autorizacao> getDaoAutorizacao() {
        return daoAutorizacao;
    }

    public void setDaoAutorizacao(AutorizacaoDAO<Autorizacao> daoAutorizacao) {
        this.daoAutorizacao = daoAutorizacao;
    }

    public Autorizacao getAutorizacao() {
        return autorizacao;
    }

    public void setAutorizacao(Autorizacao autorizacao) {
        this.autorizacao = autorizacao;
    }

    public Boolean getEditandoAutorizacao() {
        return editandoAutorizacao;
    }

    public void setEditandoAutorizacao(Boolean editandoAutorizacao) {
        this.editandoAutorizacao = editandoAutorizacao;
    }
    
}
