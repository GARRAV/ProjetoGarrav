package br.com.garrav.projetogarrav;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import br.com.garrav.projetogarrav.model.User;

public class AfterLoginActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(User.getUniqueUser() != null) User.setUniqueUser(null);
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
