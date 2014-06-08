package com.ii.subtitle.editor.commands;

import java.util.Stack;

import com.ii.subtitle.editor.Command;

public class CommandController
{
	private static final CommandController controller = new CommandController();

	private final Stack<Command> undoStack;
	private final Stack<UIMemento> undoMementos;
	private final Stack<Command> redoStack;
	private final Stack<UIMemento> redoMementos;

	private CommandActionsHandler handler;

	private CommandController()
	{
		undoStack = new Stack<>();
		undoMementos = new Stack<>();
		redoStack = new Stack<>();
		redoMementos = new Stack<>();
	}

	public static CommandController getInstance()
	{
		return controller;
	}

	public void executeCommand(Command command)
	{
		UIMemento memento = this.handler.createMemento();
		boolean success = command.execute();
		if (success)
		{
			this.undoStack.push(command);
			this.undoMementos.push(memento);
			this.redoStack.clear();
			this.redoMementos.clear();

			this.handler.notifyChanged();
			this.handler.updateActionsEnabledState(!undoStack.empty(), !redoStack.empty());
		}
	}

	private void performTransition(Stack<Command> from, Stack<UIMemento> fromMemento, Stack<Command> to, Stack<UIMemento> toMemento, boolean execute)
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
			UIMemento memento = fromMemento.pop();
			toMemento.push(memento);
			this.handler.restoreToMemento(memento);

			this.handler.notifyChanged();
			this.handler.updateActionsEnabledState(!this.undoStack.empty(), !this.redoStack.empty());
		}
	}

	public void undoLastCommand()
	{
		this.performTransition(this.undoStack, this.undoMementos, this.redoStack, this.redoMementos, false);
	}

	public void redoLastCommand()
	{
		this.performTransition(this.redoStack, this.redoMementos, this.undoStack, this.undoMementos, true);
	}

	public void clearCommandHistory()
	{
		this.undoStack.clear();
		this.undoMementos.clear();
		this.redoStack.clear();
		this.redoMementos.clear();

		this.handler.updateActionsEnabledState(!this.undoStack.empty(), !this.redoStack.empty());
	}

	public void registerCommandActionsHandler(CommandActionsHandler handler)
	{
		this.handler = handler;
	}

	public interface UIMemento
	{

	}

	public interface CommandActionsHandler
	{
		void updateActionsEnabledState(boolean undoEnabled, boolean redoEnabled);

		void notifyChanged();

		UIMemento createMemento();

		void restoreToMemento(UIMemento memento);
	}
}
