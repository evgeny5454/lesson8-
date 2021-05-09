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

public class NotesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotesAdapter adapter = new NotesAdapter(); // создали экземпляр адаптера

        adapter.setClickListener(new NotesAdapter.OnNoteClicked() { // создаём слушателя,
            // вставляем new NotesAdapter.OnNoteClicked()
            @Override
            public void onNoteClicked(Note note) {
                Toast.makeText(NotesListActivity.this, note.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        NotesListViewModel viewModel = new ViewModelProvider(this)// берем инстанс вью модели
                // с помощью такой конструкции. Подругому нельзя,
                // потому что она переживает жизнненый цикл активити и может использоваться
                // для общения между фрагментами
                .get(NotesListViewModel.class); // ViewModelProvider
        // принимает в себя оунера активити аппкомпат и
        // прочие они его реализуют, и дот класс интересующей нас вью модели

        if (savedInstanceState == null) { // если первое создание активити надо запросить данные
            viewModel.requestNotes(); // если Bundle = null мы вызовем requestNotes()
        }

        viewModel.getNotesLiveData().observe(this, new Observer<List<Note>>() {
            // передаём данные для отображения нашему адаптеру
            @Override
            public void onChanged(List<Note> notes) {
                adapter.addData(notes); // передаём адаптеру данные (notes)
                adapter.notifyDataSetChanged();// уведомляем об их изменении он примитивный

            }
        }); // получаем лайф дату с нашими заметками, вызываем метом обсёрв,
        // отдаем ему лайв сайкл оунера, в активити просто this,
        // далее нужно реализовать ананимнй класс от обсервера,
        // который в onChanged будет перекидывать список заметок (notes)

        RecyclerView notesList = findViewById(R.id.notes_list); // находим по id где хотим отображать список
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false); // создаём лайаут манаджер

        notesList.setLayoutManager(lm);// у него есть метод setLayoutManager, он принимает экземпляры layoutManager

        notesList.setAdapter(adapter); // вызываем setAdapter и скармливаем адаптер

        //adapter.addData(items); // добавили данные в наш самописный метод

        adapter.notifyDataSetChanged();// метод который заставляет перерисовать список, необходимо вызвать иначе ничего не сработает
    }
}