<%--
  Created by IntelliJ IDEA.
  User: cemre
  Date: 2023-06-03
  Time: 5:06 p.m.
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
    <a class="navbar-brand" href="<c:url value="/homepage/"/>">
      <img src="${pageContext.request.contextPath}/media/OCS.svg" style="max-height: 50px;">
    </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
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
          <svg style="color: black" xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-envelope-fill" viewBox="0 0 16 16">
            <path d="M.05 3.555A2 2 0 0 1 2 2h12a2 2 0 0 1 1.95 1.555L8 8.414.05 3.555ZM0 4.697v7.104l5.803-3.558L0 4.697ZM6.761 8.83l-6.57 4.027A2 2 0 0 0 2 14h12a2 2 0 0 0 1.808-1.144l-6.57-4.027L8 9.586l-1.239-.757Zm3.436-.586L16 11.801V4.697l-5.803 3.546Z"/>
          </svg>
        </a>
        <div class="p-2"></div>
        <a href="">
          <svg style="color: black" xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-cart4" viewBox="0 0 16 16">
            <path d="M0 2.5A.5.5 0 0 1 .5 2H2a.5.5 0 0 1 .485.379L2.89 4H14.5a.5.5 0 0 1 .485.621l-1.5 6A.5.5 0 0 1 13 11H4a.5.5 0 0 1-.485-.379L1.61 3H.5a.5.5 0 0 1-.5-.5zm1.732.5H14.11l1.223 4.892H3.12L2.732 3zm3.774 9a1.5 1.5 0 0 1-1.33-.8L4.055 11H13.5l-.548 2.2a.5.5 0 0 1-.49.3H5.506zm-.16-5a.5.5 0 0 1 .133 1l-.133.001zm6 0a.5.5 0 0 1 .133-1l.133-.001zm-7-7a1.5 1.5 0 0 1 1.415 1h6.17a1.5 1.5 0 0 1 1.415-1H4.386z"/>
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
<div class="container">
  <form method = "POST" id = "customer-forgetpassword-form" action="<c:url value="/customers/forgetPassword/"/>" class="row g-3 position-absolute top-50 start-50 translate-middle">
    <div class="form-floating mb-3 text-center">
      <h3>Customer Reset Password</h3>
    </div>
    <div class="col-12 form-floating mb-3">
      <input type="email" class="form-control" id="email" name = "email" placeholder="name@example.com">
      <label for="email">Email address</label>
    </div>
    <div class="col-12 text-center">
      <button type="submit" class="btn btn-primary">Get new password</button>
    </div>
    <c:if test="${not empty success}">
      <div class="col-12 text-center">
        <p id="success" class="text-success">${success}</p>
        <a href = "<c:url value="/customers/login/"/>" class="ms-2">Click here for login</a>
      </div>
    </c:if>
<c:if test="${not empty error}">
      <div class="col-12 text-center">
        <p id="error" class="text-danger">${error}</p>
        <a href = "<c:url value="/customers/register/"/>" class="ms-2">Click here for register</a>
      </div>
</c:if>
  </form>
</div>
<script src="/js/bootstrap.bundle.min.js"></script>
<script src="js/myJS/checkout.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
</body>
</html>
