<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thanh Toán Đơn Hàng (COD) - Nhà Sách BookStore</title>
</head>
<body>
<div class="container my-4">
    <!-- Breadcrumb -->
    <nav aria-label="breadcrumb" class="mb-4">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="<c:url value='/home'/>">Trang chủ</a></li>
            <li class="breadcrumb-item"><a href="<c:url value='/cart'/>">Giỏ hàng</a></li>
            <li class="breadcrumb-item active" aria-current="page">Thanh toán COD</li>
        </ol>
    </nav>

    <h3 class="fw-bold text-dark mb-4">
        <i class="bi bi-credit-card-2-front text-primary me-2"></i>Thanh Toán Đơn Hàng (COD)
    </h3>

    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show shadow-sm" role="alert">
            <i class="bi bi-exclamation-triangle-fill me-2"></i>${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <form action="<c:url value='/checkout'/>" method="post">
        <div class="row g-4">
            <!-- CỘT TRÁI: THÔNG TIN GIAO HÀNG (COL-LG-7) -->
            <div class="col-lg-7">
                <div class="card shadow-sm border-0 mb-4">
                    <div class="card-header bg-white py-3">
                        <h5 class="card-title fw-bold text-dark mb-0">
                            <i class="bi bi-geo-alt-fill text-danger me-2"></i>Thông Tin Giao Hàng
                        </h5>
                    </div>
                    <div class="card-body p-4">
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Họ và tên người nhận <span class="text-danger">*</span></label>
                            <input type="text" name="fullname" class="form-control" 
                                   value="${sessionScope.account.fullname}" 
                                   placeholder="Nhập họ và tên người nhận" required/>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-semibold">Số điện thoại liên hệ <span class="text-danger">*</span></label>
                            <input type="text" name="phone" class="form-control" 
                                   value="${sessionScope.account.phone != 0 ? sessionScope.account.phone : ''}" 
                                   placeholder="Ví dụ: 0912345678" required/>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-semibold">Địa chỉ nhận hàng chi tiết <span class="text-danger">*</span></label>
                            <textarea name="address" class="form-control" rows="3" 
                                      placeholder="Số nhà, tên đường, phường/xã, quận/huyện, tỉnh/thành phố..." required></textarea>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-semibold">Ghi chú giao hàng (nếu có)</label>
                            <textarea name="note" class="form-control" rows="2" 
                                      placeholder="Ví dụ: Giao vào giờ hành chính, gọi trước khi đến..."></textarea>
                        </div>
                    </div>
                </div>

                <!-- PHƯƠNG THỨC THANH TOÁN -->
                <div class="card shadow-sm border-0">
                    <div class="card-header bg-white py-3">
                        <h5 class="card-title fw-bold text-dark mb-0">
                            <i class="bi bi-wallet2 text-success me-2"></i>Phương Thức Thanh Toán
                        </h5>
                    </div>
                    <div class="card-body p-4">
                        <div class="form-check p-3 border rounded bg-light">
                            <input class="form-check-input" type="radio" name="paymentMethod" id="codPayment" value="COD" checked>
                            <label class="form-check-label fw-bold text-dark ms-2" for="codPayment">
                                <i class="bi bi-cash-coin text-success me-1"></i>Thanh toán khi nhận hàng (COD)
                            </label>
                            <div class="small text-muted ms-4 mt-1">
                                Bạn sẽ thanh toán trực tiếp bằng tiền mặt cho nhân viên giao hàng khi nhận và kiểm tra sách.
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- CỘT PHẢI: TÓM TẮT ĐƠN HÀNG (COL-LG-5) -->
            <div class="col-lg-5">
                <div class="card shadow-sm border-0 sticky-top" style="top: 20px;">
                    <div class="card-header bg-white py-3">
                        <h5 class="card-title fw-bold text-dark mb-0">
                            <i class="bi bi-bag-check-fill text-primary me-2"></i>Đơn Hàng Của Bạn (${totalItems} cuốn)
                        </h5>
                    </div>
                    <div class="card-body p-4">
                        <!-- Danh sách item -->
                        <div class="order-items-list mb-3" style="max-height: 280px; overflow-y: auto;">
                            <c:forEach items="${cartItems}" var="item">
                                <div class="d-flex align-items-center mb-3 pb-2 border-bottom">
                                    <img src="<c:url value='/images/${item.book.cover_image}'/>" 
                                         alt="${item.book.title}" 
                                         class="rounded me-3 shadow-sm" 
                                         style="width: 48px; height: 64px; object-fit: contain;"
                                         onerror="this.src='https://placehold.co/48x64/e2e8f0/1e293b?text=Book';"/>
                                    <div class="flex-grow-1 me-2">
                                        <h6 class="mb-1 text-truncate" style="max-width: 220px;" title="${item.book.title}">
                                            ${item.book.title}
                                        </h6>
                                        <div class="small text-muted">
                                            SL: <strong>${item.quantity}</strong> × <fmt:formatNumber value="${item.book.price}" type="currency" currencySymbol="VNĐ" maxFractionDigits="0"/>
                                        </div>
                                    </div>
                                    <div class="text-end fw-semibold text-danger">
                                        <fmt:formatNumber value="${item.totalPrice}" type="currency" currencySymbol="VNĐ" maxFractionDigits="0"/>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <!-- Chi tiết thanh toán -->
                        <div class="d-flex justify-content-between mb-2">
                            <span class="text-muted">Tạm tính:</span>
                            <span class="fw-semibold">
                                <fmt:formatNumber value="${totalAmount}" type="currency" currencySymbol="VNĐ" maxFractionDigits="0"/>
                            </span>
                        </div>
                        <div class="d-flex justify-content-between mb-2">
                            <span class="text-muted">Phí giao hàng:</span>
                            <span class="text-success fw-semibold">Miễn phí (0 VNĐ)</span>
                        </div>
                        <div class="d-flex justify-content-between pt-3 border-top mb-4">
                            <span class="fs-5 fw-bold text-dark">Tổng cộng:</span>
                            <span class="fs-4 fw-bold text-danger">
                                <fmt:formatNumber value="${totalAmount}" type="currency" currencySymbol="VNĐ" maxFractionDigits="0"/>
                            </span>
                        </div>

                        <!-- Nút xác nhận đặt hàng -->
                        <button type="submit" class="btn btn-success btn-lg w-100 fw-bold shadow py-3">
                            <i class="bi bi-check-circle-fill me-2"></i>XÁC NHẬN ĐẶT HÀNG COD
                        </button>

                        <div class="text-center mt-3">
                            <a href="<c:url value='/cart'/>" class="text-decoration-none text-muted small">
                                <i class="bi bi-arrow-left me-1"></i>Quay lại chỉnh sửa giỏ hàng
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>
