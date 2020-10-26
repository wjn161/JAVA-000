package me.wujn.geektime.javaweek01;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * TODO: do not using System.out.println for logging.
 *
 * @author wjn161
 * @version HelloClassLoader.java, v 0.1 2020-10-20 11:51 AM wujn
 */
public class HelloClassLoader extends ClassLoader {

    /**
     * class file suffix
     */
    private static final String CLASS_EXTENSION = ".xlass";
    /**
     * class file path: resources://classfile/Hello.xlass
     */
    private static final String CLASS_FILE_PATH = "classfile";

    /**
     * intpustream to byte array
     */
    private static final int TEMP_BYTE_ARRRAY_LENGTH = 256;

    /**
     * magic number for encode class file
     */
    private static final int MAGIC_MUBER = 255;

    public HelloClassLoader(ClassLoader parentClassLoader) {
        super(parentClassLoader);
    }

    /**
     * find class file in classpath end with .xlass and decode the class file
     *
     * @param name class name
     * @return java.lang.Class
     * @throws ClassNotFoundException class file not exist
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (name == null || "".equals(name)) {
            throw new IllegalArgumentException("class name is empty");
        }
        String filePath = CLASS_FILE_PATH + "/" + name + CLASS_EXTENSION;
        // load class file
        try (InputStream stream = getResourceAsStream(filePath)) {
            if (stream == null) {
                System.out.println("class file not found");
                return null;
            }
            byte[] byteArray = stream2Byte(stream);
            if (byteArray != null) {
                for (int i = 0; i < byteArray.length; i++) {
                    byteArray[i] = (byte) (MAGIC_MUBER - byteArray[i]);
                }
                return defineClass(name, byteArray, 0, byteArray.length);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return super.findClass(name);
    }

    /**
     * convert inputStream to byte array
     *
     * @param inStream file stream
     * @return byte []
     * @throws IOException ioexception
     */
    private static byte[] stream2Byte(InputStream inStream) throws IOException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        byte[] buff = new byte[TEMP_BYTE_ARRRAY_LENGTH];
        int rc;
        try {
            while ((rc = inStream.read(buff, 0, TEMP_BYTE_ARRRAY_LENGTH)) > 0) {
                byteArray.write(buff, 0, rc);
            }
            return byteArray.toByteArray();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
