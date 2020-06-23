package com.zowak.rasterrizer.grids;




import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author christophnowak
 */
public abstract class BasicGrid extends JPanel {
    
    
        public BasicGrid(){
            rasterSize = 25;
            rasterDimension = new Dimension(25,20);
            rasterOn = true;
            rasterType = RASTER_SOLID;
            

        }
        
         
        public void setRasterDimensions(int width, int height){
            rasterDimension = new Dimension(width, height);

            setPreferredSize(new Dimension((rasterDimension.width) * rasterSize, (rasterDimension.height) * rasterSize));
            repaint();
           
        }
        
        public void setRaster(boolean on){
            rasterOn = on;
            repaint();
        }
        
        public void setHeaders(boolean on){
            headersOn = on;
        }
        
        public boolean rasterStatus() {
            return rasterOn;
        }
        
           
        public void setRastersize(int size) {
            rasterSize = size;
            setPreferredSize(new Dimension((rasterDimension.width + 1) * rasterSize, (rasterDimension.height + 1) * rasterSize));
            repaint();
            
        }
        
        private void drawRaster(Graphics g){
           
            if(headersOn){
              g.setColor(getBackground());
              // draw column-header
              g.fillRect(0, 0, rasterSize * (rasterDimension.width + 1), rasterSize);
              //draw row-header
              g.fillRect(0, 0, rasterSize , rasterSize * (rasterDimension.height + 1));

            }
            
            
            if(rasterType == RASTER_PUNCTUAL) {
                drawPunctualRaster(g);
            }else{
                drawSolidRaster(g);
            }
         
            
        }
        
        public void setRasterType(int type){
            rasterType = type;
        }
        
        private void drawSolidRaster(Graphics g){
            
            g.setColor(Color.black);
            for (int i = 0; i <= rasterDimension.width; i++) {
                g.fillRect((i + 1) * rasterSize, 2, 2, rasterSize * (rasterDimension.height + 1));
                if(i > 0 && headersOn) g.drawString( " " + (i), i * rasterSize, 10);
            }
            
            // horizontal lines
            for (int i = 0; i <= rasterDimension.height; i++) {
                g.fillRect(2, (i + 1) * rasterSize, rasterSize * (rasterDimension.width + 1), 2);
                if(i > 0 && headersOn) g.drawString( " " + (i), 5, (i +1) * rasterSize);
            }
                        
        }
        
        private void drawPunctualRaster(Graphics g){
            
             // vertical lines
            for (int i = 0; i <= rasterDimension.width; i++) {
                drawPunctualLine(new Point(i * rasterSize, 0 ), true, rasterSize * (rasterDimension.width + 1), g);
                if(i > 0 && headersOn){
                    g.setColor(Color.black);
                    g.drawString( " " + (i), i * rasterSize, 10);
                }
            }
            
             // horizontal lines
            for (int i = 0; i <= rasterDimension.height; i++) {
                drawPunctualLine(new Point(0, i * rasterSize), false, rasterSize * (rasterDimension.height + 1), g);
                if(i > 0 && headersOn){
                    g.setColor(Color.black);
                    g.drawString( " " + (i), 5, (i +1) * rasterSize);
                }
            }
        }
        
        private void drawPunctualLine(Point position, boolean vertial, int length,  Graphics g ){
            
            int vx = (vertial) ? 0 : 1;
            int vy = (vertial) ? 1 : 0;
            
            for (int i = 0; i < length; i++) {
                int x = i * vx;
                int y = i * vy;
                
                if(i  % (rasterSize / 4) == 0){
                    g.setColor(Color.white);
                }else{
                    g.setColor(Color.black);
                }
                
                g.drawRect(x + position.x, y + position.y, 1, 1);
                
            }
        }
          
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            drawBottomLayer(g);
            
            if(rasterOn) drawRaster(g);
            
            drawTopLayer(g);
            
        }
        
        abstract void drawBottomLayer(Graphics g);
        abstract void drawTopLayer(Graphics g);
        
        public final static int RASTER_SOLID = 1;
        public final static int RASTER_PUNCTUAL = 2;
        
        
        private boolean rasterOn;
        protected boolean headersOn;
        protected int rasterSize;
        protected Dimension rasterDimension;
        protected int rasterType;
        
        
}
