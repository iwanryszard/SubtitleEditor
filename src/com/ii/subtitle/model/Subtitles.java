package com.ii.subtitle.model;

import java.util.ArrayList;
import java.util.List;

public class Subtitles
{

	private SubtitleFormat format;
	private double frameRatePerSecond;
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
		this.frameRatePerSecond = frameRatePerSecond;
		this.isFrames = frameRatePerSecond > 0;
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
		return this.items.get(index).getStart();
	}

	public void setStart(int index, int start)
	{
		this.items.get(index).setStart(start);
	}

	public int getEnd(int index)
	{
		return this.items.get(index).getEnd();
	}

	public void setEnd(int index, int end)
	{
		this.items.get(index).setEnd(end);
	}

	public int getDuration(int index)
	{
		return this.items.get(index).getEnd() - this.items.get(index).getStart();
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

	public static class SavedState
	{
		private ArrayList<SubtitleItem> subtitles;
		private double frameRatePerSecond;
		private boolean isFrames;

		private SavedState()
		{

		}
	}

	public List<SubtitleItem> getItems()
	{
		return this.items;
	}

}
