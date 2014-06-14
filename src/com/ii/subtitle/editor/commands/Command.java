package com.ii.subtitle.editor.commands;

public interface Command 
{
	public boolean execute();
	
	public boolean undo();
	
}
