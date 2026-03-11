<<%--
  Created by IntelliJ IDEA.
  User: Mohammad
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
        <a href="">
          <svg style="color: black" xmlns="http://www.w3.org/2000/svg" width="30" height="30"
               fill="currentColor" class="bi bi-cart4" viewBox="0 0 16 16">
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
<body class="bg-body-tertiary">
<div class="container pt-4">
  <main>
    <div class="order-md-last">
      <div class="col-md-5 col-lg-4 order-md-last">
        <h4 class="d-flex justify-content-between align-items-center mb-3">
          <span class="text-primary">Your cart</span>
          <span class="badge bg-primary rounded-pill">${not empty cart ? cart.getItems().size() : 0}</span>
        </h4>
        <ul class="list-group mb-3">
            <c:set var="totalPrice" value="0" />
            <c:forEach var="entry" items="${cart.getItems()}">
              <c:set var="price" value="${entry.key.getPrice() * entry.value}" />
              <c:set var="totalPrice" value="${totalPrice + price}" />
            </c:forEach>
          <li class="list-group-item d-flex justify-content-between">
            <span>Total (USD)</span>
            <strong>$${totalPrice}</strong>
          </li>
        </ul>
      </div>
      <div class="col-md-7 col-lg-8">
        <h4 class="mb-3">Billing address</h4>
        <form class="needs-validation" novalidate>
          <div class="row g-3">
            <div class="col-sm-6">
              <label for="firstName" class="form-label">First name</label>
              <input type="text" id="firstName" class="form-control" value="${customer.getName()}" required><br><br>
              <div class="invalid-feedback">
                Valid first name is required.
              </div>
            </div>

            <div class="col-sm-6">
              <label for="lastName" class="form-label">Last name</label>
              <input type="text" id="lastName" class="form-control" value="${customer.getSurname()}" required><br><br>
              <div class="invalid-feedback">
                Valid last name is required.
              </div>
            </div>

            <div class="col-12">
              <label for="phonenumber" class="form-label">Phone Number</label>
              <input type="text" id="phoneNumber" class="form-control" value="${customer.getMobile_number()}" required><br><br>
              <div class="invalid-feedback">
                Valid phone number is required.
              </div>
            </div>

            <div class="col-12">
              <label for="email" class="form-label">Email <span
                      class="text-body-secondary">(Optional)</span></label>
              <input type="text" id="email" class="form-control" value="${customer.getEmail_address()}" required><br><br>
              <div class="invalid-feedback">
                Please enter a valid email address for shipping updates.
              </div>
            </div>

            <form method="post" action="<c:url value="/cart/payment/"/>">
            <div class="col-12">
              <label for="address" class="form-label">Address</label>
              <input type="text" id="address" class="form-control" value="${customer.getAddress()}" required><br><br>
              <div class="invalid-feedback">
                Please enter your shipping address.
              </div>
            </div>
            </form>
          </div>
          <hr class="my-4">

          <div class="form-check">
            <input type="checkbox" class="form-check-input" id="save-info">
            <label class="form-check-label" for="save-info">Save this information for next time</label>
          </div>

          <hr class="my-4">
            <a class="btn btn-primary" href="<c:url value="/cart/payment/"/>" role="button">Continue to Checkout</a>
        </form>
      </div>
    </div>
  </main>
</div>


<div id="customer-content"></div>
<script src="../../HTMLfrontend/js/bootstrap.bundle.min.js"></script>
<script src="../../HTMLfrontend/js/myJS/checkout.js"></script>
</body>
</html>
