package kas.concurrente.modelos;
import java.util.Random; 
/**
 * En esta clase se simula el estacionamiento en si
 * Posee un conjunto de arreglos de tipo Lugar (o arreglo bidimensional?)
 * Posee un entero de lugaresDisponibles (Se podra hacer por pisos?) (Habra otra manera de hacerlo mejor?)
 * @author Kassandra Mirael
 * @version 1.0
 */
public class Estacionamiento {

    //Arreglo de lugares 
    private Lugar[] lugares;
    //Numero de lugares disponibles
    private int lugaresDisponibles;

    /**
     * Metodo constructor
     * Modifica el constructor o crea otro segun consideres necesario
     * @param capacidad La capacidad del estacionamiento
     */
    public Estacionamiento(int capacidad){
        this.lugares = new Lugar[capacidad];
        for(int i = 0; i < capacidad; i++){
            lugares[i] = new Lugar(i);
        }
        this.lugaresDisponibles = capacidad;
    }

    public int getLugaresDisponibles() {
        return this.lugaresDisponibles;
    }

    public void setLugaresDisponibles(int lugaresDisponibles) {
        this.lugaresDisponibles = lugaresDisponibles;
    }

    public Lugar[] getLugares() {
        return this.lugares;
    }

    /**
     * Metodo que nos indica si esta lleno el estacionamiento
     * @return true si esta lleno, false en otro caso
     */
    public boolean estaLleno(){
        return lugaresDisponibles == 0;
    }

    /**
     * Metodo que inicaliza los lugares del arreglo
     * Este es un método optativo
     */
    public void inicializaLugares(){
        for(int i = 0; i < lugares.length; i++){
            lugares[i] = new Lugar(i);
        }
    }

    /**
     * Metodo en el que se simula la entrada de un carro
     * Imprime un texto que dice que el carro a entrado de color AZUL
     * @param nombre El nombre del carro
     * @throws InterruptedException Si llega a fallar
     */
    public void entraCarro(int nombre) throws InterruptedException{
        String BLUE = "\u001B[34m";
        String RESET = "\u001B[0m";
        System.out.println(BLUE + "El carro " + nombre + " ha entrado" + RESET);
    }

    /**
     * Metodo que asigna el lugar, una vez asignado ESTACIONA su nave
     * @param lugar El lugar que correspone
     * @throws InterruptedException
     */
    public void asignaLugar(int lugar) throws InterruptedException {
        lugares[lugar].estaciona();
        lugaresDisponibles--;
    }

    /**
     * Se obtiene un lugar de forma pseudoAleatoria
     * Aqui necesito que revisen el repaso de estadistica que mande en 
     * repaso, quiero que expliquen porque lo pedimos en forma pseudoAleatoria
     * @return Retorna el indice del lugar
     */
    public int obtenLugar(){
        // Escogemos un lugar de forma pseudo aleatoria
        // hasta que encontremos uno disponible
        int lugar = new java.util.Random().nextInt(lugares.length);
        while(!lugares[lugar].isDisponible()){
            lugar = new java.util.Random().nextInt(lugares.length);
        }
        return lugar;
    }
}