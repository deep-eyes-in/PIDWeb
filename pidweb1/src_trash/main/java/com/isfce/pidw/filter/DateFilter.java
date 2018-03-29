package com.isfce.pidw.filter;


import java.util.Date;


@lombok.Data
public class DateFilter {
    private Date myDate;

    public DateFilter() {
        this.myDate = new Date();
    }

    
    
	public Date getLoadingStartDate() {
		return myDate;
	}
	
	
}







