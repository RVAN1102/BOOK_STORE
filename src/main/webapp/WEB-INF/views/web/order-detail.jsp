<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi Tiết Đơn Hàng #${order.orderId} - Nhà Sách BookStore</title>
</head>
<body>
<div class="container my-4">
    <!-- Breadcrumb -->
    <nav aria-label="breadcrumb" class="mb-4">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="<c:url value='/home'/>">Trang chủ</a></li>
            <li class="breadcrumb-item"><a href="<c:url value='/orders'/>">Lịch sử đơn hàng</a></li>
            <li class="breadcrumb-item active" aria-current="page">Đơn hàng #${order.orderId}</li>
        </ol>
    </nav>

    <!-- Thông báo thao tác thành công / lỗi -->
    <c:if test="${not empty sessionScope.message}">
        <div class="alert alert-success alert-dismissible fade show shadow-sm mb-4" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i>
            ${sessionScope.message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <c:remove var="message" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.error}">
        <div class="alert alert-danger alert-dismissible fade show shadow-sm mb-4" role="alert">
            <i class="bi bi-exclamation-triangle-fill me-2"></i>
            ${sessionScope.error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <c:remove var="error" scope="session"/>
    </c:if>

    <div class="d-flex justify-content-between align-items-center mb-4 flex-wrap gap-2">
        <div>
            <h3 class="fw-bold text-dark mb-1">
                Chi Tiết Đơn Hàng <span class="text-primary">#${order.orderId}</span>
            </h3>
            <div class="text-muted small">
                Ngày đặt: <fmt:formatDate value="${order.createdAt}" pattern="dd/MM/yyyy HH:mm:ss"/>
            </div>
        </div>
        <div class="d-flex align-items-center gap-2">
            <c:if test="${order.status == 'Đơn hàng mới' || order.status == 'Đã xác nhận'}">
                <a href="<c:url value='/orders?action=cancel&id=${order.orderId}'/>" 
                   class="btn btn-danger btn-sm fw-semibold"
                   onclick="return confirm('Bạn có chắc chắn muốn hủy đơn hàng #${order.orderId} này không?');">
                    <i class="bi bi-x-circle me-1"></i>Hủy đơn hàng
                </a>
            </c:if>
            <c:if test="${order.status == 'Đã giao'}">
                <a href="<c:url value='/orders?action=return&id=${order.orderId}'/>" 
                   class="btn btn-warning btn-sm fw-semibold text-dark"
                   onclick="return confirm('Bạn có chắc chắn muốn gửi yêu cầu trả hàng / hoàn tiền cho đơn hàng #${order.orderId} này không?');">
                    <i class="bi bi-arrow-counterclockwise me-1"></i>Yêu cầu trả hàng
                </a>
            </c:if>
            <c:if test="${order.status == 'Đơn hàng hủy'}">
                <a href="<c:url value='/orders?action=reorder&id=${order.orderId}'/>" 
                   class="btn btn-success btn-sm fw-semibold text-white">
                    <i class="bi bi-cart-plus me-1"></i>Mua lại đơn hàng
                </a>
            </c:if>
            <a href="<c:url value='/orders'/>" class="btn btn-outline-secondary btn-sm fw-semibold">
                <i class="bi bi-arrow-left me-1"></i>Về danh sách đơn
            </a>
        </div>
    </div>

    <div class="row g-4 mb-4">
        <!-- THÔNG TIN NGƯỜI NHẬN -->
        <div class="col-md-6">
            <div class="card shadow-sm border-0 h-100">
                <div class="card-header bg-white py-3">
                    <h6 class="fw-bold text-dark mb-0">
                        <i class="bi bi-person-fill text-primary me-2"></i>Thông Tin Người Nhận
                    </h6>
                </div>
                <div class="card-body">
                    <p class="mb-2"><strong>Họ và tên:</strong> ${order.fullname}</p>
                    <p class="mb-2"><strong>Số điện thoại:</strong> ${order.phone}</p>
                    <p class="mb-2"><strong>Địa chỉ giao hàng:</strong> ${order.address}</p>
                    <p class="mb-0"><strong>Ghi chú:</strong> <span class="text-muted">${not empty order.note ? order.note : 'Không có ghi chú'}</span></p>
                </div>
            </div>
        </div>

        <!-- THÔNG TIN THANH TOÁN & TRẠNG THÁI -->
        <div class="col-md-6">
            <div class="card shadow-sm border-0 h-100">
                <div class="card-header bg-white py-3">
                    <h6 class="fw-bold text-dark mb-0">
                        <i class="bi bi-info-circle-fill text-info me-2"></i>Trạng Thái & Thanh Toán
                    </h6>
                </div>
                <div class="card-body">
                    <p class="mb-2">
                        <strong>Trạng thái đơn hàng:</strong>
                        <c:choose>
                            <c:when test="${order.status == 'Đơn hàng mới'}"><span class="badge bg-primary fs-6">Đơn hàng mới</span></c:when>
                            <c:when test="${order.status == 'Đã xác nhận'}"><span class="badge bg-secondary fs-6">Đã xác nhận</span></c:when>
                            <c:when test="${order.status == 'Chuẩn bị hàng'}"><span class="badge bg-warning text-dark fs-6">Chuẩn bị hàng</span></c:when>
                            <c:when test="${order.status == 'Vận chuyển'}"><span class="badge bg-info text-dark fs-6">Vận chuyển</span></c:when>
                            <c:when test="${order.status == 'Giao hàng'}"><span class="badge bg-light text-dark border fs-6">Giao hàng</span></c:when>
                            <c:when test="${order.status == 'Đã giao'}"><span class="badge bg-success fs-6">Đã giao</span></c:when>
                            <c:when test="${order.status == 'Đơn hàng hủy'}"><span class="badge bg-danger fs-6">Đơn hàng hủy</span></c:when>
                            <c:when test="${order.status == 'Đơn hàng hoàn'}"><span class="badge bg-dark fs-6">Đơn hàng hoàn</span></c:when>
                            <c:otherwise><span class="badge bg-secondary fs-6">${order.status}</span></c:otherwise>
                        </c:choose>
                    </p>
                    <p class="mb-2">
                        <strong>Phương thức thanh toán:</strong> 
                        <span class="badge bg-secondary-subtle text-secondary border fs-6">${order.paymentMethod}</span>
                        <span class="small text-muted ms-1">(Thanh toán khi nhận hàng)</span>
                    </p>
                    <p class="mb-0">
                        <strong>Tổng giá trị đơn hàng:</strong> 
                        <span class="fs-5 fw-bold text-danger">
                            <fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="VNĐ" maxFractionDigits="0"/>
                        </span>
                    </p>
                </div>
            </div>
        </div>
    </div>

    <!-- BẢNG SẢN PHẨM TRONG ĐƠN -->
    <div class="card shadow-sm border-0">
        <div class="card-header bg-white py-3">
            <h5 class="fw-bold text-dark mb-0">
                <i class="bi bi-journal-bookmark-fill text-primary me-2"></i>Danh Sách Sách Trong Đơn
            </h5>
        </div>
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th class="text-center" style="width: 80px;">STT</th>
                        <th class="text-center" style="width: 100px;">Ảnh Bìa</th>
                        <th>Tên Sách</th>
                        <th class="text-end" style="width: 150px;">Đơn Giá</th>
                        <th class="text-center" style="width: 120px;">Số Lượng</th>
                        <th class="text-end" style="width: 160px;">Thành Tiền</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${order.items}" var="item" varStatus="loop">
                        <tr>
                            <td class="text-center text-muted fw-bold">${loop.index + 1}</td>
                            <td class="text-center">
                                <img src="<c:url value='/images/${item.book.cover_image}'/>" 
                                     alt="${item.book.title}" 
                                     class="rounded shadow-sm" 
                                     style="width: 50px; height: 68px; object-fit: contain;"
                                     onerror="this.src='https://placehold.co/50x68/e2e8f0/1e293b?text=Book';"/>
                            </td>
                            <td>
                                <a href="<c:url value='/book/detail?id=${item.bookId}'/>" class="fw-bold text-decoration-none text-dark hover-primary">
                                    ${item.book.title}
                                </a>
                                <div class="small text-muted">Mã ISBN: ${item.book.isbn}</div>
                            </td>
                            <td class="text-end fw-semibold">
                                <fmt:formatNumber value="${item.price}" type="currency" currencySymbol="VNĐ" maxFractionDigits="0"/>
                            </td>
                            <td class="text-center fw-bold fs-6">
                                ${item.quantity}
                            </td>
                            <td class="text-end fw-bold text-danger">
                                <fmt:formatNumber value="${item.totalPrice}" type="currency" currencySymbol="VNĐ" maxFractionDigits="0"/>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot class="table-light border-top">
                    <tr>
                        <td colspan="5" class="text-end fw-bold fs-5">Tổng tiền thanh toán:</td>
                        <td class="text-end fw-bold fs-4 text-danger">
                            <fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="VNĐ" maxFractionDigits="0"/>
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>
    </div>
</div>
</body>
</html>
