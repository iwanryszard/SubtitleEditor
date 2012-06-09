package com.ii.subtitle.editor;

import java.util.ArrayList;

public class SubParser extends SubtitlesParser
{

	public SubParser(String content)
	{
		super(content);
	}

	@Override
	public void parse() throws WrongFormatException
	{

		// TODO change model as well as parsing
		String content = this.getContent();

		double framesPerSecond;

		ArrayList<Subtitle> subList = new ArrayList<>();

		String[] subtitleRaw = content.split("\\n");
		boolean hasFramerate = (subtitleRaw.length > 0);
		int start = 0;
		if (hasFramerate)
		{
			if (subtitleRaw[0].startsWith("﻿{1}{1}"))
			{
				start = 1;
				framesPerSecond = Double.parseDouble(subtitleRaw[0].replace("﻿{1}{1}", ""));
			}
			if (subtitleRaw[0].startsWith("{1}{1}"))
			{
				start = 1;
				framesPerSecond = Double.parseDouble(subtitleRaw[0].replace("{1}{1}", ""));
			}
		}

		int count = 0;
		String[] subtitleAttributes;
		for (int i = start; i < subtitleRaw.length; i++)
		{
			Subtitle sub = new Subtitle();
			count++;
			sub.setSubtitleNumber(count);
			subtitleAttributes = subtitleRaw[i].split("[{}]");
			if (subtitleAttributes.length > 1)
			{
				sub.setStartFrame(subtitleAttributes[1]);
			}
			if (subtitleAttributes.length > 3)
			{
				sub.setEndFrame(subtitleAttributes[3]);
			}
			for (int j = 4; j < subtitleAttributes.length; j++)
			{
				if (subtitleAttributes[j].equals("y:b"))
				{
					sub.setBold(true);
					subtitleAttributes[j] = "";
				}
				if (subtitleAttributes[j].equals("y:u"))
				{
					sub.setUnderline(true);
					subtitleAttributes[j] = "";
				}
				if (subtitleAttributes[j].equals("y:i"))
				{
					sub.setItalics(true);
					subtitleAttributes[j] = "";
				}
				sub.addContent(subtitleAttributes[j]);
			}
			subList.add(sub);
		}
		
		// TODO set parsed subtitles
		this.setSubtitles(null);
	}

}
