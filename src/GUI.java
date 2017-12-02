import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private static JTextArea logArea;
    private static JTextArea processInfoArea;
    private static JTextArea simulatorInfoArea;
    private static DefaultCaret caret;

    public GUI() {
        initialize();
    }

    public void initialize() {
        setTitle("OS");
        setBounds(100, 100, 820, 365);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        Border border = BorderFactory.createLineBorder(Color.BLACK);

        //Terminal Input Field
        JTextField terminalInputField = new JTextField("Terminal Input Field");
        terminalInputField.setEditable(true);
        terminalInputField.setBounds(5, 5,300, 25);
        terminalInputField.setBorder(border);

        //log area
        logArea = new JTextArea();
        logArea.setEditable(false);

        //adds scroll to window
        JScrollPane logAreaScroll = new JScrollPane(logArea);
        logAreaScroll.setBounds(5, 40, 300, 300);
        logAreaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        logAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        logAreaScroll.setBorder(border);

        caret = (DefaultCaret) logArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        //processes area
        processInfoArea = new JTextArea();
        processInfoArea.setEditable(false);

        JScrollPane processAreaScroll = new JScrollPane(processInfoArea);
        processAreaScroll.setBounds(310, 5, 300, 335);
        processAreaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        processAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        processAreaScroll.setBorder(border);

        //simulator area
        simulatorInfoArea = new JTextArea();
        simulatorInfoArea.setEditable(false);
        simulatorInfoArea.setBounds(615, 5, 200, 335);
        simulatorInfoArea.setBorder(border);

        terminalInputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

}
