/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testmaven.expression;

import java.util.Collection;
import java.util.Set;

/**
 *
 * @author Robert.Tenadze
 */
public interface Expression {

    public boolean calculate(Set<String> knownFacts);
    public Collection<Expression> getExpressions();

}
