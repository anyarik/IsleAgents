package projects.isleAgents;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.sin;
import static io.jenetics.engine.EvolutionResult.toBestPhenotype;
import static io.jenetics.engine.Limits.bySteadyFitness;

import java.io.Console;
import java.util.stream.IntStream;

import io.jenetics.EnumGene;
import io.jenetics.Genotype;
import io.jenetics.Optimize;
import io.jenetics.PartiallyMatchedCrossover;
import io.jenetics.PermutationChromosome;
import io.jenetics.Phenotype;
import io.jenetics.SwapMutator;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionStatistics;


public class TravelingSalesman {
    private static final int STOPS = 20;
    public static int Count =0;
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
    private static
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

    public static void GetBestPath(String NameAgent) {

        if (Count < 3){
            Count ++;
            System.out.println("Принято сообщение от агента" + NameAgent);
            return;
        }

        final Engine<EnumGene<Integer>, Double> engine = Engine
                .builder(
                        TravelingSalesman::dist,
                        PermutationChromosome.ofInteger(STOPS))
                .optimize(Optimize.MINIMUM)
                .maximalPhenotypeAge(11)
                .populationSize(500)
                .alterers(
                        new SwapMutator<>(0.2),
                        new PartiallyMatchedCrossover<>(0.35))
                .build();

        // Create evolution statistics consumer.
        final EvolutionStatistics<Double, ?>
                statistics = EvolutionStatistics.ofNumber();

        final Phenotype<EnumGene<Integer>, Double> best =
                engine.stream()
                        // Truncate the evolution stream after 7 "steady"
                        // generations.
                        .limit(bySteadyFitness(15))
                        // The evolution will stop after maximal 100
                        // generations.
                        .limit(250)
                        // Update the evaluation statistics after
                        // each generation
                        .peek(statistics)
                        // Collect (reduce) the evolution stream to
                        // its best phenotype.
                        .collect(toBestPhenotype());
        System.out.println("Получено лучшее решение от агента" + NameAgent);
        System.out.println(statistics);
        System.out.println(best);
    }
}