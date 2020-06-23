package com.zowak.rasterrizer.grids;




import com.zowak.rasterrizer.RasterImage;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.io.File;
import javax.naming.ldap.HasControls;
import javax.swing.TransferHandler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author christophnowak
 */
    public class EditGrid extends BasicGrid  {
        
        private boolean rowMarker;
        private int currentRow;
        private RasterImage rasteredImage;
        
        public EditGrid() {
            headersOn = true;
            currentRow = 1;
        }

        public RasterImage getRasteredImage() {
            return rasteredImage;
        }

        
        public void setColorAt(Point position, Color color){
        
            Point pos = toRasterLocation(position);
            
            if(pos.x >= 0 && pos.y >= 0 && pos.y <= rasteredImage.dimensions.width && pos.x <= rasteredImage.dimensions.height){
                rasteredImage.setColorOn(color, pos.x , pos.y);
            
                repaint();
            }

        }
        
        
        private Point toRasterLocation(Point location){
            return new Point(
                            (location.x / rasterSize) - 1,
                            (location.y / rasterSize) - 1);
        }
    
        public Color getColorAt(Point location){
            Point pos = toRasterLocation(location);

            return rasteredImage.getColorAt(pos);
        }

    
        public void setRasteredImage(RasterImage image){
            rasteredImage = image;
            if(rasteredImage != null)super.setRasterDimensions(rasteredImage.dimensions.width, rasteredImage.dimensions.height);
            repaint();
        }
        
        @Override
        public void setRastersize(int size){
            super.setRastersize(size);
            repaint();
        }
        
        
        @Override
        void drawBottomLayer(Graphics g) {
           if(rasteredImage != null) rasteredImage.drawAt(g, rasterSize, rasterSize, rasterSize);
        }

        
        public void increaseRasterSize(int step){
            setRastersize(rasterSize + step);
        }
        
       
     

    public void saveRasterImage(File file) {
        rasteredImage.save(file);
    }

    public void loadRasterImage(File file) {
        setRasteredImage(RasterImage.load(file));
    }

    @Override
    void drawTopLayer(Graphics g) {
        if(rowMarker){
            

            
            Graphics2D g2 = (Graphics2D)g;
            
            // draw row marker
            g2.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(5));
            g2.drawRect(rasterSize, rasterSize * currentRow, rasterDimension.width * rasterSize, rasterSize);
            g2.setStroke(new BasicStroke(1));
            
            //draw overlay
            Color overlay_color = new Color(255, 255, 255, 200);
            g2.setColor(overlay_color);
            g2.fillRect(rasterSize, rasterSize, rasterDimension.width * rasterSize , (currentRow - 1) * rasterSize);
            g2.fillRect(rasterSize, (currentRow + 1) * rasterSize, rasterDimension.width * rasterSize , (rasterDimension.height - currentRow) * rasterSize);
        }
    }

    public void setRowMarkerVisible(boolean visible ) {
        rowMarker= visible;
    }

    public void nextRow() {
        currentRow++;
        if(currentRow > rasterDimension.height) currentRow--;
        repaint();
    }
       
    public void previousRow() {
        currentRow--;
        if(currentRow < 1) currentRow++;
        repaint();
    }
        
       
    
}

