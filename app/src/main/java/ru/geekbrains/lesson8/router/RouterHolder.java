package ru.geekbrains.lesson8.router;


/**
 * интерфейсс чтобы не было жествой связанности
 * возвращает AppRouter
 */

public interface RouterHolder {
    AppRouter getRouter();
}
