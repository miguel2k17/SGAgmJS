<!--Agregamos el core de JSTL-->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listado de Usuarios</title>
    </head>
    <body>
        <div id="wrap">
            <!--Incluimos JSPs comunes-->
            <div id="header">
                <jsp:include page="/WEB-INF/pages/commons/header.jsp"/>
            </div>

            <div id="nav">
                <jsp:include page="/WEB-INF/pages/commons/menu.jsp"/>
            </div>

            <div id="msg">
                <jsp:include page="/WEB-INF/pages/commons/messages.jsp"/>
            </div>
            <div id="main">
                <form id="form1" action="${pageContext.request.contextPath}/ServletControlador"
                      method="post">
                    <!-- Esta accion se va modificar por JavaScript según la opción seleccionada -->
                    <input type="hidden" name="accion"  id="accion"/>
                    <div class="subtitulo">
                        Listado de Usuarios
                    </div>
                    <div class="tabla">
                        <table  align="center" class="elemento" >
                            <tr>
                                <th><input type="checkbox" name="controladorCheckbox" onclick="selectAllCheckboxes(this);" /> 
                                </th>
                                <th>Nombre Persona</th>
                                <th>Username</th>
                                <th>Password</th>
                            </tr>
                            <c:forEach var="usuarioItem" items="${listaUsuariosPojo}" varStatus="row">
                                <tr class="${ (row.count % 2) == 0 ? "row1" : "row2" }" >
                                    <td align="center">
                                        <input type="checkbox" name="usuarios" id="usuarios" value="${usuarioItem.usuario.idUsuario}">
                                    </td>
                                    <td onclick="editaRegistro(${row.count});">
                                        ${usuarioItem.persona.nombre} ${usuarioItem.persona.apellido}
                                    </td>
                                    <td onclick="editaRegistro(${row.count});">
                                        ${usuarioItem.usuario.username}
                                    </td>
                                    <td onclick="editaRegistro(${row.count});">
                                        ${usuarioItem.usuario.password}
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                        <input id="botonAgregar" type="button" value="Agregar" onclick="validaFormularioListadoUsuarios(this);">
                        <input id="botonEditar" type="button" value="Editar" onclick="validaFormularioListadoUsuarios(this);">
                        <input id="botonEliminar" type="button" value="Eliminar" onclick="validaFormularioListadoUsuarios(this);">
                    </div>

                </form>
            </div>
        </div> 
    </body>
</html>
