<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Lịch Sử Đơn Hàng Của Tôi - Nhà Sách BookStore</title>
</head>
<body>
<div class="container my-4">
    <!-- Breadcrumb -->
    <nav aria-label="breadcrumb" class="mb-4">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="<c:url value='/home'/>">Trang chủ</a></li>
            <li class="breadcrumb-item active" aria-current="page">Lịch sử đơn hàng</li>
        </ol>
    </nav>

    <!-- Thông báo đặt hàng thành công -->
    <c:if test="${not empty sessionScope.orderSuccessMsg}">
        <div class="alert alert-success alert-dismissible fade show shadow-sm mb-4" role="alert">
            <i class="bi bi-check-circle-fill me-2 fs-5"></i>
            <strong>${sessionScope.orderSuccessMsg}</strong>
            <div class="small mt-1 text-muted">Chúng tôi đã tiếp nhận đơn hàng và sẽ sớm giao sách đến bạn.</div>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <c:remove var="orderSuccessMsg" scope="session"/>
    </c:if>

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

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h3 class="fw-bold text-dark mb-0">
            <i class="bi bi-clock-history text-primary me-2"></i>Lịch Sử Đơn Hàng Của Tôi
        </h3>
        <a href="<c:url value='/home'/>" class="btn btn-outline-primary btn-sm fw-semibold">
            <i class="bi bi-plus-circle me-1"></i>Đặt thêm sách
        </a>
    </div>

    <!-- THANH NAV-TABS LỌC 8 TRẠNG THÁI CHUẨN CỦA HỆ THỐNG -->
    <ul class="nav nav-pills flex-nowrap overflow-auto pb-2 mb-4 border-bottom">
        <li class="nav-item me-1">
            <a class="nav-link ${currentStatus == 'ALL' ? 'active fw-bold' : 'text-dark'}" 
               href="<c:url value='/orders'/>">Tất cả</a>
        </li>
        <li class="nav-item me-1">
            <a class="nav-link ${currentStatus == 'Đơn hàng mới' ? 'active fw-bold' : 'text-dark'}" 
               href="<c:url value='/orders?status=Đơn hàng mới'/>">Đơn hàng mới</a>
        </li>
        <li class="nav-item me-1">
            <a class="nav-link ${currentStatus == 'Đã xác nhận' ? 'active fw-bold' : 'text-dark'}" 
               href="<c:url value='/orders?status=Đã xác nhận'/>">Đã xác nhận</a>
        </li>
        <li class="nav-item me-1">
            <a class="nav-link ${currentStatus == 'Chuẩn bị hàng' ? 'active fw-bold' : 'text-dark'}" 
               href="<c:url value='/orders?status=Chuẩn bị hàng'/>">Chuẩn bị hàng</a>
        </li>
        <li class="nav-item me-1">
            <a class="nav-link ${currentStatus == 'Vận chuyển' ? 'active fw-bold' : 'text-dark'}" 
               href="<c:url value='/orders?status=Vận chuyển'/>">Vận chuyển</a>
        </li>
        <li class="nav-item me-1">
            <a class="nav-link ${currentStatus == 'Giao hàng' ? 'active fw-bold' : 'text-dark'}" 
               href="<c:url value='/orders?status=Giao hàng'/>">Giao hàng</a>
        </li>
        <li class="nav-item me-1">
            <a class="nav-link ${currentStatus == 'Đã giao' ? 'active fw-bold' : 'text-dark'}" 
               href="<c:url value='/orders?status=Đã giao'/>">Đã giao</a>
        </li>
        <li class="nav-item me-1">
            <a class="nav-link ${currentStatus == 'Đơn hàng hủy' ? 'active fw-bold' : 'text-dark'}" 
               href="<c:url value='/orders?status=Đơn hàng hủy'/>">Đơn hàng hủy</a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${currentStatus == 'Đơn hàng hoàn' ? 'active fw-bold' : 'text-dark'}" 
               href="<c:url value='/orders?status=Đơn hàng hoàn'/>">Đơn hàng hoàn</a>
        </li>
    </ul>

    <!-- BẢNG DANH SÁCH ĐƠN HÀNG -->
    <c:choose>
        <c:when test="${not empty orders}">
            <div class="card shadow-sm border-0">
                <div class="table-responsive">
                    <table class="table table-hover align-middle mb-0">
                        <thead class="table-light">
                            <tr>
                                <th class="text-center" style="width: 100px;">Mã Đơn</th>
                                <th style="width: 170px;">Ngày Đặt</th>
                                <th>Người Nhận & SĐT</th>
                                <th>Địa Chỉ Giao Hàng</th>
                                <th class="text-end" style="width: 140px;">Tổng Tiền</th>
                                <th class="text-center" style="width: 90px;">Hình Thức</th>
                                <th class="text-center" style="width: 150px;">Trạng Thái</th>
                                <th class="text-center" style="width: 120px;">Thao Tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${orders}" var="o">
                                <tr>
                                    <td class="text-center fw-bold text-primary">#${o.orderId}</td>
                                    <td>
                                        <div class="small fw-semibold">
                                            <fmt:formatDate value="${o.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="fw-semibold">${o.fullname}</div>
                                        <div class="small text-muted"><i class="bi bi-telephone me-1"></i>${o.phone}</div>
                                    </td>
                                    <td>
                                        <div class="text-truncate small" style="max-width: 240px;" title="${o.address}">
                                            ${o.address}
                                        </div>
                                        <c:if test="${not empty o.note}">
                                            <div class="small text-muted fst-italic text-truncate" style="max-width: 240px;">
                                                Ghi chú: ${o.note}
                                            </div>
                                        </c:if>
                                    </td>
                                    <td class="text-end fw-bold text-danger">
                                        <fmt:formatNumber value="${o.totalAmount}" type="currency" currencySymbol="VNĐ" maxFractionDigits="0"/>
                                    </td>
                                    <td class="text-center">
                                        <span class="badge bg-secondary-subtle text-secondary border">
                                            ${o.paymentMethod}
                                        </span>
                                    </td>
                                    <td class="text-center">
                                        <!-- BADGE MÀU SẮC CHUẨN 8 TRẠNG THÁI -->
                                        <c:choose>
                                            <c:when test="${o.status == 'Đơn hàng mới'}">
                                                <span class="badge bg-primary px-2 py-1">Đơn hàng mới</span>
                                            </c:when>
                                            <c:when test="${o.status == 'Đã xác nhận'}">
                                                <span class="badge bg-secondary px-2 py-1">Đã xác nhận</span>
                                            </c:when>
                                            <c:when test="${o.status == 'Chuẩn bị hàng'}">
                                                <span class="badge bg-warning text-dark px-2 py-1">Chuẩn bị hàng</span>
                                            </c:when>
                                            <c:when test="${o.status == 'Vận chuyển'}">
                                                <span class="badge bg-info text-dark px-2 py-1">Vận chuyển</span>
                                            </c:when>
                                            <c:when test="${o.status == 'Giao hàng'}">
                                                <span class="badge bg-light text-dark border px-2 py-1">Giao hàng</span>
                                            </c:when>
                                            <c:when test="${o.status == 'Đã giao'}">
                                                <span class="badge bg-success px-2 py-1">Đã giao</span>
                                            </c:when>
                                            <c:when test="${o.status == 'Đơn hàng hủy'}">
                                                <span class="badge bg-danger px-2 py-1">Đơn hàng hủy</span>
                                            </c:when>
                                            <c:when test="${o.status == 'Đơn hàng hoàn'}">
                                                <span class="badge bg-dark px-2 py-1">Đơn hàng hoàn</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary px-2 py-1">${o.status}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-center text-nowrap">
                                        <a href="<c:url value='/orders/detail?id=${o.orderId}'/>" 
                                           class="btn btn-outline-primary btn-sm fw-semibold" title="Xem chi tiết đơn hàng">
                                            <i class="bi bi-eye me-1"></i>Chi tiết
                                        </a>
                                        <c:if test="${o.status == 'Đơn hàng mới' || o.status == 'Đã xác nhận'}">
                                            <a href="<c:url value='/orders?action=cancel&id=${o.orderId}'/>" 
                                               class="btn btn-outline-danger btn-sm fw-semibold ms-1"
                                               onclick="return confirm('Bạn có chắc chắn muốn hủy đơn hàng #${o.orderId} này không? Số lượng sách sẽ được hoàn lại vào kho.');"
                                               title="Hủy đơn hàng này">
                                                <i class="bi bi-x-circle me-1"></i>Hủy đơn
                                            </a>
                                        </c:if>
                                        <c:if test="${o.status == 'Đã giao'}">
                                            <a href="<c:url value='/orders?action=return&id=${o.orderId}'/>" 
                                               class="btn btn-outline-warning btn-sm fw-semibold ms-1"
                                               onclick="return confirm('Bạn có chắc chắn muốn gửi yêu cầu trả hàng / hoàn tiền cho đơn hàng #${o.orderId} này không?');"
                                               title="Yêu cầu trả hàng">
                                                <i class="bi bi-arrow-counterclockwise me-1"></i>Trả hàng
                                            </a>
                                        </c:if>
                                        <c:if test="${o.status == 'Đơn hàng hủy'}">
                                            <a href="<c:url value='/orders?action=reorder&id=${o.orderId}'/>" 
                                               class="btn btn-outline-success btn-sm fw-semibold ms-1"
                                               title="Mua lại các sản phẩm trong đơn hàng này">
                                                <i class="bi bi-cart-plus me-1"></i>Mua lại
                                            </a>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="card shadow-sm border-0 text-center py-5 my-4">
                <div class="card-body">
                    <i class="bi bi-inbox text-muted" style="font-size: 3.5rem;"></i>
                    <h5 class="mt-3 fw-bold text-secondary">Không có đơn hàng nào ở trạng thái này!</h5>
                    <p class="text-muted">Các đơn hàng của bạn sẽ được cập nhật trạng thái chi tiết theo quá trình xử lý.</p>
                    <a href="<c:url value='/orders'/>" class="btn btn-outline-secondary btn-sm px-3 mt-1">
                        Xem tất cả đơn hàng
                    </a>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
