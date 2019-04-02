/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test1.printer;

import com.mycompany.testmaven.printer.IPrinter;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author Robert.Tenadze
 */
public class ConsolePrinter implements IPrinter {

    @Override
    public void showFacts(Collection<String> knownFacts) {

        if (knownFacts.isEmpty()) {
            System.err.println("Отсутсвуют известные факты!");
        } else {
            Iterator it = knownFacts.iterator();
            System.out.print(it.next());
            while (it.hasNext()) {
                System.out.print(", " + it.next());
            }
        }
    }

    @Override
    public void showError(String message, Exception ex) {
        System.err.print(message + "\n(" + ex.getCause() + ")");
    }

    @Override
    public void showError(Exception ex) {
        System.err.print(ex.getMessage());
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
}