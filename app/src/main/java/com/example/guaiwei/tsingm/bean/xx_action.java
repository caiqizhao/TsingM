package com.example.guaiwei.tsingm.bean;

public class xx_action {
    private int id;
    private String title;
    private String gif;
    private int mp4;
    private String action_type;
    private String zy_jiqun;
    private String qt_jiqun;
    private int yl_jpg;
    private String yl_str;
    private int jr_png;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGif() {
        return gif;
    }

    public void setGif(String gif) {
        this.gif = gif;
    }

    public int getMp4() {
        return mp4;
    }

    public void setMp4(int mp4) {
        this.mp4 = mp4;
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public String getZy_jiqun() {
        return zy_jiqun;
    }

    public void setZy_jiqun(String zy_jiqun) {
        this.zy_jiqun = zy_jiqun;
    }

    public String getQt_jiqun() {
        return qt_jiqun;
    }

    public void setQt_jiqun(String qt_jiqun) {
        this.qt_jiqun = qt_jiqun;
    }

    public int getYl_jpg() {
        return yl_jpg;
    }

    public void setYl_jpg(int yl_jpg) {
        this.yl_jpg = yl_jpg;
    }

    public String getYl_str() {
        return yl_str;
    }

    public void setYl_str(String yl_str) {
        this.yl_str = yl_str;
    }

    public int getJr_png() {
        return jr_png;
    }

    public void setJr_png(int jr_png) {
        this.jr_png = jr_png;
    }

    @Override
    public String toString() {
        return "xx_action{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", gif='" + gif + '\'' +
                ", mp4='" + mp4 + '\'' +
                ", action_type='" + action_type + '\'' +
                ", zy_jiqun='" + zy_jiqun + '\'' +
                ", qt_jiqun='" + qt_jiqun + '\'' +
                ", yl_jpg=" + yl_jpg +
                ", yl_str='" + yl_str + '\'' +
                ", jr_png=" + jr_png +
                '}';
    }
}
