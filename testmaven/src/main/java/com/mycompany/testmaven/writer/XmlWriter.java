/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testmaven.writer;

import com.mycompany.testmaven.Model;
import com.mycompany.testmaven.exception.XmlException;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author Robert.Tenadze
 */
public class XmlWriter implements IWriter {

    @Override
    public void write(Model model, String fileName) throws Exception {
        try {
            JAXBContext context = JAXBContext.newInstance(Model.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "model.xsd");
            if (!fileName.endsWith(".xml")) {
                marshaller.marshal(model, new File(fileName + ".xml"));
            } else {
                marshaller.marshal(model, new File(fileName));
            }
        } catch (JAXBException ex) {
            throw new XmlException("Ошибка парсинга XML файла!", ex);
        }
    }

    @Override
    public void close() throws Exception {}
}
