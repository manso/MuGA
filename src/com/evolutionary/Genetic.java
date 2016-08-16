/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evolutionary;

import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author zulu
 */
public class Genetic implements Serializable {

    public static Random defaultRandom = new Random();
    public Random random = defaultRandom; // random generator

    public String getInformation() {
        return "Not implemented";
    }

    /**
     * update parameters definition
     *
     * @param params individual parameters
     */
    public void setParameters(String params) throws RuntimeException {
        //do nothing
        // overwrite in the subclasses if needed
    }

    /**
     *
     * @param params individual parameters
     */
    public String getParameters() {
        //do nothing
        return "";
    }

    public String getName() {
       return getName(this);
    }
    
    public String getSimpleName() {
       return getClass().getSimpleName();
    }
    
     public static String getName(Genetic genetic) {
        StringBuilder txt = new StringBuilder(genetic.getClass().getCanonicalName());
        String[] params = genetic.getParameters().split("\\s+");//split string by with chars - spaces tab \n
        txt.append("( ");
        for (int i = 0; i < params.length; i++) {
            txt.append(params[i]);
            if (i < params.length - 1) {
                txt.append(" , ");
            }
        }
        txt.append(" )");
        return txt.toString().trim();
    }

    /**
     * creates a new Genetic object using a string comand <classname parameters>
     * using default constructor and setParameters
     *
     * @param def classname parametrs
     * @return genetic object or null if ERROR
     */
    public static Genetic createNew(String def) throws RuntimeException {

        try {
            String name, params = "";
            if (def.indexOf(' ') > 0) { // name + parameters
                name = def.substring(0, def.indexOf(' ')).trim();
                params = def.substring(def.indexOf(' ')).trim();
            } else { // only the name
                name = def.trim();
            }
            Genetic obj = (Genetic) Class.forName(name).newInstance();
            obj.setParameters(params);
            return obj;
        } catch (NoClassDefFoundError | ClassNotFoundException ex) {
            throw new RuntimeException(ex.getMessage());
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex.getMessage());
        } 
    }

    /**
     * creates a raw clone of a genetic object using default constructor and
     * setParameters
     *
     * @param original object to clone
     * @return raw clone of original
     */
    public static Genetic getClone(Genetic original) throws RuntimeException {
        Genetic obj = createNew(original.getClass().getCanonicalName()
                + " "
                + original.getParameters());
        obj.random = original.random;
        return obj;

    }

    @Override
    public String toString() {
        return getName();
    }

    public void setRandomGenerator(Random rnd) {
        this.random = rnd;
    }
    public Random getRandomGenerator() {
        return random;
    }

    public static String VERSION = "MuGA (c) 2016 - ver 1.0";
    public static int NUM_THREADS_TO_TESTS = 20;

}
