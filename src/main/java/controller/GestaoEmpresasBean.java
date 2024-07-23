package controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import enums.TipoEmpresa;
import model.Empresa;
import model.RamoAtividade;
import repository.Empresas;
import repository.RamoAtividades;
import service.CadastroEmpresaService;
import util.FacesMessages;

@Named
@ViewScoped
public class GestaoEmpresasBean implements Serializable {
  
    private static final long serialVersionUID = 1L;
    
    @Inject
    private Empresas empresas;
    
    @Inject
    private FacesMessages messages;
    
    @Inject
    private RamoAtividades ramoAtividades;
    
    @Inject
    private CadastroEmpresaService cadastroEmpresaService;
    
    private List<Empresa> listaEmpresas;
    
    private String termoPesquisa;    
    
    private Converter ramoAtividadeConverter;
    
    private Empresa empresa;    
    
    @PostConstruct
    public void init() {
	listaEmpresas = empresas.todas();
    }
    
    public void pesquisar() {
	listaEmpresas = empresas.pesquisar(termoPesquisa);
	
	if(listaEmpresas.isEmpty()) {
	    messages.info("Sua consulta não retornou nenhum registro!");
	}
    }
    
    public List<RamoAtividade> completarRamoAtividades(String termo) {
       List<RamoAtividade> listaRamoAtividades = ramoAtividades.pesquisar(termo);
       
       ramoAtividadeConverter = new RamoAtividadeConverter(listaRamoAtividades);
       
       return listaRamoAtividades;
   }
    
    public void prepararNovaEmpresa() {
	empresa = new Empresa();
    }
    
    public void prepararEdicaoEmpresa() {
	ramoAtividadeConverter = new RamoAtividadeConverter(Arrays.asList(empresa.getRamoAtividade()));
    }   
    
    public void excluirEmpresa() {
	cadastroEmpresaService.excluir(empresa);
	
	empresa = null;
	
	atualizarRegistros();
	
	messages.info("Empresa excluida com sucesso!");
	
    } 
    
    private void atualizarRegistros() {	
	if(jaHouvePesquisa()) {
	    pesquisar();
	} else {
	    init();
	}
	
    }
    
    public void salvar() {
	cadastroEmpresaService.salvar(empresa);
	
	atualizarRegistros();
	
	messages.info("Empresa salva com sucesso!");
	
	RequestContext.getCurrentInstance().update(Arrays.asList("frm:empresasDataTable", "frm:messages"));
    }
    
    private boolean jaHouvePesquisa() {
	return termoPesquisa != null && !"".equals(termoPesquisa);
    }
        
    public Converter getRamoAtividadeConverter() {
        return ramoAtividadeConverter;
    }

    public List<Empresa> getListaEmpresas() {
        return listaEmpresas;
    }

    public String getTermoPesquisa() {
        return termoPesquisa;
    }

    public void setTermoPesquisa(String termoPesquisa) {
        this.termoPesquisa = termoPesquisa;
    }
    
    public TipoEmpresa[] getTiposEmpresa() { //Retorna todas propriedades dentro de um array que o Enum possui
	return TipoEmpresa.values();
    }

    public Empresa getEmpresa() {
	return empresa;
    }

    public void setEmpresa(Empresa empresa) {
	this.empresa = empresa;
    }
    
    public boolean isEmpresaSelecionada() {
	return empresa != null && empresa.getId() != null;
    }

//    public Empresa getEmpresa() {  //  Para vincular o atributo à página .xhtml, é necessário criar um getter
//        return empresa;
//    }   
//    
//    public TipoEmpresa[] getTiposEmpresa() { // Obtém os valores presentes no Enum para serem vinculados na tela
//        return TipoEmpresa.values();
//    }
//    
//    public void salvar() { // Método que será chamado na página .xhtml
//        System.out.println("Salvamos os dados");
//    }
//    
//    public String ajuda() {
//        return "AjudaGestaoEmpresas?faces-redirect=true"; // Para que a URL seja alterada junto com o HTML, devemos inserir a extensão (?faces-redirect=true)
//    }

}
