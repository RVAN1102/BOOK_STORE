<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${isAdd ? 'Thêm Mới Sách' : 'Chỉnh Sửa Sách'} - Quản Trị Hệ Thống</title>
</head>
<body>
<div class="row justify-content-center">
    <div class="col-lg-8">
        <div class="card shadow-sm border-0">
            <div class="card-header ${isAdd ? 'bg-success text-white' : 'bg-warning text-dark'} py-3">
                <h4 class="mb-0 fw-bold">
                    <i class="bi ${isAdd ? 'bi-plus-circle' : 'bi-pencil-square'} me-2"></i>
                    ${isAdd ? 'Thêm Cuốn Sách Mới' : 'Cập Nhật Thông Tin Sách'}
                </h4>
            </div>
            <div class="card-body p-4">
                <form action="<c:url value='${isAdd ? \"/admin/books/add\" : \"/admin/books/edit\"}'/>" method="post">
                    
                    <c:if test="${!isAdd}">
                        <input type="hidden" name="bookid" value="${book.bookid}"/>
                    </c:if>

                    <div class="row g-3">
                        <!-- Tiêu đề sách -->
                        <div class="col-md-12">
                            <label class="form-label fw-semibold">Tên / Tiêu đề sách <span class="text-danger">*</span></label>
                            <input type="text" name="title" class="form-control" value="${book.title}" required 
                                   placeholder="Ví dụ: Mắt Biếc, Harry Potter..."/>
                        </div>

                        <!-- Mã ISBN -->
                        <div class="col-md-6">
                            <label class="form-label fw-semibold">Mã ISBN <span class="text-danger">*</span></label>
                            <input type="number" name="isbn" class="form-control" value="${book.isbn}" required 
                                   placeholder="Ví dụ: 1001"/>
                        </div>

                        <!-- Tác giả -->
                        <div class="col-md-6">
                            <label class="form-label fw-semibold">Tác giả <span class="text-danger">*</span></label>
                            <select name="authorId" class="form-select" required>
                                <option value="">-- Chọn tác giả --</option>
                                <c:forEach items="${authors}" var="a">
                                    <option value="${a.author_id}" ${(!isAdd && a.author_id == book.authorId) ? 'selected' : ''}>
                                        ${a.author_name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- Nhà xuất bản -->
                        <div class="col-md-6">
                            <label class="form-label fw-semibold">Nhà xuất bản</label>
                            <input type="text" name="publisher" class="form-control" value="${book.publisher}" 
                                   placeholder="Ví dụ: NXB Trẻ, NXB Kim Đồng..."/>
                        </div>

                        <!-- Giá bán -->
                        <div class="col-md-6">
                            <label class="form-label fw-semibold">Giá bán (VNĐ) <span class="text-danger">*</span></label>
                            <input type="number" step="0.01" name="price" class="form-control" value="${book.price}" required 
                                   placeholder="Ví dụ: 110000"/>
                        </div>

                        <!-- Ngày xuất bản -->
                        <div class="col-md-6">
                            <label class="form-label fw-semibold">Ngày xuất bản</label>
                            <input type="date" name="publish_date" class="form-control" value="${book.publish_date}"/>
                        </div>

                        <!-- Số lượng trong kho -->
                        <div class="col-md-6">
                            <label class="form-label fw-semibold">Số lượng trong kho <span class="text-danger">*</span></label>
                            <input type="number" name="quantity" class="form-control" value="${!isAdd ? book.quantity : 50}" required/>
                        </div>

                        <!-- Tên ảnh bìa -->
                        <div class="col-md-12">
                            <label class="form-label fw-semibold">Tên file ảnh bìa (trong thư mục /images/)</label>
                            <input type="text" name="cover_image" class="form-control" value="${book.cover_image}" 
                                   placeholder="Ví dụ: matbiec.jpg, default-book.jpg..."/>
                            <small class="text-muted">Nhập tên file ảnh bìa hoặc để trống để sử dụng ảnh mặc định.</small>
                        </div>

                        <!-- Mô tả nội dung -->
                        <div class="col-md-12">
                            <label class="form-label fw-semibold">Tóm tắt mô tả nội dung</label>
                            <textarea name="description" class="form-control" rows="4" 
                                      placeholder="Mô tả ngắn gọn về nội dung tác phẩm...">${book.description}</textarea>
                        </div>
                    </div>

                    <!-- Nút thao tác -->
                    <div class="d-flex justify-content-end gap-2 mt-4 pt-3 border-top">
                        <a href="<c:url value='/admin/books'/>" class="btn btn-secondary">
                            <i class="bi bi-x-circle me-1"></i>Hủy bỏ
                        </a>
                        <button type="submit" class="btn ${isAdd ? 'btn-success' : 'btn-warning'} fw-bold px-4">
                            <i class="bi ${isAdd ? 'bi-check-circle' : 'bi-save'} me-1"></i>
                            ${isAdd ? 'Lưu Sách Mới' : 'Cập Nhật Thay Đổi'}
                        </button>
                    </div>

                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
