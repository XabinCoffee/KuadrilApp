package com.kapp.rxabin.kuadrilapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kapp.rxabin.kuadrilapp.R;
import com.kapp.rxabin.kuadrilapp.obj.User;

import java.util.ArrayList;

/**
 * Created by xabinrodriguez on 30/11/17.
 */

public class CreditsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> credit_titles;
    private ArrayList<String> names;


    public CreditsAdapter(){
        credit_titles = new ArrayList<>();
        names = new ArrayList<>();

        credit_titles.add("CREATED AND DIRECTED BY");
        credit_titles.add("IDEA BY");
        credit_titles.add("BETA TESTERS");
        credit_titles.add("MOTIVATIONAL INSULTS");
        credit_titles.add("SPECIAL THANKS");

        names.add("Xabin Rodriguez");
        names.add("Pablo Jimenez");
        names.add("Iñaki Landa\nHaritz Mendizabal\nJonjo Irure\nXabi Eceiza\nPablo Jimenez");
        names.add("Kloroxark\nEsther");
        names.add("Beñat Zaldua\nXabier Eceiza\nPablo Jimenez\nXabier Linazasoro\nIñaki Landa\nHaritz Mendizabal\nJonjo Irure\nFamily & Kuadrilly");

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View iv = LayoutInflater.from(parent.getContext()).inflate(R.layout.credit_row, parent, false);
        return new CreditsAdapter.CreditsViewHolder(iv);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof CreditsViewHolder){
            final CreditsViewHolder cvh = (CreditsViewHolder) holder;

            String title = credit_titles.get(position);
            String name = names.get(position);

            cvh.credits_name.setText(title);
            cvh.users.setText(name);
        }
    }


    @Override
    public int getItemCount() {
        if (credit_titles!=null) return credit_titles.size(); else return 0;
    }

    public class CreditsViewHolder extends RecyclerView.ViewHolder{

        private TextView credits_name;
        private TextView users;


        public CreditsViewHolder(View v){
            super(v);
            credits_name = (TextView) v.findViewById(R.id.tvCreditsTitle);
            users = (TextView) v.findViewById(R.id.tvUsers);
        }

    }

}
