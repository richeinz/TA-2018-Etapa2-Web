
package br.edu.ifsul.dao;

import java.io.Serializable;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ricardo
 */
public class DAOGenerico<TIPO> implements Serializable{
    
    private List<TIPO> listaObjetos;
    private List<TIPO> listaTodos;
    @PersistenceContext(unitName = "TA-2018-1-6N1-WebPU")
    protected EntityManager em;
    protected Class classePersistente;
    protected String mensagem = "";
    protected String ordem = "id";
    protected String filtro = "";
    protected Integer maximoObjetos = 5;
    protected Integer posicaoAtual = 0;
    protected Integer totalObjetos = 0;

    public DAOGenerico() {
    }
    
    public List<TIPO> getListaObjetos() {
        String jpql = "from " + classePersistente.getSimpleName();
        String where = "";
        setFiltro(getFiltro().replaceAll("[';-]", ""));
        if (getFiltro().length() > 0) {
            if (getOrdem().equals("id")) {
                try {
                    Integer.parseInt(getFiltro());
                    where += " where " + getOrdem() + " = '" + getFiltro() + "' ";
                } catch (Exception e) {
                }
            } else {
                where += " where upper(" + getOrdem() + ") like '" + getFiltro().toUpperCase() + "%' ";
            }
        }
        jpql += where;
        jpql += " order by " + getOrdem();        
        setTotalObjetos((Integer) em.createQuery("select id from "+classePersistente.getSimpleName() +
                where + " order by " + getOrdem()).getResultList().size());
        return em.createQuery(jpql).
                setFirstResult(getPosicaoAtual()).
                setMaxResults(getMaximoObjetos()).getResultList();
    }
    
    public List<TIPO> getListaTodos() {
         String jpql = "from "+classePersistente.getSimpleName() + " order by " 
                + getOrdem();
        return em.createQuery(jpql).getResultList();
    }
    
    public void persist(TIPO obj) throws Exception {
        em.persist(obj);
    }
    
    public void merge(TIPO obj) throws Exception {
        em.merge(obj);
    }
    
   
    public TIPO getObjectById(Object id) throws Exception {
        return (TIPO) em.find(classePersistente, id);
    }
    
    @RolesAllowed("ADMINISTRADOR")
    public void remover(TIPO obj) throws Exception {
        obj = em.merge(obj);
        em.remove(obj);
    }

    public void setListaObjetos(List<TIPO> listaObjetos) {
        this.listaObjetos = listaObjetos;
    }

    public void setListaTodos(List<TIPO> listaTodos) {
        this.listaTodos = listaTodos;
    }
    
    public void primeiro(){
        setPosicaoAtual((Integer) 0);
    }
    
    public void anterior(){
        setPosicaoAtual((Integer) (getPosicaoAtual() - getMaximoObjetos()));
        if (getPosicaoAtual() < 0){
            setPosicaoAtual((Integer) 0);
        }
    }
    
    public void proximo(){
        if (getPosicaoAtual() + getMaximoObjetos() < getTotalObjetos()){
            setPosicaoAtual((Integer) (getPosicaoAtual() + getMaximoObjetos()));
        }
    }
    
    public void ultimo(){
        int resto = getTotalObjetos() % getMaximoObjetos();
        if (resto > 0 ){
            setPosicaoAtual((Integer) getTotalObjetos() - resto);
        } else {
            setPosicaoAtual((Integer) getTotalObjetos() - getMaximoObjetos());
        }
    }
    
    public String getMensagemNavegacao(){
        int ate = getPosicaoAtual() + getMaximoObjetos();
        if (ate > getTotalObjetos()){
            ate = getTotalObjetos();
        }
        return "Listando de " + (getPosicaoAtual() + 1) + " até " + ate + " de " +
                getTotalObjetos() + " registros";
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Class getClassePersistente() {
        return classePersistente;
    }

    public void setClassePersistente(Class classePersistente) {
        this.classePersistente = classePersistente;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public Integer getMaximoObjetos() {
        return maximoObjetos;
    }

    public void setMaximoObjetos(Integer maximoObjetos) {
        this.maximoObjetos = maximoObjetos;
    }

    public Integer getPosicaoAtual() {
        return posicaoAtual;
    }

    public void setPosicaoAtual(Integer posicaoAtual) {
        this.posicaoAtual = posicaoAtual;
    }

    public Integer getTotalObjetos() {
        return totalObjetos;
    }

    public void setTotalObjetos(Integer totalObjetos) {
        this.totalObjetos = totalObjetos;
    }
   
}
