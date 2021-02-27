package service;

import model.WinterGame;
import model.Enrollment;
import repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    private WinterGameRepository winterGameRepository;
    private EnrollmentRepository enrollmentRepository;

    public Service(WinterGameRepository winterGameRepository, EnrollmentRepository enrollmentRepository){
        this.winterGameRepository = winterGameRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

//----------------------------------------------------WinterGame----------------------------------------------------//
    public List<WinterGame> getWinterGameList(){
        return new ArrayList<>(winterGameRepository.getAll());
    }


//`````````````````````````````````````````````````CRUD`````````````````````````````````````````````````//
    public int addWinterGame(String name, String type, int minAge, int maxAge, LocalDate date) throws ServiceException{
        try{
            WinterGame winterGame = new WinterGame(name, type, minAge, maxAge, date);
            winterGameRepository.add(winterGame);
            return winterGame.getId();
        }catch (RepositoryException exception){
            throw new ServiceException("Error adding winter game: " + exception);
        }

    }

//`````````````````````````````````````````````````Filter`````````````````````````````````````````````````//
    public List<WinterGame> filterByChildWinterGame(String child){
        return enrollmentRepository.filterByChildName(child).stream().map(Enrollment::getWinterGame).map(winterGameId -> winterGameRepository.findById(winterGameId)).collect(Collectors.toList());
    }

    public int getWinterGameIdGenerator() {
        if (winterGameRepository instanceof WinterGameInFileRepository) {
            return ((WinterGameInFileRepository) winterGameRepository).getIdGenerator();
        }
        else return 0;
    }

//----------------------------------------------------Enrollment----------------------------------------------------//
    public List<Enrollment> getEnrollmentList(){
    return new ArrayList<>(enrollmentRepository.getAll());
}

//`````````````````````````````````````````````````CRUD`````````````````````````````````````````````````//
    public int addEnrollment(String childName, String parentName, int age, int winterGame) throws ServiceException{
        try {
            /*
            Verifies if the winterGame exists
             */
            if (winterGameRepository.findById(winterGame) == null)
                throw new ServiceException("This winter game does not exist");

            /*
            Verifies if the age of the child is ok for this enrollment
             */
            WinterGame winterGameObj = winterGameRepository.findById(winterGame);
            if (winterGameObj.getMaxAge() < age)
                throw new ServiceException("The child is too old");
            if(winterGameObj.getMinAge() > age)
                throw new ServiceException("The child is too young");

            Enrollment enrollment = new Enrollment(childName, parentName, age, winterGame);
            enrollmentRepository.add(enrollment);
            return enrollment.getId();
        }catch (RepositoryException exception){
            throw  new ServiceException("Error adding order: " + exception);
        }
    }

//`````````````````````````````````````````````````Filter`````````````````````````````````````````````````//
    public List<Enrollment> filterByWinterGameEnrollments(int winterGame){
        return enrollmentRepository.filterByWinterGame(winterGame);
    }

    public int getEnrollmentIdGenerator() {
        if (enrollmentRepository instanceof EnrollmentInFileRepository) {
            return ((EnrollmentInFileRepository) enrollmentRepository).getIdGenerator();
        }
        else return 0;
    }
}
