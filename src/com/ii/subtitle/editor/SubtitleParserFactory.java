package com.ii.subtitle.editor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SubtitleParserFactory
{

	public static SubtitlesParser getSubtitlesParser(InputStream stream, String extension)
	{
		if (stream == null)
		{
			throw new IllegalStateException("Input stream cannot be null!");
		}
		String content = readContent(stream);
		SubtitlesType type = getSubtitlesType(content, extension);

		switch (type)
		{
		case SRT:
			return new SrtParser(content);

		case SUB:
			return new SubParser(content);

		default:
			return null;
		}

	}

	private static SubtitlesType getSubtitlesType(String content, String extension)
	{
		
		// TODO use regular expressions to check if there is occurrence of supported types
		if(extension.equalsIgnoreCase("srt"))
		{
			return SubtitlesType.SRT;
		}
		if(extension.equalsIgnoreCase("sub"))
		{
			return SubtitlesType.SUB;
		}
		return SubtitlesType.UNKNOWN;
	}

	private static String readContent(InputStream stream)
	{
		StringBuilder input = new StringBuilder();

		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line;
			while ((line = reader.readLine()) != null)
			{
				input.append(line).append("\n");
			}

			return input.toString();
		}
		catch (IOException e)
		{
			return "";
		}
	}

}
