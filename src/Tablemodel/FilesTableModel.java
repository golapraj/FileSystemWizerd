package Tablemodel;

import Manager.*;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.AbstractTableModel;
public class FilesTableModel extends AbstractTableModel {

    private String[] Names;
    private int numberOfFiles;
    private String[] columnNames;
    private Object[][] dataObject;

    public FilesTableModel() {
        Names = MainWindows.fileNames;
        numberOfFiles = MainWindows.numberOfFiles;
        columnNames = new String[]{"Serial","Name","Type","Location","Size","Last Modified"};
        dataObject = new Object[numberOfFiles][6];

        if (Names != null) {
            for (int i = 0; i < numberOfFiles; i++) {
                dataObject[i][0] = i + 1;
                dataObject[i][1] = Names[i];
                dataObject[i][2]= Names[i].substring(Names[i].lastIndexOf('.')+1);
                  dataObject[i][3] = MainWindows.files[i].toString().substring(0, MainWindows.files[i].toString().lastIndexOf('\\'));
                  
                double size = (double)(MainWindows.files[i].length());
            
                if (size > 1073741824) {
                    size /= 1073741824;
                    dataObject[i][4] = String.format("%.2f", size) + " GB";
                } else if (size > 1048576) {
                    size /= 1048576;
                    dataObject[i][4] = String.format("%.2f", size) + " MB";
                } else if (size > 1024) {
                    size /= 1024;
                    dataObject[i][4] = String.format("%.2f", size) + " KB";
                } else {
                    dataObject[i][4] = String.format("%.0f", size) + " Byte";
                }
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                
                    dataObject[i][5] = dateFormat.format(MainWindows.files[i].lastModified());
            }
        } else {
            dataObject = null;
        }
    }
    
    @Override
    public int getRowCount() {
        if (dataObject != null) {
            return dataObject.length;
        } else {
            return 36;
        }
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (dataObject != null) {
            return dataObject[rowIndex][columnIndex];
        } else {
            return null;
        }
    }
}
