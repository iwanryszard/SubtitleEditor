package com.ii.subtitle.model;

import java.util.ArrayList;
import java.util.List;

public class Subtitles
{

	private SubtitleFormat format;
	private double frameRatePerSecondOriginal;
	private double frameRatePerSecond;
	private boolean isFramesOriginal;
	private boolean isFrames;
	private List<SubtitleItem> items;

	public Subtitles(List<SubtitleItem> subtitles, SubtitleFormat format)
	{
		this(subtitles, format, -1);
	}

	public Subtitles(List<SubtitleItem> subtitles, SubtitleFormat format, double frameRatePerSecond)
	{
		this.items = new ArrayList<>(subtitles);
		this.format = format;
		this.frameRatePerSecondOriginal = frameRatePerSecond;
		this.frameRatePerSecond = frameRatePerSecond;
		this.isFrames = frameRatePerSecond > 0;
		this.isFramesOriginal = isFrames;
	}

	public SavedState createSavedState()
	{
		SavedState s = new SavedState();
		s.frameRatePerSecond = frameRatePerSecond;
		s.isFrames = isFrames;
		s.subtitles = new ArrayList<>();
		s.subtitles.addAll(this.items);
		return s;
	}

	public void restoreToState(SavedState s)
	{
		this.frameRatePerSecond = s.frameRatePerSecond;
		this.isFrames = s.isFrames;
		this.items.clear();
		this.items.addAll(s.subtitles);
	}

	public double getFrameRatePerSecond()
	{
		return frameRatePerSecond;
	}

	public void setFrameRatePerSecond(double frameRatePerSecond)
	{
		if (this.frameRatePerSecondOriginal <= 0)
		{
			this.frameRatePerSecondOriginal = frameRatePerSecond;
		}
		this.frameRatePerSecond = frameRatePerSecond;
	}

	public boolean isInFrames()
	{
		return isFrames;
	}

	public void setInFrames(boolean isFrames)
	{
		this.isFrames = isFrames;
	}

	public int getStart(int index)
	{
		int start = this.items.get(index).getStart();
		start = convertOffsetOutput(start);
		return start;
	}

	public void setStart(int index, int start)
	{
		start = convertOffsetInput(start);
		this.items.get(index).setStart(start);
	}

	public int getEnd(int index)
	{
		int end = this.items.get(index).getEnd();
		end = convertOffsetOutput(end);
		return end;
	}

	public void setEnd(int index, int end)
	{
		end = convertOffsetInput(end);
		this.items.get(index).setEnd(end);
	}

	public int getDuration(int index)
	{
		return this.getEnd(index) - this.getStart(index);
	}

	public SubtitleText getText(int index)
	{
		return this.items.get(index).getText();
	}

	public SubtitleFormat getFormat()
	{
		return format;
	}

	public void setFormat(SubtitleFormat format)
	{
		this.format = format;
	}

	public List<SubtitleItem> getItems()
	{
		return this.items;
	}

	private int convertOffsetOutput(int offset)
	{
		int result = convertOffset(offset, this.isFramesOriginal, this.isFrames, this.frameRatePerSecondOriginal, this.frameRatePerSecond);
		return result;
	}
	
	private int convertOffsetInput(int offset)
	{
		int result = convertOffset(offset, this.isFrames, this.isFramesOriginal, this.frameRatePerSecond, this.frameRatePerSecondOriginal);
		return result;
	}
	
	private int convertOffset(int offset, boolean sourceInframes, boolean resultInFrames, double sourceFrameRate, double resultFrameRate)
	{
		int result = offset;
		if (!sourceInframes && resultInFrames)
		{
			//milliseconds --> frames
			result = (int) Math.round(offset * resultFrameRate / 1000);
		}
		else if (sourceInframes && !resultInFrames)
		{
			//frames --> milliseconds
			result = (int) Math.round(offset * 1000 / resultFrameRate);
		}
		else if (sourceInframes && resultInFrames)
		{
			//frames --> frames but different frame rates
			result = (int) Math.round(offset * (resultFrameRate / sourceFrameRate));
		}
		return result;
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
