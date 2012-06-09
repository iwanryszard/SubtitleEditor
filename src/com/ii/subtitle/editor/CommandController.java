package com.ii.subtitle.editor;

import java.util.Stack;

import javax.swing.JButton;

public class CommandController 
{
	private static CommandController controller;
	
	private Stack<BaseCommand> undoStack;
	private Stack<BaseCommand> redoStack;
	
	private JButton undoButton;
	private JButton redoButton;
	
	private CommandController()
	{
		undoStack = new Stack<BaseCommand>();
		redoStack = new Stack<BaseCommand>();
	}
	
	public static CommandController getCommandController()
	{
		if(controller == null)
		{
			controller = new CommandController();
		}
		
		return controller;
	}
	
	public void executeCommand(BaseCommand command)
	{
		boolean success = command.execute();
		if(success)
		{
			undoStack.push(command);
			redoStack.clear();
			
			undoButton.setEnabled(true);
			redoButton.setEnabled(false);
		}
	}
	
	public void undoLastCommand()
	{
		if( !undoStack.empty())
		{
			BaseCommand command = undoStack.pop();
			
			boolean success = command.undo();
			if(success)
			{
				redoStack.push(command);
				
				redoButton.setEnabled(true);
				if(undoStack.empty())
				{
					undoButton.setEnabled(false);
				}
			}
		}
	}
	
	public void redoLastCommand()
	{
		if( !redoStack.empty())
		{
			BaseCommand command = redoStack.pop();
			
			boolean success = command.execute();
			if(success)
			{
				undoStack.push(command);
				
				undoButton.setEnabled(true);
				if(redoStack.empty())
				{
					redoButton.setEnabled(false);
				}
			}
		}
	}
	
	public void clearCommandHistory()
	{
		undoStack.clear();
		redoStack.clear();
		
		undoButton.setEnabled(false);
		redoButton.setEnabled(false);
	}
	
	public void registerUndoButton(JButton undoButton)
	{
		this.undoButton = undoButton;
	}
	
	public void registerRedoButton(JButton redoButton)
	{
		this.redoButton = redoButton;
	}
}
