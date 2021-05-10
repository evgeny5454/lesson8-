package ru.geekbrains.lesson8.ui.list;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.geekbrains.lesson8.R;
import ru.geekbrains.lesson8.domain.Note;
import ru.geekbrains.lesson8.router.AppRouter;
import ru.geekbrains.lesson8.router.RouterHolder;

/**
 * 1. Заводим метод onCreateView()
 * 2. Заводим метод onViewCreated()
 * 3. Заводим метод onCreate(), и переносим туда экземпляр viewModel
 *
 * что бы показать контекстное меню надо во фрагменте зарегестрировать это меню
 * registerForContextMenu()
 * Надо реализовать методы которые это контекстное меню будет создавать и обрабатывать
 * Для создания контекстного меню оверайдим onCreateContextMenu()
 *
 * onContextItemSelected() реагирует на события
 *
 */

public class NotesFragment extends Fragment {

    private NotesListViewModel viewModel;// вытащил в отдельное поле, что бы она была у всего класса

    private NotesAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this)// берем инстанс вью модели
                // с помощью такой конструкции. Подругому нельзя,
                // потому что она переживает жизнненый цикл активити и может использоваться
                // для общения между фрагментами
                .get(NotesListViewModel.class); // ViewModelProvider
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false); // инфлейтим fragment_notes
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.toolbar); // убеждаемся что виджет appcompat иначе будет краш
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) { // надо слушать что нажимает пользователь
                if (item.getItemId() == R.id.action_add) { // определяем нажатие по id экшена
                    Toast.makeText(requireContext(), R.string.action_add, Toast.LENGTH_SHORT).show();
                    viewModel.addClicked(); // идем во вью модель и запускаем соответствующий метод (реагируем со стороны вьюшки)
                    return true; // говорим что с поставленной задачей спарвились
                }
                return false;
            }
        });
        adapter = new NotesAdapter(this); // создали экземпляр адаптера,
        // передаём себя в адаптер (нужно для работы с фрагментом. Который отвечает за контекстное меню)
        // Теперь фрагмент знает какая вьюшка используется для контекстного меню

        adapter.setClickListener(new NotesAdapter.OnNoteClicked() { // создаём слушателя,
            // вставляем new NotesAdapter.OnNoteClicked()
            @Override
            public void onNoteClicked(Note note) {


                Toast.makeText(requireContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });


        // принимает в себя оунера активити аппкомпат и
        // прочие они его реализуют, и дот класс интересующей нас вью модели

        if (savedInstanceState == null) { // если первое создание активити надо запросить данные
            viewModel.requestNotes(); // если Bundle = null мы вызовем requestNotes()
        }

        //registerForContextMenu(); //регестрируем контекстное меню
        RecyclerView notesList = view.findViewById(R.id.notes_list); // находим по id где хотим отображать список
        viewModel.getNotesLiveData().observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
            // передаём данные для отображения нашему адаптеру,
            // для фрагмента лучше использовать getViewLifecycleOwner()
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setData(notes); // передаём адаптеру данные (notes). после добавления DiffUtil addData поменяли на setData
                //adapter.notifyDataSetChanged();// уведомляем об их изменении он примитивный, перенесли в addData()

            }
        }); // получаем лайф дату с нашими заметками, вызываем метом обсёрв,
        // отдаем ему лайв сайкл оунера, в активити просто this,
        // далее нужно реализовать ананимнй класс от обсервера,
        // который в onChanged будет перекидывать список заметок (notes)



        /*viewModel.getNoteAddedData().observe(getViewLifecycleOwner(), new Observer<Note>() {
            // подписываемся на соответствующие изменения, вместо списка заметок у нас одна заметка
            @Override
            public void onChanged(Note note) {
                int position = adapter.addData(note);// реагируем, и получаем переменную position
                //notesList.smoothScrollToPosition(position); // говорим, что надо скролить на позицию,
                // после добавления DiffUtil убираем
            }
        });*/
        // после добавления DiffUtil
        /*viewModel.getNoteDeletedLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            // подписываемся на соответствующие изменения, мы удалили заметку
            @Override
            public void onChanged(Integer position) {
                adapter.delete(position);// командуем адаптеру. случилось удаление по такой то позиции
            }
        });*/


        RecyclerView.LayoutManager lm = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL,false); // создаём лайаут манаджер

        notesList.setLayoutManager(lm);// у него есть метод setLayoutManager, он принимает экземпляры layoutManager

        notesList.setAdapter(adapter); // вызываем setAdapter и скармливаем адаптер

        //adapter.addData(items); // добавили данные в наш самописный метод

        adapter.notifyDataSetChanged();// метод который заставляет перерисовать список, необходимо вызвать иначе ничего не сработает
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        requireActivity().getMenuInflater().inflate(R.menu.menu_list_context, menu);// поросим активити
        // у фрагмента. инфлейтим 1 ссылка на меню 2 экземпляр меню
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) { // реагируем на события
        if(item.getItemId() == R.id.action_open) {
            Toast.makeText(requireContext(), R.string.action_open, Toast.LENGTH_SHORT).show();
            return true;
        }
        if(item.getItemId() == R.id.action_delete) {
            viewModel.deleteClicked(adapter.getLongClickedPosition()); // говорим вью модели что надо
            // удалить элемент, пердаем адаптеру где было длительное нажатие
            // так же надо передать позицию. где было нажатие
            Toast.makeText(requireContext(), R.string.action_delete, Toast.LENGTH_SHORT).show();
            return true;
        }
        if(item.getItemId() == R.id.action_update) {
            if (getActivity() instanceof RouterHolder )  {// мы можем сросить если у нас
                // активити getActivity() instanceof RouterHolder, тогда мы получем наш рутер у
                // этой активити и переходим в редактирование
                AppRouter router = ((RouterHolder)getActivity()).getRouter();
                router.editNote(adapter.getItemAt(adapter.getLongClickedPosition()));// что бы это сделать нам так же нужно получить элемент
            }
            Toast.makeText(requireContext(), R.string.action_update, Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onContextItemSelected(item);
    }
}
