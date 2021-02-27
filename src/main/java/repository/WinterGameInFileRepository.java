package repository;

import model.WinterGame;

import java.io.*;
import java.time.LocalDate;

public class WinterGameInFileRepository extends WinterGameInMemoryRepository {
    private String file;
    private static int idGenerator = 0;

    public WinterGameInFileRepository(String file){
        this.file = file;
        retrieveWinterGame();
    }

    private void retrieveWinterGame(){
        try(BufferedReader buffer = new BufferedReader(new FileReader(file))){
            String line = buffer.readLine();
            try{
                idGenerator=Integer.parseInt(line);
            }catch (NumberFormatException ex){
                System.err.println("Invalid Value for idGenerator");
            }
            while((line = buffer.readLine()) != null){
                String[] attributes = line.split("; ");
                if (attributes.length != 6){
                    System.out.println(line);
                    System.out.println(attributes.length);
                    System.err.println("Invalid line ..." + line);
                    continue;
                }
                try{
                    int id = Integer.parseInt(attributes[0]);
                    int minAge = Integer.parseInt(attributes[3]);
                    int maxAge = Integer.parseInt(attributes[4]);
                    LocalDate date = LocalDate.parse(attributes[5]);
                    WinterGame winterGame = new WinterGame(id, attributes[1], attributes[2], minAge, maxAge, date);
                    super.add(winterGame);
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

    private void storeWinterGame() {
        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(file))){
            buffer.write("" + idGenerator);
            buffer.newLine();
            for(WinterGame winterGame : findAll()){
                buffer.write(winterGame.toString());
                buffer.newLine();
            }
        } catch (IOException exception) {
            throw new RepositoryException("Writing error " + exception);
        }
    }

    @Override
    public WinterGame add(WinterGame winterGame){
        winterGame.setId(getNextId());
        super.add(winterGame);
        storeWinterGame();
        return winterGame;
    }

    @Override
    public void delete(WinterGame winterGame){
        super.delete(winterGame);
        storeWinterGame();
    }

    @Override
    public void update(Integer id, WinterGame winterGame){
        super.update(id, winterGame);
        storeWinterGame();
    }

    public int getNextId(){
        return idGenerator++;
    }

    public int getIdGenerator(){
        return idGenerator;
    }
}
