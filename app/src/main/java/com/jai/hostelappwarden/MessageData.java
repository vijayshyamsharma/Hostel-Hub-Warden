package com.jai.hostelappwarden;

public class MessageData {

    String id,message,time;

    public MessageData()
    {

    }

    public MessageData(String id,String message, String time) {
        this.id=id;
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public String getId() {
        return id;
    }
}
