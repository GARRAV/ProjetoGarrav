package br.com.garrav.projetogarrav;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.com.garrav.projetogarrav.model.User;

public class AfterLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
    }

    @Override
    public void onBackPressed() {

        if(User.getUniqueUser() != null) {
            User.setUniqueUser(null);
        }

        super.onBackPressed();
    }

    /**
     *
     * @param view Elemento utilizado para inicializar a ação
     * @author Felipe Savaris
     * @since 29/11/2018
     */
    public void btChallenge(View view) {

        //Mudança de Activity -> ChallengeActivity
        Intent it = new Intent(
                this,
                ChallengeActivity.class
        );
        startActivity(it);

    }

    /**
     *
     * @param view Elemento utilizado para inicializar a ação
     * @author Felipe Savaris
     * @since 01/12/2018
     */
    public void btSupportiveFriend(View view) {

        //Mudança de Activity -> SupportiveFriendActivity
        Intent it = new Intent(
                this,
                SupportiveFriendActivity.class
        );
        startActivity(it);
    }

    /**
     *
     * @param view Elemento utilizado para inicializar a ação
     * @author Felipe Savaris
     * @since 05/12/2018
     */
    public void btMapsEvents(View view) {

        //Mudança de Activity -> MapsEventsActivity
        Intent it = new Intent(
                this,
                MapsEventsActivity.class
        );
        startActivity(it);
    }
}
