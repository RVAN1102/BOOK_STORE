<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Đăng Nhập Tài Khoản</title>
</head>
<body>
    <div class="row justify-content-center py-4">
        <div class="col-md-6 col-lg-5">
            <div class="card shadow-sm border-0 rounded-3">
                <div class="card-header bg-dark text-white text-center py-3 rounded-top-3">
                    <h4 class="mb-0 fw-bold">
                        <i class="bi bi-box-arrow-in-right me-2 text-warning"></i>ĐĂNG NHẬP
                    </h4>
                </div>
                <div class="card-body p-4">
                    <!-- Thông báo lỗi -->
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="bi bi-exclamation-triangle-fill me-2"></i>${error}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <!-- Thông báo thành công -->
                    <c:if test="${not empty success}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <i class="bi bi-check-circle-fill me-2"></i>${success}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <form action="<c:url value='/login'/>" method="post">
                        <div class="mb-3">
                            <label for="email" class="form-label fw-semibold">Địa chỉ Email:</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                                <input type="email" class="form-control" id="email" name="email" 
                                       value="${email}" placeholder="example@gmail.com" required autofocus>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label fw-semibold">Mật khẩu:</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="bi bi-lock"></i></span>
                                <input type="password" class="form-control" id="password" name="password" 
                                       placeholder="Nhập mật khẩu của bạn" required>
                            </div>
                        </div>

                        <div class="d-grid mt-4">
                            <button type="submit" class="btn btn-warning fw-bold py-2">
                                <i class="bi bi-box-arrow-in-right me-1"></i>Đăng Nhập
                            </button>
                        </div>
                    </form>

                    <div class="text-center mt-3 pt-3 border-top">
                        <p class="text-muted mb-0">
                            Chưa có tài khoản? 
                            <a href="<c:url value='/register'/>" class="text-primary fw-semibold text-decoration-none">
                                Đăng ký ngay
                            </a>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
