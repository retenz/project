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
public class BracketExpression implements Expression{
    private Collection<Expression> expressions;

    public BracketExpression(Collection<Expression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public boolean calculate(Set<String> knownFacts) {
        for (Expression ex : expressions) {
            if (!ex.calculate(knownFacts)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public Collection<Expression> getExpressions() {
        return expressions;
    }
    
}
