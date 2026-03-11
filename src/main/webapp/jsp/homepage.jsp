<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Online Carpet Store</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
          crossorigin="anonymous">
</head>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="<c:url value="/homepage/"/>">
            <img src="${pageContext.request.contextPath}/media/OCS.svg" style="max-height: 50px;">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/homepage/login/"/>">Login</a>
                </li>
            </ul>
            <div class="d-flex" role="search">
                <a href="">
                    <svg style="color: black" xmlns="http://www.w3.org/2000/svg" width="30" height="30"
                         fill="currentColor" class="bi bi-envelope-fill" viewBox="0 0 16 16">
                        <path d="M.05 3.555A2 2 0 0 1 2 2h12a2 2 0 0 1 1.95 1.555L8 8.414.05 3.555ZM0 4.697v7.104l5.803-3.558L0 4.697ZM6.761 8.83l-6.57 4.027A2 2 0 0 0 2 14h12a2 2 0 0 0 1.808-1.144l-6.57-4.027L8 9.586l-1.239-.757Zm3.436-.586L16 11.801V4.697l-5.803 3.546Z"/>
                    </svg>
                </a>
                <div class="p-2"></div>
                <a href="<c:url value="/cart/view/"/>">
                    <svg style="color: black" xmlns="http://www.w3.org/2000/svg" width="30" height="30"
                         fill="currentColor" class="bi bi-cart4" viewBox="0 0 16 16">
                        <path d="M0 2.5A.5.5 0 0 1 .5 2H2a.5.5 0 0 1 .485.379L2.89 4H14.5a.5.5 0 0 1 .485.621l-1.5 6A.5.5 0 0 1 13 11H4a.5.5 0 0 1-.485-.379L1.61 3H.5a.5.5 0 0 1-.5-.5zM3.14 5l.5 2H5V5H3.14zM6 5v2h2V5H6zm3 0v2h2V5H9zm3 0v2h1.36l.5-2H12zm1.11 3H12v2h.61l.5-2zM11 8H9v2h2V8zM8 8H6v2h2V8zM5 8H3.89l.5 2H5V8zm0 5a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0zm9-1a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0z"/>
                    </svg>
                </a>
            </div>
            <form class="d-flex">
                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-dark" type="submit">Search</button>
            </form>
        </div>
    </div>
</nav>
<body>
<style>
    .indexImages {
        width: 100%;
        aspect-ratio: 3/2;
        object-fit: contain;
    }
</style>

<div class="container text-center pt-5">
    <div class="text-start mb-3">
        <h3>Products</h3>
    </div>
    <%-- Retrieve the list of products from the session scope --%>
    <c:set var="allProducts" value="${sessionScope.allProducts}"/>
    <%-- Display the list of found products --%>
    <div class="container">
        <c:if test='${not empty allProducts}'>
            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-2 row-cols-lg-3 g-4">
                <c:forEach var="product" items="${allProducts}">
                    <div class="col">
                        <div class="card text-bg-light">
                            <img src="${product.getPicture()}" class="card-img indexImages"/><br/>
                            <div class="my-card-img-overlay">
                                <h5 class="card-title"><c:out value="${product.getProduct_name()}"/><br/></h5>
                                <p class="card-text"><c:out value="${product.getPrice()}"/>$</p>
                                <a href="<c:url value='/product-view/view'>
            <c:param name='product_id' value='${product.getProduct_id()}'/>
        </c:url>" class="stretched-link"></a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
        <nav>
            <ul class="pagination justify-content-center">
                <li class="page-item<c:if test="${currentPage == 1}"> disabled</c:if>">
                    <a class="page-link">Previous</a>
                </li>
                <c:forEach var="page" begin="1" end="${totalPages}">
                    <li class="page-item <c:if test="${currentPage == page}">active</c:if>">
                        <a class="page-link" href="?page=${page}">
                            ${page}
                        </a>
                    </li>
                </c:forEach>
                <li class="page-item<c:if test="${currentPage == totalPages}"> disabled</c:if>">
                    <a class="page-link" href="?page=${currentPage + 1}">Next</a>
                </li>
            </ul>
        </nav>

        <%--
        <ul class="pagination">
            <li class="page-item<c:if test="${currentPage == 1}"> disabled</c:if>">
                <a class="page-link" href="?page=${currentPage - 1}">&lt; Previous</a>
            </li>
            <c:forEach var="page" begin="1" end="${totalPages}">
                <li class="page-item<c:if test="${currentPage == page}"> active</c:if>">
                    <a class="page-link" href="?page=${page}">${page}</a>
                </li>
            </c:forEach>
            <li class="page-item<c:if test="${currentPage == totalPages}"> disabled</c:if>">
                <a class="page-link" href="?page=${currentPage + 1}">Next &gt;</a>
            </li>
        </ul>
        --%>
    </div>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
            crossorigin="anonymous"></script>
</body>
</html>