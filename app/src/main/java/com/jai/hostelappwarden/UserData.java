package com.jai.hostelappwarden;

import java.io.Serializable;
import java.io.SerializablePermission;

public class UserData implements Serializable {

    String date,fullName,hostelName,ifdenytext,purpose,requestedtime,status,time,transid,usermail,wardenrespondedtime;

    public UserData()
    {

    }

    public UserData(String date, String fullName, String hostelName, String ifdenytext, String purpose, String requestedtime, String status, String time, String transid, String usermail, String wardenrespondedtime) {
        this.date = date;
        this.fullName = fullName;
        this.hostelName = hostelName;
        this.ifdenytext = ifdenytext;
        this.purpose = purpose;
        this.requestedtime = requestedtime;
        this.status = status;
        this.time = time;
        this.transid = transid;
        this.usermail = usermail;
        this.wardenrespondedtime = wardenrespondedtime;
    }

    public String getDate() {
        return date;
    }

    public String getFullName() {
        return fullName;
    }

    public String getHostelName() {
        return hostelName;
    }

    public String getIfdenytext() {
        return ifdenytext;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getRequestedtime() {
        return requestedtime;
    }

    public String getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }

    public String getTransid() {
        return transid;
    }

    public String getUsermail() {
        return usermail;
    }

    public String getWardenrespondedtime() {
        return wardenrespondedtime;
    }


}