package com.ii.subtitle.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ii.subtitle.model.SubtitleFormat;

public class SubtitleParserFactory
{

	private static final Map<String, SubtitleFormat> FORMATS_BY_EXTENSION = getFormatsByExtension();
	private static final Map<SubtitleFormat, Class<? extends SubtitlesParser>> PARSERS_BY_FORMAT = getParsersByFormat();

	private static final Map<String, SubtitleFormat> getFormatsByExtension()
	{
		Map<String, SubtitleFormat> result = new HashMap<>();
		Set<SubtitleFormat> formats = EnumSet.allOf(SubtitleFormat.class);
		for (SubtitleFormat format : formats)
		{
			result.put(format.getExtension(), format);
		}

		return result;
	}
	
	private static final Map<SubtitleFormat, Class<? extends SubtitlesParser>> getParsersByFormat()
	{
		Map<SubtitleFormat, Class<? extends SubtitlesParser>> result = new HashMap<>();
		result.put(SubtitleFormat.MICRODVD, SubParser.class);
		result.put(SubtitleFormat.SUBRIP, SrtParser.class);
		return result;
	}

	public static SubtitlesParser getSubtitlesParser(InputStream stream, String extension)
	{
		if (stream == null)
		{
			throw new IllegalStateException("Input stream cannot be null!");
		}
		SubtitleFormat format = FORMATS_BY_EXTENSION.get(extension);
		String content = readContent(stream);
		return createParser(content, PARSERS_BY_FORMAT.get(format));
	}
	
	private static SubtitlesParser createParser(String content, Class<? extends SubtitlesParser> parserClass)
	{
		SubtitlesParser result = null;
		try
        {
	        Method method = parserClass.getDeclaredMethod("createParser", String.class);
	        result = (SubtitlesParser) method.invoke(null, content);
        }
        catch (Exception e)
        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		return result;
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
