package ru.geekbrains.lesson8.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
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
 *
 *  заводим ссылку на Fragment, что бы использовать контекстное меню
 *  далее работа с фрагментом в бехолдере
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private ArrayList<Note> data = new ArrayList<>(); // сдесь проинициализированы заметки

    private OnNoteClicked clickListener; // создаём clickListener и заводим геттер и сеттер

    private final Fragment fragment; // добовляем конструктор. он очень сильно нужен

    private int longClickedPosition = -1;

    public NotesAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    /*public void addData(List<Note> toAdd) { после генерации DiffUtil этот метод не нужен
        data.clear(); // что бы не дублировалось если там что то есть
        data.addAll(toAdd);// закинем в коллекцию, что бы все кто пользуется адаптером могли добавлять в него данные
        notifyDataSetChanged();// уведомляем об их изменении он примитивный
    }*/

    public void setData(List<Note> toAdd) {

        NotesDiffUtilCallback callback = new NotesDiffUtilCallback(data,toAdd); // создаём калбек что бы посчитать разницу между списками.
        // отдаем текущее состояние(data) списка и новое(toAdd)
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback); // статический метод посчитать разницу,
        // который принимает callback, считаем разницу и получаем результат (result)

        data.clear(); // что бы не дублировалось если там что то есть
        data.addAll(toAdd);// закинем в коллекцию, что бы все кто пользуется адаптером могли добавлять в него данные
        //notifyDataSetChanged();// уведомляем об их изменении он примитивный
        result.dispatchUpdatesTo(this); // все изменеия надо отправить в адаптер. в данном случае самому себе

    }
    /*public int addData(Note note) { // сделали так что метод возвращает позицию, куда скролить
        data.add(note);// просто добовляем последний элемент
        int position = data.size() - 1; // переменная для хранения позиции
        notifyItemInserted(position); //мы ставили элемент на последнее место,
        // последнее место определяется data.size() - 1 плюс это анимируется
        return position; // возвращаем переменую position
    }
          после генерации DiffUtil этоти методы не нужены
    public void delete(int index) { // метод удаления по индексу
        data.remove(index); // удаляем из коллекции
        notifyItemRemoved(index); // анимируем. так же по индексу
    }*/

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

    public int getLongClickedPosition() {
        return longClickedPosition;
    }

    public Note getItemAt(int longClickedPosition) {
        return data.get(longClickedPosition);
    }


    class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView title; // заводим вьюшку
        TextView content; // заводим вью содержания

        public NotesViewHolder(@NonNull View itemView) { // обработка нажатий происходит тут
            super(itemView);

            fragment.registerForContextMenu(itemView); // мы хотим itemView
            // зарегестрировать для контекстного меню. Теперь фрагмент будет в курсе
            // какой вьюхе его показывать

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getClickListener() != null) { // если есть нажатие, сообщаем о нажатии
                        getClickListener().onNoteClicked(data.get(getAdapterPosition())); // надо получить
                        // заметку, getAdapterPosition() передаёт индекс элемента
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemView.showContextMenu(); // при длительном нажатии покажи контекстное меню,
                    // без этой штуки на мой взгляд лучше и красивее
                    longClickedPosition = getAdapterPosition();// присваиваем getAdapterPosition() для удаления
                    return true;
                }
            });
            title = itemView.findViewById(R.id.title); // находим вьюшку, ссылаемся на вьюшку
            content = itemView.findViewById(R.id.content); // находим вью содержания
        }
    }
    interface OnNoteClicked { // интерфейс для обработки нажатий на заметку
        void onNoteClicked(Note note); // передаём заметку
    }

    public class NotesDiffUtilCallback extends DiffUtil.Callback { // нужно для синхронизации списков
        // и анимаций и нам необходимо реализовать 4 метода

        private final List<Note> oldList; // заводим списки для сравнения и добавляем в конструктор
        private final List<Note> newList;

        public NotesDiffUtilCallback(List<Note> oldList, List<Note> newList) { // конструктор
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size(); // возвращаем размер старого списка
        }

        @Override
        public int getNewListSize() {
            return newList.size(); // возвращаем размер нового списка
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getId().equals(newList.get(newItemPosition).getId());
            // cверяем списки по идентификатору
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
            // условие если поменялся весь обьект. при этом к заметке надо прикрутить методы equals и hashcode
        }

    }
}
