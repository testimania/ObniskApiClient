package swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class OpenFileDirDialog {

    public static File getFile(){
        System.out.println( "action: " );
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory( new File("c:\\temp"));
        // TODO: 31.08.2023 FileFilter
        jFileChooser.showOpenDialog(null);
        File file =  jFileChooser.getSelectedFile();
            if ( file != null)
                System.out.println(" file: " + file.toString());
            else
                System.out.println(" File was not chosen.");

        return file;
    }

    public static void openFD(){

        JFrame jFrame = new JFrame(" File open dialog");
            jFrame.setSize(180, 180);
            jFrame.setLocation( 300, 300);
            JButton jButton = new JButton(" Open file");
            jButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println( "action: " + e.toString());
                    JFileChooser jFileChooser = new JFileChooser();
                    jFileChooser.showOpenDialog( jButton);
                    File file =  jFileChooser.getSelectedFile();
                    if ( file != null)
                        System.out.println(" file: " + file.toString());
                    else
                        System.out.println(" File was not chosen.");
                }
            });
            jFrame.add( jButton);
        jFrame.setVisible( true);


    }

    public static void main(String[] args) {
//        FileOpenDialog fileOpenDialog = new FileOpenDialog();
        System.out.println( OpenFileDirDialog.getFile());
        System.out.println( OpenFileDirDialog.getDir());

    }

    private static File getDir() {
        return null;
    }

}
