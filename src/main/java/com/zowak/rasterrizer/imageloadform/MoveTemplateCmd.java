package com.zowak.rasterrizer.imageloadform;


import com.zowak.rasterrizer.grids.AllignmentGrid;
import com.zowak.rasterrizer.commandmanager.ICommand;
import java.awt.Point;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author christophnowak
 */
public class MoveTemplateCmd implements ICommand{

    private final AllignmentGrid grid;
    private final Point newLoctaion;
    private final Point oldLocation;

    public MoveTemplateCmd(AllignmentGrid grid, Point newLoctaion, Point oldLocation) {
        this.grid = grid;
        this.newLoctaion = newLoctaion;
        this.oldLocation = oldLocation;
    }
    
    
    
    @Override
    public void execute() {
        grid.moveTemplateTo(newLoctaion);
    }

    @Override
    public void undo() {
        grid.moveTemplateTo(oldLocation);
    }
    
}
