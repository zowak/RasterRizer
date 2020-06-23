package com.zowak.rasterrizer.commandmanager;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author christophnowak
 */
public interface ICommand {
    

    public void execute();
    public void undo();
    
}
