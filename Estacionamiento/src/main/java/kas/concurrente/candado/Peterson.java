package kas.concurrente.candado;

public class Peterson implements Lock {
    private volatile boolean[] flag = new boolean[2];
    private volatile int turno;

    public Peterson() {
        flag[0] = false;
        flag[1] = false;
        turno = 0;
    }

    @Override
    public void lock() {
        int i = (int) Thread.currentThread().getId() % 2;
        int j = 1 - i;
        flag[i] = true;
        turno = j;
        while (flag[j] && turno == j) {
            // Espera activa
        }
    }

    @Override
    public void unlock() {
        int i = (int) Thread.currentThread().getId() % 2;
        flag[i] = false;
    }
    
}
