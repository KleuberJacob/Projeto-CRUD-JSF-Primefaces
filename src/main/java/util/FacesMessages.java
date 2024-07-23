package util;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesMessages implements Serializable {

    private static final long serialVersionUID = 1L;

    private void add(String mensagem, FacesMessage.Severity severity) {
	FacesMessage facesMessage = new FacesMessage(mensagem);
	facesMessage.setSeverity(severity);
	
	FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
    
    public void info(String mensagem) {
	add(mensagem, FacesMessage.SEVERITY_INFO);	
    }

}
