package com.ii.subtitle.editor;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ii.subtitle.editor.SubtitleText.Type;

public class SubParser extends SubtitlesParser
{

	private static final Pattern boldPattern = Pattern.compile("\\{([Yy]:b)\\}");
	private static final Pattern italicsPattern = Pattern.compile("\\{([Yy]:i)\\}");
	private static final Pattern underlinePattern = Pattern.compile("\\{([Yy]:u)\\}");
	
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

		for (int i = start; i < subtitleRaw.length; i++)
		{
			Subtitle sub = new Subtitle();
			sub.setSubtitleNumber(i - start);
			String framesString = getFramesString(subtitleRaw[i]);
			if(framesString == null)
			{
				continue;
			}
			int[] frames = getFrames(framesString);
			String textContent = subtitleRaw[i].substring(framesString.length());
			SubtitleText text = getText(textContent);
			SubtitleItem item = new SubtitleItem(frames[0], frames[1], text);
			subList.add(item);
		}
		
		this.setSubtitles(new Subtitles(subList, framesPerSecond));
	}
	
	private SubtitleText getText(String subtitleString)
	{
		int previousLength = subtitleString.length();
		subtitleString = boldPattern.matcher(subtitleString).replaceAll("");
		boolean bold = previousLength == subtitleString.length();
		
		previousLength = subtitleString.length();
		subtitleString = italicsPattern.matcher(subtitleString).replaceAll("");
		boolean italics = previousLength == subtitleString.length();
		
		previousLength = subtitleString.length();
		subtitleString = underlinePattern.matcher(subtitleString).replaceAll("");
		boolean underline = previousLength == subtitleString.length();
		//TODO check for typeface
		
		TextGroup root = new TextGroup(Type.ROOT);
		TextGroup currentGroup = root;
		if(bold)
		{
			currentGroup = addFromatGroup(currentGroup, Type.BOLD);
		}
		if(italics)
		{
			currentGroup = addFromatGroup(currentGroup, Type.ITALICS);
		}
		if(underline)
		{
			currentGroup = addFromatGroup(currentGroup, Type.UNDERLINE);
		}
		
		addText(currentGroup, subtitleString);
		
		return root;
	}
	
	private void addText(TextGroup currentGroup, String text)
	{
		String[] lines = text.split("|");
		for (int i = 0; i < lines.length; i++)
		{
			String line = lines[i];
			if(line.equals(""))
			{
				continue;
			}
			if(i != lines.length)
			{
				TextGroup lineGroup = new TextGroup(Type.LINE);
				lineGroup.addChild(new TextLeaf(line));
				currentGroup.addChild(lineGroup);
			}
			else
			{
				TextLeaf leaf = new TextLeaf(line);
				currentGroup.addChild(leaf);
			}
		}
	}

	private TextGroup addFromatGroup(TextGroup currentGroup, Type type)
	{
		TextGroup group = new TextGroup(type);
		currentGroup.addChild(group);
		return group;
	}
	
	private String getFramesString(String subtitleString)
	{
		Pattern p = Pattern.compile("\\{(\\d)++\\}\\{(\\d)++\\}");
		Matcher matcher = p.matcher(subtitleString);
		if(matcher.find() && matcher.start() == 0)
		{
			return subtitleString.substring(matcher.start(), matcher.end());
		}
		return null;
	}
	
	private int[] getFrames(String framesString)
	{
		String[] framesComponents = framesString.split("[\\{\\}]");
		return new int[]{Integer.parseInt(framesComponents[1]), Integer.parseInt(framesComponents[3])};
	}

}
