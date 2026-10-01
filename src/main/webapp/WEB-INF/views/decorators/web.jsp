<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><sitemesh:write property="title"/> - Nhà Sách BookStore</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <style>
        body {
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            background-color: #f8f9fa;
        }
        .main-content {
            flex: 1 0 auto;
        }
        .navbar-brand {
            font-weight: 700;
            letter-spacing: 0.5px;
        }
        .footer {
            flex-shrink: 0;
            background-color: #ffffff;
            border-top: 1px solid #dee2e6;
        }
    </style>
    <sitemesh:write property="head"/>
</head>
<body>
    <!-- HEADER -->
    <header>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
            <div class="container">
                <a class="navbar-brand text-warning" href="<c:url value='/home'/>">
                    <i class="bi bi-book-half me-2"></i>BOOKSTORE
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent" 
                        aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                
                <div class="collapse navbar-collapse" id="navbarContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" href="<c:url value='/home'/>">
                                <i class="bi bi-house-door me-1"></i>Trang Chủ
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/home'/>">
                                <i class="bi bi-grid me-1"></i>Sản phẩm
                            </a>
                        </li>
                        <!-- NÚT TRANG QUẢN TRỊ: Chỉ hiển thị khi sessionScope.account != null && account.isAdmin == true -->
                        <c:if test="${sessionScope.account != null && (sessionScope.account.isAdmin == true || sessionScope.account.admin == true)}">
                            <li class="nav-item">
                                <a class="nav-link text-warning fw-semibold" href="<c:url value='/admin/books'/>">
                                    <i class="bi bi-shield-lock me-1"></i>Trang quản trị
                                </a>
                            </li>
                        </c:if>
                    </ul>

                    <ul class="navbar-nav ms-auto align-items-center">
                        <!-- NÚT GIỎ HÀNG -->
                        <li class="nav-item me-2">
                            <a href="<c:url value='/cart'/>" class="btn btn-outline-light btn-sm position-relative">
                                <i class="bi bi-cart3 me-1"></i>Giỏ hàng
                                <span class="badge bg-danger rounded-pill ms-1">
                                    ${sessionScope.cart != null ? sessionScope.cart.size() : 0}
                                </span>
                            </a>
                        </li>

                        <c:choose>
                            <c:when test="${sessionScope.account != null}">
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle text-light" href="#" role="button" data-bs-toggle="dropdown">
                                        <i class="bi bi-person-circle me-1 text-warning"></i>Xin chào, ${sessionScope.account.fullname}
                                    </a>
                                    <ul class="dropdown-menu dropdown-menu-end shadow">
                                        <li>
                                            <a class="dropdown-item" href="<c:url value='/orders'/>">
                                                <i class="bi bi-clock-history me-2 text-primary"></i>Đơn mua của tôi
                                            </a>
                                        </li>
                                        <li><hr class="dropdown-divider"></li>
                                        <c:if test="${sessionScope.account.isAdmin == true || sessionScope.account.admin == true}">
                                            <li>
                                                <a class="dropdown-item" href="<c:url value='/admin/books'/>">
                                                    <i class="bi bi-speedometer2 me-2 text-warning"></i>Khu vực Quản trị
                                                </a>
                                            </li>
                                            <li><hr class="dropdown-divider"></li>
                                        </c:if>
                                        <li>
                                            <a class="dropdown-item text-danger" href="<c:url value='/logout'/>">
                                                <i class="bi bi-box-arrow-right me-2"></i>Đăng xuất
                                            </a>
                                        </li>
                                    </ul>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="nav-item me-2">
                                    <a class="btn btn-outline-warning btn-sm px-3" href="<c:url value='/register'/>">
                                        <i class="bi bi-person-plus me-1"></i>Đăng ký
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="btn btn-outline-light btn-sm px-3" href="<c:url value='/login'/>">
                                        <i class="bi bi-box-arrow-in-right me-1"></i>Đăng nhập
                                    </a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </nav>
    </header>

    <!-- BODY CHÍNH CỦA TRANG CON -->
    <main class="main-content py-4">
        <div class="container">
            <sitemesh:write property="body"/>
        </div>
    </main>

    <!-- FOOTER CỐ ĐỊNH THEO YÊU CẦU ĐỀ THI -->
    <footer class="footer py-3 text-center">
        <div class="container">
            <p class="mb-0 text-dark fw-bold">
                Họ tên: Ngô Bá Vạn | MSSV: 24162144 | Mã đề: Đề số 02
            </p>
        </div>
    </footer>

    <!-- Bootstrap 5 JS Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
