package com.ii.subtitle.editor;

import java.io.IOException;
import java.io.OutputStream;

public class SubtitleHTMLWriter extends SrtWriter
{

	private boolean addCenteredTag;

	public SubtitleHTMLWriter(OutputStream stream, boolean center)
	{
		super(stream);
		addCenteredTag = center;
	}
	
	@Override
	public void onSubtitleStartText() throws IOException
	{
		
	}

	@Override
	public void onSubtitleEndDuration(int duration) throws IOException
	{

	}

	@Override
	public void onSubtitleStartDuration(int duration) throws IOException
	{

	}

	@Override
	public void onTextNewLine() throws IOException
	{
		writer.append("<br />");
	}

	@Override
	public void onSubtitleStarted(int index) throws IOException
	{
		if (addCenteredTag)
			writer.append("<center>");
	}

	@Override
	public void onSubtitleEnded(int index) throws IOException
	{
		if (addCenteredTag)
			writer.append("</center>");
	}

}
