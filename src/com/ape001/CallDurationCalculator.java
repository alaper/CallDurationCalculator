package com.ape001;

import org.joda.time.*;

public class CallDurationCalculator {

	private LocalTime peakStart;
	private LocalTime peakEnd;
	private DateTime callStart;
	private DateTime callEnd;
	private Interval callInterval;
	private Interval peakRateInterval;
	private DateTime callPeakStart;
	private DateTime callPeakEnd;
	private long peakSeconds;
	private long offPeakSeconds;
	
	
	
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
		setCallPeakStart();
		setCallPeakEnd();
		
	}
	
	
	private void calcSeconds()
	{
		long Days;
		
		DateTime callStartDate = new DateTime(callStart.getYear(),callStart.getMonthOfYear(), callStart.getDayOfMonth(),0,0,0);
		DateTime callEndDate = new DateTime(callEnd.getYear(), callEnd.getMonthOfYear(), callEnd.getDayOfMonth(),0,0,0);
		
		Days = new Interval(callStartDate, callEndDate).toDuration().getStandardDays();
		
		if(Days==0)
		{
			peakSeconds = calcPeakSeconds();
			offPeakSeconds = calcOffPeakSeconds();
		}
		else
		{
			
		}
		
	
		
		
	}
	
	public DateTime getCallStart()
	{
		return callStart;
	}
	
	public DateTime getCallEnd()
	{
		
		return callEnd;
		
	}
	
	private long calcPeakSeconds()
	{
		callInterval = new Interval(callStart, callEnd);
		peakRateInterval = new Interval(callPeakStart, callPeakEnd);
		
		if(callInterval.overlap(peakRateInterval) == null)
				return 0;
		else
			return callInterval.overlap(peakRateInterval).toDurationMillis()/1000;
	}
	
	public long calcOffPeakSeconds()
	{
		callInterval = new Interval(callStart, callEnd);
		return callInterval.toDurationMillis()/1000 - calcPeakSeconds();
	}
	
	
	private void setCallPeakStart()
	{
		callPeakStart = new DateTime(callStart.getYear(), callStart.getMonthOfYear(), callStart.getDayOfMonth(),0,0,0);
		callPeakStart = callPeakStart.plusSeconds(peakStart.getMillisOfDay()/1000);
	}
	
	private void setCallPeakEnd()
	{
		callPeakEnd = new DateTime(callStart.getYear(), callStart.getMonthOfYear(), callStart.getDayOfMonth(),0,0,0);
		callPeakEnd = callPeakEnd.plusSeconds(peakEnd.getMillisOfDay()/1000);
	}
	
	public DateTime getCallPeakStart()
	{
		return callPeakStart;
	}
	
	public DateTime getCallPeakEnd()
	{
		return callPeakEnd;
	}
	
	public static void main(String[] args)
	{
		
		long callDuration;
		Interval callInterval;
		Interval peakRateInterval;
		
		
		CallDurationCalculator callDurCalc = new CallDurationCalculator();
		
		callDurCalc.setPeakStart(9, 0);
		callDurCalc.setPeakEnd(17, 0);
		
		System.out.println(callDurCalc.getPeakStart());
		
		System.out.println(System.currentTimeMillis());
		
		callDurCalc.setCallStart(new DateTime(2013,02,10,8,0,0));
		callDurCalc.setCallEnd(new DateTime(2013,02,10,19,40,0));
			
		System.out.println(callDurCalc.getCallPeakStart().toString());
		System.out.println(callDurCalc.getCallPeakEnd().toString());
		
		System.out.println("Peak seconds to charge:" + callDurCalc.getPeakSeconds());
		System.out.println("Offpeak seconds to charge: " + callDurCalc.getOffPeakSeconds());
	}
	
}
