package com.ii.subtitle.editor.commands;

import java.util.Stack;

import com.ii.subtitle.editor.Command;

public class CommandController
{
	private static final CommandController controller = new CommandController();

	private final Stack<Command> undoStack;
	private final Stack<Command> redoStack;

	private CommandActionsHandler handler;

	private CommandController()
	{
		undoStack = new Stack<>();
		redoStack = new Stack<>();
	}

	public static CommandController getInstance()
	{
		return controller;
	}

	public void executeCommand(Command command)
	{
		boolean success = command.execute();
		if (success)
		{
			this.undoStack.push(command);
			this.redoStack.clear();

			this.handler.notifyChanged();
			this.handler.updateActionsEnabledState(!undoStack.empty(), !redoStack.empty());
		}
	}

	private void performTransition(Stack<Command> from, Stack<Command> to, boolean execute)
	{
		if (from.empty())
		{
			return;
		}
		Command command = from.peek();

		boolean success = execute ? command.execute() : command.undo();
		if (success)
		{
			to.push(from.pop());

			this.handler.notifyChanged();
			this.handler.updateActionsEnabledState(!this.undoStack.empty(), !this.redoStack.empty());
		}
	}

	public void undoLastCommand()
	{
		this.performTransition(this.undoStack, this.redoStack, false);
	}

	public void redoLastCommand()
	{
		this.performTransition(this.redoStack, this.undoStack, true);
	}

	public void clearCommandHistory()
	{
		this.undoStack.clear();
		this.redoStack.clear();

		this.handler.updateActionsEnabledState(!this.undoStack.empty(), !this.redoStack.empty());
	}

	public void registerCommandActionsHandler(CommandActionsHandler handler)
	{
		this.handler = handler;
	}

	public interface CommandActionsHandler
	{
		void updateActionsEnabledState(boolean undoEnabled, boolean redoEnabled);

		void notifyChanged();
	}
}
