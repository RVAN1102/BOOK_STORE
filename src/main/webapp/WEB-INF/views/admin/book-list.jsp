<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản Lý Danh Sách Sách</title>
</head>
<body>
<div class="card shadow-sm border-0">
    <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center">
        <h4 class="mb-0 fw-bold text-primary">
            <i class="bi bi-collection-fill me-2"></i>Danh Sách Sách Trong Hệ Thống
        </h4>
        <a href="<c:url value='/admin/books/add'/>" class="btn btn-success fw-bold shadow-sm">
            <i class="bi bi-plus-circle me-1"></i>Thêm Sách Mới
        </a>
    </div>
    <div class="card-body p-0">
        <div class="table-responsive">
            <table class="table table-hover table-striped align-middle mb-0">
                <thead class="table-dark">
                    <tr>
                        <th class="text-center" style="width: 50px;">ID</th>
                        <th class="text-center" style="width: 80px;">Ảnh bìa</th>
                        <th>Tên Sách</th>
                        <th>ISBN</th>
                        <th>Tác Giả</th>
                        <th>Nhà Xuất Bản</th>
                        <th class="text-end">Giá Bán</th>
                        <th class="text-center">Ngày XB</th>
                        <th class="text-center">Kho</th>
                        <th class="text-center">Đánh Giá</th>
                        <th class="text-center" style="width: 160px;">Thao Tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty books}">
                            <c:forEach items="${books}" var="b">
                                <tr>
                                    <td class="text-center fw-bold">${b.bookid}</td>
                                    <td class="text-center">
                                        <img src="<c:url value='/images/${b.cover_image}'/>" 
                                             alt="${b.title}" 
                                             class="rounded shadow-sm" 
                                             style="width: 48px; height: 64px; object-fit: cover;"
                                             onerror="this.src='https://placehold.co/48x64/e2e8f0/1e293b?text=Book';"/>
                                    </td>
                                    <td>
                                        <a href="<c:url value='/book/detail?id=${b.bookid}'/>" target="_blank" class="fw-bold text-dark text-decoration-none">
                                            ${b.title}
                                        </a>
                                    </td>
                                    <td><code>${b.isbn}</code></td>
                                    <td><span class="badge bg-info-subtle text-info-emphasis">${b.authorName}</span></td>
                                    <td>${b.publisher}</td>
                                    <td class="text-end fw-bold text-danger">
                                        <fmt:formatNumber value="${b.price}" type="currency" currencySymbol="VNĐ" maxFractionDigits="0"/>
                                    </td>
                                    <td class="text-center small">${b.publish_date}</td>
                                    <td class="text-center">
                                        <span class="badge ${b.quantity > 0 ? 'bg-success' : 'bg-danger'}">${b.quantity}</span>
                                    </td>
                                    <td class="text-center">
                                        <span class="badge bg-warning text-dark"><i class="bi bi-star-fill me-1"></i>${b.reviewCount}</span>
                                    </td>
                                    <td class="text-center">
                                        <a href="<c:url value='/admin/books/edit?id=${b.bookid}'/>" class="btn btn-sm btn-outline-warning me-1" title="Sửa">
                                            <i class="bi bi-pencil-square"></i> Sửa
                                        </a>
                                        <a href="<c:url value='/admin/books/delete?id=${b.bookid}'/>" 
                                           class="btn btn-sm btn-outline-danger" 
                                           title="Xóa"
                                           onclick="return confirm('Bạn có chắc chắn muốn xóa cuốn sách \"${b.title}\" (Mã ID: ${b.bookid})?');">
                                            <i class="bi bi-trash"></i> Xóa
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="11" class="text-center py-4 text-muted">
                                    <em>Chưa có dữ liệu sách nào trong cơ sở dữ liệu.</em>
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
