import java.io.StringReader;

public class stiva
{
    static public class Element
    {
        Element next;
        String data;

        public Element(String data)
        {
            this.data = data;
        }
    }


    Element varf;
    public stiva()
    {
        varf = null;

    }

    public void push(String data)
    {
        Element nou = new Element(data);
        nou.next = varf;
        varf = nou;
    }

    public void pop()
    {
        varf = varf.next;
    }

    public String top()
    {
        if(!isEmpty())
        {
            return varf.data;

        }
        else
        {
            return null;
        }

    }

    public boolean isEmpty()
    {
        return varf == null;

    }








}
