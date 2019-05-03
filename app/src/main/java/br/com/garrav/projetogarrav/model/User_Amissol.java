package br.com.garrav.projetogarrav.model;

import java.util.List;

public class User_Amissol {

		private long id_user;
		private String fic_name;
		private String name;
		private double latitude;
		private double longitude;

		private static List<User_Amissol> uniqueUserAmissolList;

		public long getId_user() {
				return id_user;
		}

		public void setId_user(long id_user) {
				this.id_user = id_user;
		}

		public String getFic_name() {
				return fic_name;
		}

		public void setFic_name(String fic_name) {
				this.fic_name = fic_name;
		}

		public String getName() {
				return name;
		}

		public void setName(String name) {
				this.name = name;
		}

		public double getLatitude() {
				return latitude;
		}

		public void setLatitude(double latitude) {
				this.latitude = latitude;
		}

		public double getLongitude() {
				return longitude;
		}

		public void setLongitude(double longitude) {
				this.longitude = longitude;
		}

		public static List<User_Amissol> getUniqueUserAmissolList() {
			return uniqueUserAmissolList;
		}

		public static void setUniqueUserAmissolList(List<User_Amissol> uniqueUserAmissolList) {
			User_Amissol.uniqueUserAmissolList = uniqueUserAmissolList;
		}
}
