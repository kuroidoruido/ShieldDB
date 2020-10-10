package nf.fr.k49.shielddb.core.testobject;

import java.time.LocalDate;

/**
 * A simple example class for using in tests
 */
public class ExampleUser {

    public static ExampleUser createDefaultExample() {
        ExampleUser exampleUser = new ExampleUser();
        exampleUser.firstName = "anyFirstName";
        exampleUser.lastName = "anyLastName";
        exampleUser.dayOfBirth = LocalDate.of(1919, 19, 19);
        return exampleUser;
    }

    public static ExampleUser createRealExistingExample() {
        ExampleUser exampleUser = new ExampleUser();
        exampleUser.firstName = "Stan";
        exampleUser.lastName = "Lee";
        exampleUser.dayOfBirth = LocalDate.of(1922, 12, 28);
        return exampleUser;
    }

    private String firstName;
    private String lastName;
    private LocalDate dayOfBirth;

    public ExampleUser() {
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(LocalDate dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }
}
