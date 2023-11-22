package org.luoyuhan.learn.java.niorpc;

import lombok.Data;

/**
 * @author luoyuhan
 */
@Data
public class RpcException extends Throwable {
    private String traceId;

}
