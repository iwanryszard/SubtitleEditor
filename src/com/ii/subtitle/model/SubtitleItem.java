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

	int getStart()
	{
		return start;
	}

	void setStart(int start)
	{
		this.start = start;
	}

	int getEnd()
	{
		return end;
	}

	void setEnd(int end)
	{
		this.end = end;
	}

	SubtitleText getText()
	{
		return text;
	}

	void setText(SubtitleText text)
	{
		this.text = text;
	}
	
}
