# Genetic Algorithm Implementation

## Introduction 
Optimization is the process of finding the best solution among a set of possibilities, by adjusting the parameters and variables in a given problem. It is a central concept in many fields, including mathematics, engineering, and computer science. Optimization problems can take many forms, such as finding the minimum or maximum value of a mathematical application or finding a solution that satisfies certain constraints. Many different techniques can be used to solve optimization problems, in this practical work, we going to implement one of these techniques, that it is the Genetic Algorithm.

## Genetic Algorithm 
Genetic algorithm (Holland, 1975; Goldberg, 1989) is population-based algorithms that use operators inspired by population genetics to explore the search space (the most typ- ical genetic operators are reproduction, mutation, and recombination), it mimics evo- lution by natural selection. It begins with a set of solutions (a population) and allows some pairs of solutions, perhaps the best ones, to mate. A crossover operation produces an offspring that inherits some characteristics of the parent solutions. At this point, the fewer desirable solutions are eliminated from the population so that only the fittest survive. The process repeats for several generations, and the best solution for the resulting population is selected. Indicate how this algorithm can be viewed as examining a sequence of problem restrictions. In what way does the generation of offspring produce a relaxation of the current restriction? What is the role of the selection criterion? Why is relaxation bounding unhelpful in this algorithm? Hint: Relaxation bounding is helpful when it obviates the necessity of solving the current restriction. Think about how the current relaxation is obtained.<br />
As in constrained biological systems, the best individuals in the population are those that have a better chance of reproducing and passing on some of their genetic heritage to the next generation. A new population, or generation, is then created by combining the genes of the parents. It is expected that some individuals of the new generation will have the best characteristics of both parents, and therefore they will be better and will be a better solution to the problem. The new group (the new generation) is then sub- jected to the same selection criteria and subsequently generates its own offspring. This process is repeated several times until all individuals have the same genetic heritage. Members of this latest generation, who are usually very different from their ancestors, have genetic information that corresponds to the best solution to the problem.<br />
The basic genetic algorithm has three simple operations that are no more complicated than algebraic operations:<br />
* Selection: It is processes or individuals copied according to the value of their objective function. We can describe the function f as a measure of profit, utility or quality that we want to maximize (minimize). If we copy individuals according to their f-value, this implies that individuals with higher values have a greater probability of contributing offspring to the next generation. This is an artificial version of Darwin’s "survival of the fittest".<br />
* Crossover: It is the process where new individuals are formed from parents. These new individuals, the offspring, are formed by making a cross between two parents. We choose a random position k between [1, l] where l is the length of the individual. The crossing is done by exchanging the bits from position k + 1 to l.<br />
* Mutation: It is a random process where a bit changes value. This process plays a secondary role in the genetic algorithm, but it is still important. The mutation ensures that no point in the search space has a zero probability of being reached.

## Parameters

<table align="center">
  <thead>
    <tr>
    <th>Parameter</th> <th>Signification</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>M</td>
      <td>The number of generations</td>
    </tr>
    <tr>
      <td>N</td>
      <td>Population size</td>
    </tr>
    <tr>
      <td>Pc</td>
      <td>Probability of crossover</td>
    </tr>
    <tr>
      <td>Pm</td>
      <td>Probability of Mutation</td>
    </tr>
    <tr>
      <td>L</td>
      <td>Chromosome length</td>
    </tr>
   </tbody>
</table>

## Implementation
General schema of GA.

<table align="center">
    <tr>
        <td><img src="https://github.com/SDAllouche/mas-genetic-algorithm/assets/102489525/a63691b4-c856-4a5b-bef6-a04b0a12154b"/></td>
    </tr>
</table>

## Test



## License
[MIT LICENSE](License)
