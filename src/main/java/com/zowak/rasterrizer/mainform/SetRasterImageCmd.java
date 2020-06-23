package com.zowak.rasterrizer.mainform;


import com.zowak.rasterrizer.RasterImage;
import com.zowak.rasterrizer.grids.EditGrid;
import com.zowak.rasterrizer.grids.ColorPickGrid;
import com.zowak.rasterrizer.commandmanager.ICommand;
import java.awt.Color;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author christophnowak
 */
public class SetRasterImageCmd implements ICommand{

    private final EditGrid editGrid;
    private final ColorPickGrid colorGrid;
    private final RasterImage newRasterImage;
    private final RasterImage oldRasterImage;
    private final Color[] newColors;
    private final Color[] oldColors;
    
    
    public SetRasterImageCmd(RasterImage newImage, RasterImage oldImage, Color[] newColors, Color[] oldColors, EditGrid grid, ColorPickGrid colorGrid ){
        this.editGrid = grid;
        this.newRasterImage = newImage;
        this.oldRasterImage = oldImage;
        this.newColors = newColors;
        this.oldColors = oldColors;
        this.colorGrid = colorGrid;
    }
    
    @Override
    public void execute() {
        editGrid.setRasteredImage(newRasterImage);
        colorGrid.setColors(newColors);
        //editGrid.set
    }

    @Override
    public void undo() {
        editGrid.setRasteredImage(oldRasterImage);
        if(oldColors != null) colorGrid.setColors(oldColors);
    }
    
}
