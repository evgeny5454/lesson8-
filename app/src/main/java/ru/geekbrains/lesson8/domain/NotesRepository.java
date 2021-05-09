package ru.geekbrains.lesson8.domain;

import java.util.List;

/**
 * Интерфес обеспецивающий нас заметками
 */

public interface NotesRepository {

    List<Note> getNotes(); // метод возвращающий список заметок
}
