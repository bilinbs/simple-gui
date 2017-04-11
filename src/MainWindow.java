import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class MainWindow {

    private JFrame frame;
    private JTextField textField;
    private JTextArea textArea;
    private File selectedFile;
    
    private String outputPath = "D:/outputFile";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainWindow window = new MainWindow();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MainWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 500, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setLayout(null);
        
        textField = new JTextField();
        textField.setBounds(10, 249, 266, 20);
        frame.getContentPane().add(textField);
        textField.setColumns(10);
        
        JButton btnNewButton = new JButton("Browse");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(frame);
                selectedFile = fileChooser.getSelectedFile();
                textField.setText(selectedFile.getAbsolutePath());
            }
        });
        btnNewButton.setBounds(287, 248, 89, 23);
        frame.getContentPane().add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("Tag");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    saveText(outputPath);
                } catch (IOException e1) {
                    Alert.showAlert("Error while writing to file " + e1.getMessage());
                }
            }
        });
        btnNewButton_1.setBounds(385, 248, 89, 23);
        frame.getContentPane().add(btnNewButton_1);
        
        textArea = new JTextArea();
        textArea.setBounds(10, 62, 464, 176);
        frame.getContentPane().add(textArea);
        
        JLabel lblEnterTheText = new JLabel("Enter the text here");
        lblEnterTheText.setBounds(10, 37, 113, 14);
        frame.getContentPane().add(lblEnterTheText);
        
        
    }

    protected void saveText(String path) throws IOException {
        String text = textArea.getText();
        
        if(text!=null && !text.isEmpty()){
            Files.write(Paths.get(path), text.getBytes());
        } else if(selectedFile!=null){
            Files.copy(selectedFile.toPath(), Paths.get(outputPath), StandardCopyOption.REPLACE_EXISTING);
        }
        Alert.showAlert("Successfully created file");
    }
}
