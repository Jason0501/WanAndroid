package com.jason.www.http.response;

import java.util.List;

/**
 * @author：Jason
 * @date：2020/8/11 11:32
 * @email：1129847330@qq.com
 * @description:
 */
public class Login {
    public boolean admin;
    public int coinCount;
    public String email;
    public String icon;
    public int id;
    public String nickname;
    public String password;
    public String publicName;
    public String token;
    public int type;
    public String username;
    public List<String> chapterTops;
    public List<String> collectIds;

    @Override
    public String toString() {
        return "{" +
                "admin=" + admin +
                ", coinCount=" + coinCount +
                ", email='" + email + '\'' +
                ", icon='" + icon + '\'' +
                ", id=" + id +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", publicName='" + publicName + '\'' +
                ", token='" + token + '\'' +
                ", type=" + type +
                ", username='" + username + '\'' +
                ", chapterTops=" + chapterTops +
                ", collectIds=" + collectIds +
                '}';
    }
}