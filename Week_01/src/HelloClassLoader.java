import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 自定义ClassLoader，解析Hello.xlass
 */
public class HelloClassLoader extends ClassLoader {
    public static void main(String[] args) {
        try {
            Object cla = new HelloClassLoader().findClass("Hello").newInstance();
            Method method = cla.getClass().getMethod("hello");
            method.invoke(cla);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = null;
        try {
            bytes = read(HelloClassLoader.class.getResource("Hello.xlass").getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name, bytes, 0, bytes.length);
    }

    /*
     *读取文本数据并转换
     *
     */
    public static byte[] read(String fileName) throws IOException {
        InputStream is = new FileInputStream(new File(fileName));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[is.available()];
        int n = 0;
        while ((n = is.read(buffer)) != -1) {
            bos.write(buffer, 0, n);
        }
        bos.close();
        is.close();
        byte[] bytes = bos.toByteArray();
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (255 - bytes[i]);
        }
        return bytes;
    }
}
