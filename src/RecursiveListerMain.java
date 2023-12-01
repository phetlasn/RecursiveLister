import javax.swing.*;
import java.awt.*;
import java.io.File;

public class RecursiveListerMain extends JFrame {
    JPanel mainPanel, titlePanel, displayPanel, buttonPanel;
    JLabel titleLabel;
    JScrollPane scrollPane;
    JTextArea displayTextArea;
    JButton quitButton, searchButton;

    public RecursiveListerMain() {
        setTitle("Recursive Directory Lister");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(3*(screenWidth / 4), 3*(screenHeight / 4));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        add(mainPanel);
        createTitlePanel();
        createDisplayPanel();
        createButtonPanel();

        setVisible(true);
        setResizable(false);
    }

    private void createTitlePanel() {
        titlePanel = new JPanel();
        titleLabel = new JLabel("Recursive Directory Lister");
        titleLabel.setFont(new Font("Monospaced", Font.PLAIN, 48));
        titleLabel.setVerticalTextPosition(JLabel.BOTTOM);
        titleLabel.setHorizontalTextPosition(JLabel.CENTER);
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);
    }

    private void createDisplayPanel() {
        displayPanel = new JPanel();
        displayTextArea = new JTextArea(20, 60);
        scrollPane = new JScrollPane(displayTextArea);
        displayTextArea.setEditable(false);
        displayTextArea.setFont(new Font("Dialog", Font.PLAIN, 16));
        displayPanel.add(scrollPane);
        mainPanel.add(displayPanel, BorderLayout.CENTER);
    }

    private void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        searchButton = new JButton("Search");
        searchButton.setFont(new Font("Times New Roman", Font.PLAIN, 48));
        quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Times New Roman", Font.PLAIN, 48));
        buttonPanel.add(searchButton);
        buttonPanel.add(quitButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        quitButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null, "Do You Want To Exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            } else {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }});
        searchButton.addActionListener(e -> search());
    }

    private void search() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose A Directory: ");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        File workingDirectory = new File(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(workingDirectory);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File chosenDir = chooser.getSelectedFile();
            displayTextArea.setText("Chosen Directory:   " + chosenDir + "\n\n");
            displayTextArea.append("Sub-Directories, Files From Sub-Directories, and Files From Chosen Directory Are Shown Below." + "\n\n\n");
            listNames(chosenDir);
        } else
            displayTextArea.append("File Not Found!");
    }

    private void listNames(File f) {
        File[] Names = f.listFiles();
        if (Names != null) {
            for (File n : Names) {
                displayTextArea.append(n + "\n\n");

                if (n.isDirectory()) {
                    listNames(n);
                }
            }
        }
    }
}
