/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testmaven.parser;

import com.mycompany.testmaven.Model;
import com.mycompany.testmaven.exception.InputDataException;
import com.mycompany.testmaven.exception.XmlException;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

/**
 *
 * @author Robert.Tenadze
 */
public class XmlParser implements IParser{

    @Override
    public Model parse(String fileName) throws Exception {
        Object object = null;
        try {
            URL resource = ClassLoader.getSystemResource("model.xsd");
            File file = new File(fileName);
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(resource);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(file);
            validator.validate(source);
            JAXBContext context = JAXBContext.newInstance(Model.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            object = unmarshaller.unmarshal(file);
        } catch (SAXException ex) {
            throw new XmlException("Указанный файл не соответсвует формату входных данных!", ex);
        } catch (JAXBException ex) {
            throw new XmlException("Ошибка парсинга XML файла!", ex);
        } catch (FileNotFoundException ex) {
            throw new InputDataException("Файла с таким именем не найдено!\n"
                    + "Проверьте верность введённого пути.", ex);
        }
        return (Model) object;
    }

    @Override
    public void close() throws Exception {
    }
}
