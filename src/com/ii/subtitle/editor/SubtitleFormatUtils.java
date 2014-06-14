package com.ii.subtitle.editor;

import java.io.ByteArrayOutputStream;

import com.ii.subtitle.model.Subtitles;
import com.ii.subtitle.output.SingleLineWriteDirector;
import com.ii.subtitle.output.SrtWriter;
import com.ii.subtitle.output.HTMLWriter;
import com.ii.subtitle.output.SingleLineTextWriter;
import com.ii.subtitle.output.WriteDirector;

public class SubtitleFormatUtils
{
	public static String getTime(int time){
		return SrtWriter.getSrtStringDuration(time);
	}
	
	public static String getFrames(int frames){
		return String.valueOf(frames);
	}
	
	public static String getOffset(int offset, boolean isFrames)
	{
		String result = isFrames ? String.valueOf(offset) : getTime(offset);
		return result;
	}
	
	public static String getStart(Subtitles subtitles, int index){
		return getOffset(subtitles.getStart(index), subtitles.isInFrames());
	}
	
	public static String getEnd(Subtitles subtitles, int index){
		return getOffset(subtitles.getEnd(index), subtitles.isInFrames());
	}
	
	public static String getDuration(Subtitles subtitles, int index){
		return getOffset(subtitles.getEnd(index) - subtitles.getStart(index), subtitles.isInFrames());
	}
	
	public static String getSubtitleTextSingleLine(Subtitles subtitles, int index)
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		WriteDirector director = new SingleLineWriteDirector(subtitles, new SingleLineTextWriter(stream), index);
		director.write();
		return new String(stream.toByteArray());
	}
	
	public static String getSubtitleHTMLText(Subtitles subtitles, int index, boolean edit)
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		WriteDirector director = new SingleLineWriteDirector(subtitles, new HTMLWriter(stream, !edit), index);
		director.write();
		return new String(stream.toByteArray());
	}
	
	private SubtitleFormatUtils(){
		
	}
}
