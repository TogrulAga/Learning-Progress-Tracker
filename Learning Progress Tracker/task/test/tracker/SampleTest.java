package tracker;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class SampleTest {
    @ParameterizedTest
    @MethodSource("provideInputForNewStudent")
    void test_newStudent(String input, boolean inputValid, boolean firstNameValid, boolean lastNameValid, boolean emailValid) {
        Student student = new Student(input.split(" "));
        assertEquals(inputValid, student.isInputValid());
        assertEquals(firstNameValid, student.isFirstNameValid());
        assertEquals(lastNameValid, student.isLastNameValid());
        assertEquals(emailValid, student.isEmailValid());
    }

    private static Stream<Arguments> provideInputForNewStudent() {
        return Stream.of(
                Arguments.of("John Doe johnd@yahoo.com", true, true, true, true),
                Arguments.of("Jane Spark jspark@gmail.com", true, true, true, true),
                Arguments.of("Jane Sprocket jspark@gmail.com", true, true, true, true),
                Arguments.of("Jane Spark janes@gmail.com", true, true, true, true),
                Arguments.of("Jane Spark", false, true, true, true),
                Arguments.of("Jane janes@gmail.com", false, true, true, true)
        );
    }
}
