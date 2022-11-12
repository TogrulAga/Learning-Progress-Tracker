package tracker;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Objects;

public class Student {
    private String firstName;
    private String lastName;
    private String email;
    private String id = null;

    private int points = 0;

    private boolean notified = false;

    private final boolean inputValid;
    private boolean firstNameValid = true;
    private boolean lastNameValid = true;
    private boolean emailValid = true;

    Student(String[] input) {
        this.inputValid = isInputValid(input);

        if (!this.inputValid) {
            return;
        }

        firstName = parseFirstName(input);

        if (firstName == null) {
            firstNameValid = false;
            return;
        }

        lastName = parseLastName(input);

        if (lastName == null) {
            lastNameValid = false;
        }

        email = parseEmail(input);

        if (email == null) {
            emailValid = false;
        }

        id = calculateId(email);
    }

    Student(String firstName, String lastName, String email) {
        inputValid = true;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = calculateId(email);
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getPoints() {
        return points;
    }

    private String parseEmail(String[] input) {
        if (input[input.length - 1].matches("[a-z0-9]+(\\.[a-z0-9]+)?@[a-z0-9]+\\.[a-z0-9]+")) {
            return input[input.length - 1];
        } else {
            return null;
        }
    }

    private static String parseLastName(String[] input) {
        String[] lastNameStrings = Arrays.copyOfRange(input, 1, input.length - 1);
        StringBuilder lastName = new StringBuilder();
        for (String s : lastNameStrings) {
            if (!s.matches("[a-zA-Z]([-']?[a-zA-Z]+)+")) {
                return null;
            }

            lastName.append(s);
        }

        return lastName.toString();
    }

    private static boolean isInputValid(String[] input) {
        return input.length >= 3;
    }

    private static String parseFirstName(String[] input) {
        if (input[0].matches("[a-zA-Z]([-']?[a-zA-Z]+)+")) {
            return input[0];
        } else {
            return null;
        }
    }

    private static String calculateId(String email) {
        StringBuilder result = new StringBuilder();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hash = digest.digest(email.getBytes(StandardCharsets.UTF_8));

            for (byte bt: hash) {
                result.append(bt);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return result.toString();
    }

    public boolean isInputValid() {
        return inputValid;
    }

    public boolean isFirstNameValid() {
        return firstNameValid;
    }

    public boolean isLastNameValid() {
        return lastNameValid;
    }

    public boolean isEmailValid() {
        return emailValid;
    }

    public String getId() {
        return id;
    }

    public Student copy() {
        return new Student(firstName, lastName, email);
    }

    public boolean isNotified() {
        return notified;
    }

    public void markNotified() {
        notified = true;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Student student)) {
            return false;
        }

        return id.equals(student.getId());
    }
}
