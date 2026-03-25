package service;

public class ValidationService {

    public static boolean isTextValid(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isPositiveInteger(String value) {
        try {
            int number = Integer.parseInt(value.trim());
            return number > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isNonNegativeInteger(String value) {
        try {
            int number = Integer.parseInt(value.trim());
            return number >= 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isPositiveDouble(String value) {
        try {
            double number = Double.parseDouble(value.trim());
            return number > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidEmail(String email) {
        if (!isTextValid(email)) {
            return false;
        }

        return email.contains("@") && email.contains(".");
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.trim().length() >= 8;
    }

    public static boolean isValidStatus(String status) {
        if (!isTextValid(status)) {
            return false;
        }

        String value = status.trim().toLowerCase();

        return value.equals("pending")
                || value.equals("completed")
                || value.equals("cancelled")
                || value.equals("received");
    }
}