package ui;

import service.AuthService;
import service.SessionManager;

public class TestAuthStep1 {
    public static void main(String[] args) {
        AuthService authService = new AuthService();

        System.out.println("---- Register test ----");
        boolean registered = authService.register(
                "Abdelrahmen Test",
                "abdelrahmen@test.com",
                "Password123!",
                2
        );
        System.out.println("Registered: " + registered);

        System.out.println("---- Verify email test ----");
        boolean verified = authService.verifyEmail("abdelrahmen@test.com");
        System.out.println("Verified: " + verified);

        System.out.println("---- Login test ----");
        boolean loggedIn = authService.login("abdelrahmen@test.com", "Password123!");
        System.out.println("Logged in: " + loggedIn);

        if (loggedIn) {
            System.out.println("Current user: " + SessionManager.getCurrentUserName());
            System.out.println("Current role: " + SessionManager.getCurrentUserRole());
        }

        System.out.println("---- Reset password test ----");
        boolean reset = authService.resetPassword("abdelrahmen@test.com", "NewPassword123!");
        System.out.println("Password reset: " + reset);

        System.out.println("---- Logout test ----");
        authService.logout();
        System.out.println("Logged in after logout: " + SessionManager.isLoggedIn());
    }
}
