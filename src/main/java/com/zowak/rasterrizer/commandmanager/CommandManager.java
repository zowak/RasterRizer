package com.zowak.rasterrizer.commandmanager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Stack;

/**
 *
 * @author christophnowak
 */
public class CommandManager {
    
    private final Stack<ICommand> undoStack;
    private final Stack<ICommand> redoStack;
    private final int capacity;
    
    public CommandManager(int capacity){
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        this.capacity = capacity;
        
    }
    
    public void runCommand(ICommand cmd){
        cmd.execute();
        redoStack.clear();
        undoStack.push(cmd);
        
        if(undoStack.size() > capacity) undoStack.remove(0);
    }
    
    public void undo(){
        ICommand cmd = undoStack.pop();
        cmd.undo();
        redoStack.push(cmd);
    }
    
    public void redo(){
        ICommand cmd = redoStack.pop();
        cmd.execute();
        undoStack.push(cmd);
    }
    
    public boolean undoable(){
        return (!undoStack.isEmpty());
    }
    
    public boolean redoable(){
        return (!redoStack.isEmpty());
    }

    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }
}
