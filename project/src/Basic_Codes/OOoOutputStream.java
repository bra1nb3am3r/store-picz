/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Basic_Codes;

    import java.io.ByteArrayOutputStream;
    import com.sun.star.io.BufferSizeExceededException;
    import com.sun.star.io.NotConnectedException;
    import com.sun.star.io.XOutputStream;

/**
 *
 * @reused by author, This class is used to write the content in a word or open document file
 *                    into output stream or location
 */

/**
 * <a href="http://www.oooforum.org/forum/viewtopic.phtml?t=13205">OOInputStream from the thread <b>OOo-Java: Using XInputStream...</b></a>
 */

    public class OOoOutputStream extends ByteArrayOutputStream implements XOutputStream {

        public OOoOutputStream() {
            super(32768);
        }

        // Implement XOutputStream
        public void writeBytes(byte[] values) throws NotConnectedException, BufferSizeExceededException, com.sun.star.io.IOException {
            try {
                this.write(values);
            }
            catch (java.io.IOException e) {
            }
        }

        public void closeOutput() throws NotConnectedException, BufferSizeExceededException, com.sun.star.io.IOException {
            try {
                super.flush();
                super.close();
            }
            catch (java.io.IOException e) {
            }
        }

        @Override
        public void flush() {
            try {
                super.flush();
            }
            catch (java.io.IOException e) {
            }
        }
    }
