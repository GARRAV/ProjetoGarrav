package br.com.garrav.projetogarrav;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.garrav.projetogarrav.model.Event;

public class EventIteractorFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private TextView tvEventName;

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
     */
    public void setTvEventName(View view) {

        this.tvEventName = view.findViewById(R.id.tvEventName);
        this.tvEventName.setText("Nome do Evento");

    }

    /**
     *
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Event event);
    }
}
