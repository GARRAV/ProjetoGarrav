package br.com.garrav.projetogarrav;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import br.com.garrav.projetogarrav.model.Event;

public class EventIteractorFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private TextView tvEventName;
    private TextView tvDateEvent;
    private Button btConfirmEvent;

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
            @Override
            public void onClick(View view) {
                //Temporário
                Intent it = new Intent(
                        getContext(),
                        SupportiveFriendActivity.class
                );
                startActivity(it);
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

        //Init TextView's Fragment
        this.tvEventName = view.findViewById(R.id.tvEventName);
        this.tvDateEvent = view.findViewById(R.id.tvDateEvent);

        //Set Name Event
        this.tvEventName.setText(event.getName());

        //Set Date Event - Formatada
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - hh:MM");
        this.tvDateEvent.setText(sdf.format(event.getDateEvent()));

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
