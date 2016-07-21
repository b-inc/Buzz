package com.binc.buzz.bean;

import android.graphics.Bitmap;

/**
 * Created by yashbuch on 05/05/16.
 */
public class BuzzInfo {
    String text_data;

    public String getImageUrl() {
        return img_url;
    }

    public void setImageUrl(String img_url) {
        this.img_url = img_url;
    }

    String img_url;

    public Bitmap getImageBitmap() {
        return image_bm;
    }

    public void setImageBitmap(Bitmap image_bm) {
        this.image_bm = image_bm;
    }

    Bitmap image_bm;
    int mime;

    public int getMime() {
        return mime;
    }

    public void setMime(int mime) {
        this.mime = mime;
    }

    public String getTextData() {
        return text_data;
    }

    public void setTextData(String text_data) {
        this.text_data = text_data;
    }
}
