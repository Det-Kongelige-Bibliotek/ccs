package dk.kb.ccs.workflow.steps;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.kb.ccs.SendMail;
import dk.kb.ccs.reporting.MailReport;
import dk.kb.ccs.reporting.Reporter;

/**
 * Step for retrieving changed records from COP, extracts the crowd-sourced fields from  
 * each COP record, then find the related Cumulus record, and return the fields to the Cumulus record.
 */
public class SendMailStep extends WorkflowStep {
    /** The log.*/
    protected static Logger log = LoggerFactory.getLogger(SendMailStep.class);
    
    /** The Reporter for reporting the results of each run.*/
    protected final Reporter reporter;
    /** The component for sending the mails.*/
    protected final SendMail mailer;
    /** The interval for the mail.*/
    protected final Long mailInterval; 
    
    /**
     * Constructor.
     * @param report The reporter for the backflow reports.
     * @param mailer The mailer for sending the mails.
     * @param mailInterval The interval for the report.
     */
    public SendMailStep(Reporter report, SendMail mailer, Long mailInterval) {
        this.reporter = report;
        this.mailer = mailer;
        this.mailInterval = mailInterval;
    }
    
    @Override
    protected void runStep() throws IOException {
        Date earliestDate = new Date(System.currentTimeMillis() - mailInterval);
        MailReport report = reporter.getReport(earliestDate, new Date());
        String subject = "Cumulus Crowd Service Report";
        mailer.sendReport(subject, report);
        setResultOfRun("Mail sent.");
    }
    
    @Override
    public String getName() {
        return "Send Mail Step";
    }
}
