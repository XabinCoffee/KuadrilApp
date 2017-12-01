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
    private ArrayList<String> meme_titles;
    private ArrayList<String> meme_names;


    public CreditsAdapter(){

        credit_titles = new ArrayList<>();
        names = new ArrayList<>();
        meme_titles = new ArrayList<>();
        meme_names = new ArrayList<>();


        credit_titles.add("APPLICATION ORIGIN");
        credit_titles.add("CREATED, DIRECTED AND DESIGNED BY");
        credit_titles.add("IDEA BY");
        credit_titles.add("PROGRAMMED BY");
        credit_titles.add("BETA TESTING");
        credit_titles.add("SPECIAL THANKS TO");
        credit_titles.add("EVENT ICONS FROM");

        names.add("Made in Zestoa");
        names.add("Xabin Rodriguez");
        names.add("Pablo Jimenez");
        names.add("Xabin Rodriguez");
        names.add("Iñaki Landa\nHaritz Mendizabal\nJonjo Irure\nXabi Eceiza\nPablo Jimenez");
        names.add("Beñat Zaldua\nXabier Eceiza\nPablo Jimenez\nXabier Linazasoro\nIñaki Landa\nHaritz Mendizabal\nJonjo Irure\nFamilia ta kuadrilla");
        names.add("icons8.com");


        meme_titles.add("APPLICATION ORIGIN");
        meme_titles.add("CREATED, DIRECTED AND DESIGNED BY");
        meme_titles.add("IDEA BY");
        meme_titles.add("BETA TESTING");
        meme_titles.add("SPECIAL THANKS TO");
        meme_titles.add("ORIGINAL SOUNDTRACK COMPOSED BY");
        meme_titles.add("MOTIVATIONAL INSULTS BY");
        meme_titles.add("PLEASE GET A NEW PHONE");
        meme_titles.add("GOIKO ESKUTERRIAN");

        meme_names.add("Made in Zestoa\nNot Azpeiti\nNot Azkoiti\nNOT Zumaia\nMade in motherfucking Zestoa, bitches");
        meme_names.add("Xabin \"Raba\" Rodriguez");
        meme_names.add("Pablo \"Fic\" Jimenez");
        meme_names.add("Iñaki \"Polter\" Landa\nHaritz \"Akalatxktxk\" Mendizabal\nJonjo \"Chillnation\" Irure\nXabi \"Xixa\" Eceiza\nPablo \"Pero Rody\" Jimenez");
        meme_names.add("Beñat \"Big \uD83C\uDD71\" Zaldua\nXabier \"Jim Xixare\" Eceiza\nPablo \"Mimosín\" Jimenez\nXabier \"Tot\" Linazasoro\nIñaki \"Tornadus\" Landa\nHaritz \"Creeperator\" Mendizabal\nJonjo \"Remoto\" Irure\nFamilia ta kuadrilla");
        meme_names.add("Iñaki \"Polter\" Landa");
        meme_names.add("Esther \"The Ripper\" López");
        meme_names.add("Beñat \"Large \uD83C\uDD71\" Zaldua");
        meme_names.add("Katua harrapau zian");


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

            if (position==3){
                cvh.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toggleMemeMode();
                    }
                });
            }
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


    public void toggleMemeMode(){

        credit_titles = meme_titles;
        names = meme_names;
        notifyDataSetChanged();

    }



}
