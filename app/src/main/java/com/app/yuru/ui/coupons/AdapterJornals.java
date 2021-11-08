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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdapterJornals extends RecyclerView.Adapter<AdapterJornals.Myholder> {
    Context context;
    private List<String> ids ;
    private List<String> user_id ;
    private List<String> title ;
    private List<String> date_time ;
    private List<String> description ;
    private List<String> created_at ;
    private List<String> updated_at ;
    boolean[] checkSelected ;
    ListInterface listInterface;


    public AdapterJornals(Context context, List<String> ids, List<String> user_id, List<String> title, List<String> date_time, List<String> description,
                          List<String> created_at, List<String> updated_at, ListInterface listInterface) {
        this.context = context;
        this.ids = ids;
        this.user_id = user_id;
        this.title = title;
        this.date_time = date_time;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
        checkSelected = new boolean[ids.size()];
        for (int i = 0; i <ids.size() ; i++) {
            checkSelected[i] = false;
        }
        this.listInterface = listInterface;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_journals,parent,false);
        return new AdapterJornals.Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {

        JSONObject jsonObject = new JSONObject();
        List<Integer> selectedIds = new ArrayList<>();

        holder.recyContent.setText(description.get(position));
        holder.recyDate.setText(date_time.get(position));
        holder.recyTitle.setText(title.get(position));
        holder.recyCheck.setOnClickListener(v->{
            if(checkSelected[position]) {
                holder.recyCheck.setImageResource(R.drawable.check_selected);
                checkSelected[position] = false;
                try {
                    selectedIds.remove(position);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }else {
                holder.recyCheck.setImageResource(R.drawable.check_rect);
                checkSelected[position] = true;
                selectedIds.add(position);

            }
        });

        try {
            jsonObject.putOpt("selected", checkSelected);
            listInterface.selected(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.clayoutRecycler.setOnClickListener(v->{
            Intent intent = new Intent(context, EditJournal.class);
            intent.putExtra("id", ids.get(position));
            context.startActivity(intent);
        });

        holder.cardList.setOnClickListener(v->{
            Intent intent = new Intent(context, EditJournal.class);
            intent.putExtra("id", ids.get(position));
            context.startActivity(intent);
        });

        holder.recyContent.setOnClickListener(v->{
            Intent intent = new Intent(context, EditJournal.class);
            intent.putExtra("id", ids.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return ids.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        private ImageView recyCheck;
        private EditText recyTitle, recyContent;
        private TextView recyDate;
        private ConstraintLayout clayoutRecycler;
        private CardView cardList;
        public Myholder(@NonNull View itemView) {
            super(itemView);

            recyCheck = itemView.findViewById(R.id.recyCheck);
            recyTitle = itemView.findViewById(R.id.recyTitle);
            recyContent = itemView.findViewById(R.id.recyContent);
            recyDate = itemView.findViewById(R.id.recyDate);
            clayoutRecycler = itemView.findViewById(R.id.clayoutRecycler);
            cardList = itemView.findViewById(R.id.cardList);
        }
    }
}
