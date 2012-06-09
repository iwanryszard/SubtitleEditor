package com.ii.subtitle.editor;

public abstract class BaseCommand 
{
	public abstract boolean execute();
	
	public abstract boolean undo();
}
