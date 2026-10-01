package vn.iotstar.service.impl;

import java.util.List;

import vn.iotstar.dao.IRatingDao_24162144;
import vn.iotstar.dao.impl.RatingDaoImpl_24162144;
import vn.iotstar.model.Rating_24162144;
import vn.iotstar.service.IRatingService_24162144;

public class RatingServiceImpl_24162144 implements IRatingService_24162144 {
    private IRatingDao_24162144 ratingDao = new RatingDaoImpl_24162144();

    @Override
    public List<Rating_24162144> findByBookId(int bookId) {
        return ratingDao.findByBookId(bookId);
    }

    @Override
    public int countByBookId(int bookId) {
        return ratingDao.countByBookId(bookId);
    }

    @Override
    public double getAvgRatingByBookId(int bookId) {
        return ratingDao.getAvgRatingByBookId(bookId);
    }

    @Override
    public void insert(Rating_24162144 rating) {
        ratingDao.insert(rating);
    }
}
