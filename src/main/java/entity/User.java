package entity;

import java.sql.Timestamp;

public class User {

    public static final String DEFAULT_AVATAR_NAME = "default-avatar.jpg";

    public static final Integer USERSTATE_UNACTIVE = 0;

    public static final Integer USERSTATE_ACTIVE = 1;

    public static final Integer USERSTATE_DISABLED = 2;

    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Timestamp createTime;
    private Integer state;
    private String avatar;

    public User() {}

    public User(String username,String password,String email,String phone,Integer state,String avatar) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
