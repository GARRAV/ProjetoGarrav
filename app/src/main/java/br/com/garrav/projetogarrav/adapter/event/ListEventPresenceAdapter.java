package br.com.garrav.projetogarrav.adapter.event;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

import br.com.garrav.projetogarrav.R;
import br.com.garrav.projetogarrav.model.Event;
import br.com.garrav.projetogarrav.model.Event_User;
import br.com.garrav.projetogarrav.model.User;
import br.com.garrav.projetogarrav.api.Event_UserServerService;
import br.com.garrav.projetogarrav.util.LocationUtil;

public class ListEventPresenceAdapter extends BaseAdapter {

    private AlertDialog.Builder builder;
    private LayoutInflater mLayoutInflater;
    private List<Event> eventList;
    private Context context;

    //Constructor
    public ListEventPresenceAdapter(Context context,
                                    List<Event> eventList) {

        this.mLayoutInflater = mLayoutInflater.from(context);
        this.eventList = eventList;
        this.context = context;

    }

    @Override
    public int getCount() {
        return this.eventList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.eventList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.eventList.get(i).getId();
    }

    @Override
    public View getView(int i,
                        View view,
                        ViewGroup viewGroup) {

        //Elementos a serem implementados no ListView
        final ViewHolder HOLDER;

        //Instância de Event que será colocada no ListView
        final Event EVENT = (Event) getItem(i);

        if (view == null) {

            //Inicialização do XML listview_event_presence
            view = this.mLayoutInflater.inflate(R.layout.listview_event_presence, null);
            HOLDER = new ViewHolder();

            //Inicialização dos elementos no XML
            HOLDER.tvEventPresenceName = view.findViewById(R.id.tvEventPresenceName);
            HOLDER.tvEventPresenceAddress = view.findViewById(R.id.tvEventPresenceAddress);
            HOLDER.tvEventPresenceDate = view.findViewById(R.id.tvEventPresenceDate);
            HOLDER.btCancelEventPresence = view.findViewById(R.id.btCancelEventPresence);

        } else {
            HOLDER = (ViewHolder) view.getTag();
        }

        //Get Address
        Address address = LocationUtil.seekAddress(
                context,
                EVENT.getLatitude(),
                EVENT.getLongitude()
        );

        //Set Format Date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm");

        //Set Name Event
        HOLDER.tvEventPresenceName.setText(EVENT.getName());
        //Set Address Event
        HOLDER.tvEventPresenceAddress.setText(address.getAddressLine(0));
        //Set Date Event
        HOLDER.tvEventPresenceDate.setText(sdf.format(EVENT.getDateEvent()));

        //Interação Button
        HOLDER.btCancelEventPresence.setOnClickListener(new View.OnClickListener() {
            /**
             * Método responsável por interagir com o {@link android.widget.Button}
             * da View da ListView para apagar a presença de {@link User} do
             * {@link Event}. Também é acionado um {@link AlertDialog} para ter
             * certeza absoluta da ação do usuário
             *
             * @param view Elementos do XML
             * @author Felipe Savaris
             * @since 25/01/2019
             */
            @Override
            public void onClick(View view) {

                //Init Alert Dialog
                builder = new AlertDialog.Builder(context);

                //Title, Message, Yes, No
                builder.setTitle("Deletar Presença")
                        .setMessage("Você tem certeza que deseja remover a presença do evento?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //Set Button Disabled
                                HOLDER.btCancelEventPresence.setText("Cancelado");
                                HOLDER.btCancelEventPresence.setEnabled(false);

                                //Server Event Presence - DELETE
                                Event_UserServerService.deleteUserEventPresenceFromServer(
                                        context,
                                        User.getUniqueUser().getId(),
                                        EVENT.getId(),
                                        HOLDER.btCancelEventPresence
                                );

                                //Remove Presence List
                                for (int a = 0; i < Event_User.getUniqueListEvent_User().size(); i++) {
                                    Event_User eu = Event_User.getUniqueListEvent_User().get(a);

                                    if (eu.getId_user() == User.getUniqueUser().getId()
                                            &&
                                            eu.getId_event() == EVENT.getId()) {

                                        Event_User.getUniqueListEvent_User().remove(a);

                                        break;
                                    }
                                }

                            }
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                /*
                                Nada Acontece
                                 */
                            }
                        })
                        .show();
            }
        });


        return view;
    }
}
