package service;

import model.Role;
import model.User;

public class AuthService {
    private User currentUser;

    public AuthService() {
    }

    public AuthService(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean authenticate(String email, String password) {
        if (currentUser != null &&
                currentUser.getEmail().equals(email) &&
                currentUser.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public void verifyEmail(User user) {
        if (user != null) {
            user.setVerified(true);
            System.out.println("Email verified successfully.");
        }
    }

    public boolean checkAccess(User user, Role role) {
        return user != null && role != null;
    }
}