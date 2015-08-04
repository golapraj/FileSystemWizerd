package Manager;

import static Manager.Copier.clk;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class Deleter extends SwingWorker<Object, Object>{

   public static Path[] paths;
    public static Path[] target;
    private File[] files = MainWindows.files;
    private String[] fileNames = MainWindows.fileNames;
    public static ArrayList<File> copyFiles;
    public static HashSet<File> copyFile;
    private JProgressBar progressBar;
    private JLabel label;
    public static int clk;

    public Deleter(JProgressBar prog, JLabel lab) throws IOException {
        copyFiles = new ArrayList<>();
        copyFile = new HashSet<>();
        this.progressBar = prog;
        this.label = lab;
        clk = 0;
    }

    @Override
    protected Object doInBackground() throws IOException {

        if (DeleteActionWindows.all) {
            for (int i = 0; i < files.length; i++) {
                if(!copyFiles.contains(files[i]))
                    copyFiles.add(files[i]);
               
            }
        } else {
            if (DeleteActionWindows.hasExtension) {
                if(!DeleteActionWindows.extension.isEmpty())
                for (int i = 0; i < files.length; i++) {
                    if (DeleteActionWindows.hasExtensionIgnoreCase) {
                        if (fileNames[i].substring(fileNames[i].lastIndexOf('.') + 1, fileNames[i].length()).equalsIgnoreCase(DeleteActionWindows.extension)) {
                            if(!copyFiles.contains(files[i]))
                    copyFiles.add(files[i]);
               
                        }
                    } else {
                        if (fileNames[i].substring(fileNames[i].lastIndexOf('.') + 1, fileNames[i].length()).equals(DeleteActionWindows.extension)) {
                            if(!copyFiles.contains(files[i]))
                    copyFiles.add(files[i]);
               
                        }
                    }
                    System.out.println("golap");
                }
            }
            if (DeleteActionWindows.hasWord) {
                if(!DeleteActionWindows.searchWord.isEmpty())
                for (int i = 0; i < files.length; i++) {

                    if (DeleteActionWindows.hasWordIgnoreCase) {
                        if (fileNames[i].toLowerCase().contains(DeleteActionWindows.searchWord.toLowerCase())) {
                            if(!copyFiles.contains(files[i]))
                    copyFiles.add(files[i]);
               
                        }
                    } else {
                        if (fileNames[i].contains(DeleteActionWindows.searchWord)) {
                            if(!copyFiles.contains(files[i]))
                    copyFiles.add(files[i]);
               }
                    }
                }
            }
            if (DeleteActionWindows.hasStart) {
                if(!DeleteActionWindows.startWord.isEmpty())
                for (int i = 0; i < files.length; i++) {
                    if (DeleteActionWindows.hasstartIgnoreCase) {
                        if (fileNames[i].toLowerCase().startsWith(DeleteActionWindows.startWord.toLowerCase())) {
                            if(!copyFiles.contains(files[i]))
                    copyFiles.add(files[i]);
               
                           // System.err.println("fom st else");
                        }
                    } else {
                        if (fileNames[i].startsWith(DeleteActionWindows.startWord)) {
                            if(!copyFiles.contains(files[i]))
                    copyFiles.add(files[i]);
               
                           // System.err.println("fom st else");
                        }
                    }
                }
            }

            if (DeleteActionWindows.isshortcut) {
                for (int i = 0; i < files.length; i++) {
                    if (fileNames[i].substring(fileNames[i].lastIndexOf('.') + 1, fileNames[i].length()).equalsIgnoreCase("lnk")) {
                         if(!copyFiles.contains(files[i]))
                    copyFiles.add(files[i]);
               
                    }
                }
            }
            if (DeleteActionWindows.lastmodified) {
                for (int i = 0; i < files.length; i++) {
                    if (DeleteActionWindows.isSameDate(files[i], DeleteActionWindows.date)) {
                        if(!copyFiles.contains(files[i]))
                    copyFiles.add(files[i]);
               
                    }
                }
            }
            if (DeleteActionWindows.hassize) {
                long size = (long)DeleteActionWindows.size * (long) Math.pow((double) 1024, (double) DeleteActionWindows.multiplier);
                System.out.printf("size is %d\n", size);
                for (int i = 0; i < files.length; i++) {
                    if (DeleteActionWindows.sizeConstraint == 1) {
                       // System.out.printf("Greater Size %d: %d\n", i + 1, files[i].length());
                        if (files[i].length() > size) {
                            if(!copyFiles.contains(files[i]))
                    copyFiles.add(files[i]);
                        }
                    } else {
                        //System.out.printf("Less Size %d: %d\n", i + 1, files[i].length());
                        if (files[i].length() < size) {
                            if(!copyFiles.contains(files[i]))
                    copyFiles.add(files[i]);
                        }
                    }
                }
            }
        }

       
        paths = new Path[copyFiles.size()];
        target = new Path[copyFiles.size()];
        for (int i = 0; i < copyFiles.size(); i++) {

            paths[i] = copyFiles.get(i).toPath();
        }
        for (int i = 0; i < paths.length; i++) {
            double pr = ((double) i / paths.length) * 100;
            progressBar.setValue((int) (pr + 1));
            label.setText("Deleting " + Integer.toString(i + 1) + " of " + Integer.toString(paths.length));
            Files.delete(paths[i]);
            clk=i;
        }
        return null;
    }
}
