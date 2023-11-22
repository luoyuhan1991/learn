package org.luoyuhan.learn.java.niorpc;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luoyuhan
 */
@Data
public class RpcResponse implements Serializable {
    private String traceId;
    private Object result;
    private Throwable error;
}
