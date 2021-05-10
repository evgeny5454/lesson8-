package ru.geekbrains.lesson8.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import ru.geekbrains.lesson8.R;
import ru.geekbrains.lesson8.domain.Note;
import ru.geekbrains.lesson8.router.AppRouter;
import ru.geekbrains.lesson8.router.RouterHolder;
import ru.geekbrains.lesson8.ui.list.NotesAdapter;
import ru.geekbrains.lesson8.ui.list.NotesListViewModel;

/**
 * Активити стала хранителем апрутера и она возвращает соответствующий экземпляр рутера
 */

public class MainActivity extends AppCompatActivity implements RouterHolder {

    private AppRouter router; // храним в нашей активити, связано с фрагмент менеджером

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        router = new AppRouter(getSupportFragmentManager()); //инициализируем и передаём фрагмент менеджер

        if (savedInstanceState == null); { // если первый запуск то мы должны открыть список заметок
            router.showNotesList(); // открываем лист заметок
        }

    }

    @Override
    public AppRouter getRouter() {
        return router; // возвращает экземпляр рутера, что бы обойтись без кликлистенеров
    }
}