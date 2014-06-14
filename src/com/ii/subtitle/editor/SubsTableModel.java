package com.ii.subtitle.editor;

import javax.swing.table.AbstractTableModel;

import com.ii.subtitle.model.Subtitles;

public class SubsTableModel extends AbstractTableModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7871409214981720369L;
	
	private static final String[] columns = new String[] {"Number", "Start", "End", "Content" };
	
	private Subtitles subsList;
	
	public SubsTableModel(Subtitles subsList)
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
		return subsList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		
		switch(columnIndex)
		{
			case 0: 
				return rowIndex + 1;
			case 1:
				return SubtitleFormatUtils.getStart(subsList, rowIndex);
			case 2:
				return SubtitleFormatUtils.getEnd(subsList, rowIndex);
			case 3:
				return SubtitleFormatUtils.getSubtitleTextSingleLine(subsList, rowIndex);
			default:
				return null;
		}
	}
	
	public void setSubtitleList(Subtitles in)
	{
		this.subsList = in;
		fireTableDataChanged();
	}
	
}
