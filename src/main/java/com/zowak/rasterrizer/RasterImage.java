package com.zowak.rasterrizer;




import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.List;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author christophnowak
 */
public class RasterImage {
    private final Color colorRaster[][];
    public final Dimension dimensions;
    
    public final static int DEFAULT_UNIT_SIZE = 25;
    
    public RasterImage(int width, int height, int unitSize) {
        dimensions = new Dimension(width, height);
        colorRaster = new Color[dimensions.width][dimensions.height];
    }
    
    
    public void drawAt(Graphics g, int x, int y, int unitsize){
         for (int i = 0; i < dimensions.width; i++) {
                
                for (int j = 0; j < dimensions.height; j++) {
                    
                   Color color = colorRaster[i][j];
                   
                   if(color != null){
                       g.setColor(color);
                       g.fillRect((i * unitsize) + x , (j * unitsize) + y, unitsize, unitsize);
                   }
                    
                    
                }
            }
    }
    
    public Color[] getColors(){
        ArrayList<Color> colorlist = new ArrayList<>();
        
        for (int i = 0; i < dimensions.width; i++) {
            for (int j = 0; j < dimensions.height; j++)
            {
                Color c = getColorAt(new Point(i,j));
               
                if(c != null && !colorlist.contains(c)){
                    colorlist.add(c);
                }
            }
        }
        
        Color[] colors = new Color[colorlist.size()];
        for (int i = 0; i < colorlist.size(); i++) {
            colors[i] = colorlist.get(i);
        }
        
        return colors;
    }
    
    public void setColorOn(Color color, int x, int y){
        colorRaster[x][y] = color;
    }


    public Color getColorAt(Point rasterLocation) {
        
        if(rasterLocation.x < 0 || rasterLocation.y < 0 || rasterLocation.x > dimensions.width || rasterLocation.y > dimensions.height ){
            return null;
        }
        
        return colorRaster[rasterLocation.x][rasterLocation.y];
    }
    
    public void save(File file){
        
        try {
            try (RandomAccessFile fw = new RandomAccessFile(file,"rw")) {
                fw.writeInt(dimensions.width);
                fw.writeInt(dimensions.height);
                
                for (int i = 0; i < dimensions.width; i++) {
                    for (int j = 0; j < dimensions.height; j++) {
                        fw.writeInt(colorRaster[i][j].getRGB());
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(RasterImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static RasterImage load(File file){
        RasterImage rasterImage = null;
        
        try {
            try (RandomAccessFile fr = new RandomAccessFile(file,"r")) {
                int width = fr.readInt();
                int height = fr.readInt();
                
                System.out.println(width +  "/" + height);
                
                rasterImage = new RasterImage(width, height, DEFAULT_UNIT_SIZE);
                
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        Color color = new Color(fr.readInt());
                        rasterImage.setColorOn(color, i, j);
                        
                        
                    }
                }
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RasterImage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RasterImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rasterImage;
    }
    
}
