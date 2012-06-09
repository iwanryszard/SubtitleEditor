package com.ii.subtitle.editor;

import java.io.IOException;
import java.io.OutputStream;

public class SubWriter extends AbstractSubtitlesWriter
{

	private double framesPerSecond;
	private int nestedBoldLevel = 0;
	private int nestedItalicsLevel = 0;
	private int nestedUnderlineLevel = 0;
	private int nestedTypefaceLevel = 0;
	
	public SubWriter(OutputStream stream, double framesPerSecond)
	{
		super(stream);
		this.framesPerSecond = framesPerSecond;
	}
	
	@Override
	public void onGenerationStart() throws IOException
	{
		super.onGenerationStart();
		writer.write("{1}{1}" + framesPerSecond);
	}

	@Override
	public void onSubtitleStarted(int index) throws IOException
	{
		writer.newLine();
	}

	@Override
	public void onSubtitleStartDuration(int duration) throws IOException
	{
		writer.write("{" + duration + "}");
	}

	@Override
	public void onSubtitleEndDuration(int duration) throws IOException
	{
		writer.write("{" + duration + "}");

	}

	@Override
	public void onSubtitleStartText() throws IOException
	{
		//do nothing
	}

	@Override
	public void onTextNewLine() throws IOException
	{
		writer.write("|");
	}

	@Override
	public void onBoldTextBegin() throws IOException
	{
		if(nestedBoldLevel == 0)
		{
			writer.write("{y:b}");
		}
		nestedBoldLevel++;
	}

	@Override
	public void onBoldTextEnd() throws IOException
	{
		//do nothing
	}

	@Override
	public void onItalicsTextBegin() throws IOException
	{
		if(nestedItalicsLevel == 0)
		{
			writer.write("{y:i}");
		}
		nestedItalicsLevel++;
	}

	@Override
	public void onItalicsTextEnd() throws IOException
	{
		//do nothing
	}

	@Override
	public void onUnderlineTextBegin() throws IOException
	{
		if(nestedUnderlineLevel == 0)
		{
			writer.write("{y:u}");
		}
		nestedUnderlineLevel++;
	}

	@Override
	public void onUnderlineTextEnd() throws IOException
	{
		//do nothing
	}

	@Override
	public void onTypefaceBegin(String typeFaceName) throws IOException
	{
		if(nestedTypefaceLevel == 0)
		{
			writer.write("{f:" + typeFaceName + "}");
		}
		nestedTypefaceLevel++;
		
	}

	@Override
	public void onTypefaceEnd(String typeFaceName) throws IOException
	{
		//do nothing
	}

	@Override
	public void onTextAdded(String text) throws IOException
	{
		writer.write(text);
	}

	@Override
	public void onSubtitleEnded(int index) throws IOException
	{
		nestedBoldLevel = 0;
		nestedItalicsLevel = 0;
		nestedUnderlineLevel = 0;
		nestedTypefaceLevel = 0;
	}

}
