/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testmaven.printer;

import java.util.Collection;

/**
 *
 * @author Robert.Tenadze
 */
public interface IPrinter {
    public void showFacts(Collection<String> knownFacts);
    public void showError(String message, Exception ex);
    public void showError(Exception ex);
    public void showMessage(String message);
}
