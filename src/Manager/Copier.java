package Manager;

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

public class Copier extends SwingWorker<Object, Object> {

    public static Path[] paths;
    public static Path[] target;
    private File[] files = MainWindows.files;
    private String[] fileNames = MainWindows.fileNames;
    public static ArrayList<File> copyFiles;
    public static HashSet<File> copyFile;
    private JProgressBar progressBar;
    private JLabel label;
    public static int clk;

    public Copier(JProgressBar prog, JLabel lab) throws IOException {
        copyFiles = new ArrayList<>();
        copyFile = new HashSet<>();
        this.progressBar = prog;
        this.label = lab;
        clk = 0;
    }

    @Override
    protected Object doInBackground() throws IOException {

        if (CopyMoveActionWindows.all) {
            for (int i = 0; i < files.length; i++) {
                if (!copyFiles.contains(files[i])) {
                    copyFiles.add(files[i]);
                }

            }
        } else {
            if (CopyMoveActionWindows.hasExtension) {
                if (!CopyMoveActionWindows.extension.isEmpty()) {
                    for (int i = 0; i < files.length; i++) {
                        if (CopyMoveActionWindows.hasExtensionIgnoreCase) {
                            if (fileNames[i].substring(fileNames[i].lastIndexOf('.') + 1, fileNames[i].length()).equalsIgnoreCase(CopyMoveActionWindows.extension)) {
                                if (!copyFiles.contains(files[i])) {
                                    copyFiles.add(files[i]);
                                }

                            }
                        } else {
                            if (fileNames[i].substring(fileNames[i].lastIndexOf('.') + 1, fileNames[i].length()).equals(CopyMoveActionWindows.extension)) {
                                if (!copyFiles.contains(files[i])) {
                                    copyFiles.add(files[i]);
                                }

                            }
                        }

                    }
                }
            }
            if (CopyMoveActionWindows.hasWord) {
                if (!CopyMoveActionWindows.searchWord.isEmpty()) {
                    for (int i = 0; i < files.length; i++) {
                        if (CopyMoveActionWindows.hasWordIgnoreCase) {
                            if (fileNames[i].toLowerCase().contains(CopyMoveActionWindows.searchWord.toLowerCase())) {
                                if (!copyFiles.contains(files[i])) {
                                    copyFiles.add(files[i]);
                                }

                            }
                        } else {
                            if (fileNames[i].contains(CopyMoveActionWindows.searchWord)) {
                                if (!copyFiles.contains(files[i])) {
                                    copyFiles.add(files[i]);
                                }
                            }
                        }
                    }
                }
            }
            if (CopyMoveActionWindows.hasStart) {
                if (!CopyMoveActionWindows.startWord.isEmpty()) {
                    for (int i = 0; i < files.length; i++) {
                        if (CopyMoveActionWindows.hasstartIgnoreCase) {
                            if (fileNames[i].toLowerCase().startsWith(CopyMoveActionWindows.startWord.toLowerCase())) {
                                if (!copyFiles.contains(files[i])) {
                                    copyFiles.add(files[i]);
                                }

                                System.err.println("fom st else");
                            }
                        } else {
                            if (fileNames[i].startsWith(CopyMoveActionWindows.startWord)) {
                                if (!copyFiles.contains(files[i])) {
                                    copyFiles.add(files[i]);
                                }

                                System.err.println("fom st else");
                            }
                        }
                    }
                }
            }

            if (CopyMoveActionWindows.isshortcut) {
                for (int i = 0; i < files.length; i++) {
                    if (fileNames[i].substring(fileNames[i].lastIndexOf('.') + 1, fileNames[i].length()).equalsIgnoreCase("lnk")) {
                        if (!copyFiles.contains(files[i])) {
                            copyFiles.add(files[i]);
                        }

                    }
                }
            }
            if (CopyMoveActionWindows.lastmodified) {
                for (int i = 0; i < files.length; i++) {
                    if (CopyMoveActionWindows.isSameDate(files[i], CopyMoveActionWindows.date)) {
                        if (!copyFiles.contains(files[i])) {
                            copyFiles.add(files[i]);
                        }

                    }
                }
            }
            if (CopyMoveActionWindows.hassize) {
                //System.out.println("hhhhhhhhhhhhhhhhhhhhhhhh" + CopyMoveActionWindows.sizeConstraint);
                long size = (long)CopyMoveActionWindows.size * (long) Math.pow((double) 1024, (double) CopyMoveActionWindows.multiplier);
                System.out.printf("size is %d\n", size);
                for (int i = 0; i < files.length; i++) {
                    if (CopyMoveActionWindows.sizeConstraint == 1) {
                        if (files[i].length() > size) {
                            System.out.printf("Greater Size %d: %d\n", i + 1, files[i].length());
                            if (!copyFiles.contains(files[i])) {
                                copyFiles.add(files[i]);
                            }
                        }
                    } else {
                        System.out.printf("Less Size %d: %d\n", i + 1, files[i].length());
                        if (files[i].length() < size) {
                            if (!copyFiles.contains(files[i])) {
                                copyFiles.add(files[i]);
                            }
                        }
                    }
                }
            }
        }

        paths = new Path[copyFiles.size()];
        target = new Path[copyFiles.size()];
        for (int i = 0; i < copyFiles.size(); i++) {

            paths[i] = copyFiles.get(i).toPath();
            File F = new File(CopyMoveActionWindows.targetPath + "\\" + copyFiles.get(i).getName());
            target[i] = F.toPath();
            //System.out.println(paths[i].toString());
        }
        for (int i = 0; i < paths.length; i++) {
//            if (target[i].toFile().exists()) {
//                String parent = copyFiles.get(i).getParent();
////                parent=parent.replace("\\",".");
////                System.out.println("pa "+parent);
//                File F = new File(CopyMoveActionWindows.targetPath + "\\ Copy of " + copyFiles.get(i).getName());
//                target[i] = F.toPath();
//                //System.out.println("hdhashdg"+target[i]);
//                System.out.println("existexistexist");
//                //System.out.println(target[i].toString());
//            }
            double pr = ((double) i / paths.length) * 100;
            progressBar.setValue((int) (pr + 1));
            label.setText("Copying " + Integer.toString(i + 1) + " of " + Integer.toString(paths.length));

            Files.copy(paths[i], target[i], StandardCopyOption.REPLACE_EXISTING);


            clk = i;
        }
        return null;
    }
}
