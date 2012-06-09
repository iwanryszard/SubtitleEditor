package com.ii.subtitle.editor;

public abstract class SubtitleTextParser
{
	private String formattedText;
	private SubtitleText parsedText;

	public SubtitleTextParser(String formattedText)
	{
		this.formattedText = formattedText;
	}

	protected String getFormatedText()
	{
		return this.formattedText;
	}

	public abstract void parse();
	
	public SubtitleText getResult()
	{
		return parsedText;
	}
	
	protected void setParsedtext(SubtitleText parsedText)
	{
		this.parsedText = parsedText;
	}
	
}
