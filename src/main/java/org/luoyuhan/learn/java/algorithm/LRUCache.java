package org.luoyuhan.learn.java.algorithm;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

// lru 核心思想是用一个队列保存数据，将最近常使用的放在队头，超过长度就删除队尾元素
// 使用双向队列存取方便，为了解决队列查找数据需要遍历O(n)，引入一个map辅助，O(1)查找
public class LRUCache<K, V> {
    // java 中linked hash map 就是map + 队列，而且实现了LRU
    private final LinkedHashMap<K, V> javaLRUCache = new LinkedHashMap<>(10, 0.75f, true);

    // 以下是手动实现
    Map<K, Node> cacheMap;
    Node head, tail;
    int capacity;
    int size;

    LRUCache(int capacity) {
        this.capacity = capacity;
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.pre = head;
        cacheMap = new HashMap<>(capacity + 2);
        size = 0;
    }

    public V get(K key) {
        Node n = cacheMap.get(key);
        if (n == null) {
            return null;
        }
        moveToHead(n);
        return n.value;
    }

    public void put(K key, V value) {
        Node n = cacheMap.get(key);
        // 已存在
        if (n != null) {
            // 节点赋值
            n.value = value;
            // 移到头结点
            moveToHead(n);
            return;
        }
        // 不存在则插入头结点
        // 先看是否已满，满的话要先删除尾节点
        if (capacity == size) {
            Node lastNode = tail.pre;
            deleteNode(lastNode);
            cacheMap.remove(lastNode.key);
            size--;
        }
        n = new Node(key, value);
        addNode(n);
        cacheMap.put(key, n);
        size++;
    }

    public void deleteNode(Node node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }

    public void addNode(Node node) {
        node.next = head.next;
        head.next.pre = node;

        head.next = node;
        node.pre = head;
    }

    public void moveToHead(Node node) {
        deleteNode(node);
        addNode(node);
    }

    class Node {
        K key;
        V value;

        Node pre;
        Node next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Node() {}
    }

    public static void main(String[] args) {
        LRUCache<String, String> lruCache = new LRUCache<>(3);
        System.out.println(lruCache.get("test"));
        lruCache.put("key1", "value1");
        System.out.println(lruCache.get("key1"));
        lruCache.put("key1", "value1");
        lruCache.put("key2", "value2");
        lruCache.put("key3", "value3");
//        System.out.println(lruCache.get("key1"));
        lruCache.put("key4", "value4");
//        System.out.println(lruCache.get("key1"));
    }
}
