package com.pojos;

import sga.eis.dto.Persona;
import sga.eis.dto.Usuario;


public class UsuariosPojo {
    private Usuario usuario;
    private Persona persona;
    
    //constructor vacio
    public UsuariosPojo(){
        
    }
    
    //getter y setters

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    
    
}
