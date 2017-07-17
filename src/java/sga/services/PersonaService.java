/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sga.services;

import sga.eis.dto.Persona;
import java.util.List;

/**
 *
 * @author Ubaldo
 */
public interface PersonaService {

  public List<Persona> getAllPersonas();

  public Persona getPersonaById(Integer idPersona);

  public boolean eliminarPersonas(List<Integer> idPersonas);

  public boolean guardarPersona(Persona persona);

}
