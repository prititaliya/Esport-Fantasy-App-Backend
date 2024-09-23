package com.example.androidproject;

public class databaseHelper {
    String lName,description,details,matchId,startindate,finishingdate,date;

    public databaseHelper() {
    }

    public String getStartindate() {
        return startindate;
    }

    public void setStartindate(String startindate) {
        this.startindate = startindate;
    }

    public String getFinishingdate() {
        return finishingdate;
    }

    public void setFinishingdate(String finishingdate) {
        this.finishingdate = finishingdate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public databaseHelper(String lName, String description, String details, String matchId, String startindate, String finishingdate, String date) {
        this.lName = lName;
        this.description = description;
        this.details = details;
        this.matchId = matchId;
        this.startindate = startindate;
        this.finishingdate = finishingdate;
        this.date = date;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }
}
