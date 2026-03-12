import java.util.*;

public class LongestSubarrayWithSumK {

    static final int NEG_INF = Integer.MIN_VALUE / 2;

    static List<Integer> findLongest(int[] a, int k) {
        int n = a.length;
        int[][] dp = new int[n + 1][k + 1];
        int[][] parent = new int[n + 1][k + 1];

        for (int[] row : dp) Arrays.fill(row, NEG_INF);
        for (int i = 0; i <= n; i++) dp[i][0] = 0; 

        for (int i = 1; i <= n; i++) {
            for (int s = 0; s <= k; s++) {
                dp[i][s] = dp[i-1][s];
                parent[i][s] = 0;

                if (a[i-1] <= s && dp[i-1][s - a[i-1]] != NEG_INF
                        && dp[i-1][s - a[i-1]] + 1 > dp[i][s]) {
                    dp[i][s] = dp[i-1][s - a[i-1]] + 1;
                    parent[i][s] = 1;
                }
            }
        }

        if (dp[n][k] == NEG_INF) return new ArrayList<>(); 

        // Truy vết
        List<Integer> result = new ArrayList<>();
        int s = k;
        for (int i = n; i >= 1; i--) {
            if (parent[i][s] == 1) {
                result.add(a[i-1]);
                s -= a[i-1];
            }
        }
        Collections.reverse(result);
        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), k = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = sc.nextInt();
        sc.close();

        List<Integer> result = findLongest(a, k);

        if (result.isEmpty()) {
            System.out.println("Khong tim thay day con nao co tong = " + k);
        } else {
            System.out.println("Day con dai nhat co tong = " + k + ":");
            StringJoiner sj = new StringJoiner(", ");
            for (int x : result) sj.add(String.valueOf(x));
            System.out.println(sj);
            System.out.println("Do dai: " + result.size());
        }
    }
}
