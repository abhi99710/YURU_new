package com.app.yuru.ui.coupons;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.yuru.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdapterJornals extends RecyclerView.Adapter<AdapterJornals.Myholder> {
    final Context context;
    private final List<String> ids;
    private final List<String> title;
    private final List<String> date_time;
    private final List<String> description;
    final ListInterface listInterface;
    private final List<String> selectedIds;


    public AdapterJornals(Context context, List<String> ids, List<String> user_id, List<String> title, List<String> date_time, List<String> description,
                          List<String> created_at, List<String> updated_at, ListInterface listInterface) {
        this.context = context;
        this.ids = ids;
        this.title = title;
        this.date_time = date_time;
        this.description = description;
        this.listInterface = listInterface;
        selectedIds = new ArrayList<>();
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_journals, parent, false);
        return new AdapterJornals.Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {
        holder.recyContent.setText(description.get(position));
        holder.recyDate.setText(date_time.get(position));
        holder.recyTitle.setText(title.get(position));
        if (selectedIds.contains(ids.get(position))) {
            holder.recyCheck.setImageResource(R.drawable.check_selected);
        } else {
            holder.recyCheck.setImageResource(R.drawable.check_rect);
        }
    }

    @Override
    public int getItemCount() {
        return ids.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        private final ImageView recyCheck;
        private final EditText recyTitle;
        private final EditText recyContent;
        private final TextView recyDate;

        public Myholder(@NonNull View itemView) {
            super(itemView);

            recyCheck = itemView.findViewById(R.id.recyCheck);
            recyTitle = itemView.findViewById(R.id.recyTitle);
            recyContent = itemView.findViewById(R.id.recyContent);
            recyDate = itemView.findViewById(R.id.recyDate);
            ConstraintLayout clayoutRecycler = itemView.findViewById(R.id.clayoutRecycler);
            CardView cardList = itemView.findViewById(R.id.cardList);
            recyCheck.setOnClickListener(v -> {
                String id = ids.get(getBindingAdapterPosition());
                if (selectedIds.contains(id)) {
                    selectedIds.remove(id);
                } else {
                    selectedIds.add(id);
                }
                notifyItemChanged(getBindingAdapterPosition());
               String[] newList = getSelectedIds().split(",",1);
                List<String> li = Arrays.asList(newList);
                listInterface.selected(li);
            });

            recyContent.setOnClickListener(v -> {
                Intent intent = new Intent(context, EditJournal.class);
                intent.putExtra("id", ids.get(getBindingAdapterPosition()));
                context.startActivity(intent);
            });

            clayoutRecycler.setOnClickListener(v -> {
                Intent intent = new Intent(context, EditJournal.class);
                intent.putExtra("id", ids.get(getBindingAdapterPosition()));
                context.startActivity(intent);
            });

            cardList.setOnClickListener(v -> {
                Intent intent = new Intent(context, EditJournal.class);
                intent.putExtra("id", ids.get(getBindingAdapterPosition()));
                context.startActivity(intent);
            });
        }
    }

    public String getSelectedIds() {
        StringBuilder builder = new StringBuilder();
        boolean appendComma = false;
        for (String id : selectedIds) {
            if (appendComma) {
                builder.append(",");
            }
            appendComma = true;
            builder.append(id);
        }
        return builder.toString();
    }
}
