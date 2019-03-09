package br.com.garrav.projetogarrav.adapter.supportiveFriend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

import br.com.garrav.projetogarrav.R;
import br.com.garrav.projetogarrav.model.User;

public class ListSupportiveFriendAdapter extends BaseAdapter {

    private Context context;
    private List<User> lstSupportiveFriend;
    private LayoutInflater mLayoutInflater;

    public ListSupportiveFriendAdapter(Context context,
                                       List<User> lstSupportiveFriend) {

        this.mLayoutInflater = mLayoutInflater.from(context);
        this.context = context;
        this.lstSupportiveFriend = lstSupportiveFriend;
    }

    @Override
    public int getCount(){
        return this.lstSupportiveFriend.size();
    }

    @Override
    public Object getItem(int posicao){
        return this.lstSupportiveFriend.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return posicao;
    }

    @Override
    public View getView(int position,
                        View view,
                        ViewGroup parent) {

        final ViewHolder HOLDER;
        String name = (String) getItem(position);

        if (view == null) {

            //Inicialização do XML listview_supportive_friend
            view = this.mLayoutInflater.inflate(R.layout.listview_supportive_friend, null);
            HOLDER = new ViewHolder();

            //Inicialização dos elementos no XML
            HOLDER.ivImageFriend = view.findViewById(R.id.ivImageFriend);
            HOLDER.tvFriendName = view.findViewById(R.id.tvFriendName);

            view.setTag(HOLDER);

        } else {
            HOLDER = (ViewHolder) view.getTag();
        }

        //Set Friend Name
        HOLDER.tvFriendName.setText(name);

        String primeiraLetra = String.valueOf(name.charAt(0));

        ColorGenerator geradorDeCores = ColorGenerator.MATERIAL;
        // geramos uma cor aleatória
        int cor = geradorDeCores.getColor(getItem(position));
        //int cor = geradorDeCores.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRoundRect(primeiraLetra,cor,7);// o último parametro corresponde ao raio das bordas em pixels
        // .buildRound(primeiraLetra, cor);

        HOLDER.ivImageFriend.setImageDrawable(drawable);

        return view;
    }
}
