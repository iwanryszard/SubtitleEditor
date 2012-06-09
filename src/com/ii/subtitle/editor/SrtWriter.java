package com.ii.subtitle.editor;

import java.io.IOException;
import java.io.OutputStream;

public class SrtWriter extends AbstractSubtitlesWriter
{

	public SrtWriter(OutputStream stream)
	{
		super(stream);
	}

	@Override
	public void onSubtitleStarted(int index) throws IOException
	{
		writer.write(String.valueOf(index));
		writer.newLine();

	}

	@Override
	public void onSubtitleStartDuration(int duration) throws IOException
	{
		String value = getSrtStringDuration(duration);
		writer.write(value);
	}

	@Override
	public void onSubtitleEndDuration(int duration) throws IOException
	{
		writer.write(" --> ");
		String value = getSrtStringDuration(duration);
		writer.write(value);
	}

	private String getSrtStringDuration(int duration) throws IOException
	{
		int milliseconds = duration % 1000;
		duration %= 1000;
		int seconds = duration % 60;
		duration %= 60;
		int minutes = duration % 60;
		duration %= 60;
		int hours = duration;
		return String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, milliseconds);
	}

	@Override
	public void onSubtitleStartText() throws IOException
	{
		writer.newLine();
	}

	@Override
	public void onTextNewLine() throws IOException
	{
		writer.newLine();
	}

	@Override
	public void onBoldTextBegin() throws IOException
	{
		writer.write("<b>");
	}

	@Override
	public void onBoldTextEnd() throws IOException
	{
		writer.write("</b>");
	}

	@Override
	public void onItalicsTextBegin() throws IOException
	{
		writer.write("<i>");
	}

	@Override
	public void onItalicsTextEnd() throws IOException
	{
		writer.write("</i>");
	}

	@Override
	public void onUnderlineTextBegin() throws IOException
	{
		writer.write("<u>");
	}

	@Override
	public void onUnderlineTextEnd() throws IOException
	{
		writer.write("</u>");
	}

	@Override
	public void onTypefaceBegin(String typeFaceName) throws IOException
	{
		//do nothing
	}

	@Override
	public void onTypefaceEnd(String typeFaceName) throws IOException
	{
		//do nothing
	}

	@Override
	public void onSubtitleEnded(int index) throws IOException
	{
		writer.newLine();
		writer.newLine();
	}

	@Override
	public void onTextAdded(String text) throws IOException
	{
		writer.write(text);
	}

}
