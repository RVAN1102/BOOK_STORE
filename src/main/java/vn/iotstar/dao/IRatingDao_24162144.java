package vn.iotstar.dao;

import java.util.List;
import vn.iotstar.model.Rating_24162144;

public interface IRatingDao_24162144 {
    List<Rating_24162144> findByBookId(int bookId);
    int countByBookId(int bookId);
    double getAvgRatingByBookId(int bookId);
    void insert(Rating_24162144 rating);
}
