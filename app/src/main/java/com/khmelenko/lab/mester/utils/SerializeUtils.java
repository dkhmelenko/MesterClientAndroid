package com.khmelenko.lab.mester.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Provides methods for quick data serialization
 *
 * @author Dmytro Khmelenko
 */
public final class SerializeUtils {

    private SerializeUtils() {
    }

    /**
     * Serializes data to the file
     *
     * @param filename Filename
     * @param data     Data for serializing
     */
    public static void serializeToFile(String filename, String data) {
        try {
            //use buffering
            OutputStream file = new FileOutputStream(filename);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            try {
                output.writeObject(data);
            } finally {
                output.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Deserializes data from the file
     *
     * @param fileName File name
     * @return Data in string
     */
    public static String deserializeData(String fileName) {
        String data = "";
        try {
            //use buffering
            InputStream file = new FileInputStream(fileName);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);
            try {
                //deserialize the List
                data = (String) input.readObject();
            } finally {
                input.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return data;
    }
}
