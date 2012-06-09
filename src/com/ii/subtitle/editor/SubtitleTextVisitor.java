package com.ii.subtitle.editor;

public interface SubtitleTextVisitor
{
	public void visitTextLeaf(TextLeaf leaf);
	
	public void visitTextGroup(TextGroup group);
}
