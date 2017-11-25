package projects.isleAgents;

import io.jenetics.EnumGene;
import io.jenetics.Genotype;

import java.util.stream.IntStream;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.sin;

public class Salesman {

    public static final int STOPS = 20;
    private static final double[][] ADJACENCE = matrix(STOPS);

    private static double[][] matrix(int stops) {
        final double radius = 10.0;
        double[][] matrix = new double[stops][stops];

        for (int i = 0; i < stops; ++i) {
            for (int j = 0; j < stops; ++j) {
                matrix[i][j] = chord(stops, abs(i - j), radius);
            }
        }
        return matrix;
    }

    private static double chord(int stops, int i, double r) {
        return 2.0*r*abs(sin((PI*i)/stops));
    }

    // Calculate the path length of the current genotype.
    public static
    Double dist(final Genotype<EnumGene<Integer>> gt) {
        // Convert the genotype to the traveling path.
        final int[] path = gt.getChromosome().toSeq().stream()
                .mapToInt(EnumGene<Integer>::getAllele)
                .toArray();

        // Calculate the path distance.
        return IntStream.range(0, STOPS)
                .mapToDouble(i ->
                        ADJACENCE[path[i]][path[(i + 1)%STOPS]])
                .sum();
    }
}
