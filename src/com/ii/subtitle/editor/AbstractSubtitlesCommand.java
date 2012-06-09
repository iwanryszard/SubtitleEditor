package com.ii.subtitle.editor;

public abstract class AbstractSubtitlesCommand extends BaseCommand
{
	protected SubsTableModel model;
	protected SubtitleList subsListCopy;
	protected Interface interf;
	protected Interface.Memento memento;
	
	public AbstractSubtitlesCommand(Interface interf, SubsTableModel model)
	{
		this.interf = interf;
		this.model = model;
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
		subsListCopy = new SubtitleList(model.getSubtitleList());
		
		interf.isSaved = false;
		
		boolean result = internalExecute();
		model.fireTableDataChanged();
		
		executeAfterFireTableDataChanged();
		
		return result;
	}

	@Override
	public boolean undo()
	{
		boolean result = internalUndo();
		
		interf.restoreFromMemento(memento);
		model.setSubtitleList(subsListCopy);
		model.fireTableDataChanged();
		
		undoAfterFireTableDataChanged();
		
		return result;
	}

}
