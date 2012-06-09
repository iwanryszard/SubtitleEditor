package com.ii.subtitle.editor;

import java.util.ArrayList;

public class SrtParser extends SubtitlesParser
{

	public SrtParser(String content)
	{
		super(content);
	}

	@Override
	public void parse() throws WrongFormatException
	{
		// TODO change model as well as parsing

		String content = this.getContent();

		content = "\n\n" + content;
		String[] subtitleRaw = content.split("(\\n\\n)(\\d+)(\\n)");
		String[] subtitleAttributes1;
		String[] subtitleAttributes2;

		ArrayList<Subtitle> subList = new ArrayList<>();
		for (int i = 1; i < subtitleRaw.length; i++)
		{
			Subtitle sub = new Subtitle();
			sub.setSubtitleNumber(i);
			subtitleAttributes1 = subtitleRaw[i].split(" --> ");
			if (subtitleAttributes1.length > 1)
			{
				sub.calculateStartTime(subtitleAttributes1[0]);
				subtitleAttributes2 = subtitleAttributes1[1].split("\\n");
				if (subtitleAttributes2.length > 1)
				{
					sub.calculateEndTime(subtitleAttributes2[0]);
					if (subtitleAttributes2[1].contains("<b>"))
					{
						sub.setBold(true);
						subtitleAttributes2[1] = subtitleAttributes2[1].replace("<b>", "");
					}
					if (subtitleAttributes2[1].contains("<u>"))
					{
						sub.setUnderline(true);
						subtitleAttributes2[1] = subtitleAttributes2[1].replace("<u>", "");
					}
					if (subtitleAttributes2[1].contains("<i>"))
					{
						sub.setItalics(true);
						subtitleAttributes2[1] = subtitleAttributes2[1].replace("<i>", "");
					}
					for (int j = 1; j < subtitleAttributes2.length - 1; j++)
					{
						sub.addContent(subtitleAttributes2[j] + "|");
					}
					subtitleAttributes2[subtitleAttributes2.length - 1] = subtitleAttributes2[subtitleAttributes2.length - 1].replace("</b>", "");
					subtitleAttributes2[subtitleAttributes2.length - 1] = subtitleAttributes2[subtitleAttributes2.length - 1].replace("</u>", "");
					subtitleAttributes2[subtitleAttributes2.length - 1] = subtitleAttributes2[subtitleAttributes2.length - 1].replace("</i>", "");
					sub.addContent(subtitleAttributes2[subtitleAttributes2.length - 1]);
					subList.add(i - 1, sub);
				}
			}
		}

		// TODO set parsed subtitles
		this.setSubtitles(null);

	}

}
