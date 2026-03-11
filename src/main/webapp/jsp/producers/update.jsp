<%--
  Created by IntelliJ IDEA.
  User: cemre
  Date: 2023-04-28
  Time: 9:23 a.m.
  To change this template use File | Settings | File Templates.
--%>
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
        <a class="navbar-brand" href="<c:url value="/producers/dashboard/"/>">
            <img src="${pageContext.request.contextPath}/media/OCS.svg" style="max-height: 50px;">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
    </div>
</nav>
<body>
<c:if test="${not empty error}">
    <p style="color: red">${error}</p>
</c:if>
<div class="container">
    <form method="POST" action="<c:url value="/producers/update/"/>" class="row g-3 position-absolute top-50 start-50 translate-middle needs-validation" novalidate>
        <div class="form-floating mb-3 text-center">
            <h3>Producer Update Information</h3>
        </div>
        <div class="col-md-6 mb-3">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="${producer.getName()}" required><br><br>
        </div>
        <div class="col-md-6 mb-3">
            <label for="surname">Surname:</label>
            <input type="text" id="surname" name="surname" value="${producer.getSurname()}" required><br><br>
        </div>
        <div class="col-md-6 mb-3">
            <label for="mobileNumber">Mobile Number:</label>
            <input type="tel" id="mobileNumber" name="mobileNumber" value="${producer.getMobile_number()}" required><br><br>
        </div>
        <div class="col-md-6 mb-3">
            <label for="bankAccount">Bank Account:</label>
            <input type="text" id="bankAccount" name="bankAccount" value="${producer.getBank_account()}" required><br><br>
        </div>
        <div class="col-md-6 mb-3">
            <label for="address">Address:</label>
            <input type="text" id="address" name="address" value="${producer.getAddress()}" required><br><br>
        </div>
        <div class="col-md-6 mb-3">
            <label for="brand">Brand:</label>
            <input type="text" id="brand" name="brand" value="${producer.getBrand()}" required><br><br>
        </div>
        <div class = "col-12">
            <input type = "submit" value = "Update">
        </div>
    </form>
</div>
<script src="/js/bootstrap.bundle.min.js"></script>
<script src="js/myJS/checkout.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>

</body>
</html>