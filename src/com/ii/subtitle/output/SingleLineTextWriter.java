package com.ii.subtitle.output;

import java.io.IOException;
import java.io.OutputStream;

public class SingleLineTextWriter extends AbstractSubtitlesWriter
{

	public SingleLineTextWriter(OutputStream stream)
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
		writer.write(text);
	}

}
