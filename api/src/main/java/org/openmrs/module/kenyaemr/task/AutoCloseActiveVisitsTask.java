/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.task;

import org.openmrs.Visit;
import org.openmrs.api.VisitService;
import org.openmrs.api.context.Context;
import org.openmrs.scheduler.tasks.AbstractTask;
import org.openmrs.util.OpenmrsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * A scheduled task that automatically closes all unvoided active visits
 * The focus should only be on the OUTPATIENT visit type with visit duration (checkin to now) > 8 hours
 * Set date_stopped to 11 23:59:59 of the date_started
 */
public class AutoCloseActiveVisitsTask extends AbstractTask {

    private static final Logger log = LoggerFactory.getLogger(AutoCloseActiveVisitsTask.class);

    /**
     * @see AbstractTask#execute()
     */
    @Override
    public void execute() {
        if (!isExecuting) {
            if (log.isDebugEnabled()) {
                log.debug("Starting Auto Close Visits Task...");
            }
			System.out.println("Starting the close visits task");

            String openVisitsQuery = "select visit_id, TIMESTAMPDIFF(HOUR, v.date_started, now()) as visit_duration from visit v\n" +
					"inner join visit_type vt on vt.visit_type_id = v.visit_type_id and vt.uuid ='3371a4d4-f66f-4454-a86d-92c7b3da990c'\n" +
					"where v.date_stopped is null having visit_duration > 8;";

            startExecuting();
            VisitService visitService = Context.getVisitService();
            List<List<Object>> visits = Context.getAdministrationService().executeSQL(openVisitsQuery, true);

            if (visits.size() > 0) {
                try {
                    int counter = 0;
                    for (List<Object> ls : visits) {
                        if (ls.get(0) != null) {
                            Integer visitId = (Integer) ls.get(0);
                            Visit visit = visitService.getVisit(visitId);
                            visit.setStopDatetime(OpenmrsUtil.getLastMomentOfDay(visit.getStartDatetime()));
                            visitService.saveVisit(visit);
                            counter++;

                            if ((counter % 400) == 0) {
                                Context.flushSession();
                                Context.clearSession();
                                counter = 0;
								System.out.println("AutoCloseVisitTask: Flushing session....");
							}
                        }
                    }
                } catch (Exception e) {
                    log.error("Error while auto closing visits:", e);
                } finally {
                    stopExecuting();
					System.out.println("Completed the close visits task");

				}
            }
        }
    }
}
