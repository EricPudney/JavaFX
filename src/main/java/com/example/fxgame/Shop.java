package com.example.fxgame;

public class Shop implements AppAwareController{
    private RPGApplication app;
    @Override
    public void setApp(RPGApplication app) {
        this.app = app;
    }
}
