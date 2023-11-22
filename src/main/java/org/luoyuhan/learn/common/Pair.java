package org.luoyuhan.learn.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author luoyuhan
 * @since 2023/11/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pair<K, V> implements Serializable {
    private static final long serialVersionUID = 1L;

    private K key;
    private V value;

}
