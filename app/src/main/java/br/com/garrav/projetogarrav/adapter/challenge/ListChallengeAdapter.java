package br.com.garrav.projetogarrav.adapter.challenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import br.com.garrav.projetogarrav.R;
import br.com.garrav.projetogarrav.model.Challenge;

public class ListChallengeAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private List<Challenge> challengeList;
    private Context context;

    //Constructor
    public ListChallengeAdapter(Context context,
                                List<Challenge> challengeList) {

        this.mLayoutInflater = mLayoutInflater.from(context);
        this.challengeList = challengeList;
        this.context = context;

    }

    @Override
    public int getCount() {
        return this.challengeList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.challengeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.challengeList.get(i).getId();
    }

    @Override
    public View getView(int i,
                        View view,
                        ViewGroup viewGroup) {

        //Elementos a serem implementados no ListView
        final ViewHolder HOLDER;

        //Instância de Challenge que será colocada no ListView
        final Challenge CHALLENGE = (Challenge) getItem(i);

        if(view == null) {

            //Inicialização do XML listview_challenge
            view = this.mLayoutInflater.inflate(R.layout.listview_challenge, null);
            HOLDER = new ViewHolder();

            //Inicialização dos elementos no XML
            HOLDER.cbChallengeConcluded = view.findViewById(R.id.cbChallengeConcluded);
            HOLDER.tvChallenge = view.findViewById(R.id.tvChallenge);

            view.setTag(HOLDER);
        } else {
            HOLDER = (ViewHolder) view.getTag();
        }

        HOLDER.tvChallenge.setText(CHALLENGE.getName());
        HOLDER.cbChallengeConcluded.setChecked(true);

        return view;
    }
}
