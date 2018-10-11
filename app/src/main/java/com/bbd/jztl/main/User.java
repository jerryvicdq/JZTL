package com.bbd.jztl.main;

/**
 * 类描述：用户信息
 * 创建人：圈圈D
 * 创建时间：2018-10-10 17:23
 * <p>
 * "name": "用户名称",
 * "email": "邮箱",
 * "police_code": "警号",
 * "avatar": "用户头像地址",
 * "id_card": "身份证号码",
 * "login_times": "登录次数",
 * "tel": "联系电话",
 * "province": "省",
 * "city": "市",
 * "district": "县\/区",
 * "town": "镇\/街道",
 * "role_name": "角色：社区民警 | 区县经侦支大队民警"
 */
public class User {
    private String name;

    private String email;

    private String police_code;

    private String avatar;

    private String id_card;

    private String login_times;

    private String tel;

    private String province;

    private String city;

    private String district;

    private String town;

    private String role_name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPolice_code(String police_code) {
        this.police_code = police_code;
    }

    public String getPolice_code() {
        return this.police_code;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getId_card() {
        return this.id_card;
    }

    public void setLogin_times(String login_times) {
        this.login_times = login_times;
    }

    public String getLogin_times() {
        return this.login_times;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel() {
        return this.tel;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return this.province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getTown() {
        return this.town;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getRole_name() {
        return this.role_name;
    }
}
