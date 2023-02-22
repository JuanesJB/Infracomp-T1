

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("INICIO");
        
        int cantidadProcesos = 2;
        int cantidadProductos = 2;
        int tamanioBuffer = 1;

        Buffer bufferInicial = new Buffer(tamanioBuffer);
        Buffer bufferMitad = new Buffer(tamanioBuffer);
        Buffer bufferFinal = new Buffer(200);

        //CyclicBarrier barrier = new CyclicBarrier((cantidadProcesos));
        

        //Etapa 1
        Proceso procesoNaranjaUno = new Proceso(0, "naranja", cantidadProductos, bufferInicial, bufferMitad, bufferFinal, cantidadProcesos);
        procesoNaranjaUno.start();

        for (int i = 1; i < cantidadProcesos; i++) {
             Proceso procesoAzulUno = new Proceso(0, "azul", cantidadProductos, bufferInicial, bufferMitad, bufferFinal, cantidadProcesos);
             procesoAzulUno.start();
        }

        //Etapa 2

        Proceso procesoNaranjaDos = new Proceso(1, "naranja", cantidadProductos, bufferInicial, bufferMitad, bufferFinal, cantidadProcesos);
        procesoNaranjaDos.start();

        for (int i = 1; i < cantidadProcesos; i++) {
            Proceso procesoAzulDos = new Proceso(1, "azul", cantidadProductos, bufferInicial, bufferMitad, bufferFinal, cantidadProcesos);
            procesoAzulDos.start();
        }

        //Etapa 3

        Proceso procesoNaranjaTres = new Proceso(2, "naranja", cantidadProductos, bufferInicial, bufferMitad, bufferFinal, cantidadProcesos);
        procesoNaranjaTres.start();

        for (int i = 1; i < cantidadProcesos; i++) {
            Proceso procesoAzulTres = new Proceso(2, "azul", cantidadProductos, bufferInicial, bufferMitad, bufferFinal, cantidadProcesos);
            procesoAzulTres.start();
        }

        
        //Etapa Final 

        Proceso etaFinal = new Proceso(3, "final", cantidadProductos, bufferInicial, bufferMitad, bufferFinal, cantidadProcesos);
        etaFinal.start();


    }
}
