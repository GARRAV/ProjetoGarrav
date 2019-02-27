package br.com.garrav.projetogarrav;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.garrav.projetogarrav.adapter.supportiveFriend.ListSupportiveFriendAdapter;

public class SupportiveFriendActivity extends AppCompatActivity {

    private ListView lvSuportiveFriendList;
    private ArrayList<String> nossoArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supportive_friend);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Amigo Solidário");

        setData();

        loadSupportiveFriendList();
    }

    private void loadSupportiveFriendList() {

        this.lvSuportiveFriendList = findViewById(R.id.lvSuportiveFriendList);

        ListSupportiveFriendAdapter adapter = new ListSupportiveFriendAdapter(
                this,
                this.nossoArray
        );

        this.lvSuportiveFriendList.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    //Inicialização da nossa lista de amigos
    private void setData() {
        nossoArray = new ArrayList<>();
        nossoArray.add("Ilidio Zandamela");
        nossoArray.add("Florêncio Lipoche");
        nossoArray.add("Helena Sitoe");
        nossoArray.add("Isack Mabunda");
        nossoArray.add("Vânia Khumalo");
        nossoArray.add("Genny Macaringue ");
        nossoArray.add("Edson Mazuze");
        nossoArray.add("Jaime Mulungo");
        nossoArray.add("Crimilde Nguila");
        nossoArray.add("Thobile Coutinho");
        nossoArray.add("Alison Conceição");
        nossoArray.add("Julia Maposse");
    }
}
