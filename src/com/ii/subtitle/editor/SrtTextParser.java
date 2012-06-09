package com.ii.subtitle.editor;

public class SrtTextParser extends SubtitleTextParser
{

	public SrtTextParser(String formattedText)
	{
		super(formattedText);
	}

	@Override
	public void parse()
	{
		//TODO implementation
		String content = getFormatedText();
		
		setParsedtext(null);
		
	}

}
