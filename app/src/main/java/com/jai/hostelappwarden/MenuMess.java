package com.jai.hostelappwarden;

public class MenuMess {

    String breakfastmenu,dinnermenu,lunchmenu;

    public MenuMess()
    {

    }

    public MenuMess(String breakfastmenu, String dinnermenu, String lunchmenu) {
        this.breakfastmenu = breakfastmenu;
        this.dinnermenu = dinnermenu;
        this.lunchmenu = lunchmenu;
    }

    public String getBreakfastmenu() {
        return breakfastmenu;
    }

    public String getDinnermenu() {
        return dinnermenu;
    }

    public String getLunchmenu() {
        return lunchmenu;
    }
}
