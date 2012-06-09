package com.ii.subtitle.editor;

public class SubTextParser extends SubtitleTextParser
{

	public SubTextParser(String formattedText)
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
