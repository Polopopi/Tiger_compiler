package assembleur.visual;

import java.util.ArrayList;
import java.util.List;
public class Utils {
    public static String[] bytesToLittleEndianHex(byte[] data) {
        List<String> values = new ArrayList<>();
        for (int i = 0; i < data.length; i += 4) {
            int val = 0;
            for (int j = i; j < Math.min(i + 4, data.length); j++) {
                val += data[j] << 8 * (j - i);
            }
            values.add(String.format("0x%X", val));
        }
        if (values.isEmpty()) {
            values.add("0x0");
        }
        return values.toArray(String[]::new);
    }
}