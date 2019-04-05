package cn.yanglj65.www.eloth_backend.entity;

import cn.yanglj65.www.eloth_backend.view.UserView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

@Entity
@Table(name = "user_t")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(UserView.UserCommonView.class)
    private int id;
    @Column(name = "user_name")
    @JsonView(UserView.UserCommonView.class)
    private String userName;
    @Column(name = "password")
    @JsonIgnore
    private String password;
    @Column(name = "access_token")
    @JsonView(UserView.UserCommonView.class)
    private String accessToken;
    @Column(name = "is_deleted")
    private boolean deleted = false;
    @Column(name = "create_time")
    @JsonView(UserView.UserCommonView.class)
    private String createTime;
    @Column(name = "role")
    @JsonView(UserView.UserCommonView.class)
    private String role = "COMMON";
    @Column(name = "phone")
    @JsonView(UserView.UserCommonView.class)
    private String phone;
    @Column(name = "img_url")
    @JsonView(UserView.UserCommonView.class)
    private String imgUrl;
    @Column(name = "nick_id")
    @JsonView(UserView.UserCommonView.class)
    private int nickId = 0;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getNickId() {
        return nickId;
    }

    public void setNickId(int nickId) {
        this.nickId = nickId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
