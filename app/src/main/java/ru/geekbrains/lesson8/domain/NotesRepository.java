package ru.geekbrains.lesson8.domain;

import java.util.List;

/**
 * Интерфес обеспечивающий нас заметками
 */

public interface NotesRepository {

    List<Note> getNotes(); // метод возвращающий список заметок

    Note addNote(); // метод возвращающий заметку

    void removeAtPosition(int longClickedPosition);
}
