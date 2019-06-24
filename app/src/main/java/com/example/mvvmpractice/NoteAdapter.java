package com.example.mvvmpractice;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {
    private OnItemClickListener listener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Note>() {
            @Override
            public boolean areItemsTheSame(Note oldItem, Note newItem) {
                return oldItem.getId() == newItem.getId();
                //return false;
            }

            @Override
            public boolean areContentsTheSame(Note oldItem, Note newItem) {
                return oldItem.getTitle().equals(newItem.getTitle()) &&
                        oldItem.getTitle().equals(newItem.getDescription()) &&
                        oldItem.getPriority() == newItem.getPriority();
            }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.note_item, viewGroup, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int i) {
        Note currentNote = getItem(i);
        noteHolder.textViewTitle.setText(currentNote.getTitle());
        noteHolder.textViewDescription.setText(currentNote.getDescription());
        noteHolder.textViewPriority.setText(String.valueOf(currentNote.getPriority()));
    }

    public Note getNoteAt(int position) {
        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }

                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
