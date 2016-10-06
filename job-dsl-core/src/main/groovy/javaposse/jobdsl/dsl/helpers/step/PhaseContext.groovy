package javaposse.jobdsl.dsl.helpers.step

import javaposse.jobdsl.dsl.AbstractContext
import javaposse.jobdsl.dsl.ContextHelper
import javaposse.jobdsl.dsl.DslContext
import javaposse.jobdsl.dsl.Item
import javaposse.jobdsl.dsl.JobManagement

class PhaseContext extends AbstractContext {
    protected final Item item

    String phaseName
    String continuationCondition
    String executionType

    List<PhaseJobContext> jobsInPhase = []

    PhaseContext(
        JobManagement jobManagement, Item item, String phaseName, String continuationCondition,
        String executionType
    ) {
        super(jobManagement)
        this.item = item
        this.phaseName = phaseName
        this.continuationCondition = continuationCondition
        this.executionType = executionType
    }

    /**
     * Defines the name of the MultiJob phase.
     */
    void phaseName(String phaseName) {
        this.phaseName = phaseName
    }

    /**
     * Defines how to decide the status of the whole MultiJob phase.
     */
    void continuationCondition(String continuationCondition) {
        this.continuationCondition = continuationCondition
    }

    /**
     * Defines how to run the whole MultiJob phase.
     */
    void executionType(String executionType) {
        this.executionType = executionType
    }

    /**
     * Adds a job to the phase.
     *
     * @since 1.39
     */
    void phaseJob(String jobName, @DslContext(PhaseJobContext) Closure phaseJobClosure = null) {
        PhaseJobContext phaseJobContext = new PhaseJobContext(jobManagement, item, jobName)
        ContextHelper.executeInContext(phaseJobClosure, phaseJobContext)

        jobsInPhase << phaseJobContext
    }
}
