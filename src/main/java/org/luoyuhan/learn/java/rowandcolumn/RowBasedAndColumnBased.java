package org.luoyuhan.learn.java.rowandcolumn;//package org.luoyuhan.learn.rowandcolumn;
//
//import jdk.incubator.vector.FloatVector;
//import jdk.incubator.vector.VectorSpecies;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import lombok.var;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//import java.util.stream.IntStream;
//
///**
// * @author luoyuhan
// * @date 2022/1/24
// */
//@Slf4j
//public class RowBasedAndColumnBased {
//    @Data
//    static class Rows {
//        List<Row> rows;
//    }
//    @Data
//    static class Columns {
//        ColumnInt intColumn;
//        ColumnStr strColumn;
//    }
//    @Data
//    @AllArgsConstructor
//    static class Row {
//        Integer id;
//        String name;
//    }
//    @Data
//    @AllArgsConstructor
//    static class ColumnInt {
//        List<Integer> ids;
//    }
//    @Data
//    @AllArgsConstructor
//    static class ColumnStr {
//        List<String> names;
//    }
//    public static void processRows(Rows rows) {
//        for (Row row : rows.rows) {
//            if (row.id > 100) {
//                System.out.print("");
//            }
//            if ("abc".equals(row.name)) {
//                System.out.print("");
//            }
//        }
//    }
//    public static void processColumns(Columns columns) {
//        for (Integer id : columns.intColumn.ids) {
//            if (id > 100) {
//                System.out.print("");
//            }
//        }
//        for (String name : columns.strColumn.names) {
//            if ("abc".equals(name)) {
//                System.out.print("");
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        Arrays.equals(new int[]{}, new int[]{});
//        int[] ints = IntStream.range(1, 1000).toArray();
//        System.out.println(Arrays.toString(ints));
//    }
//
//    static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;
//
//    void vectorComputation(float[] a, float[] b, float[] c) {
//        int i = 0;
//        int upperBound = SPECIES.loopBound(a.length);
//        for (; i < upperBound; i += SPECIES.length()) {
//            // FloatVector va, vb, vc;
//            var va = FloatVector.fromArray(SPECIES, a, i);
//            var vb = FloatVector.fromArray(SPECIES, b, i);
//            var vc = va.mul(va)
//                    .add(vb.mul(vb))
//                    .neg();
//            vc.intoArray(c, i);
//        }
//        for (; i < a.length; i++) {
//            c[i] = (a[i] * a[i] + b[i] * b[i]) * -1.0f;
//        }
//    }
//}
