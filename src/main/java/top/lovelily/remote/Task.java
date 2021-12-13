package top.lovelily.remote;

/**
 * Desc: Task
 * Author: Xu He
 * created: 2021/12/13 16:25
 */
public interface Task<T> {
    T execute();
}