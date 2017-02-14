/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.logging;

import fr.isima.dependencyinjector.annotations.Preferred;
import fr.isima.dependencyinjector.annotations.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kernelith
 */
@Preferred
@Singleton
public class MyLogger implements ILogger
{
    /**
     * List of log entries.
     */
    private final List<String> logs = new ArrayList<>();
    
    @Override
    public boolean contains(String str) 
    {
        return logs.contains(str);
    }

    @Override
    public void add(String str) 
    {
        logs.add(str);
    }
}
