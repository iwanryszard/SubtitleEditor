package com.ii.subtitle.input;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ii.subtitle.model.SubtitleFormat;
import com.ii.subtitle.model.SubtitleItem;
import com.ii.subtitle.model.SubtitleText;
import com.ii.subtitle.model.Subtitles;
import com.ii.subtitle.model.TextGroup;
import com.ii.subtitle.model.TextLeaf;
import com.ii.subtitle.model.SubtitleText.Type;

public class SrtParser extends SubtitlesParser
{

	private static final Pattern TIMES_STRING_PATTERN = Pattern.compile("(\\d\\d):(\\d\\d):(\\d\\d),(\\d\\d\\d) --> (\\d\\d):(\\d\\d):(\\d\\d),(\\d\\d\\d)");
	private static final Pattern TEXT_PART_PATTERN = Pattern.compile("(<[biu]>)|(</[biu]>)");
	private static final Pattern ITEM_SEPARATOR_PATTERN = Pattern.compile("(\\n\\n|\\A)\\d+\\n", Pattern.MULTILINE);
	private static final Pattern TIME_STRING_PATTERN = Pattern.compile("(\\d\\d):(\\d\\d):(\\d\\d),(\\d\\d\\d)");
	
	public static SubtitlesParser createParser(String content)
	{
		return new SrtParser(content);
	}

	public static int getTime(String hours, String minutes, String seconds, String miliseconds)
	{
		return Integer.parseInt(hours) * 3600 * 1000 + Integer.parseInt(minutes) * 60 * 1000 + Integer.parseInt(seconds) * 1000 + Integer.parseInt(miliseconds);
	}

	public static int getTime(String input)
	{
		Matcher matcher = TIME_STRING_PATTERN.matcher(input);
		if (matcher.find() && matcher.start() == 0 && matcher.end() == input.length() - 1)
		{
			return getTime(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
		}
		return -1;
	}

	SrtParser(String content)
	{
		super(content);
	}

	@Override
	public void parse() throws WrongFormatException
	{
		ArrayList<SubtitleItem> subList = new ArrayList<>();

		String content = this.getContent();
		Matcher m = ITEM_SEPARATOR_PATTERN.matcher(content);
		m.find();
		int start = m.end();
		while (m.find())
		{
			int end = m.start();
			String itemString = content.substring(start, end);
			start = m.end();
			SubtitleItem item = parseItem(itemString);
			if (item != null)
			{
				subList.add(item);
			}
		}
		if (start < content.length())
		{
			SubtitleItem item = parseItem(content.substring(start));
			if (item != null)
			{
				subList.add(item);
			}
		}
		this.setSubtitles(new Subtitles(subList, SubtitleFormat.SUBRIP));
	}

	private SubtitleItem parseItem(String itemString)
	{
		int[] times = getTimes(itemString);
		String subtitleText = times != null ? itemString.substring(Math.min(times[2] + 1, itemString.length())) : null;
		SubtitleText text = subtitleText != null ? getText(subtitleText) : null;
		SubtitleItem item = text != null ? new SubtitleItem(times[0], times[1], text) : null;
		return item;
	}

	// TODO fix the hack with index returned in result
	private int[] getTimes(String subtitleString)
	{
		Matcher matcher = TIMES_STRING_PATTERN.matcher(subtitleString);
		if (matcher.find() && matcher.start() == 0)
		{
			int start = getTime(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
			int end = getTime(matcher.group(5), matcher.group(6), matcher.group(7), matcher.group(8));
			return new int[] { start, end, matcher.end() };
		}
		return null;
	}

	private List<String> preDecodeLines(String textString)
	{
		List<String> currentlyOpenedTags = new ArrayList<>();
		int start = 0;
		int end = 0;
		List<String> linesList = new ArrayList<>();
		while ((end = textString.indexOf('\n', start)) != -1)
		{
			String line = textString.substring(start, end);
			if (line.length() > 0)
			{
				line = getPreDecodedLine(line, currentlyOpenedTags);
				linesList.add(line);
			}
			start = end + 1;
		}
		if (start < textString.length())
		{
			String line = textString.substring(start);
			line = getPreDecodedLine(line, currentlyOpenedTags);
			linesList.add(line);
		}
		return linesList;
	}

	private String getPreDecodedLine(String line, List<String> openedTags)
	{
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < openedTags.size(); i++)
		{
			builder.append(openedTags.get(i));
		}
		builder.append(line);
		openedTags.clear();
		line = builder.toString();
		Matcher mathcer = TEXT_PART_PATTERN.matcher(line);
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

	protected SubtitleText getText(String textString)
	{

		TextGroup root = new TextGroup(Type.ROOT);
		List<String> preDecodedLines = preDecodeLines(textString);
		for (int i = 0; i < preDecodedLines.size(); i++)
		{
			String line = preDecodedLines.get(i);
			if (i != preDecodedLines.size() - 1)
			{
				TextGroup lineGroup = new TextGroup(Type.LINE);
				addTextParts(lineGroup, line);
				root.addChild(lineGroup);
			}
			else
			{
				addTextParts(root, line);
			}
		}
		return root;
	}

	private void addTextParts(TextGroup group, String text)
	{
		Matcher mathcer = TEXT_PART_PATTERN.matcher(text);
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
						addTextParts(formattedGroup, text.substring(openTagEnd, mathcer.start()));
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
}
