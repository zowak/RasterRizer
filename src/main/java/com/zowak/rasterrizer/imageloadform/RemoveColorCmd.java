package com.zowak.rasterrizer.imageloadform;


import com.zowak.rasterrizer.commandmanager.ICommand;
import java.awt.Color;
import javax.swing.DefaultListModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author christophnowak
 */
public class RemoveColorCmd implements ICommand{

    private final int index;
    private final Color color;
    private final DefaultListModel soureModel;

    public RemoveColorCmd(int index, Color color, DefaultListModel soureModel) {
        this.index = index;
        this.color = color;
        this.soureModel = soureModel;
    }
    
    
    
    @Override
    public void execute() {
        soureModel.remove(index);
    }

    @Override
    public void undo() {
        soureModel.add(index, color);
    }
    
}
