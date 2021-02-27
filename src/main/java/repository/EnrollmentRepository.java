package repository;

import model.Enrollment;

import java.time.LocalDate;
import java.util.List;

public interface EnrollmentRepository extends IRepository<Integer, Enrollment>{
    List<Enrollment> filterByChildName(String childName);
    List<Enrollment> filterByWinterGame(int winterGame);
}
