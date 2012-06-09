package com.ii.subtitle.editor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public abstract class AbstractSubtitlesWriter
{
	private OutputStream stream;
	protected BufferedWriter writer;

	public AbstractSubtitlesWriter(OutputStream stream)
	{
		this.stream = stream;
	}

	public void onGenerationStart() throws IOException
	{
		this.writer = new BufferedWriter(new OutputStreamWriter(stream));
	}

	public void onGenerationEnd()
	{
		try
		{
			writer.flush();
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void onSubtitleStarted(int index) throws IOException
	{

	}

	public void onSubtitleStartDuration(int duration) throws IOException
	{

	}

	public void onSubtitleEndDuration(int duration) throws IOException
	{

	}

	public void onSubtitleStartText() throws IOException
	{

	}

	public void onTextNewLine() throws IOException
	{

	}

	public void onBoldTextBegin() throws IOException
	{

	}

	public void onBoldTextEnd() throws IOException
	{

	}

	public void onItalicsTextBegin() throws IOException
	{

	}

	public void onItalicsTextEnd() throws IOException
	{

	}

	public void onUnderlineTextBegin() throws IOException
	{

	}

	public void onUnderlineTextEnd() throws IOException
	{

	}

	public void onTypefaceBegin(String typeFaceName) throws IOException
	{

	}

	public void onTypefaceEnd(String typeFaceName) throws IOException
	{

	}

	public void onTextAdded(String text) throws IOException
	{

	}

	public void onSubtitleEnded(int index) throws IOException
	{

	}

}
