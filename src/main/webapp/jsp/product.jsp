<%--
  Created by IntelliJ IDEA.
  User: cemre
  Date: 2023-06-03
  Time: 12:17 p.m.
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
<c:set var="product" value="${product}"/>
<c:if test="${not empty product}">
    <div class="container">
        <div class="text-center">
            <div class="row">
                <div class="col">
                    <img src="${product.getPicture()}" class="img-fluid" alt="Product Image" class="rounded"
                         alt="placeholder">
                </div>
                <div class="col">
                    <div class="row">
                        <div class="col">
                            <h3>
                                <c:out value="${product.getProduct_name()}"/>
                            </h3>
                        </div>
                        <div class="col">
                            <h3>
                                $<c:out value="${product.getPrice()}"/>
                            </h3>
                        </div>
                        <hr>
                        <form method="post" action="<c:url value="/product-view/addtocart/"/>">

                            <div class="mb-3 text-start">
                                <label for="quantity" class="form-label small">Quantity</label>
                                <select id="quantity" name = "quantity" class="form-select small">
                                    <option>1</option>
                                    <option>2</option>
                                    <option>3</option>
                                    <option>4</option>
                                </select>
                            </div>
                            <hr>
                            <div class="container-sm mb-3">
                                <div class="row d-flex justify-content-evenly">
                                    <div class="col">
                                        <input type="hidden" id="product_id" name="product_id"
                                               value="${product.getProduct_id()}">
                                        <input type="hidden" name="quantityInput" id="quantityInput" value = "1">
                                        <button type="submit" onclick="setQuantity()" class="btn btn-primary btn-lg">Add to Cart</button>
                                    </div>
                                </div>
                            </div>
                        </form>



                            <%--                                    <button class="btn btn-primary">Add to Cart</button>--%>
                        <div class="col">
                            <button type="button" class="btn btn-primary status-maintenance"
                                    role="button"
                                    data-mode="add">Make an Offer
                            </button>
                        </div>
                        <div class="popover-container"></div>
                        <section>
                            <div id="status-form" class="card col-md-6 offset-md-1 d-none mb-2">
                                <div class="card-header">
                                    <h5 id="statusHeader">
                                        <c:out value="${product.getProduct_name()}"/>
                                    </h5>
                                </div>
                                <div class="card-body">
                                    <input id='popoverStatusID' type='hidden' disabled>
                                    <input id='popoverMode' type='hidden' value='add' disabled>
                                    <div class="mb-2 ms-2">
                                        <input id='popoverStatusEdit' type='text' maxlength='20'
                                               class='form-control'
                                               value='' placeholder="Offer">
                                    </div>
                                </div>
                                <div class="card-footer text-muted">
                                    <div class="mt-1 ms-2 mb-2">
                                        <button id="close-status-form" class='btn btn-default btn-sm'
                                                type='button'>
                                            Close
                                        </button>
                                        <button id="submit-status-form"
                                                class='btn btn-primary btn-sm float-end'>
                                            Submit
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                </div>
                <hr>
                <div class="container text-start">
                    <p> Quality: <c:out value="${product.getQuality()}"/></p>
                    <p> Category: <c:out value="${product.getCategory()}"/></p>
                    <p> Material: <c:out value="${product.getMaterial()}"/></p>
                    <p> Dimension: <c:out value="${product.getDimension()}"/></p>
                </div>
            </div>
        </div>
    </div>
    </div>
    <div class="text-start fw-bold">
        <p>2 comments</p>
    </div>
    <div class="row mb-3">
        <div class="col-1 align-middle">
            <svg xmlns="http://www.w3.org/2000/svg" width="45" height="45" fill="currentColor"
                 class="bi bi-person-square" viewBox="0 0 16 16">
                <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                <path
                        d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm12 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1v-1c0-1-1-4-6-4s-6 3-6 4v1a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12z"/>
            </svg>
        </div>
        <div class="col-11 ps-3">
            <div class="">
                <div>
                    <textarea class="form-control" aria-label="With textarea"></textarea>
                </div>
            </div>
        </div>
    </div>
    <div class="row mb-3">
        <div class="col-1">
            <svg xmlns="http://www.w3.org/2000/svg" width="45" height="45" fill="currentColor"
                 class="bi bi-person-square" viewBox="0 0 16 16">
                <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                <path
                        d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm12 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1v-1c0-1-1-4-6-4s-6 3-6 4v1a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12z"/>
            </svg>
        </div>
        <div class="col-11 ps-3">
            <div class="ms-2">
                <div class="row fw-bold">Ole Thomasson</div>
                <div class="row">Lorem Ipsum Bla Bla Bla</div>
            </div>
        </div>
    </div>
    <div class="row mb-3">
        <div class="col-1">
            <svg xmlns="http://www.w3.org/2000/svg" width="45" height="45" fill="currentColor"
                 class="bi bi-person-square" viewBox="0 0 16 16">
                <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                <path
                        d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm12 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1v-1c0-1-1-4-6-4s-6 3-6 4v1a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12z"/>
            </svg>
        </div>
        <div class="col-11 ps-3">
            <div class="ms-2">
                <div class="row fw-bold">Kaja Ericson</div>
                <div class="row">Lorem Ipsum Bla Bla Bla</div>
            </div>
        </div>
    </div>
    </div>
</c:if>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-0n0tPPd34ig9r+gmi+lDnqkro3rSCFSRjy3ZURe3KgtTfRBshd69QnLZ01yD9A5m"
        crossorigin="anonymous"></script>
<script src="../js/Bootstrap/bootstrap.bundle.min.js"></script>
<script>
    function setQuantity() {
        var selectElement = document.getElementById("disabledSelect");
        var quantityInput = document.getElementById("quantityInput");
        quantityInput.value = selectElement.value;
    }
</script>
</body>
</html>
