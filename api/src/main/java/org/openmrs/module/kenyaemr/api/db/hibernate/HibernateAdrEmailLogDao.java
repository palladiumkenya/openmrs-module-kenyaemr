/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.api.db.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.openmrs.module.kenyaemr.api.db.AdrEmailLogDao;
import org.openmrs.module.kenyaemr.model.AdverseDrugReactionEmailLog;

public class HibernateAdrEmailLogDao implements AdrEmailLogDao {
    
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // @Override
    // public boolean existsForPatientAndEncounter(Integer patientId, Integer encounterId) {
    //     String hql = "SELECT COUNT(l) FROM AdverseDrugReactionEmailLog l " +
    //                  "WHERE l.patient.patientId = :patientId " +
    //                  "AND l.encounter.encounterId = :encounterId";
        
    //     Query query = sessionFactory.getCurrentSession().createQuery(hql);
    //     query.setParameter("patientId", patientId);
    //     query.setParameter("encounterId", encounterId);
        
    //     Long count = (Long) query.uniqueResult();
    //     return count != null && count > 0;
    // }

    @Override
    public void save(AdverseDrugReactionEmailLog log) {
        sessionFactory.getCurrentSession().saveOrUpdate(log);
        sessionFactory.getCurrentSession().flush(); // Force immediate write
    }
}