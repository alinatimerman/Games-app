package repository;

import model.WinterGame;
import model.Enrollment;

import java.io.*;
import java.time.LocalDate;

public class EnrollmentInFileRepository extends EnrollmentInMemoryRepository {
    private String file;
    private WinterGameRepository winterGameRepository;
    private static int idGenerator = 0;

    public EnrollmentInFileRepository(String file, WinterGameRepository winterGameRepository){
        this.file = file;
        this.winterGameRepository = winterGameRepository;
        retrieveOrder();
    }

    private void retrieveOrder(){
        try(BufferedReader buffer = new BufferedReader(new FileReader(file))){
            String line = buffer.readLine();
            try{
                idGenerator=Integer.parseInt(line);
            }catch (NumberFormatException ex){
                System.err.println("Invalid Value for idGenerator");
            }
            while((line = buffer.readLine()) != null){
                String[] attributes = line.split("; ");
                if (attributes.length != 5){
                    System.err.println("Invalid line ..." + line);
                    continue;
                }
                try{
                    Integer id = Integer.parseInt(attributes[0]);
                    int age = Integer.parseInt(attributes[3]);
                    Integer winterGame = Integer.parseInt(attributes[4]);
                    if(winterGameRepository.findById(winterGame) == null)
                        System.err.println("Reading error: Winter game does not exist. Line " + line);
                    else{
                        Enrollment enrollment = new Enrollment(id, attributes[1], attributes[2], age, winterGame);
                        super.add(enrollment);
                    }
                }catch(NumberFormatException ex){
                    System.err.println("Error converting number");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }catch(IOException exception){
            throw new RepositoryException("Reading error " + exception);
        }
    }

    private void storeOrder() {
        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(file))){
            buffer.write("" + idGenerator);
            buffer.newLine();
            for(Enrollment enrollment : findAll()){
                buffer.write(enrollment.toString());
                buffer.newLine();
            }
        } catch (IOException exception) {
            throw new RepositoryException("Writing error " + exception);
        }
    }

    @Override
    public Enrollment add(Enrollment enrollment){
        enrollment.setId(getNextId());
        super.add(enrollment);
        storeOrder();
        return enrollment;
    }

    @Override
    public void delete(Enrollment enrollment){
        super.delete(enrollment);
        storeOrder();
    }

    @Override
    public void update(Integer id, Enrollment enrollment){
        super.update(id, enrollment);
        storeOrder();
    }

    public int getNextId(){
        return idGenerator++;
    }

    public int getIdGenerator(){
        return idGenerator;
    }
}
