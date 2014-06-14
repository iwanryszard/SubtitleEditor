package com.ii.subtitle.model;


public class SubtitleItem
{
	
	private int start;
	private int end;
	private SubtitleText text;
	
	public SubtitleItem(int start, int end, SubtitleText text)
	{
		this.start = start;
		this.end = end;
		this.text = text;
	}
	
	public SubtitleItem(SubtitleItem item)
	{
		this(item.start, item.end, item.text);
	}

	public int getStart()
	{
		return start;
	}

	public void setStart(int start)
	{
		this.start = start;
	}

	public int getEnd()
	{
		return end;
	}

	public void setEnd(int end)
	{
		this.end = end;
	}

	public SubtitleText getText()
	{
		return text;
	}

	public void setText(SubtitleText text)
	{
		this.text = text;
	}
	
}
