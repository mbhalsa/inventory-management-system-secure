package service;

import dao.AuditLogDAO;
import dao.UserDAO;
import model.User;
import org.mindrot.jbcrypt.BCrypt;


public class AuthService {
    private UserDAO userDAO;
    private AuditLogDAO auditLogDAO;

    public AuthService() {
        userDAO = new UserDAO();
        auditLogDAO = new AuditLogDAO();
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
        User user = userDAO.getUserByEmail(email);

        if (userDAO.isAccountLocked(email)) {
            sleepBeforeFail();
            auditLogDAO.addLog("LOGIN_BLOCKED", email, "Account temporarily locked");
            return false;
        }

        if (user == null) {
            sleepBeforeFail();
            auditLogDAO.addLog("LOGIN_FAILED", email, "Invalid email or password");
            return false;
        }

        if (!BCrypt.checkpw(password, user.getPassword())) {
            userDAO.recordFailedLogin(email);
            sleepBeforeFail();
            auditLogDAO.addLog("LOGIN_FAILED", email, "Invalid email or password");
            return false;
        }

        if (!user.isVerified()) {
            sleepBeforeFail();
            auditLogDAO.addLog("LOGIN_FAILED", email, "Account not verified");
            return false;
        }

        userDAO.resetFailedLogin(email);

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

    private void sleepBeforeFail() {
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}