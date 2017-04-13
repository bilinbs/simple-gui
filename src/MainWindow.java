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
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class MainWindow {

    private JFrame frame;
    private JTextField filePath;
    private JTextArea textArea;
    private File selectedFile;
    JButton browseButton;
    JButton tagButton;
    JLabel lblEnterTheText;
    
    private String outputPath = Messages.getString("MainWindow.0"); //$NON-NLS-1$

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
        
        filePath = new JTextField();
        filePath.setBounds(10, 249, 266, 20);
        frame.getContentPane().add(filePath);
        filePath.setColumns(10);
        
        browseButton = new JButton("Browse"); //$NON-NLS-1$
        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(frame);
                selectedFile = fileChooser.getSelectedFile();
                filePath.setText(selectedFile.getAbsolutePath());
            }
        });
        browseButton.setBounds(287, 248, 89, 23);
        frame.getContentPane().add(browseButton);
        
        tagButton= new JButton("Tag"); //$NON-NLS-1$
        tagButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    saveText(outputPath);
                } catch (IOException e1) {
                    Alert.showAlert("Error while writing to file " + e1.getMessage()); //$NON-NLS-1$
                }
            }
        });
        tagButton.setBounds(385, 248, 89, 23);
        frame.getContentPane().add(tagButton);
        
        textArea = new JTextArea();
        textArea.setBounds(10, 62, 464, 176);
        frame.getContentPane().add(textArea);
        
        lblEnterTheText = new JLabel("Enter the text here"); //$NON-NLS-1$
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
        Alert.showAlert("Successfully created file"); //$NON-NLS-1$
        Process proc = Runtime.getRuntime().exec("java "); //$NON-NLS-1$
        try {
            proc.waitFor();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        browseButton.setEnabled(false);
        tagButton.setEnabled(false);
        filePath.setEditable(false);
        filePath.setEnabled(false);
        String output = getOutputFromFile();
        lblEnterTheText.setText("Tagged output"); //$NON-NLS-1$
        textArea.setText(output);
    }

    private String getOutputFromFile() {
        List<String> lines;
        try {
             lines =  Files.readAllLines(Paths.get((Messages.getString("MainWindow.8")))); //$NON-NLS-1$
             StringBuffer sb = new StringBuffer();
             for(String line : lines){
                 sb.append(line).append("\n"); //$NON-NLS-1$
             }
             return sb.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ""; //$NON-NLS-1$
    }
}
