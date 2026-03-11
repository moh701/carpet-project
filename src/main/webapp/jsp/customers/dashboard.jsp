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
<body>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="<c:url value="/homepage/"/>">
            <img src="${pageContext.request.contextPath}/media/OCS.svg" style="max-height: 50px;">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <div class="d-flex" role="search">
                <a href="">
                    <svg style="color: black" xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-envelope-fill" viewBox="0 0 16 16">
                        <path d="M.05 3.555A2 2 0 0 1 2 2h12a2 2 0 0 1 1.95 1.555L8 8.414.05 3.555ZM0 4.697v7.104l5.803-3.558L0 4.697ZM6.761 8.83l-6.57 4.027A2 2 0 0 0 2 14h12a2 2 0 0 0 1.808-1.144l-6.57-4.027L8 9.586l-1.239-.757Zm3.436-.586L16 11.801V4.697l-5.803 3.546Z"/>
                    </svg>
                </a>
            </div>
        </div>
        <a href="<c:url value="/cart/view/"/>">
            <svg style="color: black" xmlns="http://www.w3.org/2000/svg" width="30" height="30"
                 fill="currentColor" class="bi bi-cart4" viewBox="0 0 16 16">
                <path d="M0 2.5A.5.5 0 0 1 .5 2H2a.5.5 0 0 1 .485.379L2.89 4H14.5a.5.5 0 0 1 .485.621l-1.5 6A.5.5 0 0 1 13 11H4a.5.5 0 0 1-.485-.379L1.61 3H.5a.5.5 0 0 1-.5-.5zM3.14 5l.5 2H5V5H3.14zM6 5v2h2V5H6zm3 0v2h2V5H9zm3 0v2h1.36l.5-2H12zm1.11 3H12v2h.61l.5-2zM11 8H9v2h2V8zM8 8H6v2h2V8zM5 8H3.89l.5 2H5V8zm0 5a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0zm9-1a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0z"/>
            </svg>
        </a>
    </div>
</nav>
<div class="container text-center mt-5">
    <div class="row align-items-center">
        <div class="col">
            <div class="d-flex align-items-center">
                <h2>Welcome <c:out value="${customer.getName()}" />!</h2>
            </div>
            <div class="d-flex align-items-center">
                <a href="<c:url value="/customers/update/"/>" class="btn btn-danger">Update Information</a>
            </div>
            <div class="d-flex align-items-center">
                <a href="<c:url value="/customers/logout/"/>" class="btn btn-danger">Logout</a>
            </div>
        </div>
        <div class="col">

        </div>
    </div>
    <div class="row">
        <div class="col">
            <h2>Purchase History</h2>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Product Name</th>
                    <th scope="col">Price</th>
                    <th scope="col">Date Purchased</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${purchaseHistory}" var="purchase">
                    <tr>
                        <td>${purchase.getProduct().getProduct_name()}</td>
                        <td>${purchase.getProduct().getPrice()}</td>
                        <td>${purchase.getPurchaseDate()}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script src="/js/bootstrap.bundle.min.js"></script>
</body>
</html>
