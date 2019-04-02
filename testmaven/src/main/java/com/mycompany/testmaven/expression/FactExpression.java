/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testmaven.expression;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Robert.Tenadze
 */
@XmlRootElement(name = "fact")
public class FactExpression implements Expression {

    @XmlAttribute(name ="value")
    private String fact;

    private FactExpression() {}
    public FactExpression(String fact) {
        this.fact = fact;
    }

    @Override
    public boolean calculate(Set<String> knownFacts) {
        return knownFacts.contains(fact);
    }
    
    public String getFact() {
        return fact;
    }
    
    @Override
    public Collection<Expression> getExpressions() {
        Collection<Expression> expressions= new ArrayList<>();
        expressions.add(new FactExpression(fact));
        return expressions;
    }
}
