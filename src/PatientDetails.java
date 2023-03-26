import com.github.lgooddatepicker.components.DatePicker;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class PatientDetails extends JFrame implements ActionListener {
    JLabel image, idLabel;
    DatePicker datePicker;
    JTextField fNameField, emailField;
    JTextField lNameField;
    JTextField mobNoField;
    JButton submitButton;
    JLabel savedMsg;
    JComboBox<Integer> durationBox;
    JLabel costField;
    JButton uploadButton;
    JTextArea textArea;
    JLabel consultationIdLabel;
    String consultationId;
    String sourceFilePath;
    Boolean imageSelected = false;
    public static String key = "12345678";
    public static int textKey = 8;
    public static Key secretKey;

    public static Patient lastPatient;
    public static Consultation lastConsultation;
    int cost;
    Font font1 = new Font("Ariel", Font.BOLD, 30);
    Font font2 = new Font("Ariel", Font.BOLD, 16);

    public PatientDetails(){

        Container container = getContentPane();
        container.setLayout(new GridBagLayout());
        GridBagConstraints containerConstrains = new GridBagConstraints();
        containerConstrains.anchor = GridBagConstraints.WEST;
        containerConstrains.insets = new Insets(10, 10, 10, 10);
        containerConstrains.gridy = 0;
        containerConstrains.gridx = 0;
        // Set frame title
        setTitle("Patient Details");

        // Set layout manager
        JPanel firstRow = new JPanel();
        firstRow.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Add text fields to frame
        JLabel heading = new JLabel("             Enter your details.");
        heading.setFont(font1);
        add(heading, containerConstrains);
        constraints.gridx = 0;
        constraints.gridy = 0;
        firstRow.add(new JLabel("First Name: "), constraints);
        constraints.gridx = 1;
        fNameField = new JTextField(20);
        firstRow.add(fNameField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        firstRow.add(new JLabel("Surname: "), constraints);
        constraints.gridx = 1;
        lNameField = new JTextField(20);
        firstRow.add(lNameField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        firstRow.add(new JLabel("Email: "), constraints);
        constraints.gridx = 1;
        emailField = new JTextField(20);
        emailField.setText("@gmail.com");
        firstRow.add(emailField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 3;
        firstRow.add(new JLabel("Mobile Number: "), constraints);
        constraints.gridx = 1;
        mobNoField = new JTextField(20);
        mobNoField.setText("0xxxxxxxxx");
        firstRow.add(mobNoField, constraints);


        constraints.gridx = 0;
        constraints.gridy = 4;
        firstRow.add(new JLabel("Date of Birth: "), constraints);
        constraints.gridx = 1;
        datePicker = new DatePicker();
        firstRow.add(datePicker,constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        firstRow.add(new JLabel("Duration of Consultation (Hours): "), constraints);
        constraints.gridx = 1;
        JPanel duration = new JPanel();
        Integer[] durationChoices = {1,2,3,4};
        durationBox = new JComboBox<>(durationChoices);
        durationBox.setEditable(false);
        duration.add(durationBox);
        firstRow.add(duration, constraints);

        constraints.gridy = 6;
        constraints.gridx = 0;
        firstRow.add(new JLabel("You can add a image of your skin: "), constraints);

        constraints.gridx = 1;
        uploadButton = new JButton("Choose Image");
        uploadButton.setActionCommand("upload");
        firstRow.add(uploadButton, constraints);
        constraints.gridy = 7;
        constraints.gridx = 0;
        JPanel imagePanel = new JPanel();
        image = new JLabel();
        imagePanel.add(image);
        firstRow.add(imagePanel,constraints);

        constraints.gridy = 8;
        constraints.gridx = 0;
        firstRow.add(new JLabel("Textual information: "),constraints);
        constraints.gridx = 1;

        textArea = new JTextArea(4,20 );
        textArea.setBackground(Color.LIGHT_GRAY);
        firstRow.add(textArea,constraints);

        constraints.gridy = 9;
        constraints.gridx = 0;
        submitButton = new JButton("Submit & Save Consultation");
        submitButton.setActionCommand("Submit");
        add(submitButton,constraints);

        constraints.gridy = 10;
        constraints.gridx = 0;
        savedMsg = new JLabel();
        savedMsg.setFont(font2);
        firstRow.add(savedMsg,constraints);

        constraints.gridy = 11;
        constraints.gridx = 0;
        idLabel = new JLabel();
        idLabel.setVisible(false);
        idLabel.setFont(font2);
        firstRow.add(idLabel,constraints);


        constraints.gridy = 12;
        constraints.gridx = 0;
        costField = new JLabel();
        costField.setFont(font2);
        costField.setVisible(false);
        firstRow.add(costField,constraints);

        constraints.gridy = 13;
        constraints.gridx = 0;
        consultationIdLabel = new JLabel();
        consultationIdLabel.setFont(font2);
        firstRow.add(consultationIdLabel,constraints);

        containerConstrains.gridy = 1;
        add(firstRow, containerConstrains);

        submitButton.addActionListener(this);
        uploadButton.addActionListener(this);

//        pack();

    }

    public static void main(String[] args) {
        PatientDetails patientDetails = new PatientDetails();
        patientDetails.setSize(900,800);
        patientDetails.setLocationRelativeTo(null);
        patientDetails.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        patientDetails.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String buttonLabel = event.getActionCommand();
        switch (buttonLabel){
            case "Submit" -> {

                if (fNameField.getText().isEmpty() || lNameField.getText().isEmpty() || mobNoField.getText().isEmpty() || datePicker.getDate() == null) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields !", "Consultation is Incomplete", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }

                if (!isStringOnlyAlphabet(fNameField.getText()) || !isStringOnlyAlphabet(lNameField.getText())) {
                    JOptionPane.showMessageDialog(null, "Please only use English characters when entering your name !", "Invalid Name", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }

                if (!isContactNumberValid(mobNoField.getText())) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid mobile number !", "Invalid Mobile Number", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }

                if (!isEmailValid(emailField.getText())){
                    JOptionPane.showMessageDialog(null, "Please enter a valid email address !", "Invalid Email Address", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }

                String name = fNameField.getText();
                String surname = lNameField.getText();
                int duration = (int) durationBox.getSelectedItem();
                LocalDate dateOfBirth = datePicker.getDate();
                String email = emailField.getText();

                String mobileNumber = mobNoField.getText();
                String textInfo = textArea.getText();
                char[] textChar = textInfo.toCharArray();
                for (int i=0; i<textChar.length; i++) {
                    textChar[i] += textKey;
                }
                String patientId;
                if (lastPatient == null)
                    patientId = "1";
                else patientId = String.valueOf(Integer.parseInt(lastPatient.getPatientId())+1);

                if (lastConsultation == null){
                    consultationId = "1";
                }
                else {
                    consultationId = String.valueOf(Integer.parseInt(lastConsultation.getConsultationId())+1);
                }

                Patient newPatient = new Patient(name, surname, dateOfBirth, mobileNumber, patientId, email);
                Patient tempLastPatient = lastPatient;
                lastPatient = newPatient;
                boolean isFirstConsultation = true;

                for (Patient patient : WestminsterSkinConsultationManager.patients) {
                    if (patient.getEmail().equals(email) || patient.getMobileNumber().equals(mobileNumber)){
                        newPatient = patient;
                        lastPatient = tempLastPatient;
                        isFirstConsultation = false;
                        cost = 25*duration;
                        costField.setText("Price :  £"+cost+"");
                        costField.setVisible(true);
                        break;
                    }
                }

                if (isFirstConsultation){
                    WestminsterSkinConsultationManager.patients.add(newPatient);
                    cost = 15*duration;
                    costField.setText("Price :  £"+cost+"");
                    costField.setVisible(true);
                }

                Consultation consultation = new Consultation(WestminsterSkinConsultationManager.doctors.get(ViewTable.selectedDoctor), newPatient, AddingConsultation.lookingDate, AddingConsultation.time, duration, consultationId, cost);
                WestminsterSkinConsultationManager.allConsultations.add(consultation);
                WestminsterSkinConsultationManager.consultations.get(ViewTable.selectedDoctor).add(consultation);
                lastConsultation = consultation;

                try {
                    AddingConsultation.saveFile("Doctors");
                    AddingConsultation.saveConsultations("Consultations");
                    WestminsterSkinConsultationManager.saveAllConsultations("AllConsultations");
                    saveLastPatient("LastPatient");
                    saveLastConsultation("LastConsultation");
                    savePatientList("Patients");
                    FileWriter writer = new FileWriter(".\\TextInfo\\"+consultationId+".txt");
                    writer.write(textChar);
                    writer.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                if (imageSelected) {

                    try {
                        int folderName = (Integer.parseInt(consultationId));
                        new File(".\\ConsultationImages\\"+(folderName)+"").mkdirs();

                        String destinationFilePath = ".\\ConsultationImages\\"+folderName+"\\"+newPatient.getName()+".jpg";
                        File sourceFile = new File(sourceFilePath);
                        byte[] fileData = new byte[(int) sourceFile.length()];
                        try (DataInputStream dis = new DataInputStream(new FileInputStream(sourceFile))) {
                            dis.readFully(fileData);
                        }

                        // Generate a secret key for the DES algorithm
                        secretKey = new SecretKeySpec(key.getBytes(), "DES");

                        // Create a Cipher object and initialize it for encryption
                        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

                        // Encrypt the file data
                        byte[] encryptedData = cipher.doFinal(fileData);

                        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(destinationFilePath))) {
                            dos.write(encryptedData);
                        }
                    } catch (RuntimeException | IOException | NoSuchAlgorithmException | NoSuchPaddingException |
                             InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
                        throw new RuntimeException(e);
                    }
                }

                consultationIdLabel.setText("Your consultation number : " +consultationId+ "");
                savedMsg.setText("Consultation added for Dr. "+WestminsterSkinConsultationManager.doctors.get(ViewTable.selectedDoctor).getName()+" "+WestminsterSkinConsultationManager.doctors.get(ViewTable.selectedDoctor).getSurname()+" Successfully.");
                submitButton.setVisible(false);
                idLabel.setText("Your Patient ID : "+newPatient.getPatientId()+"");
                idLabel.setVisible(true);
                JOptionPane.showMessageDialog(null, "Consultation added for Dr. "+WestminsterSkinConsultationManager.doctors.get(ViewTable.selectedDoctor).getName()+" "+WestminsterSkinConsultationManager.doctors.get(ViewTable.selectedDoctor).getSurname()+" Successfully.\n\nYour consultation number is "+consultationId+".\nRemember this number to view your consultation later.\n" +
                        "\nDate  :  "+consultation.getDate()+"\nTime  :  "+consultation.getTime()+""+"\n\nPrice :  £"+cost+"\n","Consultation added successfully.", JOptionPane.PLAIN_MESSAGE);


            }

            case "upload" -> {
                    JFileChooser fileChooser = new JFileChooser();
                    FileNameExtensionFilter filter=new FileNameExtensionFilter("images", "jpg","gif","png");
                    fileChooser.setFileFilter(filter);
                    int response = fileChooser.showSaveDialog(null);

                    if (response == JFileChooser.APPROVE_OPTION){
                        File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                        sourceFilePath = String.valueOf(file);
                        imageSelected = true;

                        ImageIcon photo = new ImageIcon(sourceFilePath);
                        Image image1 = photo.getImage(); // transform it
                        Image newImage = image1.getScaledInstance(120, 90,  Image.SCALE_SMOOTH);
                        ImageIcon newImgIcon = new ImageIcon(newImage);
                        image.setIcon(newImgIcon);
                    }
            }
        }

    }

    public static void saveLastPatient(String fileName) throws IOException {
        File file = new File(fileName);
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
        objOut.writeObject(lastPatient);
    }

    public static void loadLastPatient(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        lastPatient = (Patient) objectInputStream.readObject();

    }

    public static void savePatientList(String fileName) throws IOException {
        File file = new File(fileName);
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

        for (Patient newPatient : WestminsterSkinConsultationManager.patients){
            objOut.writeObject(newPatient);
        }
    }

    public static void saveLastConsultation(String fileName) throws IOException {
        File file = new File(fileName);
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
        objOut.writeObject(lastConsultation);
    }

    public static void loadLastConsultation(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        lastConsultation = (Consultation) objectInputStream.readObject();

    }

    public boolean isStringOnlyAlphabet(String str) {
        return ((str != null) && (!str.equals(""))
                && (str.matches("^[a-zA-Z]*$")));
    }

    public boolean isContactNumberValid(String contactNumber) {
        return ((contactNumber != null) && (!contactNumber.equals("")) && (contactNumber.matches("0\\d{9}")));
    }

    public static boolean isEmailValid(String email) {
        return email.matches( "^[A-Za-z0-9+_.-]+@(.+)$");
    }
}
