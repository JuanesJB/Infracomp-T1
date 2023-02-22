import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Proceso extends Thread {
    private int id = 0;
    private Buffer bufferEtapa1;
    private Buffer bufferEtapa2;
    private Buffer bufferEtapaFinal;
    private String tipo;
    static private int cantidadProductos;
    static private ArrayList<Integer> pFinal = new ArrayList<Integer>();
    private Integer barrier;
    static private Identificador identificador = new Identificador();

    public Proceso(int id, String tipo, int cantidadProductos, Buffer bufferEtapa1, Buffer bufferEtapa2, Buffer bufferEtapaFinal, Integer barrier) {
        this.id = id;
        this.tipo = tipo;
        this.cantidadProductos = cantidadProductos;
        this.bufferEtapa1 = bufferEtapa1;
        this.bufferEtapa2 = bufferEtapa2;
        this.bufferEtapaFinal = bufferEtapaFinal;
        this.barrier = barrier;
    }

    private void etapa1() {
        for (Integer i = 0; i < cantidadProductos; i++) {
            Producto productoNuevo = new Producto(identificador.getId(), tipo);
            if (tipo == "naranja") {
                while (!bufferEtapa1.almacenarNaranja(productoNuevo)) 
                {
                    Thread.yield();
                }
            } else if (tipo == "azul") 
            {
                bufferEtapa1.almacenarAzul(productoNuevo);
            }
            else 
            {
                System.out.println("ERROR: Tipo de producto no reconocido");
            }
            System.out.println("Producto " + productoNuevo.getTipo() + " " + productoNuevo.getId() + " creado");
        }
    }

    private void etapa2()
    {
        Integer temp = cantidadProductos;
        while (temp > 0)
        {
            if (tipo == "naranja")
            {
                Producto productoExtraido;
                while((productoExtraido = bufferEtapa1.extraerNaranja()) == null)
                {
                    Thread.yield();
                }
                try {
                    sleep(1000);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                bufferEtapa2.almacenarNaranja(productoExtraido);
                //System.out.println("Producto " + productoExtraido.getTipo() + " " + productoExtraido.getId() + " extraido");
            }
            else if (tipo == "azul")
            {
                Producto productoExtraido;
                productoExtraido = bufferEtapa1.extraerAzul();
                try {
                    int numeroAleatorio = (int)(Math.random() * (500 - 50 + 1) + 50);
                    sleep(numeroAleatorio);
                } catch (Exception e) {
                    
                }
                bufferEtapa2.almacenarAzul(productoExtraido);
            }
            else
            {
                System.out.println("ERROR: Tipo de producto no reconocido");
            }
            temp--;
        }
    }

    private void etapa3()
    {
        Integer temp = cantidadProductos;
        int numeroAleatorio = (int)(Math.random() * (500 - 50 + 1) + 50);
        while (temp > 0)
        {
            Producto productoExtraido;
            while((productoExtraido = bufferEtapa2.extraerRojo()) == null)
            {
                //Nada espera activa
            }
            try {
                sleep(numeroAleatorio);
            } catch (Exception e) {
            }
            bufferEtapaFinal.almacenarRojo(productoExtraido);

            System.out.println("El producto " + productoExtraido.getTipo() + " " + productoExtraido.getId() + " ha sido transformado en " + numeroAleatorio + " milisegundos");
            temp--;
        }
    }

    public void etapaFinal()
    {
        Integer temp = cantidadProductos * barrier;
        while (temp > 0)
        {
            Producto productoExtraido;
            while((productoExtraido = bufferEtapaFinal.extraerRojo()) == null)
            {
                //Nada espera activa
            }
            try {
                sleep(1000);
            } catch (Exception e) {
            }
            pFinal.add(productoExtraido.getId());
            temp--;
        }
        // try {
        //     barrier.await();
        // } catch (Exception e) {
        //     // TODO: handle exception
        // }
        System.out.println("temp = " + temp);
        System.out.println("pFinal.size() = " + pFinal.size());
    }

    public void run() {
        if(id == 0)
        {
            System.out.println("Ingreso a la etapa 1");
            etapa1();
            // System.out.println("FIN PROCESO, tamaño del buffer Inicial es: " + bufferEtapa1.getTamanio());
            // System.out.println("FIN PROCESO, tamaño del buffer Mitad es: " + bufferEtapa2.getTamanio());
            // System.out.println("FIN PROCESO, tamaño del buffer Rojo es: " + bufferEtapaFinal.getTamanio());
        }
        else if(id == 1)
        {
            System.out.println("Ingreso a la etapa 2");
            etapa2();
            // System.out.println("FIN PROCESO, tamaño del buffer Inicial es: " + bufferEtapa1.getTamanio());
            // System.out.println("FIN PROCESO, tamaño del buffer Mitad es: " + bufferEtapa2.getTamanio());
            // System.out.println("FIN PROCESO, tamaño del buffer Rojo es: " + bufferEtapaFinal.getTamanio());
        }
        else if(id == 2)
        {
            System.out.println("Ingreso a la etapa 3");
            etapa3();
        }
        else if(id == 3)
        {
            System.out.println("Ingreso a la etapa Final");
            etapaFinal();
            //print de los productos
            // System.out.println("FIN PROCESO, tamaño del buffer Inicial es: " + bufferEtapa1.getTamanio());
            // System.out.println("FIN PROCESO, tamaño del buffer Mitad es: " + bufferEtapa2.getTamanio());
            // System.out.println("FIN PROCESO, tamaño del buffer Rojo es: " + bufferEtapaFinal.getTamanio());

            Collections.sort(pFinal);
            for (int i = 0; i < pFinal.size(); i++) {
                System.out.println("Producto " + pFinal.get(i) + " extraido");
            }

        }
        else
        {
            System.out.println("ERROR: ID de etapa no reconocido");
        }
    }
}
