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
        </div>
</nav>
<body>
<div class="container text-center mt-5">
    <div class="row align-items-center">
        <div class="col">
            <div class="d-flex align-items-center">
                <h2>Welcome <c:out value="${producer.getName()}" />!</h2>
            </div>
            <div class="d-flex align-items-center">
                <a href="<c:url value="/producers/update/"/>" class="btn btn-danger">Update Information</a>
            </div>
            <div class="d-flex align-items-center">
                <a href="<c:url value="/producers/logout/"/>" class="btn btn-danger">Logout</a>
            </div>
        </div>
        <div class="col">
            <h2>Products</h2>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Name of Product</th>
                    <th scope="col">Price</th>
                    <th scope="col">Dimension</th>
                    <th scope="col">Quality</th>
                    <th scope="col">Quantity</th>
                    <th scope="col">Category</th>
                    <th scope="col">Edit</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${products}" var="product">
                    <tr>
                        <td>${product.getProduct_name()}</td>
                        <td>${product.getPrice()}</td>
                        <td>${product.getDimension()}</td>
                        <td>${product.getQuality()}</td>
                        <td>${product.getQuantity()}</td>
                        <td>${product.getCategory()}</td>
                        <td>
                            <a class="btn btn-primary btn-lg btn-block"
                               href="<c:url value='/producers/updateProduct/'>
            <c:param name='product_id' value='${product.getProduct_id()}'/>
        </c:url>" >Edit Product</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="d-flex align-items-center">
                <a href="<c:url value="/producers/addProduct/"/>" class="btn btn-primary">Add New Product</a>
            </div>
        </div>
    </div>
</div>
<script src="/js/bootstrap.bundle.min.js"></script>
<script src="js/myJS/checkout.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>


</body>
</html>