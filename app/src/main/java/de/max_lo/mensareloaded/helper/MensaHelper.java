package de.max_lo.mensareloaded.helper;

import android.util.Log;

import de.max_lo.mensareloaded.Mensa;

public class MensaHelper {

    public static String getMensaId(Mensa mensa) {
        switch (mensa) {
            case ALTE_MENSA:
                return "79";
            case ZELTMENSA:
                return "78";
            case SIEDEPUNKT:
                return "82";
            case WU_EINS:
                return "85";
            default:
                // ToDo Error handling
                Log.e("MensaHelper","unknown Mensa");
                return "0";
        }
    }
}
