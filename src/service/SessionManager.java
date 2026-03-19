package service;

public class SessionManager {
    private static int currentUserId;
    private static String currentUserName;
    private static String currentUserEmail;
    private static String currentUserRole;
    private static boolean loggedIn = false;

    public static void login(int userId, String userName, String email, String role) {
        currentUserId = userId;
        currentUserName = userName;
        currentUserEmail = email;
        currentUserRole = role;
        loggedIn = true;
    }

    public static void logout() {
        currentUserId = 0;
        currentUserName = null;
        currentUserEmail = null;
        currentUserRole = null;
        loggedIn = false;
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static String getCurrentUserName() {
        return currentUserName;
    }

    public static String getCurrentUserEmail() {
        return currentUserEmail;
    }

    public static String getCurrentUserRole() {
        return currentUserRole;
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static boolean isStoreManager() {
        return "Store Manager".equalsIgnoreCase(currentUserRole);
    }

    public static boolean isAdmin() {
        return "Admin".equalsIgnoreCase(currentUserRole);
    }

    public static boolean isCashier() {
        return "Cashier".equalsIgnoreCase(currentUserRole);
    }

    public static boolean isInventoryClerk() {
        return "Inventory Clerk".equalsIgnoreCase(currentUserRole);
    }
}
