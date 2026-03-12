import java.util.*;

public class WarningStations {
    
    static long cross(int[] O, int[] A, int[] B) {
        return (long)(A[0] - O[0]) * (B[1] - O[1])
           -   (long)(A[1] - O[1]) * (B[0] - O[0]);
    }

    static List<int[]> convexHull(int[][] points) {
        int n = points.length;
        if (n < 3) {
            return Arrays.asList(points);
        }

        // Sắp xếp theo x, nếu bằng thì theo y
        Arrays.sort(points, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);

        int[] anchor = points[0];

        // Sắp xếp theo góc so với điểm anchor (Graham Scan)
        int[] finalAnchor = anchor;
        int[][] rest = Arrays.copyOfRange(points, 1, n);
        Arrays.sort(rest, (a, b) -> {
            long cp = cross(finalAnchor, a, b);
            if (cp != 0) return cp > 0 ? -1 : 1;
            long da = (long)(a[0]-finalAnchor[0])*(a[0]-finalAnchor[0])
                    + (long)(a[1]-finalAnchor[1])*(a[1]-finalAnchor[1]);
            long db = (long)(b[0]-finalAnchor[0])*(b[0]-finalAnchor[0])
                    + (long)(b[1]-finalAnchor[1])*(b[1]-finalAnchor[1]);
            return Long.compare(da, db);
        });

        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(anchor);
        stack.push(rest[0]);

        for (int i = 1; i < rest.length; i++) {
            while (stack.size() >= 2) {
                int[] top = stack.peek();
                int[] below = ((ArrayDeque<int[]>) stack).peekLast();
                // Lấy phần tử thứ 2 từ đỉnh
                Iterator<int[]> it = stack.iterator();
                it.next(); 
                int[] second = it.next();
                if (cross(second, top, rest[i]) <= 0) {
                    stack.pop();
                } else break;
            }
            stack.push(rest[i]);
        }
    
        List<int[]> hull = new ArrayList<>(stack);
        Collections.reverse(hull);
        return hull;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] points = new int[n][2];

        for (int i = 0; i < n; i++) {
            points[i][0] = sc.nextInt();
            points[i][1] = sc.nextInt();
        }

        sc.close();

        List<int[]> hull = convexHull(points);

        System.out.println("Cac tram canh bao:");
        for (int[] p : hull) {
            System.out.println(p[0] + " " + p[1]);
        }
    }
}
