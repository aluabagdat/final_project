import models.*;
import java.io.*;
import java.util.List;

public class DataManager {

    private static final String FILE_NAME = "university.dat";

    public static void save(UniversitySystem system) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(system);
            System.out.println("Data saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static UniversitySystem load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (UniversitySystem) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No saved data found, creating new system");
            return UniversitySystem.getInstance();
        }
    }
}