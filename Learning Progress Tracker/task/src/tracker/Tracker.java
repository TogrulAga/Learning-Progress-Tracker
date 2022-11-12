package tracker;

import java.util.*;

public class Tracker {
    private final Scanner scanner = new Scanner(System.in);

    private final CourseCollection courseCollection = new CourseCollection();

    public void run() {
        System.out.println("Learning Progress Tracker");

        while (scanner.hasNextLine()) {
            MenuItems command = MenuItems.getValueOf(scanner.nextLine());

            switch (command) {
                case ADD_STUDENTS -> addStudents();
                case EXIT -> exit();
                case LIST -> listStudents();
                case FIND -> findStudent();
                case ADD_POINTS -> addPoints();
                case STATISTICS -> showStatistics();
                case NOTIFY -> notifyStudents();
                case NO_INPUT -> System.out.println("No input.");
                case UNKNOWN -> System.out.println("Error: unknown command!");
                case BACK -> System.out.println("Enter 'exit' to exit the program.");
            }
        }
    }

    private void notifyStudents() {
        courseCollection.notifyStudents();
    }

    private void showStatistics() {
        System.out.println("Type the name of a course to see details or 'back' to quit:");

        Set<Course> mostPopular = courseCollection.getMostPopular();
        String mostPopularString = courseSetToString(mostPopular);
        System.out.printf("Most popular: %s%n", mostPopularString);

        Set<Course> leastPopular = courseCollection.getLeastPopular();
        String leastPopularString = courseSetToString(leastPopular);
        System.out.printf("Least popular: %s%n", !leastPopularString.equals(mostPopularString) ? leastPopularString : "n/a");

        Set<Course> highestActivity = courseCollection.getHighestActivity();
        String highestActivityString = courseSetToString(highestActivity);
        System.out.printf("Highest activity: %s%n", highestActivityString);

        Set<Course> lowestActivity = courseCollection.getLowestActivity();
        String lowestActivityString = courseSetToString(lowestActivity);
        System.out.printf("Lowest activity: %s%n", !lowestActivityString.equals(highestActivityString) ? lowestActivityString : "n/a");

        Set<Course> easiest = courseCollection.getEasiest();
        String easiestString = courseSetToString(easiest);
        System.out.printf("Easiest course: %s%n", easiestString);

        Set<Course> hardest = courseCollection.getHardest();
        String hardestString = courseSetToString(hardest);
        System.out.printf("Hardest course: %s%n", !hardestString.equals(easiestString) ? hardestString : "n/a");

        while (true) {
            String input = scanner.nextLine().toLowerCase();

            if (MenuItems.BACK.equals(input)) {
                return;
            }

            switch (input) {
                case "java" -> courseCollection.printScoreBoardByCourse("Java");
                case "dsa" -> courseCollection.printScoreBoardByCourse("DSA");
                case "databases" -> courseCollection.printScoreBoardByCourse("Databases");
                case "spring" -> courseCollection.printScoreBoardByCourse("Spring");
                default -> System.out.println("Unknown course.");
            }
        }
    }

    private static String courseSetToString(Set<Course> courseSet) {
        if (courseSet == null) {
            return "n/a";
        }

        StringBuilder builder = new StringBuilder();

        int i = 0;
        for (Course course: courseSet) {
            if (i != courseSet.size() - 1) {
                builder.append(course).append(", ");
            } else {
                builder.append(course);
            }

            i++;
        }

        return builder.toString();
    }

    private void findStudent() {
        System.out.println("Enter an id or 'back' to return:");

        while (true) {
            String id = scanner.nextLine();

            if (MenuItems.BACK.equals(id)) {
                return;
            }

            if (!courseCollection.getStudentIdList().contains(id)) {
                System.out.printf("No student is found for id=%s.%n", id);
                continue;
            }

            System.out.printf("%s points: %s%n", id, courseCollection.getPointsStringById(id));
        }
    }

    private void addPoints() {
        System.out.println("Enter an id and points or 'back' to return:");
        while (true) {
            String idAndPoints = scanner.nextLine();

            if (MenuItems.BACK.equals(idAndPoints)) {
                return;
            }

            String[] idAndPointsArray = idAndPoints.split(" ");

            if (idAndPointsArray.length != 5) {
                System.out.println("Incorrect points format.");
                continue;
            }

            String id = idAndPointsArray[0];

            if (!courseCollection.getStudentIdList().contains(id)) {
                System.out.printf("No student is found for id=%s.%n", id);
                continue;
            }

            String[] points = Arrays.copyOfRange(idAndPointsArray, 1, idAndPointsArray.length);
            if (!Arrays.stream(points).allMatch(point -> point.matches("\\d+"))) {
                System.out.println("Incorrect points format.");
                continue;
            }

            courseCollection.addPointsById(id, points);

            System.out.println("Points updated.");
        }

    }

    private void listStudents() {
        List<String> studentIdList = courseCollection.getStudentIdList();
        if (studentIdList.size() == 0) {
            System.out.println("No students found.");
            return;
        }

        System.out.println("Students:");
        for (String studentId: studentIdList) {
            System.out.println(studentId);
        }
    }

    private void exit() {
        System.out.println("Bye!");
        System.exit(0);
    }

    private void addStudents() {
        System.out.println("Enter student credentials or 'back' to return");
        String answer;

        int studentCount = courseCollection.getStudentIdList().size();
        while (scanner.hasNextLine()) {
            answer = scanner.nextLine();
            if (MenuItems.BACK.equals(answer)) {
                System.out.printf("Total %d students have been added.%n", courseCollection.getStudentIdList().size() - studentCount);
                return;
            }

            String[] words = answer.split(" ");

            Student student = new Student(words);

            if (!student.isInputValid()) {
                System.out.println("Incorrect credentials.");
                continue;
            } else if (!student.isFirstNameValid()) {
                System.out.println("Incorrect first name.");
                continue;
            } else if (!student.isLastNameValid()) {
                System.out.println("Incorrect last name.");
                continue;
            } else if (!student.isEmailValid()) {
                System.out.println("Incorrect email.");
                continue;
            }

            if (courseCollection.getStudentIdList().contains(student.getId())) {
                System.out.println("This email is already taken.");
                continue;
            }

//            students.add(student);
            courseCollection.enrollStudent(student);

            System.out.println("The student has been added.");
        }
    }
}