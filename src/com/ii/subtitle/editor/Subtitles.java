package com.ii.subtitle.editor;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Subtitles
{

	private ArrayList<SubtitleItem> subtitles;
	private double frameRatePerSecond;

	public Subtitles(ArrayList<SubtitleItem> subtitles)
	{
		this(subtitles, -1);
	}

	public Subtitles(ArrayList<SubtitleItem> subtitles, double frameRatePerSecond)
	{
		this.subtitles = subtitles;
		this.frameRatePerSecond = frameRatePerSecond;
	}

	public ArrayList<SubtitleItem> getSubtitles()
	{
		return subtitles;
	}

	public void setSubtitles(ArrayList<SubtitleItem> subtitles)
	{
		this.subtitles = subtitles;
	}

	public double getFrameRatePerSecond()
	{
		return frameRatePerSecond;
	}

	public void setFrameRatePerSecond(double frameRatePerSecond)
	{
		this.frameRatePerSecond = frameRatePerSecond;
	}

	// used for getting subtitle item text content in a jtable row
	public String getSubtitleTextSingleLine(int index)
	{
		SubtitleItem item = subtitles.get(index);
		ArrayList<SubtitleItem> sub = new ArrayList<>();
		sub.add(item);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		SubtitlesWriteDirector director = new SubtitlesWriteDirector(sub, new SubtitleSingleLineTextWriter(stream));
		director.write();
		return new String(stream.toByteArray());
	}

}
