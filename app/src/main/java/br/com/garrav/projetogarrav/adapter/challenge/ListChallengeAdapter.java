package br.com.garrav.projetogarrav.adapter.challenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import br.com.garrav.projetogarrav.R;
import br.com.garrav.projetogarrav.model.Challenge;
import br.com.garrav.projetogarrav.model.Challenge_User;
import br.com.garrav.projetogarrav.model.User;
import br.com.garrav.projetogarrav.retrofitServerService.Challenge_UserServerService;

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

        //Set Name Challenge
        HOLDER.tvChallenge.setText(CHALLENGE.getName());

        //Verificação de Checagem
        for(int a = 0; a < Challenge_User.getUniqueListCheckChallenge().size(); a++) {
            if (CHALLENGE.getId() ==
                    Challenge_User.getUniqueListCheckChallenge().get(a).getId_challenge()) {
                //Set CheckBox True
                HOLDER.cbChallengeConcluded.setChecked(true);
                break;
                //Set CheckBox False
            } else HOLDER.cbChallengeConcluded.setChecked(false);
        }
        //Caso Lista Vazia
        if(Challenge_User.getUniqueListCheckChallenge().size() == 0) {
            //Set CheckBox False
            HOLDER.cbChallengeConcluded.setChecked(false);
        }

        /*
        Interações View
         */

        //Interação CheckBox
        HOLDER.cbChallengeConcluded.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view
             * @author Felipe Savaris
             * @since 14/01/2019
             */
            @Override
            public void onClick(View view) {
                /*
                Reversão Status CheckBox -
                Necessária Apenas para a interação com a CHECKBOX.
                Toda vez que o usuário clicar na CheckBox o valor
                dela será invertido, dando erro no processo com o
                Retrofit, sendo necessário uma redundancia de
                checagem
                 */
                if(HOLDER.cbChallengeConcluded.isChecked()){
                   HOLDER.cbChallengeConcluded.setChecked(false);
                } else if(!HOLDER.cbChallengeConcluded.isChecked()) {
                    HOLDER.cbChallengeConcluded.setChecked(true);
                }

                //Re-Checagem CheckBox
                if(HOLDER.cbChallengeConcluded.isChecked()) {
                    HOLDER.cbChallengeConcluded.setChecked(false);
                } else if(!HOLDER.cbChallengeConcluded.isChecked()) {
                    HOLDER.cbChallengeConcluded.setChecked(true);
                }

                //Challenge_User Instance
                Challenge_User cu = new Challenge_User();
                //Challange_User Data
                cu.setId_user(User.getUniqueUser().getId());
                cu.setId_challenge(CHALLENGE.getId());

                //Send Info to Server
                if(HOLDER.cbChallengeConcluded.isChecked()) {

                    //POST
                    Challenge_UserServerService.postCheckedChallengeToServer(
                            context,
                            cu
                    );

                } else {

                    //DELETE
                    Challenge_UserServerService.deleteCheckedChallengeFromServer(
                            context,
                            User.getUniqueUser().getId(),
                            CHALLENGE.getId()
                    );

                }
            }
        });

        //Interação TextView
        HOLDER.tvChallenge.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view
             * @author Felipe Savaris
             * @since 14/01/2019
             */
            @Override
            public void onClick(View view) {
                //Checagem CheckBox
                if(HOLDER.cbChallengeConcluded.isChecked()) {
                    HOLDER.cbChallengeConcluded.setChecked(false);
                } else if(!HOLDER.cbChallengeConcluded.isChecked()) {
                    HOLDER.cbChallengeConcluded.setChecked(true);
                }

                //Challenge_User Instance
                Challenge_User cu = new Challenge_User();
                //Challange_User Data
                cu.setId_user(User.getUniqueUser().getId());
                cu.setId_challenge(CHALLENGE.getId());

                //Send Info to Server
                if(HOLDER.cbChallengeConcluded.isChecked()) {

                    //POST
                    Challenge_UserServerService.postCheckedChallengeToServer(
                            context,
                            cu
                    );

                } else {

                    //DELETE
                    Challenge_UserServerService.deleteCheckedChallengeFromServer(
                            context,
                            User.getUniqueUser().getId(),
                            CHALLENGE.getId()
                    );

                }
            }
        });

        //Interação View Caso fora da CheckBox e do TextView
        view.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view
             * @author Felipe Savaris
             * @since 14/01/2019
             */
            @Override
            public void onClick(View view) {
                //Checagem CheckBox
                if(HOLDER.cbChallengeConcluded.isChecked()) {
                    HOLDER.cbChallengeConcluded.setChecked(false);
                } else if(!HOLDER.cbChallengeConcluded.isChecked()) {
                    HOLDER.cbChallengeConcluded.setChecked(true);
                }

                //Challenge_User Instance
                Challenge_User cu = new Challenge_User();
                //Challange_User Data
                cu.setId_user(User.getUniqueUser().getId());
                cu.setId_challenge(CHALLENGE.getId());

                //Send Info to Server
                if(HOLDER.cbChallengeConcluded.isChecked()) {

                    //POST
                    Challenge_UserServerService.postCheckedChallengeToServer(
                            context,
                            cu
                    );

                } else {

                    //DELETE
                    Challenge_UserServerService.deleteCheckedChallengeFromServer(
                            context,
                            User.getUniqueUser().getId(),
                            CHALLENGE.getId()
                    );

                }
            }
        });

        /*
        Fim das Interações View
         */

        return view;
    }
}
