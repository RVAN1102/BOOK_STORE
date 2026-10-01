<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><sitemesh:write property="title"/> - Quản Trị Hệ Thống</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <style>
        body {
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            background-color: #f4f6f9;
        }
        .admin-main {
            flex: 1 0 auto;
        }
        .admin-footer {
            flex-shrink: 0;
            background-color: #ffffff;
            border-top: 1px solid #dee2e6;
        }
    </style>
    <sitemesh:write property="head"/>
</head>
<body>
    <!-- ADMIN HEADER -->
    <header>
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
            <div class="container-fluid px-4">
                <a class="navbar-brand fw-bold text-white" href="<c:url value='/admin/books'/>">
                    <i class="bi bi-shield-shaded me-2"></i>BOOKSTORE ADMIN
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#adminNavbar">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="adminNavbar">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link text-white fw-semibold" href="<c:url value='/admin/books'/>">
                                <i class="bi bi-book me-1"></i>Quản lý Sách
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-white-50" href="<c:url value='/home'/>">
                                <i class="bi bi-arrow-left-circle me-1"></i>Về Trang Khách
                            </a>
                        </li>
                    </ul>

                    <ul class="navbar-nav ms-auto align-items-center">
                        <li class="nav-item text-white me-3">
                            <i class="bi bi-person-badge me-1"></i>
                            <strong>${sessionScope.account != null ? sessionScope.account.fullname : 'Admin'}</strong>
                            <span class="badge bg-danger ms-1">Admin</span>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-outline-light btn-sm px-3" href="<c:url value='/logout'/>">
                                <i class="bi bi-box-arrow-right me-1"></i>Đăng xuất
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </header>

    <!-- ADMIN BODY -->
    <main class="admin-main py-4">
        <div class="container-fluid px-4">
            <sitemesh:write property="body"/>
        </div>
    </main>

    <!-- ADMIN FOOTER CỐ ĐỊNH -->
    <footer class="admin-footer py-3 text-center">
        <div class="container-fluid">
            <p class="mb-0 text-dark fw-bold">
                Họ tên: Ngô Bá Vạn | MSSV: 24162144 | Mã đề: Đề số 02
            </p>
        </div>
    </footer>

    <!-- Bootstrap 5 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
