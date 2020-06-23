package com.zowak.rasterrizer.grids;




import com.zowak.rasterrizer.mainform.Main;
import com.zowak.rasterrizer.RasterImage;
import com.zowak.rasterrizer.Rasterizer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.TransferHandler;
import java.util.List;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author christophnowak
 */
    public class AllignmentGrid extends BasicGrid {
        public AllignmentGrid() {
            rasterColorSet = null;
            showRasterImage = false;
            headersOn = true;
            //setDropTarget(new DropTarget(this, DnDConstants.ACTION_MOVE, this, true));
            
            
            
        }



        public void setColorSet(Color[] colors){
            rasterColorSet = colors;
            repaint();
        }
        

        
        private void drawTemplateLayer(Graphics g){
            
             if(templateImage != null){
                //g.setClip(rasterSize, rasterSize, getWidth()-1, getHeight()-1);
                g.setColor(Color.white);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.drawImage(templateImage,
                           templateLocation.x - (templateImage.getWidth() / 2), 
                           templateLocation.y - (templateImage.getHeight() / 2), 
                           this);  
            }
            
            

        }
        
        
        public void setRasteredImageVisible(boolean visible){
            showRasterImage = visible;
            repaint();
        }
      
        
 
        
        public void loadOriginalImage(File file){
            try {
                templateImage_original = ImageIO.read(file);
                templateImage = ImageIO.read(file);
                
                templateLocation = new Point(
                                    (getWidth() / 2), 
                                    (getHeight()/ 2));
                updateRasterImage();
                repaint();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        public void scaleOriginalImage(float factor){
            if(templateImage != null){
                BufferedImage new_image = new BufferedImage(
                                (int)(templateImage_original.getWidth() * factor), 
                                (int)(templateImage_original.getHeight() * factor), 
                                 BufferedImage.TYPE_INT_ARGB);

                AffineTransform at = new AffineTransform();
                at.scale(factor, factor);
                AffineTransformOp scaleOp = 
                   new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                templateImage = scaleOp.filter(templateImage_original, new_image);

                repaint();
            }
        }
        
        
        public void moveTemplateTo(Point position){
            templateLocation = position;
           repaint();
                
        }
        
        public void translateTemplate(Point vector){
            moveTemplateTo(new Point(templateLocation.x + vector.x, templateLocation.y + vector.y));
            repaint();
        }
        
     
        
        public void updateRasterImage(){
            BufferedImage inputImage = new BufferedImage(rasterSize * (rasterDimension.width + 1), rasterSize * (rasterDimension.height + 1), BufferedImage.TYPE_BYTE_INDEXED);
            Graphics g = inputImage.createGraphics();
            drawTemplateLayer(g);
            g.dispose();
            inputImage =  inputImage.getSubimage(rasterSize, rasterSize, rasterSize * (rasterDimension.width), rasterSize * (rasterDimension.height));
            rasteredImage = Rasterizer.convertToRasterImage(rasterSize, rasterDimension.width, rasterDimension.height, inputImage, rasterColorSet);
            
        }
        
        public RasterImage getRasteredImage() {
            return rasteredImage;
        }

        @Override
        void drawBottomLayer(Graphics g) {
            drawTemplateLayer(g);

            if(showRasterImage && templateImage != null){
                updateRasterImage();
                //g.drawImage(rasteredImage, rasterSize, rasterSize, this);
                rasteredImage.drawAt(g, rasterSize, rasterSize, rasterSize);
            }


        }


        
        private BufferedImage templateImage;
        private BufferedImage templateImage_original;
        private RasterImage rasteredImage;
        private Point templateLocation;
        private boolean showRasterImage;
        private Color[] rasterColorSet;

    @Override
    void drawTopLayer(Graphics g) {
        
    }

    public Point getTemplateLocation(){
        return templateLocation;
    }

   
    
    
   }
