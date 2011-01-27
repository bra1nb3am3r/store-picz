/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Basic_Codes;

import com.sun.star.comp.helper.BootstrapException;

/**
 *  This abstract class is the parent class of Odt_extractor and Doc_extractor classes.
 * @author Ishara
 */
public abstract class File_Extractor {

    private String pathname;
    
    public File_Extractor(String pathname) {

        this.pathname = pathname;
    }

    // converts an input file into a PDF file
    abstract String convert_file()throws BootstrapException ;

}
