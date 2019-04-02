package com.mycompany.testmaven.parser;

import com.mycompany.testmaven.Model;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Robert.Tenadze
 */
public interface IParser extends AutoCloseable{
    public Model parse(String fileName) throws Exception;
}
