/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sga.services;

import java.util.List;
import sga.eis.dto.Usuario;

/**
 *
 * @author Ubaldo
 */
public interface UsuarioService {

    public boolean usuarioExistente(Usuario usuario);

    public List<Usuario> getAllUsuarios();

    public boolean guardarUsuario(Usuario usuario);
    
    public Usuario getUsuarioById(Integer idUsuario);
    
    public boolean eliminarUsuarios(List<Integer> idUsuarios);

}
