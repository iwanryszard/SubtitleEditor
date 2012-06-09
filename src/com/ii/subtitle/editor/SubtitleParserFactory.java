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
		SubtitlesType type = getSubtitlesType(content);

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

	private static SubtitlesType getSubtitlesType(String content)
	{

		// TODO implementation
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
