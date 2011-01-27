/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Basic_Codes;

    import com.sun.star.beans.PropertyValue;
    import com.sun.star.frame.XComponentLoader;
    import com.sun.star.frame.XStorable;
    import com.sun.star.lang.XComponent;
    import com.sun.star.lang.XMultiComponentFactory;
    import com.sun.star.uno.Exception;
    import com.sun.star.uno.UnoRuntime;
    import com.sun.star.uno.XComponentContext;
    import com.sun.star.util.XCloseable;
/**
 *
 * This class loads a MS Word file or ODT file to convert it to a pdf file.
 */
    
public class ODT_converter {

     private XComponentContext xComponentContext;

        public ODT_converter(XComponentContext xComponentContext) {
            this.xComponentContext = xComponentContext;
        }

        // converts an ODT document into a PDF file
        public void convert(OOoInputStream input, OOoOutputStream output, String filterName) throws Exception {
            XMultiComponentFactory xMultiComponentFactory = xComponentContext.getServiceManager();
            Object desktopService = xMultiComponentFactory.createInstanceWithContext("com.sun.star.frame.Desktop", xComponentContext);
            XComponentLoader xComponentLoader = (XComponentLoader) UnoRuntime.queryInterface(XComponentLoader.class, desktopService);

            PropertyValue[] conversionProperties = new PropertyValue[2];
            conversionProperties[0] = new PropertyValue();
            conversionProperties[1] = new PropertyValue();

            conversionProperties[0].Name = "InputStream";
            conversionProperties[0].Value = input;
            conversionProperties[1].Name = "Hidden";
            conversionProperties[1].Value = true;

            XComponent document = xComponentLoader.loadComponentFromURL("private:stream", "_blank", 0, conversionProperties);

            conversionProperties[0].Name = "OutputStream";
            conversionProperties[0].Value = output;
            conversionProperties[1].Name = "FilterName";
            conversionProperties[1].Value = filterName;

            XStorable xstorable = (XStorable) UnoRuntime.queryInterface(XStorable.class,document);
            xstorable.storeToURL("private:stream", conversionProperties);

            XCloseable xclosable = (XCloseable) UnoRuntime.queryInterface(XCloseable.class,document);
            xclosable.close(true);
        }

}
