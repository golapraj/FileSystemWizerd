package Manager;

import Manager.*;
import java.io.File;
import javax.swing.JFileChooser;

public class TargetFileChooser {

    private  JFileChooser fileChooser = new JFileChooser();
    private  File files;

    public TargetFileChooser() {

	fileChooser.setMultiSelectionEnabled(false);
	fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

	if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	    files = fileChooser.getSelectedFile();
	}
	else {
	    files = null;
	}
    }
    public File getFiles() {
	return files;
    }
}