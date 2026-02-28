import java.io.*;
class Main{
    String buf = "";
    //metoda de citire din fisier
    public String citeste(String fisier)
    {int c;
        FileReader f = null; //specificatorul pentru fisier
        
        try {
//se utilizeaza pentru a prinde exceptiile de eroare la deschiderea fisierului
            f = new FileReader(fisier);
            while ((c = f.read()) != -1)
//cat timp se mai pot citi caractere din fisier
            {
                buf = buf + (char)c;
//se citesc caracterele din fisier si se pun in buf
            }
            f.close(); // se inchide fisierul
        }
        catch (FileNotFoundException e) { // exceptie : nu s-a gasit fisierul
            System.out.println("Fisierul nu a fost gasit");
        }
        catch (IOException e) //eroare la citirea din fisier
        {
            System.out.println("Eroare la citire");
        }

        return buf;
    }

    public static void main(String[] argv)
    {
        Main c = new Main(); // c este un obiect de tip clasa Main
        String text;
        text = c.citeste("in.txt"); //se apeleaza metoda de citire din fisier
        text = text.replaceAll("\\p{P}", ""); // regex pt orice unicode (adica semn de punctuatie)
        text = text.toUpperCase();
        text = text.replaceAll("  ","");
        System.out.println(text);


    }
}