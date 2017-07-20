/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sga.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import sga.eis.dao.UsuarioDao;
import sga.eis.dto.Usuario;
import sga.eis.exceptions.UsuarioDaoException;
import sga.eis.factory.UsuarioDaoFactory;
import sga.services.UsuarioService;
import sga.services.exceptions.BusinessException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sga.eis.dto.UsuarioPk;
import sga.eis.jdbc.ResourceManager;

/**
 *
 * @author Ubaldo
 */
public class UsuarioServiceImpl implements UsuarioService {

    //Utilizamos el patron singleton, solo existen un objeto de tipo UsuarioService en Memoria
    private static UsuarioService usuarioServiceInstance;

    //Creamos un atributo usuarioDao para comunicarnos con la capa de datos
    UsuarioDao usuarioDao;

    //Constructor sin argumentos privado, para implementar el patron singleton
    private UsuarioServiceImpl() {
    }

    //Creamos una nueva y unica instancia si es que no existe
    public static UsuarioService getInstance() {
        if (usuarioServiceInstance == null) {
            usuarioServiceInstance = new UsuarioServiceImpl();
        }
        return usuarioServiceInstance;
    }

    public boolean usuarioExistente(Usuario usuarioDto) {
        try {

            this.usuarioDao = UsuarioDaoFactory.create();

            //Buscamos el objeto por UserName y password
            final String SQL_WHERE = "username = ? and password = ?";

            Object[] sqlParams = {usuarioDto.getUsername(), usuarioDto.getPassword()};
            Usuario[] usuarios = this.usuarioDao.findByDynamicWhere(SQL_WHERE, sqlParams);

            if (usuarios.length > 0) {
                return true;
            }

        } catch (UsuarioDaoException ex) {
            throw new BusinessException("Existe un problema al obtener el usuario en la BD", ex);
        }

        //En cualquier otro caso, regresa falso
        return false;
    }

    public List<Usuario> getAllUsuarios() {
        try {

            this.usuarioDao = UsuarioDaoFactory.create();
            return Arrays.asList(this.usuarioDao.findAll());
        } catch (UsuarioDaoException ex) {
            throw new BusinessException("Existe un problema al obtener el arreglo de usuarios en la BD", ex);
        }
    }

    public boolean guardarUsuario(Usuario usuario) {
        Connection conn = null;

        try {
            //Comenzamos la transaccion

            // Obtenemos una conexion del pool
            conn = ResourceManager.getConnection();
            // Activamos el manejo transaccional
            conn.setAutoCommit(false);

            this.usuarioDao.setUserConn(conn);

            //Revisamos si es un insert o un update, dependiendo si se tiene o no el valor de la PK
            if (usuario.getIdUsuario() == null) {
                this.usuarioDao.insert(usuario);
            } else {
                this.usuarioDao.update(usuario.createPk(), usuario);
            }

            // Guardamos los cambios en la BD
            conn.commit();
            //Regresamos la bandera indicando que se eliminaron los registros
            return true;

        } catch (UsuarioDaoException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                throw new BusinessException("No se pudo reestablecer el estado de la Base de Datos", ex1);
            }
            throw new BusinessException("No se puedo agregar el Usuario:" + usuario + " a la BD", ex);
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                throw new BusinessException("No se pudo reestablecer el estado de la Base de Datos", ex1);
            }
            throw new BusinessException("Existe un problema con la Base de Datos", ex);
        } finally {
            // Cerramos la conexión para regresala al pool
            ResourceManager.close(conn);
        }
    }

    public Usuario getUsuarioById(Integer idUsuario) {
        try {
            return this.usuarioDao.findByPrimaryKey(idUsuario);
        } catch (UsuarioDaoException ex) {
            throw new BusinessException("Existe un problema al obtener la persona con id:" + idUsuario, ex);
        }
    }

    public boolean eliminarUsuarios(List<Integer> idUsuarios) {
        Connection conn = null;

        try {
            //Comenzamos la transaccion, si algun elemento no se elminina
            //entonces ninguno se elmina
            // Obtenemos una conexion del pool

            conn = ResourceManager.getConnection();
            // Activamos el manejo transaccional
            conn.setAutoCommit(false);

            this.usuarioDao.setUserConn(conn);
            //Tenemos dos posibles opciones, crear un SQL con los id's a eliminar
            //o eliminar registro a registro. Escogemos la segunda opcion
            for (Integer usuario : idUsuarios) {
                this.usuarioDao.delete(new UsuarioPk(usuario));
            }

            // Guardamos los cambios en la BD
            conn.commit();
            //Regresamos la bandera indicando que se eliminaron los registros
            return true;

        } catch (UsuarioDaoException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                throw new BusinessException("No se pudo reestablecer el estado de la Base de Datos", ex1);
            }
            throw new BusinessException("Existe un problema para eliminar los elementos: " + idUsuarios, ex);
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                throw new BusinessException("No se pudo reestablecer el estado de la Base de Datos", ex1);
            }
            throw new BusinessException("Existe un problema con la Base de Datos", ex);
        } finally {
            // Cerramos la conexión para regresala al pool
            ResourceManager.close(conn);
        }
    }

}
