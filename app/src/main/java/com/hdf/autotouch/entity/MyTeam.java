package com.hdf.autotouch.entity;

public class MyTeam {


    private String allFile;
    private String allUsdt;
    private String trusteeFee;
    private int    level;
    private int    teamPerson;
    private int    performance;
    private String yesterdayUsdt;

    public String getTrusteeFee() {
        return trusteeFee;
    }

    public void setTrusteeFee(String trusteeFee) {
        this.trusteeFee = trusteeFee;
    }

    public String getAllFile() {
        return allFile;
    }

    public void setAllFile(String allFile) {
        this.allFile = allFile;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTeamPerson() {
        return teamPerson;
    }

    public void setTeamPerson(int teamPerson) {
        this.teamPerson = teamPerson;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }



    public String getAllUsdt() {
        return allUsdt;
    }

    public void setAllUsdt(String allUsdt) {
        this.allUsdt = allUsdt;
    }

    public String getYesterdayUsdt() {
        return yesterdayUsdt;
    }

    public void setYesterdayUsdt(String yesterdayUsdt) {
        this.yesterdayUsdt = yesterdayUsdt;
    }
}
