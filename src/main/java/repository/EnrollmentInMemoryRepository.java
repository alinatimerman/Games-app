package repository;

import model.Enrollment;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class EnrollmentInMemoryRepository extends AbstractRepository<Integer, Enrollment> implements EnrollmentRepository {
    @Override
    public List<Enrollment> filterByChildName(String childName) {
        return getAll().stream().filter(enrollment -> enrollment.getChildName().equals(childName)).collect(Collectors.toList());
    }

    @Override
    public List<Enrollment> filterByWinterGame(int winterGame){
        return getAll().stream().filter(enrollment -> enrollment.getWinterGame().equals(winterGame)).collect(Collectors.toList());
    }
}
