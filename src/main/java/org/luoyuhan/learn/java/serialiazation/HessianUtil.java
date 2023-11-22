package org.luoyuhan.learn.java.serialiazation;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author luoyuhan
 */
public class HessianUtil {
    public static <T> byte[] serializer(T obj) {
        byte[] bytes = new byte[0];
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Hessian2Output hessian2Output = new Hessian2Output(out);
            hessian2Output.writeObject(obj);
            hessian2Output.flush();
            bytes = out.toByteArray();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static <T> T deserializer(byte[] data, Class<T> clazz) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            Hessian2Input hessian2Input = new Hessian2Input(in);
            return (T) hessian2Input.readObject(clazz);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("反序列化错误");
        }
    }
}
