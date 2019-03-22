/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interoperability.interoperability;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 *
 * @author qbiss
 */
public class ImportCSV {

    private File file;

    public ImportCSV() {

    }

    public void copyFile() throws IOException {
        String name = this.file.getName();
        Path source = Paths.get(name);
        Path destination = Paths.get("C:/Users/qbiss/Documents/CSVFiles/" + name);
        Files.move(source, destination, REPLACE_EXISTING);
    }
}
