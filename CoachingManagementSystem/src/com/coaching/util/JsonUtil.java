package com.coaching.util;

import java.util.List;

/**
 * Minimal JSON-array builder used to feed Chart.js on JSP dashboards.
 * Avoids needing an external JSON library (Gson/Jackson) for simple cases.
 */
public class JsonUtil {

    private JsonUtil() {
    }

    /** Builds a JSON array of strings, e.g. ["Math","Physics"] */
    public static String toJsonArray(List<String> values) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append("\"").append(escape(values.get(i))).append("\"");
        }
        sb.append("]");
        return sb.toString();
    }

    /** Builds a JSON array of numbers, e.g. [10,20,30] */
    public static String toJsonNumberArray(List<? extends Number> values) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(values.get(i));
        }
        sb.append("]");
        return sb.toString();
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
