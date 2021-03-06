package dk.kb.ccs.workflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import dk.kb.ccs.conf.Configuration;
import dk.kb.ccs.utils.CalendarUtils;
import dk.kb.ccs.workflow.steps.WorkflowStep;

/**
 * Abstract class for workflows.
 * Deals with the generic part of when the workflow should run.
 * @author jolf
 */
public abstract class Workflow extends TimerTask {
    /** The log.*/
    protected final Logger log = LoggerFactory.getLogger(Workflow.class);

    /** The date for the next run of the workflow.*/
    protected Date nextRun;
    /** The current state of the workflow.*/
    protected WorkflowState state = WorkflowState.WAITING;
    /** The status of this workflow.*/
    protected String status = "Has not run yet";
    
    /** The configuration. Auto-wired.*/
    @Autowired
    protected Configuration conf;

    /** The steps for the workflow.*/
    protected List<WorkflowStep> steps = new ArrayList<WorkflowStep>();
    
    /**
     * Initialization
     */
    @PostConstruct
    protected void init() {
        initSteps();
        readyForNextRun();
    }
    
    /**
     * Initializes the steps for the workflow.
     */
    abstract void initSteps();
    
    /**
     * @return The interval for the workflow.
     */
    abstract Long getInterval();
    
    /**
     * @return The name of the workflow
     */
    abstract String getName();
    
    @Override
    public void run() {
        if(state == WorkflowState.WAITING && nextRun.getTime() < System.currentTimeMillis()) {
            try {
                state = WorkflowState.RUNNING;
                runWorkflowSteps();
            } finally {
                readyForNextRun();
            }
        }
    }
    
    /**
     * Start the workflow by setting the nextRun time to 'now'.
     * It will not actually start the workflow immediately, but it will trigger that the timertask is executed 
     * the next time it is executed by the scheduler.
     */
    public void startManually() {
        nextRun = new Date(System.currentTimeMillis());
    }
    
    /**
     * The method for actually running the workflow.
     * Goes through all steps and runs them one after the other.
     */
    protected void runWorkflowSteps() {
        try {
            for(WorkflowStep step : steps) {
                step.run();
            }
        } catch (Exception e) {
            log.error("Faild to run all the workflow steps.", e);
            status = "Failure during last run: " + e.getMessage();
        }
    }
    
    /**
     * @return The current state of the workflow.
     */
    public WorkflowState getState() {
        return state;
    }
    
    /**
     * @return The date for the next time this workflow should be run.
     */
    public Date getNextRunDate() {
        return new Date(nextRun.getTime());
    }
    
    /**
     * @return The steps of the workflow.
     */
    public List<WorkflowStep> getSteps() {
        return new ArrayList<WorkflowStep>(steps);
    }
    
    /**
     * Sets this workflow ready for the next run by setting the date for the next run and the state to 'waiting'.
     */
    protected void readyForNextRun() {
        Long time = System.currentTimeMillis() + getInterval() - 
                (System.currentTimeMillis() % CalendarUtils.MILLIS_PER_HOUR); 
        nextRun = new Date(time);
        state = WorkflowState.WAITING;        
    }
    
    /**
     * The enumerator for the workflow state.
     */
    public enum WorkflowState {
        /** The state when the workflow is not running, but waiting for the time to run.*/
        WAITING,
        /** The state when the workflow is running.*/
        RUNNING
    }

}
