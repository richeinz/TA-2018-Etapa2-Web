
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.UsadorDAO;
import br.edu.ifsul.modelo.Usador;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Ricardo/Joel
 */
@Named(value = "controleLogin")
@SessionScoped
public class ControleLogin implements Serializable{
    
    private Usador usadorAutenticado;
    @EJB
    private UsadorDAO<Usador> dao;
    private String usador;
    private String senha;

    public ControleLogin() {
    }
    
    public String paginaLogin(){
        return "/login?faces-redirect=true";
    }
    
    public String efetuarLogin(){
        try{
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            request.login(this.usador, this.senha);
            if(request.getUserPrincipal()!=null){
                usadorAutenticado = dao.localizaPorNomeUsador(request.getUserPrincipal().getName());
                Util.mensagemInformacao("Login efetuado com sucesso!");
                usador = "";
                senha = "";
            }
            return "/index";
        }catch(Exception e){
            Util.mensagemErro("Usuário ou senha inválidos! " + Util.getMensagemErro(e));
            return "/login";
        }
    }
    
    public String efetuarLogout(){
        try{
            usadorAutenticado = null;
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            request.logout();
        }catch(Exception e){
            Util.mensagemErro("Erro: " + Util.getMensagemErro(e));}
        return "/index";
    }

    public Usador getUsadorAutenticado() {
        return usadorAutenticado;
    }

    public void setUsadorAutenticado(Usador usadorAutenticado) {
        this.usadorAutenticado = usadorAutenticado;
    }

    public UsadorDAO<Usador> getDao() {
        return dao;
    }

    public void setDao(UsadorDAO<Usador> dao) {
        this.dao = dao;
    }

    public String getUsador() {
        return usador;
    }

    public void setUsador(String usador) {
        this.usador = usador;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
}
