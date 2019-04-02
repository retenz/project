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
public class InputDataException extends Exception {

    public InputDataException() {
    }

    public InputDataException(String msg) {
        super(msg);
    }
    public InputDataException(String msg, Exception ex) {
        super(msg, ex);
    }
}
