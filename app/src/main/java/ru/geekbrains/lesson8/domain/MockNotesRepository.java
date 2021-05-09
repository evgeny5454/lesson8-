package ru.geekbrains.lesson8.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Этот класс должен реализовывать NotesRepository
 * имплементируем метод от туда
 *
 * если у нас в дальнейшем появится репозиторий мы просто реализуем NotesRepository
 */
public class MockNotesRepository implements NotesRepository {
    @Override
    public List<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        notes.add(new Note("1","Заметка 1","Содержание 1 заметки"));
        notes.add(new Note("2","Заметка 2","Содержание 2 заметки"));
        notes.add(new Note("3","Заметка 3","Содержание 3 заметки"));
        notes.add(new Note("4","Заметка 4","Содержание 4 заметки"));
        notes.add(new Note("5","Заметка 5","Содержание 5 заметки"));
        notes.add(new Note("6","Заметка 6","Содержание 6 заметки"));
        notes.add(new Note("7","Заметка 7","Содержание 7 заметки"));
        notes.add(new Note("8","Заметка 8","Содержание 8 заметки"));
        notes.add(new Note("9","Заметка 9","Содержание 9 заметки"));
        notes.add(new Note("10","Заметка 10","Содержание 10 заметки"));

        return notes; //возвращаем массив заметок
    }
}
