package service;

import dao.AuditLogDAO;
import dao.UserDAO;
import model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private UserDAO userDAO;
    private AuditLogDAO auditLogDAO;
    private Map<String, Integer> loginAttempts;

    public AuthService() {
        userDAO = new UserDAO();
        auditLogDAO = new AuditLogDAO();
        loginAttempts = new HashMap<>();
    }

    public boolean register(String fullName, String email, String password, int roleId) {
        if (userDAO.emailExists(email)) {
            return false;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setVerified(false);

        boolean registered = userDAO.registerUser(user, roleId);

        if (registered) {
            auditLogDAO.addLog("REGISTER", fullName, "New user registered with email: " + email);
        }

        return registered;
    }

    public boolean login(String email, String password) {
        if (isLocked(email)) {
            auditLogDAO.addLog("LOGIN_BLOCKED", email, "Too many failed login attempts");
            return false;
        }

        User user = userDAO.getUserByEmail(email);

        if (user == null) {
            increaseAttempts(email);
            auditLogDAO.addLog("LOGIN_FAILED", email, "Invalid email or password");
            return false;
        }

        if (!BCrypt.checkpw(password, user.getPassword())) {
            increaseAttempts(email);
            auditLogDAO.addLog("LOGIN_FAILED", email, "Invalid email or password");
            return false;
        }

        if (!user.isVerified()) {
            auditLogDAO.addLog("LOGIN_FAILED", email, "Account not verified");
            return false;
        }

        resetAttempts(email);

        String roleName = user.getRole() != null ? user.getRole().getRoleName() : "No Role";
        SessionManager.login(user.getUserId(), user.getFullName(), user.getEmail(), roleName);

        auditLogDAO.addLog("LOGIN_SUCCESS", user.getFullName(), "User logged in successfully");
        return true;
    }

    public void logout() {
        if (SessionManager.isLoggedIn()) {
            auditLogDAO.addLog("LOGOUT", SessionManager.getCurrentUserName(), "User logged out");
        }
        SessionManager.logout();
    }

    public boolean verifyEmail(String email) {
        boolean verified = userDAO.verifyEmail(email);

        if (verified) {
            auditLogDAO.addLog("VERIFY_EMAIL", email, "Email verified successfully");
        }

        return verified;
    }

    public boolean resetPassword(String email, String newPassword) {
        if (!userDAO.emailExists(email)) {
            return false;
        }

        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        boolean updated = userDAO.updatePassword(email, hashedPassword);

        if (updated) {
            auditLogDAO.addLog("RESET_PASSWORD", email, "Password reset successfully");
        }

        return updated;
    }

    public boolean assignRole(String email, int roleId) {
        boolean updated = userDAO.assignRole(email, roleId);

        if (updated) {
            auditLogDAO.addLog("ASSIGN_ROLE", SessionManager.getCurrentUserName(), "Assigned role " + roleId + " to " + email);
        }

        return updated;
    }

    private void increaseAttempts(String email) {
        loginAttempts.put(email, loginAttempts.getOrDefault(email, 0) + 1);
    }

    private void resetAttempts(String email) {
        loginAttempts.remove(email);
    }

    public boolean isLocked(String email) {
        return loginAttempts.getOrDefault(email, 0) >= 3;
    }

    public int getAttempts(String email) {
        return loginAttempts.getOrDefault(email, 0);
    }
}
