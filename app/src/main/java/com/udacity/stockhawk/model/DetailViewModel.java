package com.udacity.stockhawk.model;

/**
 * Created by venugopalraog on 12/16/16.
 */
public class DetailViewModel {

    private String title;
    private String value;

    public DetailViewModel(String title, String value) {
        this.title = title;
        this.value = value;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
