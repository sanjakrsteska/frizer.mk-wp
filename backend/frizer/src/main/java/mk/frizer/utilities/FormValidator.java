package mk.frizer.utilities;

public class FormValidator {
    public static boolean isPhoneNumberValid(String number) {
        return number != null && ((number.length() == 9 && number.startsWith("07"))
                || (number.length() == 12 && number.startsWith("+389")));
    }

    public static boolean isEmailValid(String email) {
        return email != null && email.contains("@");
    }

    public static boolean isNameValid(String name) {
        return name != null && !name.isEmpty();
    }

}
