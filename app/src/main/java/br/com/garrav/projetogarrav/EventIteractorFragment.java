package br.com.garrav.projetogarrav;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import br.com.garrav.projetogarrav.model.Event;
import br.com.garrav.projetogarrav.model.Event_User;
import br.com.garrav.projetogarrav.model.User;
import br.com.garrav.projetogarrav.retrofitServerService.Event_UserServerService;

public class EventIteractorFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private TextView tvEventName;
    private TextView tvDateEvent;
    private Button btConfirmEvent;

    //Id_Event
    private static long id_event;

    public EventIteractorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(
                R.layout.fragment_event_iteractor,
                container,
                false
        );

        //Listener Button Fragment
        this.btConfirmEvent = view.findViewById(R.id.btConfirmEvent);
        this.btConfirmEvent.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view
             * @author Felipe Savaris
             * @since 27/12/2018
             */
            @Override
            public void onClick(View view) {

                //Set Event_User
                Event_User eventUser = new Event_User();
                eventUser.setId_user(User.getUniqueUser().getId());
                eventUser.setId_event(id_event);

                //Requisição Retrofit - POST
                Event_UserServerService.postEventUserPresenceToServer(
                        getContext(),
                        eventUser
                );

                //Reseta a Activity
                getActivity().recreate();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     *
     * @param view
     * @param event
     * @author Felipe Savaris
     * @since 27/12/2018
     */
    public void setEventData(View view,
                             Event event) {

        //Init TextView's and Button Fragment
        this.tvEventName = view.findViewById(R.id.tvEventName);
        this.tvDateEvent = view.findViewById(R.id.tvDateEvent);
        this.btConfirmEvent = view.findViewById(R.id.btConfirmEvent);

        //Set Name Event
        this.tvEventName.setText(event.getName());

        //Set Date Event - Formatada
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - hh:MM");
        this.tvDateEvent.setText(sdf.format(event.getDateEvent()));

        for(int i = 0; i < Event_User.getUniqueListEvent_User().size(); i++) {
            if(Event_User.getUniqueListEvent_User().get(i).getId_event()
                    ==
                    event.getId()
                    &&
                    Event_User.getUniqueListEvent_User().get(i).getId_user()
                    ==
                    User.getUniqueUser().getId()) {

                //
                this.btConfirmEvent.setEnabled(false);
                this.btConfirmEvent.setClickable(false);
                this.btConfirmEvent.setText("Confirmado");
                break;

            } else {
                if(!this.btConfirmEvent.isEnabled()) {
                    this.btConfirmEvent.setEnabled(true);
                    this.btConfirmEvent.setClickable(true);
                    this.btConfirmEvent.setText("Confirmar");
                }
            }
        }

        id_event = event.getId();
    }

    /**
     *
     * @author Felipe Savaris
     * @since 27/12/2018
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction();
    }
}
