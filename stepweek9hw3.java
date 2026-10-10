import java.util.*;

public class Main {
    static void mostPopular(String[] orders) {
        HashMap<String, Integer> count = new HashMap<>();

        for (String item : orders) {
            count.put(item, count.getOrDefault(item, 0) + 1);
        }

        String popular = orders[0];
        int max = count.get(popular);

        for (String item : orders) {
            if (count.get(item) > max) {
                max = count.get(item);
                popular = item;
            }
        }

        System.out.println("(" + popular + ", " + max + ")");
    }

    public static void main(String[] args) {
        String[] orders = {
            "dosa", "idli", "vada", "dosa",
            "idli", "dosa", "tea"
        };

        mostPopular(orders);
    }
}
