package com.ii.subtitle.model;


public interface SubtitleTextVisitor
{
	public void visitTextLeaf(TextLeaf leaf);
	
	public void visitTextGroup(TextGroup group);
}
