import java.time.LocalDate;

public class Patient extends Person{

    private String patientId;
    private String email;

    public Patient(String name, String surname, LocalDate dateOfBirth, String mobileNumber, String patientId, String email) {
        super(name, surname, dateOfBirth, mobileNumber); // Here calling the constructor of Person class which is the super class of Patient.
        this.patientId = patientId;
        this.email = email;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "Name= "+this.getName()+", "+
                "Surname= "+this.getSurname()+", " +
                "Date of birth= "+this.getDateOfBirth()+", "+
                "Mobile number= "+this.getMobileNumber()+", " +
                "patientId='" + patientId + '\'' +
                '}';
    }
}
