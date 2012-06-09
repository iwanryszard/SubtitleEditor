package com.ii.subtitle.editor;

import java.util.ArrayList;

public class SubtitleList
{
	private ArrayList<Subtitle> subtitles;
	
	public SubtitleList(ArrayList<Subtitle> subtitles)
	{
		this.subtitles = subtitles;
	}
	
	//performs deep copy
	public SubtitleList(SubtitleList subsList)
	{
		this.subtitles = new ArrayList<Subtitle>();
		
		for(int i = 0; i < subsList.subtitles.size(); ++i)
		{
			this.subtitles.add(new Subtitle(subsList.subtitles.get(i)));
		}
	}
	
	public Subtitle getSubtitleFromIndex(int index)
	{
		return subtitles.get(index);
	}
	
	public void removeSubtitleFromIndex(int index)
	{
		subtitles.remove(index);
	}
	
	public void insertSubtitleAtIndex(int index, Subtitle sub)
	{
		subtitles.add(index, sub);
	}
	
	public int getSubtitlesCount()
	{
		return subtitles.size();
	}
}
