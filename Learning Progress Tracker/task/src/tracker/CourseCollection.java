package tracker;

import java.util.*;
import java.util.stream.Collectors;

public class CourseCollection {
    private final Course java = new JavaCourse();
    private final Course dsa = new DSACourse();
    private final Course databases = new DatabasesCourse();
    private final Course spring = new SpringCourse();

    private final List<Course> courseList = new ArrayList<>();

    private final List<String> studentIdList = new ArrayList<>();

    public List<String> getStudentIdList() {
        return studentIdList;
    }

    CourseCollection() {
        courseList.add(java);
        courseList.add(dsa);
        courseList.add(databases);
        courseList.add(spring);
    }

    public void enrollStudent(Student student) {
        java.addStudent(student);
        dsa.addStudent(student);
        databases.addStudent(student);
        spring.addStudent(student);

        studentIdList.add(student.getId());
    }

    public Object getPointsStringById(String id) {
        return String.format("%s=%d; ", java, java.getPointsById(id)) +
                String.format("%s=%d; ", dsa, dsa.getPointsById(id)) +
                String.format("%s=%d; ", databases, databases.getPointsById(id)) +
                String.format("%s=%d; ", spring, spring.getPointsById(id));
    }

    public void addPointsById(String id, String[] points) {
        java.addPointsById(id, points[0]);
        dsa.addPointsById(id, points[1]);
        databases.addPointsById(id, points[2]);
        spring.addPointsById(id, points[3]);
    }

    public Set<Course> getMostPopular() {
        if (getStudentIdList().size() == 0) {
            return null;
        }
        int maxEnrolledCount = courseList.stream().max(Comparator.comparing(Course::getEnrolledCount)).get().getEnrolledCount();

        return courseList.stream().filter(c -> c.getEnrolledCount() == maxEnrolledCount).collect(Collectors.toSet());
    }

    public Set<Course> getLeastPopular() {
        if (getStudentIdList().size() == 0) {
            return null;
        }

        int minEnrolledCount = courseList.stream().min(Comparator.comparing(Course::getEnrolledCount)).get().getEnrolledCount();

        return courseList.stream().filter(c -> c.getEnrolledCount() == minEnrolledCount).collect(Collectors.toSet());
    }

    public Set<Course> getEasiest() {
        if (getStudentIdList().size() == 0) {
            return null;
        }

        int lowestPoints = courseList.stream().max(Comparator.comparing(Course::getTotalPoints)).get().getTotalPoints();

        return courseList.stream().filter(c -> c.getTotalPoints() == lowestPoints).collect(Collectors.toSet());
    }

    public Set<Course> getHardest() {
        if (getStudentIdList().size() == 0) {
            return null;
        }

        int highestPoints = courseList.stream().min(Comparator.comparing(Course::getTotalPoints)).get().getTotalPoints();

        return courseList.stream().filter(c -> c.getTotalPoints() == highestPoints).collect(Collectors.toSet());
    }

    public Set<Course> getHighestActivity() {
        if (getStudentIdList().size() == 0) {
            return null;
        }

        int highestCount = courseList.stream().max(Comparator.comparing(Course::getActivityCount)).get().getActivityCount();

        return courseList.stream().filter(course -> course.getActivityCount() == highestCount).collect(Collectors.toSet());
    }

    public Set<Course> getLowestActivity() {
        if (getStudentIdList().size() == 0) {
            return null;
        }
        int lowestCount = courseList.stream().min(Comparator.comparing(Course::getActivityCount)).get().getActivityCount();
        return courseList.stream().filter(course -> course.getActivityCount() == lowestCount).collect(Collectors.toSet());
    }

    public void printScoreBoardByCourse(String courseName) {
        Course course = courseList.stream().filter(c -> c.toString().equals(courseName)).toList().get(0);

        course.printScoreBoard();
    }

    public void notifyStudents() {
        Set<Student> notifiedStudents = new HashSet<>();
        for (Course course: courseList) {
            List<Student> students = course.getStudentsToNotify();
            for (Student student: students) {
                System.out.printf("To: %s%n", student.getEmail());
                System.out.println("Re: Your learning progress");
                System.out.printf("Hello, %s %s! You have accomplished our %s course!%n", student.getFirstName(), student.getLastName(), course);
                student.markNotified();

                notifiedStudents.add(student);
            }
        }

        System.out.printf("Total %s students have been notified.", notifiedStudents.size());
    }
}
