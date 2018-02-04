package com.exit.skool.Contracts;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ryanl on 04/02/2018.
 */

public interface FileContract {
    /**
     * Returns the root directory.
     * @return
     */
    File root(File file);
    
    /**
     * Stores the given file.
     * @param src
     * @param dest
     * @return
     */
    boolean store(File src, File dest);
    
    
    /**
     * Moves the given file to another location.
     * @param src
     * @param dest
     * @return
     */
    boolean move(File src, File dest) ;
    
    
    /**
     * Copies the given file to another location.
     * @param src
     * @param dest
     * @return
     */
    boolean copy(File src, File dest);
    
    
    /**
     * Deletes the given file.
     * @param file
     * @return
     */
    boolean delete(File file);
    
    
    /**
     * Deletes the given files.
     * @param file
     * @return
     */
    boolean delete(ArrayList<File> file);
    
    
    /**
     * Retrieve all files from a directory without recursion.
     * @param directory
     * @return
     */
    ArrayList<File> files(File directory);
    
    
    /**
     * Retrieve all files from a directory including all subdirectories.
     * @param directory
     * @return
     */
    ArrayList<File> allFiles(File directory);
    
    
    /**
     * Retrieve all directories within a directory without recursion.
     * @param directory
     * @return
     */
    ArrayList<File> directories(File directory);
    
    
    /**
     * Retrieve all directories within a directory including all subdirectories.
     * @param directory
     * @return
     */
    ArrayList<File> allDirectories(File directory);
    
    
    /**
     * Create the given directory.
     * @param directory
     * @return
     */
    boolean mkdir(File directory);
    
    
    /**
     * Remove the given directory and all its files.
     * @param directory
     * @return
     */
    boolean rmdir(File directory);
}
