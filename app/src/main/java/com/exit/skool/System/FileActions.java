package com.exit.skool.System;

import android.util.Log;

import com.exit.skool.Contracts.FileContract;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by ryanl on 04/02/2018.
 */

public class FileActions implements FileContract {

    /**
     * Returns the root directory.
     * @param file
     * @return a root file.
     */
    @Override
    public File root(File file)  {

        if(!file.exists()) {
            throw new RuntimeException("The file given by " + file.toString() + " does not exist.");
        }

        File root;
        File current;
        root = current = file;

        while(current != null) {
            root = current;
            current = current.getParentFile();
        }

        return root;
    }

    /**
     * Stores the given file.
     * @param src
     * @param dest
     * @return true if the file was stored in right destination.
     */
    @Override
    public boolean store(File src, File dest) {
        return move(src,dest);
    }

    /**
     *  Moves the given file to another location.
     * @param src
     * @param dest
     * @return true if file was moved, false otherwise.
     */
    @Override
    public boolean move(File src, File dest) {
        if(!src.exists()) {
            throw new RuntimeException("The directory given by " + src.toString() + " does not exist.");
        }

        if(dest.exists()){
            throw new RuntimeException("The destination file already exists and overwrite is not allowed.");
        }

        if(mkdir(dest.getParentFile())) {
            transferFile(src,dest);
            delete(src);
        }

        return false;
    }

    /**
     * Copies the given file to another location.
     * @param src
     * @param dest
     * @return true if successfully copy the file in destination and false otherwise.
     */

    @Override
    public boolean copy(File src, File dest) {
        if(!src.exists()) {
            throw new RuntimeException("The directory given by " + src.toString() + " does not exist.");
        }

        if(dest.exists() && !delete(dest)){
            throw new RuntimeException("The destination file already exists and overwrite is not allowed.");
        }

        if(mkdir(dest.getParentFile())){
            transferFile(src,dest);
        } else{
            return false;
        }
        return true;
    }

    /**
     * Remove a file denoted by this abstract pathname.
     *
     * @param file
     * @return  <code>true</code> if and only if the file is
     *          successfully deleted; <code>false</code> otherwise
     */
    @Override
    public boolean delete(File file) {
        return !file.exists() || file.delete();
    }

    /**
     * Remove all files denoted by this abstract pathname.
     * TODO Implement delete
     * @param file
     * @return <code>true</code> if and only if the files are
     *          successfully deleted; <code>false</code> otherwise
     */
    @Override
    public boolean delete(ArrayList<File> file) {
      return false;
    }

    /**
     * Retrieve all files from a directory without recursion.
     * @param directory
     * @return All Files from a directory not including the files from subdirectories.
     */
    @Override
    public ArrayList<File> files(File directory){

        if(!directory.exists()) {
            throw new RuntimeException("The directory given by " + directory.toString() + " does not exist.");
        }

        if(!directory.isDirectory()) {
            throw new RuntimeException("The path " + directory.toString() + " is not a directory.");
        }

        ArrayList<File> arraylistFiles = new ArrayList<>();

        for(File fileEntry: directory.listFiles()) {
            if(!fileEntry.isDirectory())
                arraylistFiles.add(fileEntry);
        }

        return arraylistFiles;
    }

    /**
     * Retrieve all files from a directory including all subdirectories.
     * @param directory
     * @return  All Files from a directory including all files from subdirectories.
     */
    @Override
    public ArrayList<File> allFiles(File directory) {

        if(!directory.exists()) {
            throw new RuntimeException("The directory given by " + directory.toString() + " does not exist.");
        }

        if(!directory.isDirectory()) {
            throw new RuntimeException("The path " + directory.toString() + " is not a directory.");
        }

        ArrayList<File> arraylistFiles = new ArrayList<>();
        Queue<File> queue = new PriorityQueue<>();

        queue.add(directory);

        while(!queue.isEmpty()) {
            File current = queue.poll();

            for (File f : current.listFiles()) {
                if(f.isDirectory()) {
                    queue.add(f);
                } else {
                    arraylistFiles.add(f);
                }
            }
        }

        return arraylistFiles;
    }

    /**
     * Retrieve all directories within a directory without recursion.
     * @param directory
     * @return a file array with all directories not including the subdirectories.
     */
    @Override
    public ArrayList<File> directories(File directory) {

        if(!directory.exists()) {
            throw new RuntimeException("The directory given by " + directory.toString() + " does not exist.");
        }

        if(!directory.isDirectory()) {
            throw new RuntimeException("The path " + directory.toString() + " is not a directory.");
        }

        ArrayList<File> arrayListDirectories = new ArrayList<>();

        for(File fileEntry: directory.listFiles()) {
            if(fileEntry.isDirectory())
                arrayListDirectories.add(fileEntry);
        }

        return arrayListDirectories;
    }

    /**
     * Retrieve all directories within a directory including all subdirectories.
     * @param directory
     * @return a file array with all directories including all subdirectories.
     */
    @Override
    public ArrayList<File>allDirectories(File directory) {

        if(!directory.exists()) {
            throw new RuntimeException("The directory given by " + directory.toString() + " does not exist.");
        }

        if(!directory.isDirectory()) {
            throw new RuntimeException("The path " + directory.toString() + " is not a directory.");
        }

        ArrayList<File> arraylistDirectory = new ArrayList<>();
        Queue<File> queue = new PriorityQueue<>();

        queue.add(directory);

        while(!queue.isEmpty()) {
            File current = queue.poll();

            for (File f : current.listFiles()) {
                if(f.isDirectory()) {
                    arraylistDirectory.add(f);
                    queue.add(f);
                }
            }
        }

        return arraylistDirectory;
    }

    /**
     * Creates the directory named by this abstract pathname, including any
     * necessary but nonexistent parent directories.
     *
     * //todo Check for parent directories that might have been created in case of failure.
     *
     * @param directory
     * @return <code>true</code> if and only if the directory was created,
     *          along with all necessary parent directories; <code>false</code>
     *          otherwise.
     */
    @Override
    public boolean mkdir(File directory) {
        return directory.exists() || directory.mkdirs();
    }

    /**
     * Remove a directory denoted by this abstract pathname.
     * @param directory
     * @return <code>true</code> if and only if the directory is
     *          successfully deleted; <code>false</code> otherwise.
     */
    @Override
    public boolean rmdir(File directory) {

        if(!directory.exists()) {
            throw new RuntimeException("The directory given by " + directory.toString() + " does not exist.");
        }

        if(!directory.isDirectory()) {
            throw new RuntimeException("The path " + directory.toString() + " is not a directory.");
        }

        Stack<File> stack = new Stack<>();

        stack.add(directory);

        while(!stack.isEmpty()) {
            File current = stack.pop();

            for (File f : current.listFiles()) {
                if(f.isDirectory()) {
                    stack.add(f);
                } else {
                    if(!delete(f)){
                        return false;
                    }
                }
            }
        }

        return directory.delete();
    }

    /**
     * transfer the file in src to destination bit by bit.
     * @param src
     * @param dest
     * @return true if was successful Transferred and false otherwise.
     */
    private boolean transferFile(File src, File dest) {
        try {
            InputStream in = null;
            OutputStream out = null;

            in = new FileInputStream(src.getCanonicalPath());
            out = new FileOutputStream(dest.getAbsolutePath());

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            out.flush();
            out.close();
            out = null;
        } catch (IOException e) {
            Log.e("FileSystem error", e.getMessage());
            return  false;
        }
        return true;
    }
}