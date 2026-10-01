package vn.iotstar.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class User_24162144 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String email;
    private String fullname;
    private int phone;
    private String passwd;
    private Timestamp signup_date;
    private Timestamp last_login;
    private boolean is_admin;

    public User_24162144() {
    }

    public User_24162144(int id, String email, String fullname, int phone, String passwd, Timestamp signup_date,
            Timestamp last_login, boolean is_admin) {
        this.id = id;
        this.email = email;
        this.fullname = fullname;
        this.phone = phone;
        this.passwd = passwd;
        this.signup_date = signup_date;
        this.last_login = last_login;
        this.is_admin = is_admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Timestamp getSignup_date() {
        return signup_date;
    }

    public void setSignup_date(Timestamp signup_date) {
        this.signup_date = signup_date;
    }

    public Timestamp getLast_login() {
        return last_login;
    }

    public void setLast_login(Timestamp last_login) {
        this.last_login = last_login;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public boolean getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    // Getter/setter tương thích với biểu thức EL ${sessionScope.account.isAdmin}
    public boolean isAdmin() {
        return is_admin;
    }

    public boolean getIsAdmin() {
        return is_admin;
    }

    public void setAdmin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    @Override
    public String toString() {
        return "User_24162144 [id=" + id + ", email=" + email + ", fullname=" + fullname + ", phone=" + phone
                + ", is_admin=" + is_admin + "]";
    }
}
