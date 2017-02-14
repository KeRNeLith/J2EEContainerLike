/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.logging;

/**
 *
 * @author kernelith
 */
public interface ILogger 
{
    /**
     * Indicate if logger had logged the passed string.
     * @param str String to check.
     * @return True if logger had logged the string.
     */
    boolean contains(String str);

    /**
     * Add a log entry.
     * @param str Log entry.
     */
    void add(String str);
}
