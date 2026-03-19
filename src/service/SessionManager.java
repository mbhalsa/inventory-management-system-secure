package service;

public class SessionManager {
    private static String currentUserRole = "Store Manager";
    private static String currentUserName = "Mohamed Abdulla";

    public static String getCurrentUserRole() {
        return currentUserRole;
    }

    public static void setCurrentUserRole(String role) {
        currentUserRole = role;
    }

    public static String getCurrentUserName() {
        return currentUserName;
    }

    public static void setCurrentUserName(String name) {
        currentUserName = name;
    }

    public static boolean isStoreManager() {
        return "Store Manager".equalsIgnoreCase(currentUserRole);
    }
}