package com.ii.subtitle.output;

import java.io.IOException;
import java.io.OutputStream;

public class HTMLWriter extends SrtWriter
{

	private boolean addCenteredTag;

	public HTMLWriter(OutputStream stream, boolean center)
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
