package org.littlemonkey.connectivity;


/**
 * 
 *  @author nick
 */
public class Connectivity {

	public Connectivity() {
	}

	public static boolean isConnected() {
	}

	public static Connectivity.ConnectionState getConnectionState() {
	}

	public static final class ConnectionState {


		public static final Connectivity.ConnectionState DISCONNECTED;

		public static final Connectivity.ConnectionState WIFI;

		public static final Connectivity.ConnectionState MOBILE;

		public static Connectivity.ConnectionState[] values() {
		}

		public static Connectivity.ConnectionState valueOf(String name) {
		}
	}
}
