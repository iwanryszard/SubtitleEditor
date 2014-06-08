package com.ii.subtitle.editor;

import java.util.Stack;

import javax.swing.JButton;

public class CommandController 
{
	private static CommandController controller;
	
	private Stack<Command> undoStack;
	private Stack<Command> redoStack;
	
	private JButton undoButton;
	private JButton redoButton;
	
	private CommandController()
	{
		undoStack = new Stack<Command>();
		redoStack = new Stack<Command>();
	}
	
	public static CommandController getCommandController()
	{
		if(controller == null)
		{
			controller = new CommandController();
		}
		
		return controller;
	}
	
	public void executeCommand(Command command)
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
			Command command = undoStack.pop();
			
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
			Command command = redoStack.pop();
			
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
