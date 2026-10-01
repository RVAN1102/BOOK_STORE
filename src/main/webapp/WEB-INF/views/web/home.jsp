<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang Chủ - Danh Sách Sách Theo Tác Giả</title>
</head>
<body>
<div class="container my-4">

    <!-- BỘ CHỌN TÁC GIẢ -->
    <div class="card shadow-sm mb-4 border-0">
        <div class="card-header bg-primary text-white py-3">
            <h5 class="mb-0 fw-bold"><i class="bi bi-person-lines-fill me-2"></i>Chọn Tác Giả</h5>
        </div>
        <div class="card-body bg-light">
            <div class="d-flex flex-wrap gap-2">
                <c:forEach items="${authors}" var="author">
                    <a href="<c:url value='/home?authorId=${author.author_id}'/>" 
                       class="btn ${author.author_id == selectedAuthorId ? 'btn-primary active fw-bold shadow-sm' : 'btn-outline-secondary'}">
                        <i class="bi bi-pen me-1"></i>${author.author_name}
                    </a>
                </c:forEach>
            </div>
        </div>
    </div>

    <!-- TIÊU ĐỀ TÁC GIẢ ĐANG CHỌN -->
    <div class="d-flex justify-content-between align-items-center mb-3 pb-2 border-bottom">
        <h4 class="text-dark fw-bold mb-0">
            <span class="text-muted fw-normal">Tác giả:</span> 
            <span class="text-primary">${currentAuthor != null ? currentAuthor.author_name : 'Tất cả'}</span>
        </h4>
        <span class="badge bg-secondary fs-6">Tổng: ${totalBooks} cuốn sách</span>
    </div>

    <!-- HIỂN THỊ DANH SÁCH 3 SÁCH / TRANG -->
    <c:choose>
        <c:when test="${not empty books}">
            <div class="row row-cols-1 row-cols-md-3 g-4">
                <c:forEach items="${books}" var="book">
                    <div class="col">
                        <div class="card h-100 shadow-sm border-0 transition-hover">
                            <!-- Ảnh bìa sách -->
                            <div class="text-center pt-3 bg-light rounded-top" style="height: 260px; overflow: hidden;">
                                <img src="<c:url value='/images/${book.cover_image}'/>" 
                                     alt="${book.title}" 
                                     class="img-fluid h-100 rounded" 
                                     style="object-fit: contain;"
                                     onerror="this.src='https://placehold.co/200x260/e2e8f0/1e293b?text=BookStore';"/>
                            </div>
                            
                            <!-- Thông tin sách -->
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title mb-2">
                                    <a href="<c:url value='/book/detail?id=${book.bookid}'/>" 
                                       class="text-decoration-none text-dark fw-bold hover-primary" 
                                       title="${book.title}">
                                        ${book.title}
                                    </a>
                                </h5>
                                
                                <ul class="list-unstyled small text-muted mb-3 flex-grow-1">
                                    <li><strong>Mã ISBN:</strong> ${book.isbn}</li>
                                    <li><strong>Tác giả:</strong> ${book.authorName != null ? book.authorName : currentAuthor.author_name}</li>
                                    <li><strong>Nhà XB:</strong> ${book.publisher}</li>
                                    <li><strong>Ngày XB:</strong> ${book.publish_date}</li>
                                    <li><strong>Số lượng:</strong> <span class="badge ${book.quantity > 0 ? 'bg-success-subtle text-success' : 'bg-danger-subtle text-danger'}">${book.quantity} cuốn</span></li>
                                </ul>

                                <div class="d-flex justify-content-between align-items-center pt-2 border-top">
                                    <span class="fs-5 fw-bold text-danger">
                                        <fmt:formatNumber value="${book.price}" type="currency" currencySymbol="VNĐ" maxFractionDigits="0"/>
                                    </span>
                                    <span class="badge bg-warning-subtle text-warning-emphasis">
                                        <i class="bi bi-chat-left-dots-fill me-1"></i>${book.reviewCount} Review
                                    </span>
                                </div>

                                <div class="d-flex gap-2 mt-3">
                                    <a href="<c:url value='/book/detail?id=${book.bookid}'/>" 
                                       class="btn btn-outline-primary btn-sm flex-grow-1 fw-semibold">
                                        <i class="bi bi-info-circle me-1"></i>Chi Tiết
                                    </a>
                                    <c:if test="${book.quantity > 0}">
                                        <a href="<c:url value='/cart/add?bookId=${book.bookid}&quantity=1'/>" 
                                           class="btn btn-primary btn-sm fw-semibold" title="Thêm vào giỏ">
                                            <i class="bi bi-cart-plus"></i>
                                        </a>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <!-- PHÂN TRANG (PAGINATION) THEO ĐÚNG ĐỀ BÀI: Trang trước - 1 2 3 - Trang sau -->
            <c:if test="${totalPages > 1}">
                <nav aria-label="Book Pagination" class="mt-5">
                    <ul class="pagination justify-content-center">
                        <!-- Nút Trang trước -->
                        <c:choose>
                            <c:when test="${currentPage > 1}">
                                <li class="page-item">
                                    <a class="page-link" href="<c:url value='/home?authorId=${selectedAuthorId}&page=${currentPage - 1}'/>">
                                        <i class="bi bi-chevron-left"></i> Trang trước
                                    </a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item disabled">
                                    <span class="page-link"><i class="bi bi-chevron-left"></i> Trang trước</span>
                                </li>
                            </c:otherwise>
                        </c:choose>

                        <!-- Danh sách các số trang -->
                        <c:forEach begin="1" end="${totalPages}" var="p">
                            <li class="page-item ${p == currentPage ? 'active' : ''}">
                                <a class="page-link fw-bold" href="<c:url value='/home?authorId=${selectedAuthorId}&page=${p}'/>">${p}</a>
                            </li>
                        </c:forEach>

                        <!-- Nút Trang sau -->
                        <c:choose>
                            <c:when test="${currentPage < totalPages}">
                                <li class="page-item">
                                    <a class="page-link" href="<c:url value='/home?authorId=${selectedAuthorId}&page=${currentPage + 1}'/>">
                                        Trang sau <i class="bi bi-chevron-right"></i>
                                    </a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item disabled">
                                    <span class="page-link">Trang sau <i class="bi bi-chevron-right"></i></span>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </nav>
            </c:if>
        </c:when>
        <c:otherwise>
            <div class="alert alert-info text-center py-4 my-4 shadow-sm" role="alert">
                <i class="bi bi-info-circle-fill fs-3 d-block mb-2"></i>
                Chưa có cuốn sách nào của tác giả này trong hệ thống.
            </div>
        </c:otherwise>
    </c:choose>

</div>
</body>
</html>
