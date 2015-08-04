package Manager;

import static Manager.MainWindows.files;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

public class FileChooser extends SwingWorker<File[], Object> {

    private JFileChooser fileChooser;
    private File[] abstractFiles;
    private ArrayList<File> allFiles;
    public static int progress=0;

    public FileChooser() {
        fileChooser = new JFileChooser();
        allFiles = new ArrayList<>();

        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            abstractFiles = fileChooser.getSelectedFiles();
        } else {
           
            abstractFiles = null;
        }

    }

    public void populateFilesList() {
        Path path;
        for (File file : abstractFiles) {
            if (file.isDirectory()) {
                path = file.toPath();
                System.out.println(path.toString());
                extractFiles(path);
            } else {
                progress++;
                allFiles.add(file);
            }
        }
    }

    private void extractFiles(Path dir) {
        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
            for (Path path : stream) {
                if (path.toFile().isDirectory()) {
                    if ((path.toFile().isHidden() && path.toFile().canRead()) == false) {
                        extractFiles(path);
                    }
                } else {
                    if ((path.toFile().isHidden() && path.toFile().canRead()) == false) {
                        progress++;
                        allFiles.add(path.toFile());
                    }
                }
            }
            stream.close();
        } catch (IOException e) {
            System.err.println("ERROR");
            e.printStackTrace();
        }
    }

    public File[] getAbstractFiles() {
        return files;
    }

    @Override
    protected File[] doInBackground() throws Exception {
        if(abstractFiles!=null)
        populateFilesList();
        int size = allFiles.size();
        File[] files = new File[size];
        for (int i = 0; i < size; i++) {
            files[i] = allFiles.get(i);
        }
        return files;
    }
//
//    @Override
//    protected void done() {
//        System.out.println("Done");
//    }
}
