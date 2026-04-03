package pokedex.util;

public class PrintUtils {
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static void slowPrint(String mensagem, int delay) throws InterruptedException {
        for (char c : mensagem.toCharArray()) {
            System.out.print(c);
            Thread.sleep(delay);
        }
        System.out.print("\n");
    }
}
