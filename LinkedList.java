/**
 * 手写 LinkedList 单链表
 * 实现其基本的增删改查的同时
 * 实现了 LRU 算法
 * @param <T> 存入数据的类型
 * @author cofbor
 * @author admin 1808413743@qq.com
 */
public class LinkedList<T> {
    final int DEFAULT_SIZE = 6;
    int maxCacheSize;
    Node list;
    int size;

    public LinkedList() {
        maxCacheSize = DEFAULT_SIZE;
    }

    public LinkedList(int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
    }
    // 在链表头部插入
    public void put(T data) {
        list = new Node(data, list);
        size++;
    }

    // 在链表指定位置插入
    public void put(int index, T data) {
        checkIndexPosition(index);
        Node pre = list;
        Node cur = list;
        for (int i = 0; i < index; i++) {
            pre = cur;
            cur = cur.next;
        }
        pre.next = new Node(data, cur);
        size++;
    }

    // 在链表的尾部插入
    public void putAfterLast(T data) {
        Node cur = list;
        Node node = new Node(data, null);
        while(cur.next != null) {
            cur = cur.next;
        }
        cur.next = node;
        size++;
    }

    public void lruPut(T data) {
        if (size >= maxCacheSize) {
            removeLast();
        }
        put(data);
    }

    // 根据 index 查询
    public T get(int index) {
        checkIndexPosition(index);
        Node cur = list;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.data;
    }

    // LRU 查询
    public T lruGet(int index) {
        checkIndexPosition(index);
        Node pre = list;
        Node cur = list;
        for (int i = 0; i < index; i++) {
            pre = cur;
            cur = cur.next;
        }
        T data = cur.data;
        pre.next = cur.next;
        cur.next = list;
        list = cur;
        return data;
    }

    // 删除头部
    public T remove() {
        if (list != null) {
            Node node = list;
            list = list.next;
            // GC 回收
            node.next = null;
            size--;
            return node.data;
        }
        return null;
    }

    // 删除尾部
    public T removeLast() {
        Node pre = list;
        Node cur = list;
        while(cur.next != null) {
            pre = cur;
            cur = cur.next;
        }
        pre.next = null;
        size--;
        return cur.data;
    }

    // 根据 index 删除
    public T remove(int index) {
        checkIndexPosition(index);
        Node pre = list;
        Node cur = list;
        for (int i = 0; i < index; i++) {
            pre = cur;
            cur = cur.next;
        }
        pre.next = cur.next;
        // GC 回收
        cur.next = null;
        size--;
        return cur.data;
    }

    // LRU 删除
    private T lruRemove() {
        return removeLast();
    }

    // 根据 index 修改
    // 成功返回 true
    // 失败返回 false
    public boolean modify(int index, T data) {
        if (index < 0 || index >= size) {
            return false;
        }
        Node cur = list;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        cur.data = data;
        return true;
    }

    // 检查 index 合法性
    private void checkIndexPosition(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index + "size: " + size);
        }
    }

    // 结点 T -> 值域  next -> 指针域
    class Node {
        T data;
        Node next;
        public Node(T data, Node node) {
            this.data = data;
            this.next = node;
        }
    }
}
