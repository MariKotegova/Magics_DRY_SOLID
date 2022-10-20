public class ConsoleLoger implements Loger {
    public void printConsole(String msg) {
        System.out.println(msg);
    }

    @Override
    public void log(String msg) {
        printConsole(msg);
    }
}
