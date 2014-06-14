package com.ii.subtitle.editor;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ii.subtitle.editor.SubtitleText.Type;

public class SubParser extends SubtitlesParser
{

	private static final Pattern TEXT_STYLE_PATTERN = Pattern.compile("\\{[Yy]:(b?i?u?)\\}");
	private static final Pattern FRAMES_PATTERN = Pattern.compile("\\{(\\d++)\\}\\{(\\d++)\\}");

	public SubParser(String content)
	{
		super(content);
	}

	@Override
	public void parse() throws WrongFormatException
	{
		String content = this.getContent();
		double framesPerSecond = 23.976;
		ArrayList<SubtitleItem> subList = new ArrayList<>();

		String[] subtitleRaw = content.split("\\n");
		int start = 0;
		if (subtitleRaw.length > 0 && subtitleRaw[0].startsWith("{1}{1}"))
		{
			start = 1;
			framesPerSecond = Double.parseDouble(subtitleRaw[0].substring("{1}{1}".length()));
		}

		for (int i = start; i < subtitleRaw.length; i++)
		{
			int[] frames = getFrames(subtitleRaw[i]);
			if (frames == null)
			{
				continue;
			}
			String textContent = subtitleRaw[i].substring(frames[2]);
			SubtitleText text = getText(textContent);
			SubtitleItem item = new SubtitleItem(frames[0], frames[1], text);
			subList.add(item);
		}

		this.setSubtitles(new Subtitles(subList, framesPerSecond));
	}

	private SubtitleText getText(String subtitleString)
	{
		TextGroup root = new TextGroup(Type.ROOT);
		TextGroup currentGroup = root;

		Matcher m = TEXT_STYLE_PATTERN.matcher(subtitleString);
		if (m.find())
		{
			String styles = m.group(0);
			if (styles.indexOf('b') != -1)
			{
				currentGroup = addFromatGroup(currentGroup, Type.BOLD);
			}
			if (styles.indexOf('i') != -1)
			{
				currentGroup = addFromatGroup(currentGroup, Type.ITALICS);
			}
			if (styles.indexOf('u') != -1)
			{
				currentGroup = addFromatGroup(currentGroup, Type.UNDERLINE);
			}
			// TODO check for typeface
			subtitleString = subtitleString.substring(m.end());
		}
		addText(currentGroup, subtitleString);

		return root;
	}

	private void addText(TextGroup currentGroup, String text)
	{
		int start = 0;
		int end = 0;
		while ((end = text.indexOf('|', start)) != -1)
		{
			String line = text.substring(start, end);
			if (line.length() > 0)
			{
				TextGroup lineGroup = new TextGroup(Type.LINE);
				lineGroup.addChild(new TextLeaf(line));
				currentGroup.addChild(lineGroup);
			}
			start = end + 1;
		}
		if (start < text.length())
		{
			String line = text.substring(start);
			currentGroup.addChild(new TextLeaf(line));
		}
	}

	private TextGroup addFromatGroup(TextGroup currentGroup, Type type)
	{
		TextGroup group = new TextGroup(type);
		currentGroup.addChild(group);
		return group;
	}

	private int[] getFrames(String subtitleString)
	{
		Matcher matcher = FRAMES_PATTERN.matcher(subtitleString);
		if (matcher.find() && matcher.start() == 0)
		{
			int start = Integer.parseInt(matcher.group(1));
			int end = Integer.parseInt(matcher.group(2));
			return new int[] { start, end, matcher.end() };
		}
		return null;
	}

}
