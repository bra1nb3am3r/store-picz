/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Basic_Codes;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File ;
import java.io.IOException ;
import java.util.ArrayList;
import java.util.Iterator ;
import java.util.List ;
import java.util.Map ;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

 import org.apache.pdfbox.pdmodel.PDDocument;
 import org.apache.pdfbox.pdmodel.PDPage;
 import org.apache.pdfbox.pdmodel.PDResources;
 import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
 import org.apache.pdfbox.pdmodel.encryption.StandardDecryptionMaterial;
 import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

/**
       * This will read a pdf and extract images.
       *
       * usage: java org.apache.pdfbox.ExtractImages &lt;pdffile&gt; &lt;password&gt; [imageprefix]
       *
       * @author  <a href="mailto:ben@benlitchfield.com">Ben Litchfield</a>
       * @version $Revision: 1.7 $
       */
/** Reused by the Author Ishara Premadasa, ishaa@live.com */

public class PDF_extractor {

/* Initialization of variables */

     private int imageCounter = 0;
     private int l =0;
     private String path;
     private static final String  PASSWORD = "password";
     private static final String  PREFIX = "prefix";
     private ArrayList <Image> image_array = new ArrayList<Image>() ;
     JFrame frame = new JFrame();

     PDF_extractor(){

     }

/* This method will get the input PDF file and will extract the images from it*/
     public void  extractImages( String [] input, String path ) throws Exception
     {      this.path= path;
         if( input.length < 1 || input.length > 3 )
         {
             usage();
         }
         else
         {
             String  pdfFile = null;
             String  password = "";
             String  prefix = null;

             // Check the initial information is validated
             for( int i=0; i<input.length; i++ )
             {
                 if( input[i].equals( PASSWORD ) )
                 {
                   i++;
                   if( i >= input.length )
                     {
                         usage();
                     }
                     password = input[i];
                 }
                else if( input[i].equals( PREFIX ) )
                 {
                     i++;
                     if( i >= input.length )
                     {
                         usage();
                     }
                     prefix = input[i];
                 }
                 else
                 {
                     if( pdfFile == null )
                     {
                         pdfFile = input[i];
                     }
                 }
             }
             if(pdfFile == null)
             {
                 usage();
             }
             else
             {
                 if( prefix == null && pdfFile.length() >4 )
                 {
                     prefix = pdfFile.substring( 0, pdfFile.length() -4 );
                 }

                 PDDocument document = null;

                 try
                 {

                     document = PDDocument.load( pdfFile );

                     if( document.isEncrypted() )
                     {

                         StandardDecryptionMaterial spm = new StandardDecryptionMaterial(password);
                         document.openProtection(spm);
                         AccessPermission ap = document.getCurrentAccessPermission();


                         if( ! ap.canExtractContent() )
                         {
                             
                    JOptionPane.showMessageDialog(frame, "No permission to extract this file ", "Error!",JOptionPane.WARNING_MESSAGE );
                         }
                     }

                         List pages = document.getDocumentCatalog().getAllPages();

                         PDPage page = (PDPage)pages.get(0);
                         PDResources resources = page.getResources();
                         Map  images = resources.getImages();
                         if( images != null )
                         {
                            Iterator  imageIter = images.keySet().iterator();

                         if(imageIter.hasNext()){
                             while( imageIter.hasNext() )
                             {
                                 String  key = (String )imageIter.next();
                                 PDXObjectImage image = (PDXObjectImage)images.get( key );

                                 try{
                                 Image img = image.getRGBImage();
                                 WriteToFloder(img, path);
                                 Add_to_array(img);
                                 imageCounter++;
                                     
                                 }
                                 catch(Exception e){
                                 }
                            }
                         }
                         else if ( !imageIter.hasNext()){

                                 List mypages = document.getDocumentCatalog().getAllPages();
                                 Iterator  iter = mypages.iterator();

                             while( iter.hasNext() )
                             {
                                 PDPage mypage = (PDPage)iter.next();
                                 PDResources myresources = mypage.getResources();
                                 Map  myimages = myresources.getImages();

                            if( myimages != null )
                            {

                             Iterator  myimageIter = myimages.keySet().iterator();
                             while( myimageIter.hasNext() )
                             {
                                 String  key = (String )myimageIter.next();
                                 PDXObjectImage image = (PDXObjectImage)myimages.get( key );

                                 try{
                                 Image myimg = image.getRGBImage();
                                 WriteToFloder(myimg, pdfFile);
                                 Add_to_array(myimg);
                                 imageCounter++;

                                 }
                                 catch(Exception e){
                                 }
                              }
                            }
                         }
                      }
                   }

                 }

                 catch(Exception e){

                 }

                 finally
                 {
                     if( document != null )
                     {
                         document.close();
                     }
                 }
             
             }
         }
     }

     /**
      * This will print the usage requirements and exit.
      */
     private static void usage()
     {
         System.out.println( "Usage: Follow this method to give inputs to the program..\n" +
             " -password <password> Password to decrypt document\n" +
             " -prefix <image-prefix> Image prefix(default to pdf name)\n" +
             " <PDF file> The PDF document to use\n"
             );
         System.exit( 1 );
     }

     // This method stores the extracted images in the ArrayList
    private ArrayList<Image> Add_to_array(Image name) {

                image_array.add(name);
                return image_array;
    }

    private int CheckImageSize(PDXObjectImage image){

        int height = image.getHeight();
        int width = image.getWidth();

        int Size = height * width;
        int kbSize = Size/ 1024;

        return kbSize;
    }

/*This method will save the extracted image into the given folder*/

        // @ param output_image
 private void WriteToFloder(Image image, String path) throws IOException {

        BufferedImage bufferedImage = getBufferedImageFromImage(image);

        String location = path.substring(0, path.length()-4);
        System.out.println("save: " + location);

        File f = new File(location);
        f.mkdirs();
        File output_image = new File(location+"/"+"Image"+ (l+1)+".jpg");

        ImageIO.write(bufferedImage, "jpg", output_image);
         l++;


 }

    /**
     * This method takes the Image object and
     * creates BufferedImage of it
     *
     * @param img
     * @return
     */
    public BufferedImage getBufferedImageFromImage(Image img)
    {
        //This line is important, this makes sure that the image is
        //loaded fully
        img = new ImageIcon(img).getImage();

        //Create the BufferedImage object with the width and height of the Image
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);

        //Create the graphics object from the BufferedImage
        Graphics g = bufferedImage.createGraphics();

        //Draw the image on the graphics of the BufferedImage
        g.drawImage(img, 0, 0, null);

        //Dispose the Graphics
        g.dispose();

        //return the BufferedImage
        return bufferedImage;
    }

}