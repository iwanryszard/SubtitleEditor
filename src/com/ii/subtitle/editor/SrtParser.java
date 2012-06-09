package com.ii.subtitle.editor;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ii.subtitle.editor.SubtitleText.Type;

public class SrtParser extends SubtitlesParser
{

	public SrtParser(String content)
	{
		super(content);
	}

	@Override
	public void parse() throws WrongFormatException
	{
		String content = this.getContent();

		content = "\n\n" + content;
		String[] subtitleRaw = content.split("(\\n\\n)(\\d+)(\\n)");

		ArrayList<SubtitleItem> subList = new ArrayList<>();
		for (int i = 1; i < subtitleRaw.length; i++)
		{
			String timesString = getTimeString(subtitleRaw[i]);
			if (timesString == null)
			{
				continue;
			}
			int[] times = getTimes(timesString);
			SubtitleText text = getText(subtitleRaw[i].substring(timesString.length()));
			if (text == null)
			{
				continue;
			}
			SubtitleItem item = new SubtitleItem(times[0], times[1], text);
			subList.add(item);
		}
		this.setSubtitles(new Subtitles(subList));

	}

	private String getTimeString(String subtitleString)
	{
		Pattern p = Pattern.compile("\\d\\d:\\d\\d:\\d\\d,\\d\\d\\d --> \\d\\d:\\d\\d:\\d\\d,\\d\\d\\d");
		Matcher matcher = p.matcher(subtitleString);
		if (matcher.find() && matcher.start() == 0)
		{
			return subtitleString.substring(matcher.start(), matcher.end());
		}
		return null;
	}

	private String[] preDecodeines(String textString)
	{
		ArrayList<String> currentlyOpenedTags = new ArrayList<>();
		String[] lines = textString.split("\\n++");
		for (int i = 0; i < lines.length; i++)
		{
			lines[i] = getPreDecodedLine(lines[i], currentlyOpenedTags);
		}
		return lines;
	}

	private String getPreDecodedLine(String line, ArrayList<String> openedTags)
	{
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < openedTags.size(); i++)
		{
			builder.append(openedTags.get(i));
		}
		openedTags.clear();
		line = builder.toString();
		Pattern p = Pattern.compile("(<[biu]>)|(</[biu]>)");
		Matcher mathcer = p.matcher(line);
		while (mathcer.find())
		{
			String match = mathcer.group();
			if (!match.startsWith("</"))
			{
				openedTags.add(match);
			}
			else if (openedTags.size() > 0)
			{
				openedTags.remove(openedTags.size() - 1);
			}
		}
		for (int i = openedTags.size() - 1; i >= 0; i--)
		{
			String tag = openedTags.get(i);
			if (tag.equals("<b>"))
			{
				builder.append("</b>");
			}
			else if (tag.equals("<i>"))
			{
				builder.append("</i>");
			}
			else if (tag.equals("<u>"))
			{
				builder.append("</i>");
			}
		}
		return builder.toString();
	}

	private SubtitleText getText(String textString)
	{

		TextGroup root = new TextGroup(Type.ROOT);
		Pattern p = Pattern.compile("(<[biu]>)|(</[biu]>)");
		String[] preDecodedLines = preDecodeines(textString);
		for (int i = 0; i < preDecodedLines.length; i++)
		{
			String line = preDecodedLines[i];
			if (i != preDecodedLines.length)
			{
				TextGroup lineGroup = new TextGroup(Type.LINE);
				addTextParts(lineGroup, line, p);
				root.addChild(lineGroup);
			}
			else
			{
				addTextParts(root, line, p);
			}

		}

		return root;
	}

	private void addTextParts(TextGroup group, String text, Pattern p)
	{
		Matcher mathcer = p.matcher(text);
		int level = 0;
		int openTagEnd = -1;
		int closeTagEnd = -1;
		while (mathcer.find())
		{
			String match = mathcer.group();
			if (!match.startsWith("</"))
			{
				if (level == 0)
				{
					if (closeTagEnd != -1 && mathcer.start() - closeTagEnd > 0)
					{
						group.addChild(new TextLeaf(text.substring(closeTagEnd, mathcer.start())));
					}
					else if (closeTagEnd == -1 && mathcer.start() > 0)
					{
						group.addChild(new TextLeaf(text.substring(0, mathcer.start())));
					}
					openTagEnd = mathcer.end();
				}
				level++;
			}
			else
			{
				level--;
				if (level == 0 && mathcer.start() - openTagEnd > 0)
				{
					closeTagEnd = mathcer.end();
					char c = match.charAt(2);
					TextGroup formattedGroup = null;
					switch (c)
					{
					case 'b':
						formattedGroup = new TextGroup(Type.BOLD);
						break;

					case 'i':
						formattedGroup = new TextGroup(Type.ITALICS);
						break;

					case 'u':
						formattedGroup = new TextGroup(Type.UNDERLINE);
						break;

					default:
						break;
					}
					if (formattedGroup != null)
					{
						group.addChild(formattedGroup);
						addTextParts(formattedGroup, text.substring(openTagEnd, mathcer.start()), p);
					}
				}
			}
		}

		if (closeTagEnd == -1 || closeTagEnd < text.length())
		{
			closeTagEnd = Math.max(0, closeTagEnd);
			group.addChild(new TextLeaf(text.substring(closeTagEnd, text.length())));
		}

	}

	private int[] getTimes(String times)
	{
		String[] timesComponents = times.split(" --> ");
		if (timesComponents.length > 1)
		{
			int start = getTime(timesComponents[0]);
			int end = getTime(timesComponents[1]);
			if (start != -1 && end != -1)
			{
				return new int[] { start, end };
			}
		}
		return null;
	}

	private int getTime(String timeString)
	{
		String startTimeArray[] = timeString.split("[:,]");
		if (startTimeArray.length > 3)
		{
			int miliseconds = Integer.parseInt(startTimeArray[3]);
			int seconds = Integer.parseInt(startTimeArray[2]);
			int minutes = Integer.parseInt(startTimeArray[1]);
			int hours = Integer.parseInt(startTimeArray[0]);
			return hours * 3600 * 1000 + minutes * 60 * 1000 + seconds * 1000 + miliseconds;
		}
		return -1;
	}

}
