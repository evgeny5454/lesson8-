package ru.geekbrains.lesson8.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.geekbrains.lesson8.domain.MockNotesRepository;
import ru.geekbrains.lesson8.domain.Note;
import ru.geekbrains.lesson8.domain.NotesRepository;

/**
 * Наследуемся от ViewModel
 * Необходимо создать экземпляр лайв даты и создаём гетер
 * в возвращаемом методе убираем мютебл
 * (В мютебл лайф дату мы можем записывать данные в лайф дату не можем записывать
 * данные можем только надлюбать(подписываться на ёё изменение)
 * надо для того что бы ни кто со стороны вью не могли менять в ней данные
 *
 * Создаем метод requestNotes
 *
 */

public class NotesListViewModel extends ViewModel {

    private final MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>(); // в эту лайв дату будем передавать список заметок <List<Note>>

    private final NotesRepository repository = new MockNotesRepository(); // экземпляр репозитория заметок

    public LiveData<List<Note>> getNotesLiveData() {
        return notesLiveData;
    }

    public void requestNotes() { // Заводим метод requestNotes. Через него можно получить список заметок
        List<Note> notes = repository.getNotes(); // получаем значение от репозитория


        notesLiveData.setValue(notes);// когда мы его получили выставляем значение в нашу лайф дату
    }
}
