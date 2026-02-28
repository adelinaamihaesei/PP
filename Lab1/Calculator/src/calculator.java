import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class calculator extends JFrame {
    JButton digits[] = {
            new JButton(" 0 "),
            new JButton(" 1 "),
            new JButton(" 2 "),
            new JButton(" 3 "),
            new JButton(" 4 "),
            new JButton(" 5 "),
            new JButton(" 6 "),
            new JButton(" 7 "),
            new JButton(" 8 "),
            new JButton(" 9 ")
    };

    JButton operators[] = {
            new JButton(" + "),
            new JButton(" - "),
            new JButton(" * "),
            new JButton(" / "),
            new JButton(" = "),
            new JButton(" C "),
            new JButton("("),
            new JButton(")")
    };

    String oper_values[] = {"+", "-", "*", "/", "=", "", "(", ")"};

    String value;
    char operator;

    JTextArea area = new JTextArea(3, 5);

    public static void main(String[] args) throws IOException
    {
        calculator calculator = new calculator();
        calculator.setSize(230, 230);
        calculator.setTitle(" Java-Calc, PP Lab1 ");
        calculator.setResizable(false);
        calculator.setVisible(true);
        calculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



    }


    public calculator() {
        add(new JScrollPane(area), BorderLayout.NORTH);
        JPanel buttonpanel = new JPanel();
        buttonpanel.setLayout(new FlowLayout());

        for (int i=0;i<10;i++)
            buttonpanel.add(digits[i]);

        for (int i=0;i<8;i++)
            buttonpanel.add(operators[i]);

        add(buttonpanel, BorderLayout.CENTER);
        area.setForeground(Color.BLACK);
        area.setBackground(Color.WHITE);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);

        for (int i=0;i<10;i++) {
            int finalI = i;
            digits[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    area.append(Integer.toString(finalI));
                }
            });
        }

        for (int i=0;i<8;i++){
            int finalI = i;
            operators[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if (finalI == 5)
                        area.setText("");
                    else
                    if (finalI == 4) {
                        String expresie;
                        String expr_poloneza;
                        try {
                            expresie = area.getText();
                            expr_poloneza = String.valueOf(Poloneza(expresie));
                            area.setText(Calcul(expr_poloneza));
                        } catch (Exception e) {
                            area.setText(" !!!Probleme!!! ");
                        }
                    }
                    else {
                        area.append(oper_values[finalI]);
                        operator = oper_values[finalI].charAt(0);
                    }
                }
            });
        }
    }





//punem prioritate pentru inmultire si impartire returnnand un int mai mare
        public static int gradSemn(char semn)
        {
            switch (semn)
            {
                case '*': case'/':     return 2;
                case '+': case '-': return 1;
                default: return -1;

            }
        }

        //construim expresia data in forma poloneza (prefixata)
    //punem numerele in string, apoi cand gasim un semn, daca e operator ii verificam gradul,
    // daca e grad mai mare fata de ce am in stiva, il pun in stiva
    //altfel, cat timp ce am eu e de grad mai mic, le scot pe celelalte cu grad mai mare si le pun in string si pun la final in stiva ce am


        public static String Poloneza(String expresie)
        {
            stiva stack = new stiva();
//        stack.push("sv");
//        stack.push("vsv");
//        System.out.println(stack.top());
//        stack.pop();
//        System.out.println(stack.top());

//            String expresie = "2*(3+4)"
            String expresie_rev= new StringBuilder(expresie).reverse().toString();

            char c[] = expresie_rev.toCharArray();
            StringBuilder pref = new StringBuilder();

            for (int i = 0; i < c.length; i++)
            {
                if (Character.isDigit(c[i]))
                {

                    pref.append(c[i]);
                }
                else if (c[i] == ')')
                {
                    stack.push(")");
                }
                else if (c[i] == '(')
                {
                    while (!stack.isEmpty() && !stack.top().equals(")")) {
                        pref.append(stack.top());
                        stack.pop();
                    }
                    stack.pop();
                }
                else
                {

                    while (!stack.isEmpty() && gradSemn(stack.top().charAt(0)) >= gradSemn(c[i]))
                    {
                        pref.append(stack.top());
                        stack.pop();
                    }
                    stack.push(String.valueOf(c[i]));


                }
            }

            while (!stack.isEmpty())
            {
                pref.append(stack.top());
                stack.pop();
            }
            //inversam rez final
            return pref.reverse().toString();
        }


        public static String Operatie(char operator, double a, double b)
        {
            String rezultat = new String();
            switch(operator)
            {
                case '+':
                    rezultat = String.valueOf(a+b);
                    break;
                case '-':
                    rezultat = String.valueOf(a-b);
                    break;
                case '*':
                    rezultat = String.valueOf(a*b);
                    break;
                case '/':
                    rezultat = String.valueOf(a/b);
                    break;


            }
            return rezultat;
        }

//punem in stiva operanzii(numerele) pana cand gasim un semn.
// daca gasim semn scoatem numere, facem operatia si punem reultatul inapoi in stiva ca string
// parcurgem expresia de la stanga la dreapta (daca voiam inr=vers, pun rezultat.reverse())
        public static String Calcul(String expresie)
        {
            char c[] = expresie.toCharArray();
            stiva stack = new stiva();
            String rezultat = null;
            for(int i =expresie.length()-1; i>=0; i--)
            {
                if(Character.isDigit(c[i]))
                {
                    stack.push(String.valueOf(c[i]));

                }
                else
                {
                    double a, b;
                    a = Double.parseDouble(stack.top());
                    stack.pop();
                    b = Double.parseDouble(stack.top());
                    stack.pop();
                    rezultat = Operatie(c[i], a, b);
                    stack.push(rezultat);

                }
            }
            return rezultat;
        }

    }




