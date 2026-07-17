package com.coaching.util;

import jakarta.servlet.http.HttpSession;

/**
 * Small helper around the HttpSession attributes set at login time.
 * Session attributes used: "userId", "username", "role", "linkedId" (student_id/faculty_id), "displayName"
 */
public class SessionUtil {

    public static final String ATTR_USER_ID = "userId";
    public static final String ATTR_USERNAME = "username";
    public static final String ATTR_ROLE = "role";
    public static final String ATTR_LINKED_ID = "linkedId";
    public static final String ATTR_DISPLAY_NAME = "displayName";

    private SessionUtil() {
    }

    public static boolean isLoggedIn(HttpSession session) {
        return session != null && session.getAttribute(ATTR_ROLE) != null;
    }

    public static String getRole(HttpSession session) {
        Object o = session.getAttribute(ATTR_ROLE);
        return o == null ? null : o.toString();
    }

    public static int getLinkedId(HttpSession session) {
        Object o = session.getAttribute(ATTR_LINKED_ID);
        return o == null ? -1 : (Integer) o;
    }

    public static int getUserId(HttpSession session) {
        Object o = session.getAttribute(ATTR_USER_ID);
        return o == null ? -1 : (Integer) o;
    }
}
