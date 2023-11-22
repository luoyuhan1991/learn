package org.luoyuhan.learn.java.niorpc;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luoyuhan
 */
@Data
public class RpcRequest implements Serializable {
    private String traceId;
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;
}
