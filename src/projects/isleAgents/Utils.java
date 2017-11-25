package projects.isleAgents;

import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionStatistics;

import static io.jenetics.engine.EvolutionResult.toBestPhenotype;
import static io.jenetics.engine.Limits.bySteadyFitness;

public class Utils
{

    public static Engine<EnumGene<Integer>, Double> GetEngine(Integer maximalPhenotypeAge, Integer populationSize)
    {
        return  Engine
                .builder(
                        Salesman::dist,
                        PermutationChromosome.ofInteger(Salesman.STOPS))
                .optimize(Optimize.MINIMUM)
                .maximalPhenotypeAge(maximalPhenotypeAge)
                .populationSize(populationSize)
                .alterers(
                        new SwapMutator<>(0.2),
                        new PartiallyMatchedCrossover<>(0.35))
                .build();
    }

    public  static Phenotype<EnumGene<Integer>, Double> GetBest(Engine<EnumGene<Integer>, Double> engine,
                                                                Integer limit,
                                                                Integer generations )
    {
        final EvolutionStatistics<Double, ?>
                statistics = EvolutionStatistics.ofNumber();

        return  engine.stream()
                // Truncate the evolution stream after 7 "steady"
                // generations.
                .limit(bySteadyFitness(generations))
                // The evolution will stop after maximal 100
                // generations.
                .limit(limit)
                // Update the evaluation statistics after
                // each generation
                .peek(statistics)
                // Collect (reduce) the evolution stream to
                // its best phenotype.
                .collect(toBestPhenotype());
    }


}
