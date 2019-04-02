/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testmaven.writer;

import com.mycompany.testmaven.Model;


/**
 *
 * @author Robert.Tenadze
 */
public interface IWriter extends AutoCloseable{
    public void write(Model model, String fileName) throws Exception;
}
