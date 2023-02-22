import java.util.ArrayList;

public class Buffer {
    private ArrayList<Producto> buff;
    private int tamanio;

    public Buffer(int tamanio) {
        this.tamanio = tamanio;
        this.buff = new ArrayList<Producto>();
    }
    
    public Integer getTamanio() {
        return buff.size();
    }
    public synchronized Boolean almacenarNaranja(Producto p) {
        //System.out.println("INICIO ALMACENAR NARANJA - Buffer naranja es " + buff.size());
        if (buff.size() == tamanio) {
            //System.out.println("BUFFER LLENO - size: " + buff.size());
            return false;
        }
        else
        {
            buff.add(p);
            //System.out.println("FIN NARANJA - Buffer naranja es " + buff.size());
            return true;
        }
    }

    public synchronized void almacenarAzul(Producto p) {
        //System.out.println("INICIO ALMACENAR AZUL - Buffer azul es " + buff.size());
        while (buff.size() == tamanio) {
            try {
                //System.out.println("BUFFER LLENO - size: " + buff.size());
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        buff.add(p);
        notify();
        //System.out.println("FIN ALMACENAR AZUL - Buffer azul es " + buff.size());
    }

    public synchronized void almacenarRojo(Producto p) {
        buff.add(p);
        //System.out.println("FIN ALMACENAR ROJO - Buffer rojo es " + buff.size());
    }

    public synchronized Producto extraerRojo() {

        //System.out.println("EXTRAER INICIO ROJO - size del buffer es: " + buff.size());
            if (buff.size() == 0)
            {
                return null;
            }
            else
            {
                Producto i = buff.remove(0);
                //System.out.println("Buffer no vacio, Producto " + i.getTipo() + " " + i.getId() + " extraido");
                // System.out.println("EXTRAER FIN - size del buffer es: " + buff.size());
                return i ;
            }
    }

    public synchronized Producto extraerNaranja() {
        if (buff.size() == 0)
        {
                return null;
        }
        else
        {
            Producto p = buff.remove(0);
            //System.out.println("Buffer no vacio, Producto " + p.getTipo() + " " + p.getId() + " extraido");
            //System.out.println("EXTRAER FIN - size del buffer es: " + buff.size());
            return p;
        }
    }

    public synchronized Producto extraerAzul() {
        //System.out.println("EXTRAER INICIO - size del buffer es: " + buff.size());
            while (buff.size() == 0)
            {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Producto i = buff.remove(0);
            //System.out.println("Buffer no vacio, Producto " + i.getTipo() + " " + i.getId() + " extraido");
            notify();
            //System.out.println("EXTRAER FIN - size del buffer es: " + buff.size());
            return i ;
    }

}
