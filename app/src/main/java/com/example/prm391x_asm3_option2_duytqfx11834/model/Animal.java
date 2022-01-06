package com.example.prm391x_asm3_option2_duytqfx11834.model;

import android.graphics.Bitmap;

public class Animal {
    private final String topicName; //Tên nhóm động vật
    private final Bitmap photo; //Ảnh đại diện
    private final Bitmap photoBg; //Ảnh bìa
    private final String name; //Tên động vật
    private final String content; //Nội dung mô tả
    private boolean isFav; //Được yêu thích hay không

    /**
     * Phương thức Animal khởi tạo đối tượng là một con vật
     *
     * @param topicName tên nhóm động vật
     * @param photo ảnh đại diện
     * @param photoBg ảnh bìa
     * @param name tên động vật
     * @param content nội dung mô tả
     * @param isFav được yêu thích hay không
     * */
    public Animal(String topicName, Bitmap photo, Bitmap photoBg, String name, String content, boolean isFav) {
        this.topicName = topicName;
        this.photo = photo;
        this.photoBg = photoBg;
        this.name = name;
        this.content = content;
        this.isFav = isFav;
    }

    /**
     * Phương thức getTopicName lấy giá trị tên nhóm động vật
     *
     * @return Giá trị String là tên nhóm động vật
     * */
    public String getTopicName() {
        return topicName;
    }

    /**
     * Phương thức getPhoto lấy giá trị ảnh đại diện
     *
     * @return Giá trị Bitmap là ảnh đại diện
     * */
    public Bitmap getPhoto() {
        return photo;
    }

    /**
     * Phương thức getPhotoBg lấy giá trị ảnh bìa
     *
     * @return Giá trị Bitmap là ảnh bìa
     * */
    public Bitmap getPhotoBg() {
        return photoBg;
    }

    /**
     * Phương thức getName lấy giá trị tên con vật
     *
     * @return Giá trị String là tên con vật
     * */
    public String getName() {
        return name;
    }

    /**
     * Phương thức getContent lấy giá trị nội dung mô tả
     *
     * @return Giá trị String là nội dung mô tả
     * */
    public String getContent() {
        return content;
    }

    /**
     * Phương thức isFav xác định xem con vật có được yêu thích không
     *
     * @return Giá trị boolean là kết quả kiểm tra (true nếu được yêu thích)
     * */
    public boolean isFav() {
        return isFav;
    }

    /**
     * Phương thức setFav thiết lập con vật có được yêu thích không
     *
     * @param fav Giá trị boolean muốn thiết lập
     * */
    public void setFav(boolean fav) {
        isFav = fav;
    }
}
