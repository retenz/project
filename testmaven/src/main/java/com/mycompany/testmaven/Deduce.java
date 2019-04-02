/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testmaven;

import org.apache.commons.cli.*;
import test1.printer.ConsolePrinter;

/**
 *
 * @author Robert.Tenadze
 */
public class Deduce {

    public static void main(String[] args) {
        Option txtOption = new Option("t", "txt", true, "данная команда осуществляет чтение из TXT файла и парсинг, полученной информации");
        txtOption.setArgs(1);
        txtOption.setArgName("Путь до файла с исходной информацией");

        Option xmlOption = new Option("x", "xml", true, "данная команда осуществляет чтение из XML файла и парсинг, полученной информации");
        xmlOption.setArgs(1);
        xmlOption.setArgName("Путь до файла с исходной информацией");

        Option dbOption = new Option("d", "db", true, "данная команда осуществляет чтение из базы данных и парсинг, полученной информации");
        dbOption.setArgs(1);
        dbOption.setArgName("Путь до файла с настройками базы данных");

        Option convertToXmlOption = new Option("c", "toXML", true, "данная команда осуществляет ковертацию TXT файла в XML файл");
        convertToXmlOption.setArgs(2);
        convertToXmlOption.setArgName("Путь до файла с исходной информацией, путь до файла в который будет осуществляться запись");
        
        Option convertToDBOption = new Option("z", "toDB", true, "данная команда осуществляет ковертацию TXT файла в DB");
        convertToDBOption.setArgs(2);
        convertToDBOption.setArgName("Путь до файла с исходной информацией, путь до файла с настройками базы данных");

        Option helpOption = new Option("h", "help", true, "данная команда показывает все режимы работы программы");
        helpOption.setArgs(0);

        OptionGroup optionGroup = new OptionGroup();
        Options options = new Options();
        optionGroup.addOption(txtOption);
        optionGroup.addOption(xmlOption);
        optionGroup.addOption(dbOption);
        optionGroup.addOption(convertToXmlOption);
        optionGroup.addOption(helpOption);
        optionGroup.addOption(convertToDBOption);
        options.addOptionGroup(optionGroup);

        CommandLineParser commandLineParser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();

        Engine engine = new Engine(new ConsolePrinter());
        try {
            CommandLine commandLine = commandLineParser.parse(options, args);
            if (commandLine.getOptions().length == 0) {
                helpFormatter.printHelp(200, "Данная програма, в зависимости от выбранного режима, осуществляет парсинг или конвертацию файла,"
                        + " полученного на вход. Может быть выбран только один режим работы.\n", "В данном меню показаны все режимы работы программы с их описанием:\n", options,
                        "Вы не выбрали режим работы программы!");
                return;
            }
            if (commandLine.hasOption("t")) {
                engine.calculate(commandLine.getOptionValue("t"), Engine.FileType.TXT);
            }
            if (commandLine.hasOption("x")) {
                engine.calculate(commandLine.getOptionValue("x"), Engine.FileType.XML);
            }
            if (commandLine.hasOption("d")) {
                engine.calculate(commandLine.getOptionValue("d"), Engine.FileType.DB);
            }
            if (commandLine.hasOption("c")) {
                engine.convert(commandLine.getOptionValues("c")[0], commandLine.getOptionValues("c")[1], Engine.FileType.TXT, Engine.FileType.XML);
            }
            if (commandLine.hasOption("z")) {
                engine.convert(commandLine.getOptionValues("z")[0], commandLine.getOptionValues("z")[1], Engine.FileType.TXT, Engine.FileType.DB);
            }
            if (commandLine.hasOption("h")) {
                helpFormatter.printHelp(200, "В данном меню показаны все режимы работы программы с их описанием:\n", "", options, "");
            }
        } catch (ParseException e) {
            helpFormatter.printHelp(200, "Данная програма, в зависимости от выбранного режима, осуществляет парсинг или конвертацию файла,"
                    + " полученного на вход. Может быть выбран только один режим работы.\n", 
                    "В данном меню показаны все режимы работы программы с их описанием:\n", options,
                    "Режим работы программы был задан неверно!");
        }
    }
}
