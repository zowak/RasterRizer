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
public class AddColorCmd implements  ICommand{

    private Color newColor;
    private DefaultListModel sourceModel;

    public AddColorCmd(Color newColor, DefaultListModel sourceModel) {
        this.newColor = newColor;
        this.sourceModel = sourceModel;
    }
    
    @Override
    public void execute() {
        sourceModel.addElement(newColor);
    }

    @Override
    public void undo() {
        sourceModel.removeElement(newColor);
    }
    
}
