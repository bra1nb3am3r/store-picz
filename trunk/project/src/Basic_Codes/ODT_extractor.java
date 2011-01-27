/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Basic_Codes;

import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.uno.XComponentContext;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import ooo.connector.BootstrapSocketConnector;

/**
 *
 * @author Ishara
 */
public class ODT_extractor extends File_Extractor {

/* Variable Initialization*/
    private String inputFilename ;
    private String outputfile  = "C:/StorePiczFiles/TemporyFile.pdf";
    

    private JPanel panel = new JPanel();

    public ODT_extractor( String inputFilename ) {
        super(inputFilename);
        this.inputFilename = inputFilename;
    }

    public String convert_file(){

        String oooExecutableFolder = "c:/program files/OpenOffice.org 3/program/";
        try {
                // Connect to OOo server

                XComponentContext xComponentContext = BootstrapSocketConnector.bootstrap(oooExecutableFolder);
                ODT_converter converter = new ODT_converter(xComponentContext);

                // Create OOoInputStream
                InputStream inputFile = new BufferedInputStream(new FileInputStream(inputFilename));
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[4096];
                int byteBufferLength = 0;
                while ((byteBufferLength = inputFile.read(byteBuffer)) > 0) {
                        bytes.write(byteBuffer,0,byteBufferLength);
                }
                inputFile.close();
                OOoInputStream inputStream = new OOoInputStream(bytes.toByteArray());

                // Create OOoOutputStream
                OOoOutputStream outputStream = new OOoOutputStream();

                // Convert the document to PDF
                converter.convert(inputStream, outputStream, "writer_pdf_Export");

                // Save the converted file as a tempory file in the given location
                // @ Param outputfile
                FileOutputStream outputFile = new FileOutputStream(outputfile);
                outputFile.write(outputStream.toByteArray());
                outputFile.close();
        }
        catch (BootstrapException e) {
                JOptionPane.showMessageDialog(panel, "Connection not available ", "Error!",JOptionPane.ERROR_MESSAGE );
        }  catch (Exception e) {
//                JOptionPane.showMessageDialog(panel, "File can't be opened ", "Error!",JOptionPane.ERROR_MESSAGE );
        }

        return outputfile;
    }

    
      
}

   