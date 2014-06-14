package com.ii.subtitle.input;

import com.ii.subtitle.model.Subtitles;

public abstract class SubtitlesParser
{

	private String content;
	private Subtitles subtitles;

	public static class WrongFormatException extends Exception
	{

		private static final long serialVersionUID = -1074439044337255975L;

		public WrongFormatException(String subtitleParserType)
		{
			super("Unable to parse content in " + subtitleParserType);
		}

	}

	public static SubtitlesParser createParser(String content)
	{
		return null;
	}

	protected SubtitlesParser(String content)
	{
		this.setContent(content);
	}

	protected final void setContent(String content)
	{
		if (content == null)
		{
			throw new IllegalArgumentException("Contnet cannot be null!");
		}
		this.content = content;
	}

	protected String getContent()
	{
		return this.content;
	}

	protected void setSubtitles(Subtitles subtitles)
	{
		this.subtitles = subtitles;
	}

	public Subtitles getSubtitles()
	{
		return this.subtitles;
	}

	public abstract void parse() throws WrongFormatException;

}
