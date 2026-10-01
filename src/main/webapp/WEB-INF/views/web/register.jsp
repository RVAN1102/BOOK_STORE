<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Đăng Ký Tài Khoản</title>
</head>
<body>
    <div class="row justify-content-center py-4">
        <div class="col-md-7 col-lg-6">
            <div class="card shadow-sm border-0 rounded-3">
                <div class="card-header bg-dark text-white text-center py-3 rounded-top-3">
                    <h4 class="mb-0 fw-bold">
                        <i class="bi bi-person-plus me-2 text-warning"></i>ĐĂNG KÝ TÀI KHOẢN
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

                    <form action="<c:url value='/register'/>" method="post" id="registerForm">
                        <div class="mb-3">
                            <label for="fullname" class="form-label fw-semibold">Họ và tên:</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="bi bi-person"></i></span>
                                <input type="text" class="form-control" id="fullname" name="fullname" 
                                       value="${fullname}" placeholder="Nguyễn Văn A" required>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label fw-semibold">Địa chỉ Email:</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                                <input type="email" class="form-control" id="email" name="email" 
                                       value="${email}" placeholder="example@gmail.com" required>
                            </div>
                            <div class="form-text">Mã OTP xác thực sẽ được gửi tới địa chỉ email này.</div>
                        </div>

                        <div class="mb-3">
                            <label for="phone" class="form-label fw-semibold">Số điện thoại:</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="bi bi-telephone"></i></span>
                                <input type="tel" class="form-control" id="phone" name="phone" 
                                       value="${phone}" placeholder="0912345678" pattern="[0-9]{9,11}">
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="passwd" class="form-label fw-semibold">Mật khẩu:</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="bi bi-lock"></i></span>
                                <input type="password" class="form-control" id="passwd" name="passwd" 
                                       placeholder="Tối thiểu 6 ký tự" required minlength="6">
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="confirm_passwd" class="form-label fw-semibold">Xác nhận mật khẩu:</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="bi bi-lock-fill"></i></span>
                                <input type="password" class="form-control" id="confirm_passwd" name="confirm_passwd" 
                                       placeholder="Nhập lại mật khẩu" required minlength="6">
                            </div>
                            <div id="passwordError" class="text-danger small mt-1" style="display: none;">
                                Mật khẩu xác nhận không trùng khớp!
                            </div>
                        </div>

                        <div class="d-grid mt-4">
                            <button type="submit" class="btn btn-warning fw-bold py-2">
                                <i class="bi bi-send me-1"></i>Tiếp Tục & Nhận Mã OTP
                            </button>
                        </div>
                    </form>

                    <div class="text-center mt-3 pt-3 border-top">
                        <p class="text-muted mb-0">
                            Đã có tài khoản? 
                            <a href="<c:url value='/login'/>" class="text-primary fw-semibold text-decoration-none">
                                Đăng nhập ngay
                            </a>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.getElementById("registerForm").addEventListener("submit", function(e) {
            const pass = document.getElementById("passwd").value;
            const confirmPass = document.getElementById("confirm_passwd").value;
            const errorDiv = document.getElementById("passwordError");
            if (pass !== confirmPass) {
                e.preventDefault();
                errorDiv.style.display = "block";
            } else {
                errorDiv.style.display = "none";
            }
        });
    </script>
</body>
</html>
