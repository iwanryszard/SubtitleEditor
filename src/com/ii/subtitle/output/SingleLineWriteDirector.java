package com.ii.subtitle.output;

import java.io.IOException;

import com.ii.subtitle.model.Subtitles;

public class SingleLineWriteDirector extends WriteDirector
{

	private int index;
	
	public SingleLineWriteDirector(Subtitles subtitles, AbstractSubtitlesWriter writer, int index)
    {
	    super(subtitles, writer);
	    this.index = index;
    }
	
	@Override
	public void write()
	{
		try
		{
			writer.onGenerationStart();
			writeItem(index);
			writer.onGenerationEnd();

		}
		catch (IOException e)
		{
			try
			{
				writer.onGenerationEnd();
			}
			catch (Exception e1)
			{
				
			}
			e.printStackTrace();
		}

	}

}
