package com.example.hengky.proiftraintracker;

import ir.mirrajabi.searchdialog.core.Searchable;

/**
 * Created by Lenovo Iyoss on 10/02/2018.
 */

public class SearchModel implements Searchable {
    private String mTitle;

    public SearchModel(String mTitle){
        this.mTitle = mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }
}
