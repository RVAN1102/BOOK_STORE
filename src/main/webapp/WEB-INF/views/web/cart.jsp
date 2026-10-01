<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Giỏ Hàng Của Bạn - Nhà Sách BookStore</title>
</head>
<body>
<div class="container my-4">
    <!-- Breadcrumb -->
    <nav aria-label="breadcrumb" class="mb-4">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="<c:url value='/home'/>">Trang chủ</a></li>
            <li class="breadcrumb-item active" aria-current="page">Giỏ hàng</li>
        </ol>
    </nav>

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h3 class="fw-bold text-dark mb-0">
            <i class="bi bi-cart3 text-primary me-2"></i>Giỏ Hàng Của Bạn
        </h3>
        <c:if test="${not empty cartItems}">
            <a href="<c:url value='/cart/clear'/>" class="btn btn-outline-danger btn-sm" 
               onclick="return confirm('Bạn có chắc chắn muốn làm trống toàn bộ giỏ hàng?');">
                <i class="bi bi-trash3 me-1"></i>Xóa toàn bộ giỏ
            </a>
        </c:if>
    </div>

    <!-- Thông báo lỗi hoặc thành công -->
    <c:if test="${not empty sessionScope.error}">
        <div class="alert alert-danger alert-dismissible fade show shadow-sm" role="alert">
            <i class="bi bi-exclamation-triangle-fill me-2"></i>${sessionScope.error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <c:remove var="error" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.message}">
        <div class="alert alert-success alert-dismissible fade show shadow-sm" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i>${sessionScope.message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <c:remove var="message" scope="session"/>
    </c:if>

    <c:choose>
        <c:when test="${not empty cartItems}">
            <div class="card shadow-sm border-0 mb-4">
                <div class="table-responsive">
                    <table class="table table-hover align-middle mb-0">
                        <thead class="table-light">
                            <tr>
                                <th style="width: 100px;" class="text-center">Ảnh Bìa</th>
                                <th>Tên Sách</th>
                                <th class="text-end" style="width: 130px;">Đơn Giá</th>
                                <th class="text-center" style="width: 120px;">Tồn Kho</th>
                                <th class="text-center" style="width: 160px;">Số Lượng</th>
                                <th class="text-end" style="width: 150px;">Thành Tiền</th>
                                <th class="text-center" style="width: 80px;">Xóa</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${cartItems}" var="item">
                                <tr>
                                    <td class="text-center">
                                        <img src="<c:url value='/images/${item.book.cover_image}'/>" 
                                             alt="${item.book.title}" 
                                             class="rounded shadow-sm" 
                                             style="width: 60px; height: 80px; object-fit: contain;"
                                             onerror="this.src='https://placehold.co/60x80/e2e8f0/1e293b?text=Book';"/>
                                    </td>
                                    <td>
                                        <a href="<c:url value='/book/detail?id=${item.book.bookid}'/>" class="fw-bold text-decoration-none text-dark hover-primary">
                                            ${item.book.title}
                                        </a>
                                        <div class="small text-muted">ISBN: ${item.book.isbn}</div>
                                    </td>
                                    <td class="text-end fw-semibold">
                                        <fmt:formatNumber value="${item.book.price}" type="currency" currencySymbol="VNĐ" maxFractionDigits="0"/>
                                    </td>
                                    <td class="text-center">
                                        <span class="badge ${item.book.quantity > 5 ? 'bg-success-subtle text-success' : 'bg-warning-subtle text-warning-emphasis'}">
                                            ${item.book.quantity} cuốn
                                        </span>
                                    </td>
                                    <td class="text-center">
                                        <form action="<c:url value='/cart/update'/>" method="post" class="d-flex justify-content-center align-items-center">
                                            <input type="hidden" name="bookId" value="${item.book.bookid}"/>
                                            <input type="number" name="quantity" value="${item.quantity}" min="1" max="${item.book.quantity}" 
                                                   class="form-control form-control-sm text-center me-1" style="width: 65px;" required/>
                                            <button type="submit" class="btn btn-sm btn-outline-primary" title="Cập nhật số lượng">
                                                <i class="bi bi-arrow-repeat"></i>
                                            </button>
                                        </form>
                                    </td>
                                    <td class="text-end fw-bold text-danger">
                                        <fmt:formatNumber value="${item.totalPrice}" type="currency" currencySymbol="VNĐ" maxFractionDigits="0"/>
                                    </td>
                                    <td class="text-center">
                                        <a href="<c:url value='/cart/delete?bookId=${item.book.bookid}'/>" 
                                           class="btn btn-outline-danger btn-sm"
                                           onclick="return confirm('Bạn có chắc chắn muốn xóa cuốn sách này khỏi giỏ hàng?');"
                                           title="Xóa cuốn này">
                                            <i class="bi bi-trash"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        <tfoot class="table-light border-top">
                            <tr>
                                <td colspan="4" class="text-end fw-bold fs-5">Tổng tiền thanh toán:</td>
                                <td class="text-center fw-bold fs-6 text-muted">${totalItems} cuốn</td>
                                <td class="text-end fw-bold fs-4 text-danger">
                                    <fmt:formatNumber value="${totalAmount}" type="currency" currencySymbol="VNĐ" maxFractionDigits="0"/>
                                </td>
                                <td></td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </div>

            <!-- Nút hành động -->
            <div class="d-flex justify-content-between align-items-center mt-4">
                <a href="<c:url value='/home'/>" class="btn btn-outline-secondary px-4 py-2 fw-semibold">
                    <i class="bi bi-arrow-left me-2"></i>Tiếp tục mua hàng
                </a>
                <a href="<c:url value='/checkout'/>" class="btn btn-primary btn-lg px-5 py-2 fw-bold shadow">
                    <i class="bi bi-credit-card-2-front me-2"></i>Tiến hành thanh toán COD
                </a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="card shadow-sm border-0 text-center py-5 my-4">
                <div class="card-body">
                    <i class="bi bi-cart-x text-muted" style="font-size: 4rem;"></i>
                    <h4 class="mt-3 fw-bold text-secondary">Giỏ hàng của bạn đang trống!</h4>
                    <p class="text-muted">Hãy dạo một vòng và chọn cho mình những cuốn sách yêu thích nhé.</p>
                    <a href="<c:url value='/home'/>" class="btn btn-primary px-4 py-2 mt-2 fw-semibold">
                        <i class="bi bi-book me-2"></i>Khám phá ngay
                    </a>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
