package ru.geekbrains.lesson8.router;


import androidx.fragment.app.FragmentManager;

import ru.geekbrains.lesson8.R;
import ru.geekbrains.lesson8.domain.Note;
import ru.geekbrains.lesson8.ui.edit.EditNoteFragment;
import ru.geekbrains.lesson8.ui.list.NotesFragment;

/**
 * Класс для перемещения между фрагментами
 *
 * FragmentManager сначала создаём этот класс, потом добавляем его в конструктор
 *
 * можем сделать любую сущьность хранителем апрутера и в нем сделаем методы
 */
public class AppRouter {

    private FragmentManager fragmentManager; // нам нужен фрагмент менеджер, добавляем его в конструктор

    public AppRouter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void showNotesList() { // метод для того чтобы показать фрагмент со списком заметок
        fragmentManager.beginTransaction().replace(R.id.container, new NotesFragment()).commit();

    }
    public void editNote(Note note) { // метод для того чтобы показать фрагмент редактирования заметок, в котором мы передаём заметку
        fragmentManager.beginTransaction()
                .replace(R.id.container, EditNoteFragment.newInstance(note))
                .addToBackStack(null) // чтобы была возможность уйти назад
                .commit();

    }

}
