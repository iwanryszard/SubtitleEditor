package com.ii.subtitle.editor;

import java.util.ArrayList;

public class TextGroup extends SubtitleText
{
	private final ArrayList<SubtitleText> children;

	public TextGroup(SubtitleText parent, Type function)
	{
		super(function);
		this.children = new ArrayList<>();
	}

	public ArrayList<SubtitleText> getChildern()
	{
		return children;
	}
	
	public void addChild(SubtitleText textPart)
	{
		this.children.add(textPart);
	}

	@Override
	public void acceptVisitor(SubtitleTextVisitor visitor)
	{
		visitor.visitTextGroup(this);
	}

}