package trolley.tcc.networkconnectivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetworkConnection {

    public static Boolean connectionChecking(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // Get all state of network connection from connection manager
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    return true;
                }
            }
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static Boolean connectionType(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                } else {
                    switch (info.getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                            return false;
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            return false;
                        case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                            return false;
                        default:
                            return true;
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        return false;
    }
}
