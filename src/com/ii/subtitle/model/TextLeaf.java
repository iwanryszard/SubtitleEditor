package com.ii.subtitle.model;

public class TextLeaf extends SubtitleText
{
	private String text;

	public TextLeaf(String text)
	{
		super(Type.TEXT);
		this.text = text;
	}
	
	public String getText()
	{
		return this.text;
	}

	@Override
	public void acceptVisitor(SubtitleTextVisitor visitor)
	{
		visitor.visitTextLeaf(this);
	}

}
