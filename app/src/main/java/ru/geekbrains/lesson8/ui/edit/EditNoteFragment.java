package ru.geekbrains.lesson8.ui.edit;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import ru.geekbrains.lesson8.R;
import ru.geekbrains.lesson8.domain.Note;

/**
 * Наследуется от фрагмента
 * onCreateView переопределяем
 *
 * EditNoteFragment newInstance создаем этот класс и передаём заметку
 * это базовые вещи
 * после этого надо его научиться открывать
 *
 * идем в майн активити ихмл и меняем фрагмент на фрейм лейаут
 */
public class EditNoteFragment extends Fragment {

    private static String ARG_NOTE = "ARG_NOTE";

    public static EditNoteFragment newInstance(Note note) {
        EditNoteFragment fragment = new EditNoteFragment(); // создаём экземпряр данного фрагмента
        Bundle bundle = new Bundle(); // выставляем аргументы в виде бандла
        bundle.putParcelable(ARG_NOTE, note); // укладываем парселабл который сейчас прикрутим,
        // note у нас не реализует парселбл. поэтому идем туда
        fragment.setArguments(bundle); // передаём аргуметы фрагменту
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit, container, false); // инфлейтим лайоут
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText editText = view.findViewById(R.id.edit_name); // находим вьюшки по айди
        DatePicker picker = view.findViewById(R.id.date_picker);

        editText.addTextChangedListener(new TextWatcher() {// чтобы следить за изменениями текста
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) { // отлеживает изменения сразу после ввода символа
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();

            }
        });

        picker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() { // чтобы отслеживать изменеия пикера работает с 8 андройда
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Toast.makeText(requireContext(), dayOfMonth + "/" + (monthOfYear + 1) + "/" + year , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
