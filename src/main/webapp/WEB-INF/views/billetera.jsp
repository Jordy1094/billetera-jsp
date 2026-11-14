<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Mi Billetera Digital</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-dark">
    <div class="container">
        <span class="navbar-brand fw-semibold">Mi Billetera Digital</span>
        <span class="badge rounded-pill text-bg-secondary">JSP + Servlet</span>
    </div>
</nav>

<main class="container py-4">

    <div class="row g-3 mb-4">
        <div class="col-md-5">
            <div class="card h-100 shadow-sm">
                <div class="card-body">
                    <p class="text-uppercase text-muted small mb-1">Saldo disponible</p>
                    <p class="display-6 fw-bold text-success mb-1" id="saldo">S/ ${billetera.saldoTexto}</p>
                    <p class="text-muted small mb-0">
                        Después de ${billetera.cantidadMovimientos}
                        ${billetera.cantidadMovimientos == 1 ? 'movimiento válido' : 'movimientos válidos'}
                    </p>
                </div>
            </div>
        </div>
        <div class="col-md-7">
            <div class="card h-100 border-primary-subtle bg-primary-subtle">
                <div class="card-body">
                    <span class="badge text-bg-primary mb-2">GET / Servlet</span>
                    <p class="mb-1">Obtiene el saldo y el historial de la sesión HTTP.</p>
                    <p class="text-muted small mb-0">Envía los datos a la vista JSP para mostrarlos.</p>
                </div>
            </div>
        </div>
    </div>

    <h2 class="h5 fw-semibold mb-2">Movimientos recientes</h2>
    <div class="row g-3 mb-4">
        <div class="${empty error ? 'col-12' : 'col-lg-8'}">
            <div class="card shadow-sm">
                <div class="table-responsive">
                    <table class="table align-middle mb-0" id="tabla-movimientos">
                        <thead class="table-dark">
                        <tr>
                            <th>Tipo</th>
                            <th>Monto (S/)</th>
                            <th>Orden</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty billetera.movimientos}">
                            <tr>
                                <td colspan="3" class="text-muted text-center" id="historial-vacio">Aún no hay movimientos</td>
                            </tr>
                        </c:if>
                        <c:forEach var="mov" items="${billetera.movimientos}" varStatus="estado">
                            <tr>
                                <td>
                                    <span class="badge rounded-pill px-4 ${mov.recarga ? 'text-bg-success' : 'text-bg-danger'}">${mov.tipo.etiqueta}</span>
                                </td>
                                <td class="fw-semibold ${mov.recarga ? 'text-success' : 'text-danger'}">
                                    ${mov.recarga ? '+' : '-'} ${mov.montoTexto}
                                </td>
                                <td class="text-muted small">${estado.first ? 'Más reciente' : 'Anterior'}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <c:if test="${not empty error}">
            <div class="col-lg-4">
                <div class="card h-100 border-danger-subtle" id="mensaje-error">
                    <div class="card-body">
                        <h3 class="h6 text-danger fw-semibold">Validación</h3>
                        <c:if test="${error == 'Saldo insuficiente'}">
                            <p class="small mb-1">Gasto solicitado: S/ <c:out value="${montoSolicitado}"/></p>
                            <p class="small mb-2">Saldo disponible: S/ ${billetera.saldoTexto}</p>
                        </c:if>
                        <div class="alert alert-danger py-2 mb-0 small fw-semibold" role="alert">
                            <c:out value="${error}"/>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
    </div>

    <div class="card shadow-sm">
        <div class="card-body">
            <h2 class="h6 fw-semibold mb-2">Nuevo movimiento</h2>
            <form method="post" action="${pageContext.request.contextPath}/billetera" class="row g-3 align-items-end" novalidate>
                <div class="col-md-4">
                    <label for="tipo" class="form-label small text-muted mb-1">Tipo</label>
                    <select id="tipo" name="tipo" class="form-select">
                        <option value="RECARGA" ${tipoIngresado == 'GASTO' ? '' : 'selected'}>Recarga</option>
                        <option value="GASTO" ${tipoIngresado == 'GASTO' ? 'selected' : ''}>Gasto</option>
                    </select>
                </div>
                <div class="col-md-4">
                    <label for="monto" class="form-label small text-muted mb-1">Monto (S/)</label>
                    <input type="text" id="monto" name="monto" placeholder="0.00"
                           class="form-control ${not empty error ? 'is-invalid' : ''}"
                           value="<c:out value='${montoIngresado}'/>">
                </div>
                <div class="col-md-4 d-grid">
                    <button type="submit" class="btn btn-primary">Registrar movimiento</button>
                </div>
            </form>
        </div>
    </div>
</main>

</body>
</html>
