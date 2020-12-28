package com.zjy.marqueelib;

/**
 * Date: 2020/12/25
 * Author: Yang
 * Describe: The observer of marquee adapter
 */
public interface DataObserver<T> {
    /**
     * 数据源发生变更
     */
    void onDataChange();

    /**
     * 更新某一行数据
     *
     * @param position 行下标
     * @param data     覆盖的数据
     */
    void onItemChange(int position, T data);

    /**
     * 插入某一行数据
     *
     * @param position 行下标
     * @param data     插入的数据
     */
    void onItemInsert(int position, T data);
}
