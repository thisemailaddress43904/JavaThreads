//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void countUpto(final int upperLimit, final boolean verbose){
    int val = 0;
    while(val < upperLimit){
        val++;
        if (verbose)
            System.out.println(val);
    }
}

void main() {
    Thread t = new Thread(() -> {
        countUpto(10, true);
    });

    t.start();
}
