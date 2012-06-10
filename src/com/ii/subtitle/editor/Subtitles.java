package com.ii.subtitle.editor;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import com.ii.subtitle.editor.SubtitlesParser.WrongFormatException;

public class Subtitles
{
	
	public static String getFormatedOffset(int offset, boolean isFrames)
	{
		if(isFrames)
		{
			return String.valueOf(offset);
		}
		else
		{
			return SrtWriter.getSrtStringDuration(offset);
		}
	}
	
	private ArrayList<SubtitleItem> subtitles;
	private double frameRatePerSecond;
	private boolean isFrames;

	public Subtitles(ArrayList<SubtitleItem> subtitles)
	{
		this(subtitles, -1);
		isFrames = false;
	}

	public Subtitles(ArrayList<SubtitleItem> subtitles, double frameRatePerSecond)
	{
		this.subtitles = subtitles;
		this.frameRatePerSecond = frameRatePerSecond;
		this.isFrames = true;
	}
	
	public SavedState createSavedState()
	{
		SavedState s = new SavedState();
		s.frameRatePerSecond = frameRatePerSecond;
		s.isFrames = isFrames;
		
		ArrayList<SubtitleItem> subtitles = new ArrayList<>();
		for(int i = 0; i < this.subtitles.size(); ++i)
		{
			subtitles.add(new SubtitleItem(this.subtitles.get(i)));
		}
		s.subtitles = subtitles;
		
		return s;
	}
	
	public void restoreToState(SavedState s)
	{
		this.frameRatePerSecond = s.frameRatePerSecond;
		this.isFrames = s.isFrames;
		this.subtitles = s.subtitles;
	}

	public ArrayList<SubtitleItem> getSubtitles()
	{
		return subtitles;
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
	
	public String getSubtitleHTMLFormattedText(int index, boolean edit)
	{
		SubtitleItem item = subtitles.get(index);
		ArrayList<SubtitleItem> sub = new ArrayList<>();
		sub.add(item);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		SubtitlesWriteDirector director = new SubtitlesWriteDirector(sub, new SubtitleHTMLWriter(stream, !edit));
		director.write();
		return new String(stream.toByteArray());
	}
	
	public String getStart(int index)
	{
		return getFormatedOffset(subtitles.get(index).getStart(), isFrames);
	}
	
	public String getEnd(int index)
	{
		return getFormatedOffset(subtitles.get(index).getEnd(), isFrames);
	}
	
	public String getDuration(int index)
	{
		return getFormatedOffset(Math.max(0, subtitles.get(index).getEnd() - subtitles.get(index).getStart()), isFrames);
	}
	
	public void update(int rowIndex, String start, String end, String duration, String text)
	{
		UserInputSubtitleParser parser = new UserInputSubtitleParser(text, start, end, duration, isFrames);
		try
		{
			parser.parse();
			if(parser.getSubtitle() != null)
			{
				this.subtitles.set(rowIndex, parser.getSubtitle());
			}
		}
		catch (WrongFormatException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void moveUpByOne(int startSubtitle, int selectedCount)
	{
		if(startSubtitle == 0)
		{
			return;
		}
		SubtitleItem s = subtitles.get(startSubtitle - 1);
		subtitles.remove(startSubtitle - 1);
		subtitles.add(startSubtitle + selectedCount - 1, s);
	}
	
	public void moveDownByOne(int startIndex, int count)
	{
		if(startIndex + count >= subtitles.size())
		{
			return;
		}
		SubtitleItem s = subtitles.get(startIndex + count);
		subtitles.remove(startIndex + count);
		subtitles.add(startIndex, s);
	}
	
	public int getCount()
	{
		return this.subtitles.size();
	}
	
	public void removeInRange(int startIndex, int count)
	{
		int start = Math.max(0, startIndex);
		int end = Math.min(startIndex + count - 1, subtitles.size() - 1);
		for (int i = end; i >= start; i--)
		{
			subtitles.remove(i);
		}
	}
	
	public void insertNewSubtitle(int index)
	{
		//TODO could use more intelligent start and end calculation
		TextLeaf text = new TextLeaf("");
		SubtitleItem s = new SubtitleItem(0, 0, text);
		subtitles.add(index, s);
	}
	
	public void switchToFrames()
	{
		if(!isFrames)
		{
			for (SubtitleItem item : subtitles)
			{
				int start = (int) Math.round(item.getStart() * frameRatePerSecond / 1000);
				int end = (int) Math.round(item.getEnd() * frameRatePerSecond / 1000);
				item.setStart(start);
				item.setEnd(end);
			}
		}
		isFrames = true;
	}
	
	public void switchToTime()
	{
		if(isFrames)
		{
			for (SubtitleItem item : subtitles)
			{
				int start = (int) Math.round(item.getStart() / frameRatePerSecond * 1000);
				int end = (int) Math.round(item.getEnd() / frameRatePerSecond * 1000);
				item.setStart(start);
				item.setEnd(end);
			}
		}
		isFrames = false;
	}

	public void translate(String delta, int startIndex, int count)
	{
		delta = delta.trim();
		int translateDelta;
		if(isFrames)
		{
			translateDelta = Integer.parseInt(delta);
		}
		else
		{
			boolean negative = delta.startsWith("-");
			delta = negative ? delta.substring(1) : delta;
			translateDelta = SrtParser.getTime(delta);
		}
		
		int start = Math.max(0, startIndex);
		int end = Math.min(startIndex + count - 1, subtitles.size() - 1);
		for (int i = start; i <= end; i++)
		{
			SubtitleItem item = subtitles.get(i);
			item.setStart(item.getStart() + translateDelta);
			item.setEnd(item.getEnd() + translateDelta);
		}
	}
	
	public void interpolate(String startInterval, int startIndex, String endInterval, int count)
	{
		startInterval = startInterval.trim();
		endInterval = endInterval.trim();
		int start;
		int end;
		if(isFrames)
		{
			start = Integer.parseInt(startInterval);
			end = Integer.parseInt(endInterval);
		}
		else
		{
			start = SrtParser.getTime(startInterval);
			end = SrtParser.getTime(endInterval);
		}
		if (start >= end)
		{
			return;
		}
		
		startIndex = Math.max(0, startIndex);
		int endIndex = Math.min(startIndex + count - 1, subtitles.size() - 1);
		
		int oldStart = subtitles.get(startIndex).getStart();
		int oldEnd = subtitles.get(endIndex).getStart();
		
		//start = a * oldStart + b
		//end = a * oldEnd + b
		double a = (end - start) / (double) (oldEnd - oldStart);
		double b = end - a * oldEnd;
		
		for (int i = startIndex; i <= endIndex; i++)
		{
			SubtitleItem item = subtitles.get(i);
			item.setStart((int) Math.round(item.getStart() * a + b));
			item.setEnd((int) Math.round(item.getEnd() * a + b));
		}
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
