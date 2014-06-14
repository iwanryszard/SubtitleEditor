package com.ii.subtitle.model;


public abstract class SubtitleText
{
	
	public static enum Type {ROOT, TEXT, LINE, BOLD, ITALICS, UNDERLINE, TYPEFACE}
	
	private Type type;
	
	public SubtitleText(Type type)
	{
		this.type = type;
	}
	
	public Type getType()
	{
		return type;
	}
	
	public abstract void acceptVisitor(SubtitleTextVisitor visitor);
	
	
	
}
