package ru.geekbrains.lesson8.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Этот класс должен реализовывать NotesRepository
 * имплементируем метод от туда
 *
 * если у нас в дальнейшем появится репозиторий мы просто реализуем NotesRepository
 */
public class MockNotesRepository implements NotesRepository {

    private ArrayList<Note> data = new ArrayList<>(); // храним заметки тут

    @Override
    public List<Note> getNotes() {
        return new ArrayList<>(data); // возвращаем список заметок, возвращаем эрей лист что бы была копия


    }

    @Override
    public Note addNote() {
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

        Note added = notes.get(new Random().nextInt(notes.size() -1)); //возвращаем рандомную заметку
        data.add(added);// добавляем рандомную заметку
        return added;//выкидываем наружу

    }

    @Override
    public void removeAtPosition(int longClickedPosition) {
        data.remove(longClickedPosition);
    }
}
