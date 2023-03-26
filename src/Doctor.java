import java.time.LocalDate;

public class Doctor extends Person {

    private String medicalLicenceNumber;
    private Specialisation specialisation;

    public Doctor(String name, String surname, LocalDate dateOfBirth, String mobileNumber, String medicalLicenceNumber, Specialisation specialisation) {
        super(name, surname, dateOfBirth, mobileNumber); // Here calling the constructor of Person class which is the super class of Doctor class.
        this.medicalLicenceNumber = medicalLicenceNumber;
        this.specialisation = specialisation;
    }

    public String getMedicalLicenceNumber() {
        return medicalLicenceNumber;
    }

    public void setMedicalLicenceNumber(String medicalLicenceNumber) {
        this.medicalLicenceNumber = medicalLicenceNumber;
    }

    public Specialisation getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(Specialisation specialisation) {
        this.specialisation = specialisation;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "Name= "+this.getName()+", "+
                "Surname= "+this.getSurname()+", " +
                "Date of birth= "+this.getDateOfBirth()+", "+
                "Mobile number= "+this.getMobileNumber()+", " +
                "medicalLicenceNumber='" + medicalLicenceNumber + '\'' +
                ", specialisation=" + specialisation +
                '}';
    }
}
