package org.luoyuhan.learn.java.bfsdfs;

import lombok.AllArgsConstructor;
import org.luoyuhan.learn.model.Point;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Maze {
    public static void main(String[] args) throws IOException {
        File mazeFile = new File("/Users/luoyuhan/dev/code/learn/src/main/java/org/luoyuhan/learn/bfsdfs/maze.txt");
        BufferedReader fileReader = new BufferedReader(new FileReader(mazeFile));
        List<String> mazeNums = fileReader.lines().collect(Collectors.toList());

        int m = mazeNums.size();
        int n = mazeNums.get(0).length();
        System.out.println(m + "*" + n);

        int[][] maze = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                maze[i][j] = Integer.parseInt(mazeNums.get(i).charAt(j) + "");
            }
        }
        bfsMazePath(maze, m, n);
    }

    public static void bfsMazePath(int[][] maze, int m, int n) {
        Queue<PointPath> queue = new LinkedList<>();
        Set<PointPath> visited = new HashSet<>();

        Point start = new Point(0, 0);
        PointPath firstStep = new PointPath(start, null, ' ');
        queue.offer(firstStep);
        visited.add(firstStep);

        while (!queue.isEmpty()) {
            PointPath cur = queue.poll();
            if (cur.cur.x == m - 1 && cur.cur.y == n - 1) {
                System.out.println("find path");
                print(maze, cur);
                return;
            }
            List<PointPath> nextSteps = getNextStep(cur, maze, m, n);
            for (PointPath pointPath : nextSteps) {
                if (!visited.contains(pointPath)) {
                    queue.offer(pointPath);
                    visited.add(pointPath);
                }
            }
        }
    }

    public static void print(int[][] maze, PointPath pointPath) {
        // 补充路径
        // 重点：由于bfs记录的是当前节点和父节点的关系，不能体现当前节点下一步怎么做
        // 因此，首先标记终点没有下一步，然后把当前关系(操作)赋值给父节点当做父节点的下一步操作
        maze[pointPath.cur.x][pointPath.cur.y] = 999;
        while (pointPath.parent != null) {
            maze[pointPath.parent.cur.x][pointPath.parent.cur.y] = pointPath.direction;
            pointPath = pointPath.parent;
        }
        // 补充外墙
        int[][] newMaze = new int[maze.length + 2][maze[0].length + 2];
        for (int i = 0; i < newMaze.length; i++) {
            for (int j = 0; j < newMaze[i].length; j++) {
                if (i == 0 || i == newMaze.length - 1 || j == 0 || j == newMaze[i].length - 1) {
                    newMaze[i][j] = 1;
                } else {
                    newMaze[i][j] = maze[i - 1][j - 1];
                }
            }
        }

        for (int[] ints : newMaze) {
            for (int anInt : ints) {
                if (anInt == 1) {
                    System.out.print("+");
                } else if (anInt == 'u') {
                    System.out.print("A");
                } else if (anInt == 'd') {
                    System.out.print("V");
                } else if (anInt == 'l') {
                    System.out.print("<");
                } else if (anInt == 'r') {
                    System.out.print(">");
                } else if (anInt == 999) {
                    System.out.print("#");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    @AllArgsConstructor
    static class PointPath {
        Point cur;
        PointPath parent;
        char direction;

        @Override
        public int hashCode() {
            return cur.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof PointPath)) {
                return false;
            }
            return cur.equals(((PointPath) obj).cur);
        }
    }

    public static List<PointPath> getNextStep(PointPath pointPath, int[][] maze, int m, int n) {
        List<PointPath> pointPaths = new ArrayList<>();
        Point cur = pointPath.cur;
        int x, y;
        x = cur.x - 1;
        y = cur.y;
        if (x >= 0 && maze[x][y] == 0) {
            Point next = new Point(x, y);
            PointPath nextStep = new PointPath(next, pointPath, 'u');
            pointPaths.add(nextStep);
        }
        x = cur.x + 1;
        y = cur.y;
        if (x < m && maze[x][y] == 0) {
            Point next = new Point(x, y);
            PointPath nextStep = new PointPath(next, pointPath, 'd');
            pointPaths.add(nextStep);
        }
        x = cur.x;
        y = cur.y - 1;
        if (y >= 0 && maze[x][y] == 0) {
            Point next = new Point(x, y);
            PointPath nextStep = new PointPath(next, pointPath, 'l');
            pointPaths.add(nextStep);
        }
        x = cur.x;
        y = cur.y + 1;
        if (y < n && maze[x][y] == 0) {
            Point next = new Point(x, y);
            PointPath nextStep = new PointPath(next, pointPath, 'r');
            pointPaths.add(nextStep);
        }
        return pointPaths;
    }
}
