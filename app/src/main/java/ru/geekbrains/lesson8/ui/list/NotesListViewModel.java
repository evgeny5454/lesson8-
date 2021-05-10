package ru.geekbrains.lesson8.ui.list;

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
 * Что бы получить информацию о том что добавлена заметка и надо отскролится общаемся через
 * лайв даты
 */

public class NotesListViewModel extends ViewModel {

    private final MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>(); // в эту лайв дату будем передавать список заметок <List<Note>>
    //private final MutableLiveData<Note> noteAddedData = new MutableLiveData<>(); // в эту лайв дату
    // будем передавать инфу что заметка добавлена, заводим для неё гетер лайфдатовский (после добавления DiffUtil более не нужно)
    //private final MutableLiveData<Integer> noteDeletedLiveData = new MutableLiveData<>(); // лайф дата
    // для удаления. будет передавать интеджер. создаём геттер. удаляем мутебл (после добавления DiffUtil более не нужно)
    private final NotesRepository repository = new MockNotesRepository(); // экземпляр репозитория заметок

    public LiveData<List<Note>> getNotesLiveData() {
        return notesLiveData;
    }

    public void requestNotes() { // Заводим метод requestNotes. Через него можно получить список заметок
        List<Note> notes = repository.getNotes(); // получаем значение от репозитория


        notesLiveData.setValue(notes);// когда мы его получили выставляем значение в нашу лайф дату
    }

    public void addClicked() {
        /*Note note = */repository.addNote(); //запускаем в репозитории метод добавления заметки
        //noteAddedData.setValue(note);// заметку вернули и в соответсвующую лайф дату ее передали
        // (после добавления DiffUtil более не нужно)
        notesLiveData.setValue(repository.getNotes()); // вызываем изменнённое сосотояние
    }

    /*public LiveData<Note> getNoteAddedData() { (после добавления DiffUtil более не нужно)
        return noteAddedData;
    }*/

    public void deleteClicked(int longClickedPosition) {
        repository.removeAtPosition(longClickedPosition); // мы должны удалить заметку в репозитории,
        // так же передаём longClickedPosition
        //noteDeletedLiveData.setValue(longClickedPosition); // выставляем значение что мы удалили
        // ту по которой нажали (после добавления DiffUtil более не нужно)
        notesLiveData.setValue(repository.getNotes()); // вызываем изменнённое сосотояние
    }

    /*public LiveData<Integer> getNoteDeletedLiveData() { (после добавления DiffUtil более не нужно)
        return noteDeletedLiveData;
    }*/
}
