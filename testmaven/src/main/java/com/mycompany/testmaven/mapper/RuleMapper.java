/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testmaven.mapper;

import com.mycompany.testmaven.dto.RuleDTO;
import java.util.Collection;

/**
 *
 * @author Robert.Tenadze
 */
public interface RuleMapper {

    public Collection<RuleDTO> getRules();
    public void insertRule(RuleDTO dTO);
    public void deleteALL();
}
