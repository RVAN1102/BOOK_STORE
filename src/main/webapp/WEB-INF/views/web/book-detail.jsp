<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${book.title} - Chi Tiết Sách</title>
</head>
<body>
<div class="container my-4">

    <!-- BREADCRUMB -->
    <nav aria-label="breadcrumb" class="mb-4">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="<c:url value='/home'/>">Trang chủ</a></li>
            <c:if test="${not empty book.authorName}">
                <li class="breadcrumb-item"><a href="<c:url value='/home?authorId=${book.authorId}'/>">${book.authorName}</a></li>
            </c:if>
            <li class="breadcrumb-item active" aria-current="page">${book.title}</li>
        </ol>
    </nav>

    <div class="row g-4">
        <!-- CỘT BÊN TRÁI: CHI TIẾT SÁCH (COL-MD-7) -->
        <div class="col-lg-7">
            <div class="card shadow-sm border-0 h-100">
                <div class="card-body p-4">
                    <div class="row">
                        <!-- Ảnh bìa lớn -->
                        <div class="col-sm-5 text-center mb-3 mb-sm-0">
                            <div class="bg-light p-2 rounded shadow-sm">
                                <img src="<c:url value='/images/${book.cover_image}'/>" 
                                     alt="${book.title}" 
                                     class="img-fluid rounded" 
                                     style="max-height: 340px; object-fit: contain;"
                                     onerror="this.src='https://placehold.co/240x340/e2e8f0/1e293b?text=Book+Cover';"/>
                            </div>
                            <div class="mt-3">
                                <span class="badge ${book.quantity > 0 ? 'bg-success' : 'bg-danger'} px-3 py-2 fs-6">
                                    <i class="bi bi-box-seam me-1"></i>${book.quantity > 0 ? 'Còn hàng' : 'Hết hàng'} (${book.quantity})
                                </span>
                            </div>
                        </div>

                        <!-- Thông tin chi tiết -->
                        <div class="col-sm-7">
                            <h3 class="fw-bold text-dark mb-2">${book.title}</h3>
                            
                            <!-- Đánh giá sao trung bình -->
                            <div class="mb-3 d-flex align-items-center">
                                <span class="text-warning fs-5 me-2">
                                    <c:choose>
                                        <c:when test="${avgRating >= 4.5}"><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i></c:when>
                                        <c:when test="${avgRating >= 3.5}"><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i class="bi bi-star"></i></c:when>
                                        <c:when test="${avgRating >= 2.5}"><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i class="bi bi-star"></i><i class="bi bi-star"></i></c:when>
                                        <c:when test="${avgRating >= 1.5}"><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i class="bi bi-star"></i><i class="bi bi-star"></i><i class="bi bi-star"></i></c:when>
                                        <c:when test="${avgRating > 0}"><i class="bi bi-star-fill"></i><i class="bi bi-star"></i><i class="bi bi-star"></i><i class="bi bi-star"></i><i class="bi bi-star"></i></c:when>
                                        <c:otherwise><span class="text-muted fs-6 fst-italic">Chưa có điểm</span></c:otherwise>
                                    </c:choose>
                                </span>
                                <span class="badge bg-warning text-dark fw-bold fs-6">${avgRating} / 5</span>
                                <span class="text-muted ms-2 small">(${reviewCount} lượt đánh giá)</span>
                            </div>

                            <!-- Giá bán & Thêm vào giỏ -->
                            <div class="p-3 bg-light rounded mb-3">
                                <span class="text-muted small d-block">Giá bìa:</span>
                                <div class="d-flex justify-content-between align-items-center mb-2">
                                    <span class="fs-3 fw-bold text-danger">
                                        <fmt:formatNumber value="${book.price}" type="currency" currencySymbol="VNĐ" maxFractionDigits="0"/>
                                    </span>
                                    <span class="badge ${book.quantity > 0 ? 'bg-success' : 'bg-danger'}">
                                        ${book.quantity > 0 ? 'Còn hàng' : 'Hết hàng'}
                                    </span>
                                </div>
                                <c:choose>
                                    <c:when test="${book.quantity > 0}">
                                        <form action="<c:url value='/cart/add'/>" method="post" class="d-flex align-items-center mt-2">
                                            <input type="hidden" name="bookId" value="${book.bookid}"/>
                                            <div class="input-group me-2" style="max-width: 120px;">
                                                <span class="input-group-text bg-white">SL</span>
                                                <input type="number" name="quantity" value="1" min="1" max="${book.quantity}" class="form-control text-center" required/>
                                            </div>
                                            <button type="submit" class="btn btn-primary flex-grow-1 fw-bold">
                                                <i class="bi bi-cart-plus-fill me-1"></i>Thêm vào giỏ
                                            </button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <button class="btn btn-secondary w-100 disabled" disabled>
                                            <i class="bi bi-x-circle me-1"></i>Sản phẩm tạm hết hàng
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <ul class="list-group list-group-flush mb-3 small">
                                <li class="list-group-item px-0 py-1"><strong>Mã ISBN:</strong> ${book.isbn}</li>
                                <li class="list-group-item px-0 py-1"><strong>Tác giả:</strong> <span class="text-primary fw-semibold">${book.authorName}</span></li>
                                <li class="list-group-item px-0 py-1"><strong>Nhà xuất bản:</strong> ${book.publisher}</li>
                                <li class="list-group-item px-0 py-1"><strong>Ngày xuất bản:</strong> ${book.publish_date}</li>
                            </ul>
                        </div>
                    </div>

                    <!-- Mô tả nội dung sách -->
                    <div class="mt-4 pt-3 border-top">
                        <h5 class="fw-bold text-dark"><i class="bi bi-file-text me-2"></i>Mô tả tóm tắt nội dung</h5>
                        <p class="text-secondary" style="line-height: 1.7; text-align: justify;">
                            ${not empty book.description ? book.description : 'Đang cập nhật tóm tắt nội dung cho tác phẩm này.'}
                        </p>
                    </div>

                    <div class="mt-4">
                        <a href="<c:url value='/home?authorId=${book.authorId}'/>" class="btn btn-outline-secondary">
                            <i class="bi bi-arrow-left me-1"></i>Quay lại danh sách sách
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <!-- CỘT BÊN PHẢI: FORM ĐÁNH GIÁ & DANH SÁCH REVIEW (COL-MD-5) -->
        <div class="col-lg-5">
            <!-- KHUNG GỬI ĐÁNH GIÁ MỚI -->
            <div class="card shadow-sm border-0 mb-4">
                <div class="card-header bg-dark text-white py-3">
                    <h5 class="mb-0 fw-bold"><i class="bi bi-pencil-square me-2 text-warning"></i>Gửi Đánh Giá Của Bạn</h5>
                </div>
                <div class="card-body p-4">
                    <c:choose>
                        <c:when test="${not empty sessionScope.account}">
                            <form action="<c:url value='/book/detail'/>" method="post">
                                <input type="hidden" name="bookId" value="${book.bookid}"/>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Đánh giá chất lượng:</label>
                                    <select name="rating" class="form-select" required>
                                        <option value="5" selected>⭐⭐⭐⭐⭐ (5 sao - Xuất sắc / Rất hài lòng)</option>
                                        <option value="4">⭐⭐⭐⭐ (4 sao - Tốt / Đáng đọc)</option>
                                        <option value="3">⭐⭐⭐ (3 sao - Bình thường)</option>
                                        <option value="2">⭐⭐ (2 sao - Chưa ấn tượng)</option>
                                        <option value="1">⭐ (1 sao - Kém / Không hay)</option>
                                    </select>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Nội dung nhận xét:</label>
                                    <textarea name="review_text" rows="3" class="form-control" 
                                              placeholder="Chia sẻ nhận định của bạn về cuốn sách này..." required></textarea>
                                </div>

                                <button type="submit" class="btn btn-primary w-100 fw-bold py-2 shadow-sm">
                                    <i class="bi bi-send-fill me-1"></i>Gửi Đánh Giá
                                </button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <div class="alert alert-warning mb-0" role="alert">
                                <i class="bi bi-exclamation-triangle-fill me-2 fs-5"></i>
                                Bạn cần <a href="<c:url value='/login'/>" class="alert-link fw-bold">Đăng nhập</a> để tham gia đánh giá tác phẩm này.
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <!-- DANH SÁCH REVIEW ĐÃ CÓ -->
            <div class="card shadow-sm border-0">
                <div class="card-header bg-light py-3 border-bottom d-flex justify-content-between align-items-center">
                    <h5 class="mb-0 fw-bold text-dark"><i class="bi bi-chat-quote-fill me-2 text-primary"></i>Nhận Xét Từ Độc Giả</h5>
                    <span class="badge bg-primary rounded-pill">${reviewCount}</span>
                </div>
                <div class="card-body p-3" style="max-height: 480px; overflow-y: auto;">
                    <c:choose>
                        <c:when test="${not empty ratings}">
                            <div class="d-flex flex-column gap-3">
                                <c:forEach items="${ratings}" var="r">
                                    <div class="p-3 bg-light rounded border">
                                        <div class="d-flex justify-content-between align-items-center mb-1">
                                            <strong class="text-dark">
                                                <i class="bi bi-person-circle text-secondary me-1"></i>${r.userFullname}
                                            </strong>
                                            <span class="text-warning small">
                                                <c:forEach begin="1" end="${r.rating}"><i class="bi bi-star-fill"></i></c:forEach><c:forEach begin="1" end="${5 - r.rating}"><i class="bi bi-star"></i></c:forEach>
                                            </span>
                                        </div>
                                        <div class="text-muted small mb-2">
                                            <i class="bi bi-clock me-1"></i>
                                            <fmt:formatDate value="${r.created_at}" pattern="dd/MM/yyyy HH:mm"/>
                                        </div>
                                        <p class="mb-0 text-secondary" style="font-size: 0.95rem;">
                                            ${r.review_text}
                                        </p>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="text-center py-4 text-muted">
                                <i class="bi bi-chat-dots fs-1 d-block mb-2 text-secondary-subtle"></i>
                                <em>Chưa có nhận xét nào cho cuốn sách này. Hãy là người đầu tiên chia sẻ cảm nhận!</em>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>
