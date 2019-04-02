/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testmaven.mapper;

import java.util.Collection;

/**
 *
 * @author Robert.Tenadze
 */
public interface FactMapper {

    public Collection<String> getKnownFact();
    public void insertKnownFact(String s);
    public void deleteALL();
}
