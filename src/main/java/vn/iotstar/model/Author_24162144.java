package vn.iotstar.model;

import java.io.Serializable;
import java.sql.Date;

public class Author_24162144 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int author_id;
    private String author_name;
    private Date date_of_birth;

    public Author_24162144() {
    }

    public Author_24162144(int author_id, String author_name, Date date_of_birth) {
        this.author_id = author_id;
        this.author_name = author_name;
        this.date_of_birth = date_of_birth;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public int getAuthorId() {
        return author_id;
    }

    public void setAuthorId(int authorId) {
        this.author_id = authorId;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthorName() {
        return author_name;
    }

    public void setAuthorName(String authorName) {
        this.author_name = authorName;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public Date getDateOfBirth() {
        return date_of_birth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.date_of_birth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Author_24162144 [author_id=" + author_id + ", author_name=" + author_name + ", date_of_birth="
                + date_of_birth + "]";
    }
}
