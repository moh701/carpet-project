<%--
  Created by IntelliJ IDEA.
  User: cemre
  Date: 2023-06-05
  Time: 9:51 p.m.
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
  <form method="POST" action="<c:url value="/producers/addProduct/"/>" class="row g-3 position-absolute top-50 start-50 translate-middle needs-validation" novalidate>
    <div class="form-floating mb-3 text-center">
      <h3>Add Product</h3>
    </div>
    <div class="col-md-6 mb-3">
      <label for="name">Name:</label>
      <input type="text" id="name" name="name" required><br><br>
    </div>
    <div class="col-md-6 mb-3">
      <label for="quantity">Quantity:</label>
      <input type="number" id="quantity" name="quantity" required><br><br>
    </div>
    <div class="col-md-6 mb-3">
      <label for="no_color">Has Color?:</label>
      <select id="no_color" name="no_color" required>
        <option value="0">No</option>
        <option value="1">Yes</option>
      </select><br><br>
    </div>

    <div class="col-md-6 mb-3">
      <label for="dimension">Dimension:</label>
      <input type="text" id="dimension" name="dimension" required><br><br>
    </div>
    <div class="col-md-6 mb-3">
      <label for="material">Material:</label>
      <select id="material" name="material" required>
        <option value="Woolen">Woolen</option>
        <option value="Fiber">Fiber</option>
        <option value="Linen">Linen</option>
        <option value="Silk">Silk</option>
      </select><br><br>
    </div>

    <div class="col-md-6 mb-3">
      <label for="quality">Quality:</label>
      <select id="quality" name="quality" required>
        <option value="Low">Low</option>
        <option value="Medium">Medium</option>
        <option value="High">High</option>
      </select><br><br>
    </div>

    <div class="col-md-6 mb-3">
      <label for="category">Category:</label>
      <select id="category" name="category" required>
        <option value="Artificial">Artificial</option>
        <option value="Handicrafts">Handicrafts</option>
        <option value="Other">Other</option>
      </select><br><br>
    </div>

    <div class="col-md-6 mb-3">
      <label for="price">Price:</label>
      <input type="number" id="price" name="price" required><br><br>
    </div>
    <div class="col-12">
      <input type="submit" value="Add">
    </div>
  </form>
</div>
<script>
  window.addEventListener('DOMContentLoaded', function() {
    var form = document.querySelector('form');
    var noColorSelect = document.getElementById('no_color');

    form.addEventListener('submit', function(event) {
      if (noColorSelect.value === '0') {
        event.preventDefault();
        alert('You cannot add a carpet without a color!');
      }
    });
  });
</script>
<script src="/js/bootstrap.bundle.min.js"></script>
<script src="js/myJS/checkout.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
</body>
</html>

