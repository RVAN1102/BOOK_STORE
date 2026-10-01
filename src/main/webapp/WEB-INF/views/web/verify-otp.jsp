<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Xác Thực Mã OTP</title>
</head>
<body>
    <div class="row justify-content-center py-4">
        <div class="col-md-6 col-lg-5">
            <div class="card shadow-sm border-0 rounded-3 text-center">
                <div class="card-header bg-dark text-white py-3 rounded-top-3">
                    <h4 class="mb-0 fw-bold">
                        <i class="bi bi-shield-check me-2 text-warning"></i>KÍCH HOẠT TÀI KHOẢN
                    </h4>
                </div>
                <div class="card-body p-4">
                    <div class="my-3">
                        <i class="bi bi-envelope-check text-warning" style="font-size: 3rem;"></i>
                    </div>

                    <p class="text-secondary mb-2">
                        Mã OTP xác thực đã được gửi tới email:
                    </p>
                    <h6 class="text-primary fw-bold mb-3">${sessionScope.tempUser.email}</h6>

                    <!-- Thông báo lỗi nếu OTP sai -->
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="bi bi-exclamation-triangle-fill me-2"></i>${error}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <div class="alert alert-info small py-2 text-start mb-4">
                        <i class="bi bi-info-circle me-1"></i>
                        <strong>Lưu ý:</strong> Nếu hệ thống mạng phòng thi chặn gửi email, bạn có thể xem mã OTP được in ra trực tiếp tại <strong>Console của STS/Eclipse</strong>.
                    </div>

                    <form action="<c:url value='/verify-otp'/>" method="post">
                        <div class="mb-4">
                            <label for="otp" class="form-label fw-semibold">Nhập mã OTP 6 chữ số:</label>
                            <input type="text" class="form-control text-center font-monospace fs-3 fw-bold letter-spacing-2" 
                                   id="otp" name="otp" maxlength="6" pattern="[0-9]{6}" 
                                   placeholder="------" required autofocus autocomplete="one-time-code"
                                   style="letter-spacing: 0.5rem;">
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-success fw-bold py-2">
                                <i class="bi bi-check2-circle me-1"></i>Kích Hoạt Tài Khoản
                            </button>
                        </div>
                    </form>

                    <div class="mt-4 pt-3 border-top">
                        <a href="<c:url value='/register'/>" class="text-muted small text-decoration-none">
                            <i class="bi bi-arrow-left me-1"></i>Nhập lại thông tin đăng ký
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
