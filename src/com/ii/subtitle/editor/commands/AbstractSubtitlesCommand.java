package com.ii.subtitle.editor.commands;

import com.ii.subtitle.editor.Command;
import com.ii.subtitle.editor.Subtitles;

public abstract class AbstractSubtitlesCommand implements Command
{
	protected Subtitles subtitles;
	
	public AbstractSubtitlesCommand(Subtitles subtitles)
	{
		this.subtitles = subtitles;
	}
	
	protected abstract boolean internalExecute();
	
	protected abstract boolean internalUndo();
	
	protected void executeAfterFireTableDataChanged()
	{
		
	}
	
	protected void undoAfterFireTableDataChanged()
	{
		
	}
	
	@Override
	public boolean execute()
	{
		boolean result = internalExecute();		
		return result;
	}

	@Override
	public boolean undo()
	{
		boolean result = internalUndo();	
		return result;
	}

}
