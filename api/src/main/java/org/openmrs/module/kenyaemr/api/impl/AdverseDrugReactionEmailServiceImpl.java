/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.api.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.kenyaemr.api.AdverseDrugReactionEmailService;
import org.openmrs.module.kenyaemr.api.db.AdrEmailLogDao;
import org.openmrs.module.kenyaemr.model.AdverseDrugReactionEmailLog;
import org.openmrs.module.kenyaemr.util.ADRReportingFormGenerator;
import org.openmrs.module.kenyaemr.util.ADRReportingFormGenerator.ADRFormData;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Transactional;

public class AdverseDrugReactionEmailServiceImpl extends BaseOpenmrsService
        implements AdverseDrugReactionEmailService {

    private AdrEmailLogDao adrEmailLogDao;

    public void setAdrEmailLogDao(AdrEmailLogDao adrEmailLogDao) {
        this.adrEmailLogDao = adrEmailLogDao;
    }

    @Override
    @Transactional
    public void sendReport(String patientUuid, String encounterUuid, String recipientEmail) throws Exception {

        Patient patient = Context.getPatientService().getPatientByUuid(patientUuid);
        Encounter encounter = Context.getEncounterService().getEncounterByUuid(encounterUuid);
        User currentUser = Context.getAuthenticatedUser();

        if (patient == null) {
            throw new IllegalArgumentException("Patient not found: " + patientUuid);
        }

        if (encounter == null) {
            throw new IllegalArgumentException("Encounter not found: " + encounterUuid);
        }
        // Create log entry with PENDING status FIRST
        AdverseDrugReactionEmailLog log = new AdverseDrugReactionEmailLog();
        log.setPatient(patient);
        log.setEncounter(encounter);
        log.setRecipientEmail(recipientEmail);
        log.setUser(currentUser);
        log.setStatus("PENDING");
        log.setDateSent(new Date());

        // Save immediately - this will commit
        adrEmailLogDao.save(log);
        Context.flushSession(); // Force immediate save

        System.out.println("Log saved with ID: " + log.getId());

        try {
            // Generate PDF
            ADRReportingFormGenerator generator = new ADRReportingFormGenerator();
            String fileName = "ADR_Reporting_Form_" + System.currentTimeMillis() + ".pdf";
            String filePath = System.getProperty("java.io.tmpdir") + File.separator + fileName;

            ADRFormData formData = ADRReportingFormGenerator.createSampleFormData(patientUuid);
            generator.generatePDF(filePath, formData);

            byte[] pdfBytes = Files.readAllBytes(Paths.get(filePath));

            // Get email configuration
            Properties props = new Properties();
            props.put("mail.smtp.host", Context.getAdministrationService().getGlobalProperty("mail.smtp.host"));
            props.put("mail.smtp.port", Context.getAdministrationService().getGlobalProperty("mail.smtp.port"));
            props.put("mail.from", Context.getAdministrationService().getGlobalProperty("mail.from"));
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            Context.getAdministrationService().getGlobalProperty("mail.smtp.username"),
                            Context.getAdministrationService().getGlobalProperty("mail.smtp.password"));
                }
            });

            // Create and send email
            MimeMessage message = new MimeMessage(session);
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(recipientEmail);
            helper.setFrom(props.getProperty("mail.from"));
            helper.setSubject("Adverse Drug Reaction Report for " + patient.getPersonName());
            helper.setText("Please find attached the ADR report.");
            helper.addAttachment("ADR_Report_" + patient.getPatientId() + ".pdf",
                    new ByteArrayResource(pdfBytes));

            Transport.send(message);

            // Update status to SENT
            log.setStatus("SENT");
            adrEmailLogDao.save(log);

            // Clean up temp file
            try {
                new File(filePath).delete();
            } catch (Exception e) {
                System.err.println("Could not delete temp file: " + filePath);
            }

        } catch (Exception e) {
            // Update status to FAILED
            log.setStatus("FAILED");
            adrEmailLogDao.save(log);

            System.err.println("ERROR generating/sending report: " + e.getMessage());
            e.printStackTrace();

            // Re-throw so the caller knows it failed
            throw new RuntimeException("Failed to send ADR report: " + e.getMessage(), e);
        }
    }

}