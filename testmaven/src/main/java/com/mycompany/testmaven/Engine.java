/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testmaven;

import com.mycompany.testmaven.writer.XmlWriter;
import com.mycompany.testmaven.exception.XmlException;
import com.mycompany.testmaven.parser.DBParser;
import com.mycompany.testmaven.parser.IParser;
import com.mycompany.testmaven.parser.TxtParser;
import com.mycompany.testmaven.parser.XmlParser;
import com.mycompany.testmaven.printer.IPrinter;
import com.mycompany.testmaven.writer.DBWriter;
import com.mycompany.testmaven.writer.IWriter;
import java.io.IOException;

/**
 *
 * @author Robert.Tenadze
 */
public class Engine {

    public enum FileType {
        TXT,
        XML,
        DB
    }

    IPrinter printer;

    public Engine(IPrinter printer) {
        this.printer = printer;
    }

    public void calculate(String fileName, FileType type) {

        try (IParser parser = createParser(type, fileName)) {
            try {
                Model model = parser.parse(fileName);
                model.calculate();
                model.printFacts(printer);
            } catch (XmlException ex) {
                printer.showError(ex.getMessage(), ex);
            } catch (Exception ex) {
                printer.showError(ex);
            }
        } catch (Exception ex) {
            printer.showError(new Exception("Ошибка парсинга! Указанные параметры не соответсвуют формату!\n"));
        }
    }

    public void convert(String sourceFileName, String fileName, FileType fromType, FileType toType) {

        try (IParser parser = createParser(fromType, sourceFileName);
                IWriter writer = createWriter(toType, fileName)) {
            Model model = parser.parse(sourceFileName);
            writer.write(model, fileName);
            printer.showMessage("Фаил успешно сконвертирован!");
        } catch (XmlException ex) {
            printer.showError(ex.getMessage(), ex);
        } catch (Exception ex) {
            printer.showError(new Exception("Ошибка конвертации! Указанные параметры не соответсвуют формату!\n"));
        }
    }

    private IParser createParser(FileType type, String fileName) throws IOException {
        IParser parser = null;
        switch (type) {
            case TXT:
                parser = new TxtParser();
                break;
            case XML:
                parser = new XmlParser();
                break;
            case DB:
                parser = new DBParser(fileName);
                break;
            default:
                printer.showError(new Exception("Ошибка парсинга! Указанные параметры не соответсвуют формату!\n"));
                break;
        }
        return parser;
    }

    private IWriter createWriter(FileType type, String fileName) throws IOException {
        IWriter writer = null;
        switch (type) {
            case XML:
                writer = new XmlWriter();
                break;
            case DB:
                writer = new DBWriter(fileName);
                break;
            default:
                printer.showError(new Exception("Ощибка конвертации! Указанные параметры не соответсвуют формату!\n"));
                break;
        }
        return writer;
    }
}
