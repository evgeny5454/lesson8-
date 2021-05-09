package ru.geekbrains.lesson8.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.lesson8.R;
import ru.geekbrains.lesson8.domain.Note;

/**
 * 1.
 * Адаптер который занимается отображением списка
 * наследуется от Адаптера в дженерике хранится тип Вью Холдера
 *
 * нам неодходимо создать свой класс Вью холдера и наследоваться от вью холдера
 * и создать конструктор который матчит родительский конструктор
 *
 * отдаем класс в дженерик и реализуем методы (3 обязательных метода)
 *
 * getItemCount (сколько таких элементов должно быть в списке)
 *
 * onCreateViewHolder - создает вью холдер
 *
 *  onBindViewHolder - проставляем в позиции интересующие нас данные,
 *  создает копию вюшки и отображает данные (заполняет данными вьюшку)
 *
 *  Заводим метод addData что бы закинуть элементы в data
 *
 *  после надо создать экземпляр этого Адаптера, и прицепить его к RecyclerView что бы они вместе научились работать идем в майн
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private ArrayList<Note> data = new ArrayList<>(); // сдесь проинициализированы заметки

    private OnNoteClicked clickListener; // создаём clickListener и заводим геттер и сеттер

    public void addData(List<Note> toAdd) {
        data.clear(); // что бы не дублировалось если там что то есть
        data.addAll(toAdd); // закинем в коллекцию, что бы все кто пользуется адаптером могли добавлять в него данные
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note,
                parent, false); // инфлейтим хмл item_note присваиваем вьюшке
        return new NotesViewHolder(view); // создаём ImportantViewHolder в
        // качестве аргумента отдаем вьюшку вью холдер создали.
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = data.get(position); // создаём экземпляр Note и передаём туда position

        holder.title.setText(note.getTitle()); // из data берем элемент под
        // индексом position из холдера берем title и выставляем текстовое значение,
        // по позиции элемента берем титул
        holder.content.setText(note.getContent());// из data берем элемент под
        // индексом position из холдера берем content и выставляем текстовое значение,
        // по позиции элемента берем content
    }

    @Override
    public int getItemCount() {
        return data.size(); //сколько у нас элементов в data, столько же и в списке
    }

    public OnNoteClicked getClickListener() {
        return clickListener;
    }

    public void setClickListener(OnNoteClicked clickListener) {
        this.clickListener = clickListener;
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView title; // заводим вьюшку
        TextView content; // заводим вью содержания

        public NotesViewHolder(@NonNull View itemView) { // обработка нажатий происходит тут
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getClickListener() != null) { // если есть нажатие, сообщаем о нажатии
                        getClickListener().onNoteClicked(data.get(getAdapterPosition())); // надо получить
                        // заметку, getAdapterPosition() передаёт индекс элемента
                    }
                }
            });
            title = itemView.findViewById(R.id.title); // находим вьюшку, ссылаемся на вьюшку
            content = itemView.findViewById(R.id.content); // находим вью содержания
        }
    }
    interface OnNoteClicked { // интерфейс для обработки нажатий на заметку
        void onNoteClicked(Note note); // передаём заметку
    }
}
