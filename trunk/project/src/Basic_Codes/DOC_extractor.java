/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Basic_Codes;

import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import ooo.connector.BootstrapSocketConnector;

/**
 *
 * This class extract the images from a MS Word document
 */

public class DOC_extractor extends File_Extractor {

    private String pathname;
    private String outputfile ="C:/StorePiczFiles/TemporyFile.pdf";


    public DOC_extractor(String pathname) {
        super(pathname);
        this.pathname = pathname;

    }

    public String convert_file() throws BootstrapException  {

        String oooExecutableFolder = "c:/program files/OpenOffice.org 3/program/";
//        outputfile = processOutput(pathname);

        try {
            // Connect to OOo server

            XComponentContext xComponentContext;
            xComponentContext = BootstrapSocketConnector.bootstrap(oooExecutableFolder);

           // get the remote office component context
           // get the remote office service manager

            com.sun.star.lang.XMultiComponentFactory xMCF = xComponentContext.getServiceManager();
            Object oDesktop = xMCF.createInstanceWithContext( "com.sun.star.frame.Desktop", xComponentContext);
            com.sun.star.frame.XComponentLoader xCompLoader = (com.sun.star.frame.XComponentLoader) UnoRuntime.queryInterface(
                                                            com.sun.star.frame.XComponentLoader.class, oDesktop);

            java.io.File file = new java.io.File(pathname);
           // locate the file name to be used
            StringBuilder sLoadUrl = new StringBuilder("file:///" + pathname);
            file = new java.io.File( pathname + ".pdf");
           // get the output file path to be saved
            StringBuilder sSaveUrl = new StringBuilder("file:///" + pathname + ".pdf");

             com.sun.star.beans.PropertyValue[] propertyValue = new com.sun.star.beans.PropertyValue[1];
             propertyValue[0] = new com.sun.star.beans.PropertyValue();
             propertyValue[0].Name = "Hidden";
             propertyValue[0].Value = true;

             Object oDocToStore = xCompLoader.loadComponentFromURL( "file:///" + pathname, "_blank", 0, propertyValue );
             com.sun.star.frame.XStorable xStorable = (com.sun.star.frame.XStorable)UnoRuntime.queryInterface( com.sun.star.frame.XStorable.class, oDocToStore ); propertyValue = new com.sun.star.beans.PropertyValue[ 2 ]; propertyValue[0] = new com.sun.star.beans.PropertyValue(); propertyValue[0].Name = "Overwrite";
             propertyValue[0].Value = true;
             propertyValue[1] = new com.sun.star.beans.PropertyValue();
             propertyValue[1].Name = "FilterName"; propertyValue[1].Value = "writer_pdf_Export";

            // Save the converted pdf file of the Word doc into the given path
             xStorable.storeToURL( "file:///"+ outputfile, propertyValue );


             com.sun.star.util.XCloseable xCloseable = (com.sun.star.util.XCloseable) UnoRuntime.queryInterface(com.sun.star.util.XCloseable.class, oDocToStore );
             if (xCloseable != null ) {
                 xCloseable.close(false);
             }
             else { com.sun.star.lang.XComponent xComp = (com.sun.star.lang.XComponent) UnoRuntime.queryInterface( com.sun.star.lang.XComponent.class, oDocToStore );
                xComp.dispose();
             }

        }
        catch(com.sun.star.uno.Exception e){
        }


        return outputfile;
    }

    
}

