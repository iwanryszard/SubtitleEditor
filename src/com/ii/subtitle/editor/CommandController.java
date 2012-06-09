package com.ii.subtitle.editor;

import java.util.Stack;

public class CommandController 
{
	private static CommandController controller;
	
	private Stack<BaseCommand> undoStack;
	private Stack<BaseCommand> redoStack;
	
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
			}
		}
	}
}
