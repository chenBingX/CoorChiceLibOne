package com.chenbing.coorchicelibone.CustemViews.filter;

/**
 * Created by coorchice on 2017/11/15.
 */

public class FilterDetailData implements Cloneable{

    private String content;
    private String image;

    public FilterDetailData(String content, String image) {
        this.content = content;
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
