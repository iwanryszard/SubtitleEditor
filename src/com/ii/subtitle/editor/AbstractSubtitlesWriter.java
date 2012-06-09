package com.ii.subtitle.editor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public abstract class AbstractSubtitlesWriter implements SubtitleGenerator
{
	private OutputStream stream;
	protected BufferedWriter writer;
	
	public AbstractSubtitlesWriter(OutputStream stream)
	{
		this.stream = stream;
	}
	
	@Override
	public void onGenerationStart() throws IOException
	{
		this.writer = new BufferedWriter(new OutputStreamWriter(stream));
	}
	
	@Override
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
	
}
