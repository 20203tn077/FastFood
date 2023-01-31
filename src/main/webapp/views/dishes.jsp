<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="c" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FastFood</title>
    <link rel="stylesheet" href="${c}/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="${c}/assets/css/fontawesome.min.css">
</head>
<body class="bg-light">
    <div class="container my-4">
        <h1 class="text-center">Fast Food</h1>
        <h3 class="text-center">CRUD sencillo con JSP y Servlets</h3>
        <div class="mt-3 card shadow">
            <div class="card-body vstack gap-3">
                <div class="d-flex justify-content-between align-items-center">
                    <h2 class="m-0">Platillos</h2>
                    <button onclick="dishCreation.show()" class="btn btn-success"><i class="fas fa-plus"></i>&nbsp;Registrar platillo</button>
                </div>
                <hr class="m-0">
                <div class="table-responsive">
                    <table class="table table-striped m-0">
                        <tr>
                            <th class="text-center">#</th>
                            <th>Nombre</th>
                            <th>Descripción</th>
                            <th class="text-center">Precio</th>
                            <th class="text-center">Fecha de registro</th>
                            <th class="text-center">Estado</th>
                            <th class="text-center">Categoría</th>
                            <th class="text-center">Ingredientes</th>
                            <th class="text-center">Acciones</th>
                        </tr>
                        <c:choose>
                            <c:when test="${dishes != null && dishes.size() > 0}">
                                <c:forEach items="${dishes}" var="dish" varStatus="i">
                                    <tr>
                                        <td class="text-center align-middle">${i.getCount()}</td>
                                        <td class="align-middle">${dish.name}</td>
                                        <td class="align-middle">${dish.description}</td>
                                        <td class="text-center align-middle">$${dish.price}</td>
                                        <td class="text-center align-middle date-container">${dish.registrationDate}Z</td>
                                        <td class="text-center align-middle">
                                            <span class="badge text-bg-${dish.status ? "success" : "secondary"}">
                                                ${dish.status ? "Activo" : "Inactivo"}
                                            </span>
                                        </td>
                                        <td class="text-center align-middle">
                                            <span class="badge text-bg-primary">
                                                ${dish.category.name}
                                            </span>
                                        </td>
                                        <td class="align-middle">
                                            <ul class="m-0">
                                                <c:forEach items="${dish.ingredients}" var="ingredient">
                                                    <li>
                                                        ${ingredient.name}
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </td>
                                        <td class="text-center align-middle">
                                            <div class="row g-1 justify-content-center">
                                                <c:if test="${dish.status}">
                                                    <div class="col-auto">
                                                        <button class="btn btn-primary btn-sm"><i class="fas fa-edit"></i></button>
                                                    </div>
                                                    <div class="col-auto">
                                                        <button onclick="dishDeletion.show(${dish.id})" class="btn btn-danger btn-sm"><i class="fas fa-trash-alt"></i></button>
                                                    </div>
                                                </c:if>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <script>
                                    for (dateContainer of document.querySelectorAll(".date-container")) dateContainer.innerText = new Date(dateContainer.innerText).toLocaleString(undefined, {dateStyle: 'long'})
                                </script>
                            </c:when>
                            <c:otherwise>
                                <tr><td colspan="9" class="text-center py-3">No hay registros para mostrar</td></tr>
                            </c:otherwise>
                        </c:choose>
                    </table>
                </div>
            </div>
        </div>
    </div>
    
    <!-- FORMULARIO DE REGISTRO -->
    <div class="modal fade" id="dishCreation_modal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Registrar platillo</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="dishCreation_form" action="${c}/Platillos">
                        <input type="hidden" name="action" value="create">
                        <div class="row g-3">
                            <div class="col-md-6">
                                <label for="dishCreation_inpName" class="form-label">Nombre: *</label>
                                <input id="dishCreation_inpName" name="name" class="form-control" type="text" required maxlength="45">
                            </div>
                            <div class="col-md-6">
                                <label for="dishCreation_inpPrice" class="form-label">Precio: *</label>
                                <input id="dishCreation_inpPrice" name="price" class="form-control" type="number" step="any" required min="0">
                            </div>
                            <div class="col-12">
                                <label for="dishCreation_inpDescription" class="form-label">Descripción:</label>
                                <input id="dishCreation_inpDescription" name="description" class="form-control" type="text" required maxlength="100">
                            </div>
                            <div class="col-md-6">
                                <label for="dishCreation_inpCategory" class="form-label">Categoría: *</label>
                                <select class="form-select" name="category" required id="dishCreation_inpCategory">
                                    <c:forEach items="${categories}" var="category">
                                        <option value="${category.id}">${category.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Ingredientes: *</label>
                                <c:forEach items="${ingredients}" var="ingredient" varStatus="i">
                                    <div class="form-check">
                                        <input required class="form-check-input" type="checkbox" value="${ingredient.id}" id="dishCreation_ingredient${i.getCount()}" name="ingredients">
                                        <label class="form-check-label" for="dishCreation_ingredient${i.getCount()}">
                                            ${ingredient.name}
                                        </label>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    <button onclick="dishCreation.confirm()" type="button" class="btn btn-primary" id="dishCreation_btnCreate" data-bs-dismiss="modal">Registrar</button>
                </div>
            </div>
        </div>
    </div>

    
    <div class="modal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Modal title</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p>Modal body text goes here.</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Save changes</button>
                </div>
            </div>
        </div>
    </div>

    <!-- FORMULARIO DE ELIMINACIÓN (Oculto) -->
    <form hidden id="dishDeletion_form" action="${c}/Platillos">
        <input type="text" name="action" value="delete">
        <input type="number" id="dishDeletion_inpId" name="id">
    </form>

    <!-- MODAL DE CONFIRMACIÓN -->
    <div class="modal fade" tabindex="-1" id="confirmation_modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <i class="fa-regular fa-circle-exclamation"></i>
                    <h5 id="confirmation_txtTitle"></h5>
                    <p id="confirmation_txtText"></p>
                </div>
                <div class="modal-footer">
                    <button id="confirmation_btnConfirm" type="submit" class="btn btn-danger">Aceptar</button>
                    <button id="confirmation_btnCancel" type="button" class="btn btn-secondary">Cancelar</button>
                </div>
            </div>
        </div>
    </div>

    <!-- TOAST DE NOTIFICACIONES -->
    <div class="toast-container position-fixed top-0 end-0 p-3">
        <div id="notification_toast" class="toast border-0 text-bg-success" role="alert">
            <div class="d-flex">
                <div class="toast-body">
                    <i class="fas fa-check me-2" id="notification_elIcon"></i><span id="notification_txtMessage"></span>
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
        </div>
    </div>
    
    <script src="${c}/assets/js/bootstrap.bundle.min.js"></script>
    <script src="${c}/assets/js/main.js"></script>
    <script>if (${message != null}) notification.show('${message}', ${success})</script>
</body>
</html>