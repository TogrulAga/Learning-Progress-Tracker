package tracker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Course {
    private final String name;
    protected int pointsToComplete;

    private int activityCount = 0;

    private final List<Student> enrolledStudents = new ArrayList<>();

    public int getActivityCount() {
        return activityCount;
    }

    Course(String name, int pointsToComplete) {
        this.pointsToComplete = pointsToComplete;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void addStudent(Student student) {
        enrolledStudents.add(student.copy());
    }

    public int getEnrolledCount() {
        int count = 0;

        for (Student enrolledStudent: enrolledStudents) {
            if (enrolledStudent.getPoints() > 0) {
                count++;
            }
        }

        return count;
    }

    public int getTotalPoints() {
        AtomicInteger totalPoints = new AtomicInteger();

        enrolledStudents.forEach(e -> totalPoints.addAndGet(e.getPoints()));

        return totalPoints.get();
    }

    public int getPointsById(String id) {
        Student enrolledStudent = enrolledStudents.stream().filter(e -> e.getId().equals(id)).toList().get(0);

        return enrolledStudent.getPoints();
    }

    public void addPointsById(String id, String points) {
        if ("0".equals(points)) {
            return;
        }
        Student enrolledStudent = enrolledStudents.stream().filter(e -> e.getId().equals(id)).toList().get(0);
        enrolledStudent.addPoints(Integer.parseInt(points));

        activityCount++;
    }

    public void printScoreBoard() {
        System.out.println(name);

        System.out.printf("id%spoints%scompleted%n", " ".repeat(10), " ".repeat(10));

        List<Student> studentsWithPoints = new ArrayList<>(enrolledStudents.stream().filter(c -> c.getPoints() != 0).toList());
        studentsWithPoints.sort(Comparator.comparing(Student::getPoints).reversed());
        for (Student enrolledStudent: studentsWithPoints) {
            float completion = (float) enrolledStudent.getPoints() / pointsToComplete * 100;
            System.out.printf("%s %d %.1f%%%n", enrolledStudent.getId(), enrolledStudent.getPoints(), completion);
        }
    }

    public List<Student> getStudentsToNotify() {
        List<Student> students = new ArrayList<>();
        for (Student student: enrolledStudents) {
            if (student.getPoints() >= pointsToComplete && !student.isNotified()) {
                students.add(student);
            }
        }

        return students;
    }
}
