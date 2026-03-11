<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Online Carpet Store</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .container {
            margin-top: 50px;
        }
        .card {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="text-center mb-5">Welcome to the Producer Dashboard</h1>

        <div class="row">
            <div class="col-lg-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Producer Information</h5>
                        <p class="card-text">Name: <%= session.getAttribute("producerName") %></p>
                        <p class="card-text">Email: <%= session.getAttribute("producerEmail") %></p>
                        <p class="card-text">Company: <%= session.getAttribute("producerCompany") %></p>
                    </div>
                </div>
            </div>
            <div class="col-lg-8">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Upload or Edit Products</h5>
                        <form action="UploadProductServlet" method="post">
                            <!-- Include fields for product information -->
                            <button type="submit" class="btn btn-primary">Upload/Edit Product</button>
                        </form>
                    </div>
                </div>

                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Upload Advertising</h5>
                        <form action="UploadAdvertisingServlet" method="post">
                            <!-- Include fields for video and photo advertising -->
                            <button type="submit" class="btn btn-primary">Upload Advertising</button>
                        </form>
                    </div>
                </div>

                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Value or Price Products</h5>
                        <form action="ValueProductServlet" method="post">
                            <!-- Include fields for product valuation -->
                            <button type="submit" class="btn btn-primary">Value/Price Product</button>
                        </form>
                    </div>
                </div>

                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Contact Customers</h5>
                        <form action="ContactCustomerServlet" method="post">
                            <!-- Include fields for contacting customers -->
                            <button type="submit" class="btn btn-primary">Contact Customers</button>
                        </form>
                    </div>
                </div>

                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Answer Comments</h5>
                        <form action="AnswerCommentServlet" method="post">
                            <!-- Include fields for answering comments -->
                            <button type="submit" class="btn btn-primary">Answer Comments</button>
                        </form>
                    </div>
                </div>

                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Manage Products</h5>
                        <form action="ManageProductsServlet" method="post">
                            <button type="submit" class="btn btn-primary">Manage Products</button>
                        </form>
                    </div>
                </div>

                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Logout</h5>
                        <form action="LogoutServlet" method="post">
                            <button type="submit" class="btn btn-primary">Logout</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
