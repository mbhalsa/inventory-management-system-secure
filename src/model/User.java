package model;


public class User {
    private int userId;
    private String fullName;
    private String email;
    private String password;
    private boolean isVerified;
    private Role role;


    public User() {
    }

    public User(int userId, String fullName, String email, String password, boolean isVerified) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.isVerified = isVerified;
    }

    public User(int userId, String fullName, String email, String password, boolean isVerified, Role role) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.isVerified = isVerified;
        this.role = role;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public Role getRole() { return role; }

    public void setRole(Role role) {this.role = role; }

    public void register() {
        System.out.println("User registered successfully.");
    }

    public void login() {
        System.out.println("User logged in successfully.");
    }

    public void logout() {
        System.out.println("User logged out successfully.");
    }

    public void resetPassword() {
        System.out.println("Password reset requested.");
    }
}
