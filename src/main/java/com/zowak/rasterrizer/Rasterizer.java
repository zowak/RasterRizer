package com.zowak.rasterrizer;




import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author christophnowak
 */
public class Rasterizer {
        
    private final int gridUnitsWidth;
    private final int gridUnitsHeight;
    private final int rasterSize;
    private final Color[] colorSet;
    private final int brightThreshold;
    private final Color backGroudColor;
    private final BufferedImage inputImage;
    private RasterImage rasteredOutput;
            
    
    public Rasterizer(int rastersize, int rasterWidth, int rasterHeight, BufferedImage sourceImage, Color[] colors){
        inputImage = sourceImage;
        gridUnitsWidth = rasterWidth;
        gridUnitsHeight = rasterHeight;
        rasterSize = rastersize;
        colorSet = colors;
        brightThreshold = 0;
        backGroudColor = Color.WHITE;
    }
    
    public void process(){
        //outputImage = new BufferedImage(rasterSize * gridUnits, rasterSize * gridUnits, BufferedImage.TYPE_INT_RGB);
        rasteredOutput = new RasterImage(gridUnitsWidth, gridUnitsHeight, rasterSize);
        
        for (int x = 0; x < gridUnitsWidth; x++) {
            
            for (int y = 0; y < gridUnitsHeight; y++) {
                
                Color color = new Color(getDominantColor(x, y));
                
                if(colorSet != null){
                    color = getClosestColorTo(color);
                }
                
                rasteredOutput.setColorOn(color, x, y);
 
            }
            
        }
        
    }
    
    public Color getClosestColorTo(Color color){
        
        Color closest_color = colorSet[0];
        int smallest_difference = Integer.MAX_VALUE; 
        
        for (Color c : colorSet){
            //int diff = colorRGBDiffrence(color, c);
            int diff = colorRGBDiffrence(color, c);
            if( diff < smallest_difference){
                //System.out.println("diff: " + diff);
                smallest_difference = diff;
                closest_color = c;
            }
        }
        
        return closest_color;
    }
    
    public static int colorRGBDiffrence(Color a, Color b){

        return  Math.abs(
                ((b.getRed()    - a.getRed())   * (b.getRed()    - a.getRed())) + 
                ((b.getGreen()  - a.getGreen()) * (b.getGreen()  - a.getGreen())) +
                ((b.getBlue()   - a.getBlue())  * (b.getBlue()   - a.getBlue())));
    }
    
    public static int colorHslDiffrence(Color a, Color b){
        float[] hsb_a = Color.RGBtoHSB(a.getRed(), a.getBlue(), a.getGreen(), null);
        float[] hsb_b = Color.RGBtoHSB(b.getRed(), b.getBlue(), b.getGreen(), null);
        
        
        return  Math.abs(
                (int)(
                ((hsb_b[0]   -  hsb_a[0]) * (hsb_b[0]   -  hsb_a[0])) + 
                ((hsb_b[1]   -  hsb_a[1]) * (hsb_b[1]   -  hsb_a[1])) +
                ((hsb_b[2]   -  hsb_a[2]) * (hsb_b[2]   -  hsb_a[2])))
                );
    }
        
    public static int colorContrast(Color a, Color b){
        return Math.abs((a.getRed() + a.getBlue() + a.getGreen()) - (b.getRed() + b.getBlue() + b.getGreen()));
    }
    
    public int getDominantColor(int gridX, int gridY){
        
        HashMap<Integer,Integer> color_weights = new HashMap<>();
        
        // collect color weights
        for (int x = 0; x < rasterSize; x++) {
            
            for (int y = 0; y < rasterSize; y++) {
                
                /*
                int xcoor = x + (gridX * gridUnits);
                int ycoor = y + (gridY * gridUnits);
                */
                int xcoor = x + (gridX * rasterSize);
                int ycoor = y + (gridY * rasterSize);
                
                int color = backGroudColor.getRGB();
                if(xcoor < inputImage.getWidth() && ycoor < inputImage.getHeight()){
     
                    if(colorContrast(new Color(color), backGroudColor) < brightThreshold){
                        color = backGroudColor.getRGB();
                    }else{
                        color = inputImage.getRGB(xcoor,ycoor);
                    }
                }

                Integer current_weight = color_weights.get(color);

                if(current_weight != null){
                    current_weight += 1;
                    color_weights.replace(color, current_weight);
                }else{
                   color_weights.put( color, 1);
                }

                
            }
        }
        
        // get dominant color
        int dominant_color = 0;
        int highest_weight = 0;
        
        for (Map.Entry<Integer, Integer> c : color_weights.entrySet()) {
            Integer key = c.getKey();
            Integer value = c.getValue();
            
            if(value > highest_weight){
                highest_weight = value;
                dominant_color = key;
            }
            
        }
        
        return dominant_color;
    }
    
    public RasterImage getRasterImage(){
        return rasteredOutput;
    }
    
    
    public static RasterImage convertToRasterImage(int rasterSize, int gridUnitsWidth, int gridUnitsHeight, BufferedImage sourceImage, Color[] colors){
        Rasterizer r = new Rasterizer(rasterSize, gridUnitsWidth, gridUnitsHeight, sourceImage, colors);
        r.process();
        
        return r.getRasterImage();
        
    }
        
    
}
