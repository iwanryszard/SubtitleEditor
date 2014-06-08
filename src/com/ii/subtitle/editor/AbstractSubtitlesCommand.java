package com.ii.subtitle.editor;

public abstract class AbstractSubtitlesCommand implements Command
{
	protected Subtitles subtitles;
	protected Interface interf;
	protected Interface.Memento memento;
	
	public AbstractSubtitlesCommand(Interface interf, Subtitles subtitles)
	{
		this.interf = interf;
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
		memento = interf.saveToMemento();
		interf.isSaved = false;
		
		boolean result = internalExecute();
		interf.notifyJtableDataChanged();
		
		executeAfterFireTableDataChanged();
		
		return result;
	}

	@Override
	public boolean undo()
	{
		boolean result = internalUndo();
		
		interf.restoreFromMemento(memento);
		interf.notifyJtableDataChanged();
		
		undoAfterFireTableDataChanged();
		
		return result;
	}

}
