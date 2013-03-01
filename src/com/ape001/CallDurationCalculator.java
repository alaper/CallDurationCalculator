package com.ape001;

import org.joda.time.*;

public class CallDurationCalculator {

	private LocalTime peakStart;
	private LocalTime peakEnd;
	private DateTime callStart;
	private DateTime callEnd;
	private int peakSeconds;
	private int offPeakSeconds;
	private Interval callTime;
		
	public void setPeakStart(int hour, int minute)
	{
		peakStart = new LocalTime(hour, minute);
	}
	
	public void setPeakEnd(int hour, int minute)
	{	
		peakEnd = new LocalTime(hour, minute);
	}

	public LocalTime getPeakStart()
	{
		return peakStart;
	}
	
	public LocalTime getPeakEnd()
	{
		return peakEnd;
	}
	
	public void setCallStart(DateTime callStart)
	{
		this.callStart = callStart;
	}
	
	public void setCallEnd(DateTime callEnd)
	{
		this.callEnd = callEnd;
	}
	
	public int calcPeakSeconds()
	{
	
		peakSeconds += calcPeakSecondsForDay(false);
		
		if(multiDayCall() == true)
			peakSeconds += calcPeakSecondsForDay(true);
		
		return peakSeconds;
			
	}
	
	
	public int calcPeakSecondsForDay(boolean DayPlus)
	{
		DateTime peakStartDateTime;
		DateTime peakEndDateTime;
	
		if(DayPlus == true)
		{
		peakStartDateTime = setNewLocalTime(callStart.plusDays(1), peakStart);
		peakEndDateTime = setNewLocalTime(callStart.plusDays(1), peakEnd);
		}else
		{
		peakStartDateTime = setNewLocalTime(callStart, peakStart);
		peakEndDateTime = setNewLocalTime(callStart, peakEnd);	
		}
		
		Interval peakTime = new Interval(peakStartDateTime, peakEndDateTime);
		callTime = new Interval(callStart, callEnd);
	
		if(peakTime.overlap(callTime)==null)
			{return 0;}
		else
		{return (int)peakTime.overlap(callTime).toDurationMillis()/1000;}
				
	}
	
	private boolean multiDayCall ()
	{
		if(callStart.getDayOfMonth() != callEnd.getDayOfMonth())
			return true;
		
		return false;
	}
	
	public int calcOffPeakSeconds()
	{
		offPeakSeconds = (int)(callTime.toDurationMillis()/1000) - peakSeconds;
		
		return offPeakSeconds;
		
	}
	
	
	
	
	private DateTime setNewLocalTime(DateTime aDateTime, LocalTime aTime)
	{
		return new DateTime(aDateTime.getYear(), aDateTime.getMonthOfYear(), aDateTime.getDayOfMonth(), aTime.getHourOfDay(), aTime.getMinuteOfHour(),0);
	}
	
	
	
		
	public static void main(String[] args)
	{
		
		
		CallDurationCalculator callDurCalc = new CallDurationCalculator();
		
		callDurCalc.setPeakStart(9, 0);
		callDurCalc.setPeakEnd(17, 0);
		
			
		callDurCalc.setCallStart(new DateTime(2013,02,10,8,0,0));
		callDurCalc.setCallEnd(new DateTime(2013,02,11,6,40,0));
		
		System.out.println("Peak Seconds: " + callDurCalc.calcPeakSeconds());
		System.out.println("OffPeak Seconds: " + callDurCalc.calcOffPeakSeconds());
			}
	
		
		
		
		
		

	
}
