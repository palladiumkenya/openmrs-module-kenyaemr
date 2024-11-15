package org.openmrs.module.kenyaemr.chore;

import org.openmrs.api.context.Context;
import org.openmrs.module.kenyacore.chore.AbstractChore;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
/**
 * update FSW concept from 165083 to 166513
 */
@Component("kenyaemr.chore.UpdateFSWConcept")
public class UpdateFSWConcept extends AbstractChore {

    /**
     * @see AbstractChore#perform(PrintWriter)
     */

    @Override
    public void perform(PrintWriter out) {
        String updateConceptSql = "update obs set value_coded = 166513 where value_coded =165083;";
        Context.getAdministrationService().executeSQL(updateConceptSql, false);
        out.println("Completed updating FSW concept");

    }




}

