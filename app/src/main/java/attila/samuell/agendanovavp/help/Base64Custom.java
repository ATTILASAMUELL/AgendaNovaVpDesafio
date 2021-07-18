package attila.samuell.agendanovavp.help;

import android.util.Base64;

public class Base64Custom {

    public static String codificarBase64(String texto){
        return Base64.encodeToString(texto.getBytes(),Base64.DEFAULT).replaceAll("(\\n|\\n)","");
    }
    public static String decodificarBase64(String textoCodificado){
        return new String( Base64.decode(textoCodificado,Base64.DEFAULT));

    }
}
