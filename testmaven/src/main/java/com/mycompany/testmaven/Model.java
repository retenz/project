/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testmaven;

import com.mycompany.testmaven.printer.IPrinter;
import java.util.Collection;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Robert.Tenadze
 */
@XmlRootElement(name = "model")
@XmlType(propOrder = {"rules", "knownFacts"})
public class Model {

    @XmlElementWrapper(name = "rules")
    @XmlElement(type = Rule.class, name = "rule")
    private Collection<Rule> rules;

    @XmlElementWrapper(name = "knownFacts")
    @XmlElement(name = "knownFact")
    private Set<String> knownFacts;

    private Model() {
    }

    public Model(Collection<Rule> rules, Set<String> knownFacts) {
        this.rules = rules;
        this.knownFacts = knownFacts;
    }

    public void calculate() {
        int knownFactsSize = 0;
        while (knownFactsSize != knownFacts.size()) {
            knownFactsSize = knownFacts.size();
            for (Rule rule : rules) {
                rule.calculate(knownFacts);
            }
        }
    }

    public void printFacts(IPrinter printer) {
        printer.showFacts(knownFacts);
    }

    public Collection<Rule> getRules() {
        return rules;
    }

    public Set<String> getKnownFacts() {
        return knownFacts;
    }
}
