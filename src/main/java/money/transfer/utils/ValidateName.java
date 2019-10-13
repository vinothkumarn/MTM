package money.transfer.utils;

public class ValidateName {

  public static boolean validate(String name) {
    // Only characters from A-Z and space is allowed
    return name.matches("[a-zA-Z]+( +[a-zA-Z]+)*");
  }
}
