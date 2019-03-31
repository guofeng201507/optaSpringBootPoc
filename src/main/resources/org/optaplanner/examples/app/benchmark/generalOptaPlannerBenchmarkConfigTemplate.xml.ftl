<?xml version="1.0" encoding="UTF-8"?>
<plannerBenchmark>
  <benchmarkDirectory>local/data/general/template</benchmarkDirectory>

  <inheritedSolverBenchmark>
    <solver>
      <termination>
        <minutesSpentLimit>5</minutesSpentLimit>
      </termination>
    </solver>
  </inheritedSolverBenchmark>


<#list ['JDK', 'MERSENNE_TWISTER', 'WELL512A', 'WELL1024A', 'WELL19937C', 'WELL44497B'] as randomType>
  <solverBenchmark>
    <name>Nurse Rostering Tabu Search ${randomType}</name>
    <problemBenchmarks>
      <xStreamAnnotatedClass>org.optaplanner.examples.nurserostering.domain.NurseRoster</xStreamAnnotatedClass>
      <inputSolutionFile>data/nurserostering/unsolved/medium01.xml</inputSolutionFile>
      <inputSolutionFile>data/nurserostering/unsolved/medium_hint01.xml</inputSolutionFile>
    </problemBenchmarks>
    <solver>
      <solutionClass>org.optaplanner.examples.nurserostering.domain.NurseRoster</solutionClass>
      <entityClass>org.optaplanner.examples.nurserostering.domain.ShiftAssignment</entityClass>
      <randomType>${randomType}</randomType>
      <scoreDirectorFactory>
        <scoreDrl>org/optaplanner/examples/nurserostering/solver/nurseRosteringScoreRules.drl</scoreDrl>
      </scoreDirectorFactory>
      <constructionHeuristic>
        <constructionHeuristicType>WEAKEST_FIT</constructionHeuristicType>
      </constructionHeuristic>
      <localSearch>
        <unionMoveSelector>
          <moveListFactory>
            <cacheType>PHASE</cacheType>
            <moveListFactoryClass>org.optaplanner.examples.nurserostering.solver.move.factory.EmployeeChangeMoveFactory</moveListFactoryClass>
          </moveListFactory>
          <moveListFactory>
            <cacheType>PHASE</cacheType>
            <moveListFactoryClass>org.optaplanner.examples.nurserostering.solver.move.factory.ShiftAssignmentSwapMoveFactory</moveListFactoryClass>
          </moveListFactory>
          <moveListFactory>
            <cacheType>STEP</cacheType>
            <moveListFactoryClass>org.optaplanner.examples.nurserostering.solver.move.factory.ShiftAssignmentPillarPartSwapMoveFactory</moveListFactoryClass>
          </moveListFactory>
        </unionMoveSelector>
        <acceptor>
          <entityTabuSize>7</entityTabuSize>
        </acceptor>
        <forager>
          <acceptedCountLimit>800</acceptedCountLimit>
        </forager>
      </localSearch>
    </solver>
  </solverBenchmark>
</#list>
</plannerBenchmark>
