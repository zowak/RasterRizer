package com.zowak.rasterrizer.mainform;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import com.zowak.rasterrizer.grids.EditGrid;
import com.zowak.rasterrizer.commandmanager.ICommand;
import java.awt.Color;
import java.awt.Point; 

/**
 *
 * @author christophnowak
 */
public class SetColorOnGridCmd implements ICommand{

    
    private final EditGrid editGrid;
    private final Point gridLocation;
    private final Color newColor;
    private final Color oldColor;
    
    public SetColorOnGridCmd(Point location, Color newcolor, Color oldcolor, EditGrid grid ){
        editGrid = grid;
        gridLocation = location;
        newColor = newcolor;
        oldColor = oldcolor;
    }
    
    @Override
    public void execute() {
        editGrid.setColorAt(gridLocation, newColor);
    }

    @Override
    public void undo() {
        editGrid.setColorAt(gridLocation, oldColor);
    }
    
}
