import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Objects;
import java.util.Scanner;

public class WestminsterSkinConsultationManager extends Person implements SkinConsultationManager{
    // WestminsterSkinConsultationManager class also extended to Person class, because WestminsterSkinConsultationManager represents the manager of the medical center.
    // And this class implements SkinConsultationManager Interface.

    public static ArrayList<Doctor> doctors = new ArrayList<>();  // This arrayList is used to store doctor objects.
    public static ArrayList<Patient> patients = new ArrayList<>();  // This arrayList is used to store patient objects.
    public static ArrayList<ArrayList<Consultation>> consultations = new ArrayList<>();  // This is a 2D arrayList. Inside this, there is a separate arraylist for each doctor to store their consultations separately.
    public static ArrayList<Consultation> allConsultations = new ArrayList<>();  // This arrayList storing all consultations without disaggregation consultations according to doctors.

    public static void printMenu() {
        System.out.print("""
                Enter 1: Add a New Doctor
                Enter 2: Delete a Doctor
                Enter 3: Print the list of the doctors
                Enter 4: Save in a file
                Enter 5: Exit the program
                Enter 6: Open GUI
                Enter your choice here:\s""");
    }

    public WestminsterSkinConsultationManager(String name, String surname, LocalDate dateOfBirth, String mobileNumber) {
        super(name, surname, dateOfBirth, mobileNumber);
    }

    @Override
    public void addDoctor() { // This method runs when the user selects the 'Enter 1: Add Doctor' option.
        System.out.println();
        int doctorCount = doctors.size();
        if (canAddMoreDoctors(doctorCount)) { // Checking whether the medical center have 10 doctors or fewer than 10.
            Scanner scanner = new Scanner(System.in);
            String name;
            while (true) {
                System.out.print("Enter First Name : ");
                name = scanner.nextLine();
                if (isStringOnlyAlphabet(name)) {
                    break;
                }
                System.out.println("\nPlease Enter a valid name.\n");
            }

            String surname;
            while (true) {
                System.out.print("Enter Surname : ");
                surname = scanner.nextLine();
                if (isStringOnlyAlphabet(surname)) {
                    break;
                }
                System.out.println("\nPlease Enter a valid surname.\n");
            }

            LocalDate dateOfBirth;
            while (true) {
                try {
                    int year = integerInputValidator("Enter the Year of birth : ", "\nEnter a valid year.\n"); // integerInputValidator is a method that I used to validate user input. Method can be found at the bottom of this class.
                    int month;
                    while (true) {
                        month = integerInputValidator("Enter the Month of birth : ", "\nEnter a valid month.\n");
                        if (isMonthValid(month))
                            break;
                        else
                            System.out.println("\nEnter a month between 1 and 12\n");
                    }

                    int day;
                    while (true) {
                        day = integerInputValidator("Enter the Date of birth : ", "\nEnter a valid date\n");
                        if (isDateValid(day))
                            break;
                        else
                            System.out.println("\nPlease enter a valid date.\n");
                    }
                    dateOfBirth = LocalDate.of(year, month, day); // Creating a localDate object for date of birth.
                    break;
                } catch (Exception e) {
                    System.out.println("\nPlease enter a valid date.\n");
                }
            }
            String mobileNumber;
            while (true) {
                System.out.print("Enter Mobile Number : ");
                mobileNumber = scanner.nextLine();
                if (isContactNumberValid(mobileNumber)) {
                    break;
                }
                System.out.println("\nPlease Enter a valid mobile number.\n");
            }
            System.out.print("Enter medical licence number : ");
            String medicalLicenceNumber = scanner.nextLine();
            Specialisation specialisation;
            while (true) {
                int specialisationInput = integerInputValidator("""
                        Enter Specialisation
                        (1 for cosmetic dermatology)
                        (2 for medical dermatology)
                        (3 for paediatric dermatology):\s""", "\nPlease enter a valid input\n");
                if (specialisationInput == 1) {
                    specialisation = Specialisation.cosmetic_dermatology; // Specialisation is assigning to the variable from enum class of Specialisation.
                    break;
                } else if (specialisationInput == 2) {
                    specialisation = Specialisation.medical_dermatology;
                    break;
                } else if (specialisationInput == 3) {
                    specialisation = Specialisation.paediatric_dermatology;
                    break;
                } else System.out.println("\nPlease enter a valid specialisation number.\n");
            }

            Doctor newDoctor = new Doctor(name, surname, dateOfBirth, mobileNumber, medicalLicenceNumber, specialisation); // Creating a Doctor object from the user input details.
            ArrayList<Consultation> consultationList = new ArrayList<>(); // Creating a new arrayList to store future consultations of this doctor.
            consultations.add(consultationList); // Adding the created arrayList to the 2D arrayList.
            doctors.add(newDoctor); // Adding the created doctor object to the doctor arrayList.
            System.out.println("\nDr. " + newDoctor.getName() + " " + newDoctor.getSurname() + " has added to the system successfully.");
            System.out.println("Total number of Doctors in the medical center : " + doctors.size() + "\n");

        }

        else System.out.println("\nYou can't add more doctors. There are 10 doctors in the centre.\n"); // If the medical center already have 10 doctors...
    }

    @Override
    public void deleteDoctor() { // This method runs when the user selects the 'Enter 2: Delete Doctor' option.
        if (doctors.size() != 0) { // Checking whether the doctor arrayList is empty or not before running the delete method.
            Scanner scanner = new Scanner(System.in);
            System.out.println();
            Formatter formatter = new Formatter();
            formatter.format("%20s%15s\n\n", "Full Name", "Med Lic No");
            for (Doctor doctor : doctors){
                formatter.format("%20s%15s\n", doctor.getName()+" "+doctor.getSurname(), doctor.getMedicalLicenceNumber());  // Printing the names and their licence numbers of doctors for the ease of the user to choose the correct doctor.
            }
            System.out.println(formatter);
            whileLoop:
            while (true) {
                System.out.print("Enter 6 to go back to main menu.\nEnter the Medical Licence Number of the doctor you want to delete from the system : ");
                String medLicNo = scanner.nextLine();
                if (Objects.equals(medLicNo, "6")) {
                    System.out.println();
                    break;
                }
                ArrayList<String> medLicNumbers = new ArrayList<>();
                for (Doctor doc : doctors) {
                    medLicNumbers.add(doc.getMedicalLicenceNumber());
                }
                int deleteDocIndex = 0;
                for (Doctor deleteDoc : doctors) {
                    if (Objects.equals(deleteDoc.getMedicalLicenceNumber(), medLicNo)){
                        System.out.println("\nDr. "+deleteDoc.getName()+" "+deleteDoc.getSurname()+" with Medical Licence" +
                                " Number "+deleteDoc.getMedicalLicenceNumber()+" was removed from the system successfully.");
                        doctors.remove(deleteDoc); // deleting the doctor from the arrayList.
                        consultations.remove(deleteDocIndex); // deleting the consultation arrayList of that doctor.
                        System.out.println("Total number of Doctors in the medical center : "+doctors.size()+"\n");
                        break whileLoop;}
                    if (!medLicNumbers.contains(medLicNo)) {
                        System.out.println("\nThe number you entered is invalid.\nPlease enter a valid Medical Licence Number.\n"); // If user enters an invalid licence number which is not belongs to any doctor in the medical center.
                        break;
                    }
                    deleteDocIndex++;
                }
            }
        }
        else System.out.println("\nDoctors List is Empty.\n");
    }

    @Override
    public void printList() { // This method runs when the user selects the 'Enter 3: Print the list of doctors' option.
        if (doctors.size() != 0) {
            Doctor[] sortedDoctorList = new Doctor[doctors.size()];
            for (Doctor newDoctor : doctors) { // Copying all doctors to a new array to sort their surnames using bubble sort.
                sortedDoctorList[doctors.indexOf(newDoctor)] = newDoctor;
            }

            for (int x=0; x<sortedDoctorList.length; x++) { // Bubble sort
                for (int y=x+1; y<sortedDoctorList.length; y++) {
                    if (sortedDoctorList[x].getSurname().compareTo(sortedDoctorList[y].getSurname()) > 0) {
                        Doctor temp = sortedDoctorList[x];
                        sortedDoctorList[x] = sortedDoctorList[y];
                        sortedDoctorList[y] = temp;
                    }
                }
            }
            System.out.println();
            Formatter formatter = new Formatter();
            formatter.format("%15s%15s%15s%15s%15s%30s\n\n", "First Name", "Surname", "Birthday", "Phone Number", "Med Lic No", "Specialization");

            for (Doctor sortedDoc : sortedDoctorList){
                formatter.format("%15s%15s%15s%15s%15s%30s\n", sortedDoc.getName(), sortedDoc.getSurname(), sortedDoc.getDateOfBirth(),
                        sortedDoc.getMobileNumber(), sortedDoc.getMedicalLicenceNumber(), sortedDoc.getSpecialisation());
            }
            System.out.println(formatter);
        }
        else System.out.println("\nDoctors List in Empty\n");
    }

    @Override
    public void saveFile(String fileName) throws IOException { // This method runs when the user selects the 'Enter 4: Save data' option.
        File file = new File(fileName); // Creating new file.
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

        for (Doctor newDoctor : doctors) { // Writing all objects in doctors arraylist to the text file.
            objOut.writeObject(newDoctor);
        }
        System.out.println("\nData saved successfully\n");
    }

    @Override
    public void saveConsultations(String fileName) throws IOException { // This method runs when the user selects the 'Enter 4: Save data' option.
        File file = new File(fileName);
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

        for (ArrayList<Consultation> doctorConsultations : consultations){
            objOut.writeObject(doctorConsultations);
        }
    }

    public static void saveAllConsultations(String fileName) throws IOException { // This method runs when the user selects the 'Enter 4: Save data' option.
        File file = new File(fileName);
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

        for (Consultation consultation : allConsultations){
            objOut.writeObject(consultation);
        }
    }

    public static void loadData(String fileName) throws IOException { // This method runs everytime the program starts to run.
        FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        while(true) {
            try {
                Doctor newDoctor = (Doctor) objectInputStream.readObject();
                doctors.add(newDoctor);
            }
            catch (IOException | ClassNotFoundException e){
                break;
            }
        }

    }

    public static void loadConsultations(String fileName) throws IOException { // This method runs everytime the program starts to run.
        FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        while (true) {
            try {
                ArrayList<Consultation> doctorConsultations = (ArrayList<Consultation>) objectInputStream.readObject();
                consultations.add(doctorConsultations);
            }
            catch (IOException | ClassNotFoundException e){
                break;
            }
        }
    }

    public static void loadAllConsultations(String fileName) throws IOException{ // This method runs everytime the program starts to run.
        FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        while (true) {
            try {
                Consultation consultation = (Consultation) objectInputStream.readObject();
                allConsultations.add(consultation);
            }
            catch (IOException | ClassNotFoundException e){
                break;
            }
        }
    }

    public static void loadPatients(String fileName) throws IOException { // This method runs everytime the program starts to run.
        FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        while (true) {
            try {
                Patient newPatient = (Patient) objectInputStream.readObject();
                patients.add(newPatient);
            }
            catch (IOException | ClassNotFoundException e){
                break;
            }
        }
    }

    public int integerInputValidator(String menuOption, String errorMsg){ // This method is used to validate the integer user inputs.
        Scanner userInput = new Scanner(System.in);
        int input;
        while (true) {
            System.out.print(menuOption);
            try {
                input = userInput.nextInt();
                break;
            } catch (Exception e) {
                System.out.println(errorMsg);
                userInput.next();
            }
        }
        return input;
    }

    public static boolean isStringOnlyAlphabet(String str) {
        return ((str != null) && (!str.equals(""))
                && (str.matches("^[a-zA-Z]*$")));
    }

    public static boolean isContactNumberValid(String contactNumber) {
        return ((contactNumber != null) && (!contactNumber.equals("")) && (contactNumber.matches("0\\d{9}")));
    }

    public static boolean canAddMoreDoctors(int doctorCount){return (doctorCount < 10);}

    public static boolean isMonthValid(int month) {return (month >= 1 && month <= 12);}
    public static boolean isDateValid(int day){return (day >= 1 && day <= 31);}

    public static void main(String[] args) throws IOException { // Main method.
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager("Shakya","Wijerathne", LocalDate.of(2000, 7, 24), "00998" ); // Creating a manager object.
        try {
            loadData("Doctors");                                           // Loading all saved data from the files before running other methods.
            loadConsultations("Consultations");
            PatientDetails.loadLastPatient("LastPatient");
            loadPatients("Patients");
            PatientDetails.loadLastConsultation("LastConsultation");
            loadAllConsultations("AllConsultations");
        }
        catch (Exception ignored) {
        }
        String choice = null;
        while (!Objects.equals(choice, "5") && !Objects.equals(choice, "6") ) {
            printMenu();
            Scanner userInput = new Scanner(System.in);
            choice = userInput.next();
            switch (choice) {
                case "1" -> manager.addDoctor();
                case "2" -> manager.deleteDoctor();
                case "3" -> manager.printList();
                case "4" -> {
                    manager.saveFile("Doctors");
                    manager.saveConsultations("Consultations");
                }
                case "5" -> System.out.println("\nExit the program");
                case "6" -> {
                    ViewTable.main(null);
                    System.out.println("\nGUI Opening\n");}
                default -> System.out.println("\nPlease enter a valid input\n");
            }

        }


    }
}
