package com.ii.subtitle.editor;

import javax.swing.table.AbstractTableModel;

public class SubsTableModel extends AbstractTableModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7871409214981720369L;
	
	private static final String[] columns = new String[] {"Number", "Start", "End", "Content" };
	
	private SubtitleList subsList;
	
	public SubsTableModel(SubtitleList subsList)
	{
		this.subsList = subsList;
	}
	
	@Override
	public String getColumnName(int index)
	{
		return columns[index];
	}
	
	@Override
	public int getColumnCount()
	{
		return columns.length;
	}

	@Override
	public int getRowCount()
	{
		return subsList.getSubtitlesCount();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Subtitle sub = subsList.getSubtitleFromIndex(rowIndex);
		
		switch(columnIndex)
		{
			case 0: 
				return sub.subtitleNumber;
			case 1:
				return sub.startTimeToString();
			case 2:
				return sub.endTimeToString();
			case 3:
				return sub.content;
			default:
				return null;
		}
	}
	
	public void setSubtitleList(SubtitleList subsList)
	{
		this.subsList = subsList;
		fireTableDataChanged();
	}
	
	public SubtitleList getSubtitleList()
	{
		return subsList;
	}
	
	public void removeSubtitles(int start, int count)
	{		
		for (int i = start + count - 1; i >= start; i--)
		{
			subsList.removeSubtitleFromIndex(i);
		}
		
		for (int i = start; i < subsList.getSubtitlesCount(); i++)
		{
			subsList.getSubtitleFromIndex(i).setSubtitleNumber(i + 1);
		}
	}
	
	public void moveUpByOne(int startSubtitle, int selectedCount)
	{
		Subtitle s = subsList.getSubtitleFromIndex(startSubtitle - 1);

		subsList.removeSubtitleFromIndex(startSubtitle - 1);

		subsList.insertSubtitleAtIndex(startSubtitle + selectedCount - 1, s);
		for (int i = startSubtitle - 1; i < startSubtitle + selectedCount; i++)
		{
			subsList.getSubtitleFromIndex(i).setSubtitleNumber(i + 1);
		}
	}
	
	public void moveDownByOne(int startSubtitle, int selectedCount)
	{
		Subtitle s = subsList.getSubtitleFromIndex(startSubtitle + selectedCount);

		subsList.removeSubtitleFromIndex(startSubtitle + selectedCount);

		subsList.insertSubtitleAtIndex(startSubtitle, s);
		for (int i = startSubtitle; i < startSubtitle + selectedCount + 1; i++)
		{
			subsList.getSubtitleFromIndex(i).setSubtitleNumber(i + 1);
		}
	}
	
	public void addNewSubtitle(int index)
	{
		Subtitle s = new Subtitle();
		subsList.insertSubtitleAtIndex(index, s);

		for (int i = index; i < subsList.getSubtitlesCount(); i++)
		{
			subsList.getSubtitleFromIndex(i).setSubtitleNumber(i + 1);
		}
	}
	
	public void convertFromFramesToTime(double framesPerSec)
	{
		for (int i = 0; i < subsList.getSubtitlesCount(); i++)
		{
			subsList.getSubtitleFromIndex(i).framesToTime(framesPerSec);
		}
	}
	
	public void convertFromTimeToFrames(double framesPerSec)
	{
		for (int i = 0; i < subsList.getSubtitlesCount(); i++)
		{
			subsList.getSubtitleFromIndex(i).timeToFrames(framesPerSec);
		}
	}
}
