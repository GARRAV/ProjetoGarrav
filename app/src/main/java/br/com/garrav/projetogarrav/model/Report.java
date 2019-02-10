package br.com.garrav.projetogarrav.model;

import java.util.Date;

public class Report {

    private long id;
    private long id_user;
    private String bugReport;
    private Date dateReport;

    public long getId() {
        return id;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public String getBugReport() {
        return bugReport;
    }

    public void setBugReport(String bugReport) {
        this.bugReport = bugReport;
    }

    public Date getDateReport() {
        return dateReport;
    }

    public void setDateReport(Date dateReport) {
        this.dateReport = dateReport;
    }
}
