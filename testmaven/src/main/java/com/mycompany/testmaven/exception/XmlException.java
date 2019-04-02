/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testmaven.exception;

/**
 *
 * @author Robert.Tenadze
 */
public class XmlException extends Exception {

    public XmlException() {
    }
    public XmlException(String msg) {
        super(msg);
    }
    public XmlException(String msg, Exception ex) {
        super(msg, ex);
    }
}
