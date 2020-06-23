package com.zowak.rasterrizer.colorlist;


import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author christophnowak
 */
public class ColorListCellRenderer extends DefaultListCellRenderer{

    public ColorListCellRenderer(){
        
    }
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) { 
        super.getListCellRendererComponent(list, value,index,isSelected,cellHasFocus);  
        Color c = (Color)value; 
        setText(" "); 
        setBackground(c);
        
        //drawColorIcon(c,getGraphics());
        
        return this;
    }
    
    /*
    public void drawColorIcon(Color color, Graphics g){
        g.drawRoundRect(5, 5, 10, 10, 4, 4);
    }
*/

 
}


