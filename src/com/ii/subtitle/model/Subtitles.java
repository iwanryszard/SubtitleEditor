package com.ii.subtitle.model;

import java.util.ArrayList;
import java.util.List;

public class Subtitles extends ArrayList<SubtitleItem>
{
	
	private static final long serialVersionUID = 8057481440980075348L;
	
	private SubtitleFormat format;
	private double frameRatePerSecond;
	private boolean isFrames;

	public Subtitles(List<SubtitleItem> subtitles, SubtitleFormat format)
	{
		this(subtitles, format, -1);
	}

	public Subtitles(List<SubtitleItem> subtitles, SubtitleFormat format, double frameRatePerSecond)
	{
		this.addAll(subtitles);
		this.format = format;
		this.frameRatePerSecond = frameRatePerSecond;
		this.isFrames = frameRatePerSecond > 0;
	}
	
	public SavedState createSavedState()
	{
		SavedState s = new SavedState();
		s.frameRatePerSecond = frameRatePerSecond;
		s.isFrames = isFrames;
		s.subtitles = new ArrayList<>();
		s.subtitles.addAll(this);
		return s;
	}
	
	public void restoreToState(SavedState s)
	{
		this.frameRatePerSecond = s.frameRatePerSecond;
		this.isFrames = s.isFrames;
		this.clear();
		this.addAll(s.subtitles);
	}

	public double getFrameRatePerSecond()
	{
		return frameRatePerSecond;
	}

	public void setFrameRatePerSecond(double frameRatePerSecond)
	{
		this.frameRatePerSecond = frameRatePerSecond;
	}
	
	public boolean isInFrames()
	{
		return isFrames;
	}
	
	public void setInFrames(boolean isFrames){
		this.isFrames = isFrames;
	}
	
	public int getStart(int index)
	{
		return this.get(index).getStart();
	}
	
	public void setStart(int index, int start)
	{
		this.get(index).setStart(start);
	}
	
	public int getEnd(int index)
	{
		return this.get(index).getEnd();
	}
	
	public void setEnd(int index, int end)
	{
		this.get(index).setEnd(end);
	}
	
	public int getDuration(int index)
	{
		return this.get(index).getEnd() - this.get(index).getStart();
	}
	
	public SubtitleText getText(int index)
	{
		return this.get(index).getText();
	}
	
	public SubtitleFormat getFormat()
	{
		return format;
	}

	public void setFormat(SubtitleFormat format)
	{
		this.format = format;
	}

	public static class SavedState
	{
		private ArrayList<SubtitleItem> subtitles;
		private double frameRatePerSecond;
		private boolean isFrames;
		
		private SavedState()
		{
			
		}
	}
	

}
