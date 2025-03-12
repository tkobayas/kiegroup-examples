package com.sample;

import java.util.Date;

public class Span {

    private Date startDate;
    private Date endDate;
    
    
    
    public Span() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Span(Date startDate, Date endDate) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Span [startDate=" + startDate + ", endDate=" + endDate + "]";
    }
    
    
}
