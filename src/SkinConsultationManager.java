import java.io.IOException;

public interface SkinConsultationManager {
    void addDoctor();
    void deleteDoctor();
    void printList();
    void saveFile(String fileName) throws IOException;
    void saveConsultations(String fileName) throws IOException;
}
