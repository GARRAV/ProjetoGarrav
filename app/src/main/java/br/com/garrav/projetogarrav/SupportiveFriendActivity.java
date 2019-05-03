package br.com.garrav.projetogarrav;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import br.com.garrav.projetogarrav.adapter.supportiveFriend.ListSupportiveFriendAdapter;
import br.com.garrav.projetogarrav.model.User;

public class SupportiveFriendActivity extends AppCompatActivity {

		private ListView lvSuportiveFriendList;
		private List<User> lstSupportiveFriend;
		private LocationManager locationManager;
		private Location location;
		private double latitude;
		private double longitude;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_supportive_friend);

				getLocation();

				ActionBar ab = getSupportActionBar();
				ab.setTitle("AMISSOL");

				loadSupportiveFriendList();
		}

		/**
		 * Método responsável por carregar o adapter do {@link ListView} e mostra-los
		 * na Activiy
		 *
		 * @author Felipe Savaris
		 * @since 27/02/2019
		 */
		private void loadSupportiveFriendList() {

				// Init ListView
				this.lvSuportiveFriendList = findViewById(R.id.lvSuportiveFriendList);

				// Adapter ListView
				ListSupportiveFriendAdapter adapter = new ListSupportiveFriendAdapter(
								this,
								this.lstSupportiveFriend
				);

				// Set Adapter no ListView
				this.lvSuportiveFriendList.setAdapter(adapter);

				adapter.notifyDataSetChanged();
		}

		private void getLocation() {
				this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
				if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
								!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
								Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
						// TODO: Consider calling
						//    ActivityCompat#requestPermissions
						// here to request the missing permissions, and then overriding
						//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
						//                                          int[] grantResults)
						// to handle the case where the user grants the permission. See the documentation
						// for ActivityCompat#requestPermissions for more details.
						return;
				}
				this.location = this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

    		this.latitude = this.location.getLatitude();
    		this.longitude = this.location.getLongitude();
		}
}
