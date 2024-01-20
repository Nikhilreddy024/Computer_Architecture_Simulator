import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import javax.swing.text.*;

//CLASS TO SET CHARACTER LIMIT IN JTEXTFIELD
class JTextFieldLimit extends PlainDocument {
    private int limit;
    JTextFieldLimit(int limit) {
        super();
        this.limit = limit;
    }
    JTextFieldLimit(int limit, boolean upper) {
        super();
        this.limit = limit;
    }
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null)
            return;
        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}

//MAIN UI
public class UI {

    public boolean inputInstructionValid;

    //SIMULATOR / CPU OBJECT
    Simulator simulator;
    Font f2  = new Font(Font.MONOSPACED,  Font.BOLD, 37);
    JFrame frame = new JFrame("Front Panel");

    /**--- FOLLOWING ARE THE VARIABLES OF REQUIRED ALL COMPONENTS/FUNCTIONALITIES---**/
    /** NAMING CONVENTION **/
    //example:- lblPC => "lbl" text label of PC
    //ldPC => "ld" load button of PC
    //PC => text field for PC register
    /**ALL components follow the above convention--**/

    JLabel lblInputInstruction = new JLabel("Input Instruction");
    JTextField inputInstruction = new JTextField();
    JLabel msg = new JLabel();
    JButton submit = new JButton("SUBMIT");
    JTextField PC = new JTextField();
    JLabel lblPC = new JLabel("PC");
    JButton ldPC = new JButton("LD");

    JTextField MAR = new JTextField();
    JLabel lblMAR = new JLabel("MAR");
    JButton ldMAR = new JButton("LD");

    JTextField MBR = new JTextField();
    JLabel lblMBR = new JLabel("MBR");
    JButton ldMBR = new JButton("LD");
    JTextField IR = new JTextField();
    JLabel lblIR = new JLabel("IR");
    JButton ldIR = new JButton("LD");
    JButton store = new JButton("STORE");
    JButton load = new JButton("LOAD");

    JButton ss = new JButton("SS");
    JButton run = new JButton("RUN");
    JButton ipl = new JButton("IPL");
    JTextField GPR0 = new JTextField();
    JLabel lblGPR0 = new JLabel("GPR0");
    JButton ldGPR0 = new JButton("LD");
    JTextField GPR1 = new JTextField();
    JLabel lblGPR1 = new JLabel("GPR1");
    JButton ldGPR1 = new JButton("LD");
    JTextField GPR2 = new JTextField();
    JLabel lblGPR2 = new JLabel("GPR2");
    JButton ldGPR2 = new JButton("LD");
    JTextField GPR3 = new JTextField();
    JLabel lblGPR3 = new JLabel("GPR3");
    JButton ldGPR3 = new JButton("LD");
    JTextField IX1 = new JTextField();
    JLabel lblIX1 = new JLabel("IX1");
    JButton ldIX1 = new JButton("LD");
    JTextField IX2 = new JTextField();
    JLabel lblIX2 = new JLabel("IX2");
    JButton ldIX2 = new JButton("LD");
    JTextField IX3 = new JTextField();
    JLabel lblIX3 = new JLabel("IX3");
    JButton ldIX3 = new JButton("LD");

    JLabel lblCC = new JLabel("CC");
    JTextField CC0 = new JTextField();
    JLabel lblCC0 = new JLabel("CC0");

    JTextField CC1 = new JTextField();
    JLabel lblCC1 = new JLabel("CC1");

    JTextField CC2 = new JTextField();
    JLabel lblCC2 = new JLabel("CC2");

    JTextField CC3 = new JTextField();
    JLabel lblCC3 = new JLabel("CC3");

    JTextField printer = new JTextField();
    JLabel lblPrinter = new JLabel("Printer");

    JTextField KeyBoard = new JTextField();
    JLabel lblKeyBoard = new JLabel("Keyboard");



    JButton fullReset = new JButton("FULL RESET");

    //STARTS UI (INCLUDES THE CPU OBJECT)
    public void init(){

        this.simulator = new Simulator();


        /*-----FOLLOWING SETS THE DESIGN OF EACH COMPONENT OF THE UI AS WELL AS BINDS SIMULATOR CLASS FUNCTIONS--------*/

        frame.setSize(1500, 950);


        lblInputInstruction.setBounds(230, 530, 300, 20);

        inputInstruction.setDocument(new JTextFieldLimit(16));
        inputInstruction.setBounds(100,550, 380, 40);
        inputInstruction.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        inputInstruction.setColumns(16);
        inputInstruction.setFont(f2);

        msg.setVisible(false);

        submit.setBounds(500, 550, 60, 40);
        submit.setForeground(new Color(0, 153, 51));

        //SETS INPUT INSTRUCTION DATA IN CPU ONLY WHEN IT IS BINARY OF LENGTH 16
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputInstructionValid = Pattern.matches("[01]{16}", inputInstruction.getText());
                if(inputInstructionValid){

                    char[] chars = inputInstruction.getText().toCharArray();
                    int [] temp = new int[chars.length];

                    for (int i = 0; i < temp.length; i++) {
                        temp[i] = Integer.parseInt(String.valueOf(chars[i]));
                    }

                    simulator.setInputInstruction(temp);

                    msg.setText("SUBMITTED!");
                    msg.setBounds(220, 560, 400, 90 );
                    msg.setForeground(new Color(0, 153, 204));
                    msg.setVisible(true);

                }
                else {
                    msg.setText("Enter Input which is binary of length 16");
                    msg.setBounds(170, 560, 400, 90 );
                    msg.setForeground(Color.red);
                    msg.setVisible(true);
                }
            }
        });

        PC.setDocument(new JTextFieldLimit(12));
        PC.setEditable(false);
        PC.setBounds(700,50, 300, 40);
        PC.setColumns(12);
        PC.setFont(f2);

        lblPC.setBounds(675, 50, 20, 40);

        ldPC.setBounds(1010, 50, 30, 40);
        ldPC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PC.setText(simulator.loadPC());
                resetInputInstruction();
            }
        });

        MAR.setDocument(new JTextFieldLimit(12));
        MAR.setEditable(false);
        MAR.setBounds(700,100, 300, 40);
        MAR.setColumns(12);
        MAR.setFont(f2);

        lblMAR.setBounds(670, 100, 40, 40);

        ldMAR.setBounds(1010, 100, 30, 40);
        ldMAR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MAR.setText(simulator.loadMAR());
                resetInputInstruction();
            }
        });

        MBR.setDocument(new JTextFieldLimit(16));
        MBR.setEditable(false);
        MBR.setBounds(700,150, 370, 40);
        MBR.setColumns(16);
        MBR.setFont(f2);

        lblMBR.setBounds(670, 150, 40, 40);

        ldMBR.setBounds(1070, 150, 30, 40);
        ldMBR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MBR.setText(simulator.loadMBR());
                resetInputInstruction();
            }
        });

        IR.setDocument(new JTextFieldLimit(16));
        IR.setEditable(false);
        IR.setBounds(700,200, 370, 40);
        IR.setColumns(16);
        IR.setFont(f2);

        lblIR.setBounds(670, 200, 40, 40);

        ldIR.setBounds(1070, 200, 30, 40);
        ldIR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IR.setText(simulator.loadIR());
                resetInputInstruction();
            }
        });

        JFrame operationsPanel = new JFrame();
        operationsPanel.setLayout(new FlowLayout());

        store.setBounds(800, 340, 60, 40);
        store.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulator.store();
                clearUI();
            }
        });

        load.setBounds(880, 340, 60, 40);
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulator.load();
                updateUI(simulator.getUiValues());
            }
        });

        ss.setBounds(800, 400, 60, 40);
        ss.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulator.singleStep();
                updateUI(simulator.getUiValues());
            }
        });

        run.setBounds(880, 400, 60, 40);
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while (true){
                    simulator.singleStep();
                    updateUI(simulator.getUiValues());
                    if(simulator.getIR() == 0){
                        System.out.println("Machine Halted!!");
                        break;
                    }
                }

            }
        });

        ipl.setBounds(840, 500, 60, 60);
        ipl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulator.loadIPL();
                updateUI(simulator.getUiValues());
                resetInputInstruction();
            }
        });


        GPR0.setDocument(new JTextFieldLimit(16));
        GPR0.setEditable(false);
        GPR0.setBounds(50,50, 370, 40);
        GPR0.setColumns(16);
        GPR0.setFont(f2);

        lblGPR0.setBounds(10, 50, 40, 40);

        ldGPR0.setBounds(420, 50, 30, 40);
        ldGPR0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GPR0.setText(simulator.loadGPR0());
                resetInputInstruction();
            }
        });


        GPR1.setDocument(new JTextFieldLimit(16));
        GPR1.setEditable(false);
        GPR1.setBounds(50,100, 370, 40);
        GPR1.setColumns(16);
        GPR1.setFont(f2);

        lblGPR1.setBounds(10, 100, 40, 40);

        ldGPR1.setBounds(420, 100, 30, 40);
        ldGPR1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GPR1.setText(simulator.loadGPR1());
                resetInputInstruction();
            }
        });

        GPR2.setDocument(new JTextFieldLimit(16));
        GPR2.setEditable(false);
        GPR2.setBounds(50,150, 370, 40);
        GPR2.setColumns(16);
        GPR2.setFont(f2);

        lblGPR2.setBounds(10, 150, 40, 40);

        ldGPR2.setBounds(420, 150, 30, 40);
        ldGPR2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GPR2.setText(simulator.loadGPR2());
                resetInputInstruction();
            }
        });

        GPR3.setDocument(new JTextFieldLimit(16));
        GPR3.setEditable(false);
        GPR3.setBounds(50,200, 370, 40);
        GPR3.setColumns(16);
        GPR3.setFont(f2);

        lblGPR3.setBounds(10, 200, 40, 40);

        ldGPR3.setBounds(420, 200, 30, 40);
        ldGPR3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GPR3.setText(simulator.loadGPR3());
                resetInputInstruction();
            }
        });

        IX1.setDocument(new JTextFieldLimit(16));
        IX1.setEditable(false);
        IX1.setBounds(50,300, 370, 40);
        IX1.setColumns(16);
        IX1.setFont(f2);

        lblIX1.setBounds(10, 300, 40, 40);

        ldIX1.setBounds(420, 300, 30, 40);
        ldIX1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IX1.setText(simulator.loadIX1());
                resetInputInstruction();
            }
        });

        IX2.setDocument(new JTextFieldLimit(16));
        IX2.setEditable(false);
        IX2.setBounds(50,350, 370, 40);
        IX2.setColumns(16);
        IX2.setFont(f2);

        lblIX2.setBounds(10, 350, 40, 40);
        ldIX2.setBounds(420, 350, 30, 40);
        ldIX2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IX2.setText(simulator.loadIX2());
                resetInputInstruction();
            }
        });

        IX3.setDocument(new JTextFieldLimit(16));
        IX3.setEditable(false);
        IX3.setBounds(50,400, 370, 40);
        IX3.setColumns(16);
        IX3.setFont(f2);

        lblIX3.setBounds(10, 400, 40, 40);
        ldIX3.setBounds(420, 400, 30, 40);
        ldIX3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IX3.setText(simulator.loadIX3());
                resetInputInstruction();
            }
        });

        lblCC.setBounds(670, 250, 40, 40);

        CC0.setDocument(new JTextFieldLimit(1));
        CC0.setEditable(false);
        CC0.setBounds(700,250, 40, 40);
        CC0.setColumns(3);
        CC0.setFont(f2);
        lblCC0.setBounds(710, 292, 30, 12);

        CC1.setDocument(new JTextFieldLimit(1));
        CC1.setEditable(false);
        CC1.setBounds(750,250, 40, 40);
        CC1.setColumns(3);
        CC1.setFont(f2);
        lblCC1.setBounds(760, 292, 30, 12);

        CC2.setDocument(new JTextFieldLimit(1));
        CC2.setEditable(false);
        CC2.setBounds(800,250, 40, 40);
        CC2.setColumns(3);
        CC2.setFont(f2);
        lblCC2.setBounds(810, 292, 30, 12);

        CC3.setDocument(new JTextFieldLimit(1));
        CC3.setEditable(false);
        CC3.setBounds(850,250, 40, 40);
        CC3.setColumns(3);
        CC3.setFont(f2);
        lblCC3.setBounds(860, 292, 30, 12);

        printer.setDocument(new JTextFieldLimit(40));
        printer.setEditable(false);
        printer.setBounds(1000, 340, 250, 150);
        printer.setColumns(10);
        printer.setFont(f2);
        lblPrinter.setBounds(1090, 480, 90, 40);

        KeyBoard.setDocument(new JTextFieldLimit(100));
        printer.setEditable(true);
        KeyBoard.setBounds(1000, 520, 250, 150);
        KeyBoard.setColumns(10);
        KeyBoard.setFont(f2);
        lblKeyBoard.setBounds(1090, 660, 90, 40);

        KeyBoard.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                simulator.KeyBoardKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                simulator.KeyBoardKeyReleased(evt, KeyBoard);
            }
        });


        fullReset.setBounds(840, 590, 90, 60);
        fullReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fullReset();
            }
        });


        frame.add(inputInstruction); frame.add(lblInputInstruction); frame.add(submit); frame.add(msg);

        frame.add(PC); frame.add(lblPC); frame.add(ldPC);
        frame.add(MAR); frame.add(lblMAR); frame.add(ldMAR);
        frame.add(MBR); frame.add(lblMBR); frame.add(ldMBR);
        frame.add(IR); frame.add(lblIR); frame.add(ldIR);

        frame.add(store); frame.add(load); frame.add(ss); frame.add(run); frame.add(ipl); frame.add(fullReset);

        frame.add(GPR0); frame.add(lblGPR0); frame.add(ldGPR0);
        frame.add(GPR1); frame.add(lblGPR1); frame.add(ldGPR1);
        frame.add(GPR2); frame.add(lblGPR2); frame.add(ldGPR2);
        frame.add(GPR3); frame.add(lblGPR3); frame.add(ldGPR3);

        frame.add(IX1); frame.add(lblIX1); frame.add(ldIX1);
        frame.add(IX2); frame.add(lblIX2); frame.add(ldIX2);
        frame.add(IX3); frame.add(lblIX3); frame.add(ldIX3);

        frame.add(lblCC);
        frame.add(CC0); frame.add(lblCC0);
        frame.add(CC1); frame.add(lblCC1);
        frame.add(CC2); frame.add(lblCC2);
        frame.add(CC3); frame.add(lblCC3);

        frame.add(printer);
        frame.add(lblPrinter);

        frame.add(KeyBoard);
        frame.add(lblKeyBoard);


        frame.setLayout(null);
        frame.setVisible(true);
    }


    //FULL RESET OF BOTH UI AND CPU VALUES
    public void fullReset(){
        clearUI();
        this.simulator.reset();
        System.out.println("===========ALL CLEARED!==========");
    }

    //CLEARS ONLY TEXTFIELD VALUE
    public  void clearUI(){
        PC.setText("");
        MAR.setText("");
        MBR.setText("");
        IR.setText("");

        GPR0.setText("");
        GPR1.setText("");
        GPR2.setText("");
        GPR3.setText("");

        IX1.setText("");
        IX2.setText("");
        IX3.setText("");
    }

    //UPDATES TEXTFIELDS USING VALUES RETRIEVED FROM SIMULATOR
    public void updateUI(HashMap uiValues){

        System.out.println(uiValues.get("PC"));
        PC.setText((String) uiValues.get("PC"));
        MAR.setText((String) uiValues.get("MAR"));
        MBR.setText((String) uiValues.get("MBR"));
        IR.setText((String) uiValues.get("IR"));

        GPR0.setText((String) uiValues.get("GPR0"));
        GPR1.setText((String) uiValues.get("GPR1"));
        GPR2.setText((String) uiValues.get("GPR2"));
        GPR3.setText((String) uiValues.get("GPR3"));

        IX1.setText((String) uiValues.get("IX1"));
        IX2.setText((String) uiValues.get("IX2"));
        IX3.setText((String) uiValues.get("IX3"));

        CC0.setText((String) uiValues.get("CC0"));
        CC1.setText((String) uiValues.get("CC1"));
        CC2.setText((String) uiValues.get("CC2"));
        CC3.setText((String) uiValues.get("CC3"));

        printer.setText((String) uiValues.get("printer"));

    }

    //RESETS INPUT INSTRUCTION AS WELL AS VALIDATION MESSAGE
    private void resetInputInstruction(){
        inputInstruction.setText("");
        msg.setVisible(false);
    }



}
