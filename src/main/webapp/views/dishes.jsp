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
                    <button class="btn btn-success"><i class="fas fa-plus"></i>&nbsp;Registrar platillo</button>
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
                                                        <button class="btn btn-danger btn-sm"><i class="fas fa-trash-alt"></i></button>
                                                    </div>
                                                </c:if>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
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
    <script src="${c}/assets/js/bootstrap.bundle.min.js"></script>
    <script>
        for (dateContainer of document.querySelectorAll(".date-container")) dateContainer.innerText = new Date(dateContainer.innerText).toLocaleString(undefined, {dateStyle: 'long'})
    </script>
</body>
</html>