package com.ii.subtitle.model;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.ii.subtitle.output.SrtWriter;
import com.ii.subtitle.output.SubtitleHTMLWriter;
import com.ii.subtitle.output.SubtitleSingleLineTextWriter;
import com.ii.subtitle.output.SubtitlesWriteDirector;

public class Subtitles extends ArrayList<SubtitleItem>
{
	
	private static final long serialVersionUID = 8057481440980075348L;

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

	public String getSubtitleTextSingleLine(int index)
	{
		SubtitleItem item = this.get(index);
		ArrayList<SubtitleItem> sub = new ArrayList<>();
		sub.add(item);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		SubtitlesWriteDirector director = new SubtitlesWriteDirector(sub, new SubtitleSingleLineTextWriter(stream));
		director.write();
		return new String(stream.toByteArray());
	}
	
	public String getSubtitleHTMLFormattedText(int index, boolean edit)
	{
		SubtitleItem item = this.get(index);
		ArrayList<SubtitleItem> sub = new ArrayList<>();
		sub.add(item);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		SubtitlesWriteDirector director = new SubtitlesWriteDirector(sub, new SubtitleHTMLWriter(stream, !edit));
		director.write();
		return new String(stream.toByteArray());
	}
	
	public String getStart(int index)
	{
		return getFormatedOffset(this.get(index).getStart(), isFrames);
	}
	
	public String getEnd(int index)
	{
		return getFormatedOffset(this.get(index).getEnd(), isFrames);
	}
	
	public String getDuration(int index)
	{
		return getFormatedOffset(Math.max(0, this.get(index).getEnd() - this.get(index).getStart()), isFrames);
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
