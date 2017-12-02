import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GUI extends JFrame {
    private static JTextArea logArea;
    private static JTextArea processInfoArea;
    private static JTextArea simulatorInfoArea;
    private static DefaultCaret caret;
    private static JTextArea filePanel = new JTextArea();
    private JButton btnTutup  = new JButton("Tutup");
    private JButton btnTambah = new JButton("Tambah");

    private JTextField txtA = new JTextField();
    private JTextField txtB = new JTextField();
    private JTextField txtC = new JTextField();

    private JLabel lblA = new JLabel("A :");
    private JLabel lblB = new JLabel("B :");
    private JLabel lblC = new JLabel("C :");

    public GUI(){
        setTitle("WELCOME TO WEEBOO!!!");
        setSize(400,200);
        setLocation(new Point(300,200));
        setLayout(null);
        setResizable(false);

        initComponent();
        initEvent();
    }


    private void initComponent(){
        setBounds(100, 100,820,365);
        Border border = BorderFactory.createLineBorder(Color.BLACK);

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
        btnTutup.setBounds(300,130, 80,25);
        btnTambah.setBounds(300,100, 80,25);

        txtA.setBounds(100,10,100,20);
        txtB.setBounds(100,35,100,20);
        txtC.setBounds(100,65,100,20);

        lblA.setBounds(20,10,100,20);
        lblB.setBounds(20,35,100,20);
        lblC.setBounds(20,65,100,20);


        add(btnTutup);
        add(btnTambah);

        add(lblA);
        add(lblB);
        add(lblC);

        add(txtA);
        add(txtB);
        add(txtC);
    }

    private void initEvent(){

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                System.exit(1);
            }
        });

        btnTutup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnTutupClick(e);
            }
        });

        btnTambah.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnTambahClick(e);
            }
        });
    }

    private void btnTutupClick(ActionEvent evt){
        System.exit(0);
    }

    private void btnTambahClick(ActionEvent evt){
        Integer x,y,z;
        try{
            x = Integer.parseInt(txtA.getText());
            y = Integer.parseInt(txtB.getText());
            z = x + y;
            txtC.setText(z.toString());

        }catch(Exception e){
            System.out.println(e);
            JOptionPane.showMessageDialog(null,
                    e.toString(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}



//
//import javax.swing.*;
//import javax.swing.border.Border;
//import javax.swing.text.DefaultCaret;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class GUI extends JFrame {
//    private static JTextArea logArea;
//    private static JTextArea processInfoArea;
//    private static JTextArea simulatorInfoArea;
//    private static DefaultCaret caret;
//
//    public GUI() {
//        System.out.println("guiiugugugiuguigugi");
//        initialize();
//    }
//
//    public void initialize()
//    {
//        setBounds(100, 100,820,365);
//        Border border = BorderFactory.createLineBorder(Color.BLACK);
//
//        //Terminal Input Field
//        JTextField terminalInputField = new JTextField("Terminal Input Field");
//        terminalInputField.setEditable(true);
//        terminalInputField.setBounds(5, 5,300, 25);
//        terminalInputField.setBorder(border);
//
//        //log area
//        logArea = new JTextArea();
//        logArea.setEditable(false);
//
//        //adds scroll to window
//        JScrollPane logAreaScroll = new JScrollPane(logArea);
//        logAreaScroll.setBounds(5, 40, 300, 300);
//        logAreaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//        logAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        logAreaScroll.setBorder(border);
//
//        caret = (DefaultCaret) logArea.getCaret();
//        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
//
//        //processes area
//        processInfoArea = new JTextArea();
//        processInfoArea.setEditable(false);
//
//        JScrollPane processAreaScroll = new JScrollPane(processInfoArea);
//        processAreaScroll.setBounds(310, 5, 300, 335);
//        processAreaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//        processAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        processAreaScroll.setBorder(border);
//
//        //simulator area
//        simulatorInfoArea = new JTextArea();
//        simulatorInfoArea.setEditable(false);
//        simulatorInfoArea.setBounds(615, 5, 200, 335);
//        simulatorInfoArea.setBorder(border);
//
//        terminalInputField.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });
//
//    }
//
//}
