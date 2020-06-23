package com.zowak.rasterrizer.grids;




import com.zowak.rasterrizer.RasterImage;
import java.awt.Color;
import java.awt.Graphics;
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
public class ColorPickGrid extends BasicGrid{

    public ColorPickGrid(){
        colorRaster = new RasterImage(COLUMNS, ROWS, RASTERSIZE);
        headersOn = false;
        super.setRaster(true);
        super.setRastersize(RASTERSIZE);
        super.setRasterDimensions(COLUMNS, ROWS);
    }
    
    public void setColors(Color[] colors){
        
        this.colors = colors;
        
        int c = 0;
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                if(c < colors.length){
                    colorRaster.setColorOn(colors[c], i, j);
                }else{
                    colorRaster.setColorOn(Color.WHITE, i, j);
                }
                c++;
            }
            
        } 
        repaint();
    }
    
    public Color[] getColors(){
        return colors;
    }
    
    
    public Color getColorAt(Point location){
        int x = (location.x / rasterSize);
        int y = (location.y / rasterSize);
        
        return colorRaster.getColorAt(new Point(x,y));
    }
            
    @Override
    void drawBottomLayer(Graphics g) {
        colorRaster.drawAt(g, 0, 0, rasterSize);
        g.setColor(Color.white);
        //g.drawRoundRect(selectedCell.x * rasterSize, selectedCell.y * rasterSize, rasterSize, rasterSize, 10, 10);
    }
    
    private static final int COLUMNS = 3;
    private static final int ROWS = 8;
    private static final int RASTERSIZE = 32;
    private final RasterImage colorRaster;
    private Color[] colors;

    @Override
    void drawTopLayer(Graphics g) {
    }
    
}
