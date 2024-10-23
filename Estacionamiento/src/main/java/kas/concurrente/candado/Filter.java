package kas.concurrente.candado;

public class Filter implements Lock {
    private volatile int[] level;
    private volatile int[] victim;

    public Filter(int n) {
        level = new int[n];
        victim = new int[n];
        for (int i = 0; i < n; i++) {
            level[i] = 0;
        }
    }

    @Override
    public void lock() {
        int i = (int) Thread.currentThread().getId() % level.length;
        for (int j = 1; j < level.length; j++) {
            level[i] = j;
            victim[j] = i;
            for (int k = 0; k < level.length; k++) {
                while ((k != i) && (level[k] >= j && victim[j] == i)) {
                    // Espera activa
                }
            }
        }
    }

    @Override
    public void unlock() {
        int i = (int) Thread.currentThread().getId() % level.length;
        level[i] = 0;
    }
    
}
