package com.ii.subtitle.editor;

import java.io.IOException;
import java.io.OutputStream;

public class SubtitleSingleLineTextWriter extends AbstractSubtitlesWriter
{

	public SubtitleSingleLineTextWriter(OutputStream stream)
	{
		super(stream);
	}

	@Override
	public void onTextNewLine() throws IOException
	{
		writer.write('|');
	}

	@Override
	public void onTextAdded(String text) throws IOException
	{
		writer.write("text");
	}

}
