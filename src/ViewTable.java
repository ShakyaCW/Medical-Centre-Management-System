import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewTable extends JFrame implements ActionListener{
    JTable table;

    public static int selectedDoctor;
    String[] columnNames = {"First Name", "Surname", "Date of Birth", "Phone Number", "Med Lic No", "Specialisation"};
    String[][] docDetails = new String[WestminsterSkinConsultationManager.doctors.size()][6];
    Font  font1  = new Font("Verdana", Font.PLAIN, 12);
    Font font2 = new Font("Ariel", Font.BOLD, 30);

    public ViewTable() {

        int count = 0;
        for (Doctor newDoctor : WestminsterSkinConsultationManager.doctors) {
            String[] details = new String[6];
            details[0] = newDoctor.getName();
            details[1] = newDoctor.getSurname();
            details[2] = String.valueOf(newDoctor.getDateOfBirth());
            details[3] = newDoctor.getMobileNumber();
            details[4] = newDoctor.getMedicalLicenceNumber();
            details[5] = String.valueOf(newDoctor.getSpecialisation());
            docDetails[count] = details;
            count++;
        }
        Container container = getContentPane();
        container.setLayout(new FlowLayout());

        JLabel heading = new JLabel("List of Doctors");
        heading.setFont(font2);
        add(heading);

        table = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel(docDetails, columnNames){

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        table.setFont(font1);
        table.setModel(tableModel);
        table.setRowHeight(40);


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900,350));
        table.setGridColor(Color.BLACK);
        add(scrollPane);

        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new GridLayout(3,1));
        JPanel firstRow = new JPanel();
        firstRow.setPreferredSize(new Dimension(1100,100));

        for (int i=0; i<WestminsterSkinConsultationManager.doctors.size(); i++){
            JButton button = new JButton("Add Consultation for Dr. "+WestminsterSkinConsultationManager.doctors.get(i).getName().toUpperCase()+" "+WestminsterSkinConsultationManager.doctors.get(i).getSurname().toUpperCase()+"");
            button.setActionCommand(""+i+"");
            firstRow.add(button);
            button.addActionListener(this);
        }
        lowerPanel.add(firstRow);

        JPanel secondRow = new JPanel();
        secondRow.setBorder(new EmptyBorder(30, 10, 30, 10));
        JButton sort = new JButton("Sort Alphabetically");
        secondRow.add(sort);
        sort.addActionListener(this);

        JButton viewUnsorted = new JButton("View Original");
        secondRow.add(viewUnsorted);
        viewUnsorted.addActionListener(this);
        lowerPanel.add(secondRow);

        JPanel thirdPanel = new JPanel();
        JButton viewConsultation = new JButton("View Saved Consultation");
        viewConsultation.setActionCommand("viewConsultation");
        viewConsultation.setVerticalAlignment(JButton.CENTER);
        thirdPanel.add(viewConsultation);
        viewConsultation.addActionListener(this);
        lowerPanel.add(thirdPanel);

        add(lowerPanel);

    }

    public static void main(String[] args) {

        ViewTable viewTable = new ViewTable();
        viewTable.setSize(1100,700);
        viewTable.setLocationRelativeTo(null);
        viewTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewTable.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String buttonLabel = event.getActionCommand();
                switch (buttonLabel) {
                    case "Sort Alphabetically" -> {
                        Doctor[] sortedDoctorList = new Doctor[WestminsterSkinConsultationManager.doctors.size()];
                        for (Doctor newDoctor : WestminsterSkinConsultationManager.doctors) {
                            sortedDoctorList[WestminsterSkinConsultationManager.doctors.indexOf(newDoctor)] = newDoctor;
                        }

                        for (int x=0; x<sortedDoctorList.length; x++) {
                            for (int y=x+1; y<sortedDoctorList.length; y++) {
                                if (sortedDoctorList[x].getSurname().compareToIgnoreCase(sortedDoctorList[y].getSurname()) > 0) {
                                    Doctor temp = sortedDoctorList[x];
                                    sortedDoctorList[x] = sortedDoctorList[y];
                                    sortedDoctorList[y] = temp;
                                }
                            }
                        }
                        String[] columnNames = {"First Name", "Surname", "Date of Birth", "Phone Number", "Med Lic No", "Specialisation"};
                        String[][] docDetails = new String[WestminsterSkinConsultationManager.doctors.size()][6];
                        int count = 0;
                        for (Doctor newDoctor : sortedDoctorList) {
                            String[] details = new String[6];
                            details[0] = newDoctor.getName();
                            details[1] = newDoctor.getSurname();
                            details[2] = String.valueOf(newDoctor.getDateOfBirth());
                            details[3] = newDoctor.getMobileNumber();
                            details[4] = newDoctor.getMedicalLicenceNumber();
                            details[5] = String.valueOf(newDoctor.getSpecialisation());
                            docDetails[count] = details;
                            count++;
                        }
                        TableModel tableModel = new DefaultTableModel(docDetails, columnNames){

                            @Override
                            public boolean isCellEditable(int row, int column) {
                                //all cells false
                                return false;
                            }
                        };
                        table.setModel(tableModel);
                    }
                    case "View Original" -> {
                        TableModel original = new DefaultTableModel(docDetails, columnNames){

                            @Override
                            public boolean isCellEditable(int row, int column) {
                                //all cells false
                                return false;
                            }
                        };
                        table.setModel(original);
                    }
                    case "viewConsultation" -> ViewConsultation.main(null);

                    case "0" -> {
                        selectedDoctor = 0;
                        AddingConsultation.main(null);

                    }
                    case "1" -> {
                        selectedDoctor = 1;
                        AddingConsultation.main(null);

                    }
                    case "2" -> {
                        selectedDoctor = 2;
                        AddingConsultation.main(null);
                    }
                    case "3" -> {
                        selectedDoctor = 3;
                        AddingConsultation.main(null);
                    }
                    case "4" -> {
                        selectedDoctor = 4;
                        AddingConsultation.main(null);
                    }
                    case "5" -> {
                        selectedDoctor = 5;
                        AddingConsultation.main(null);
                    }
                    case "6" -> {
                        selectedDoctor = 6;
                        AddingConsultation.main(null);
                    }
                    case "7" -> {
                        selectedDoctor = 7;
                        AddingConsultation.main(null);
                    }
                    case "8" -> {
                        selectedDoctor = 8;
                        AddingConsultation.main(null);
                    }
                    case "9" -> {
                        selectedDoctor = 9;
                        AddingConsultation.main(null);
                    }
                }

    }


}
