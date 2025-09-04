/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.*;


import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.MedicationDispense;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.MedicationDispenseService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.patient.PatientCalculationService;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.parameter.MedicationDispenseCriteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class ADRReportingFormGenerator {

    // Data models for form information
    public static class ADRFormData {
        public String reportTitle = "";
        public ReportType reportType = new ReportType();
        public ProductCategory productCategory = new ProductCategory();
        public InstitutionDetails institutionDetails = new InstitutionDetails();
        public PatientInfo patientInfo = new PatientInfo();
        public AdverseReaction adverseReaction = new AdverseReaction();
        public String medicalHistory = "";
        public List<CurrentMedication> currentMedications = new ArrayList<>();
        public List<PastMedication> pastMedications = new ArrayList<>();
        public DechallengeRechallenge dechallengeRechallenge = new DechallengeRechallenge();
        public String labInvestigations = "";
        public ReactionGrading reactionGrading = new ReactionGrading();
        public String otherComments = "";
        public ReporterDetails reporterDetails = new ReporterDetails();
    }

    public static class ReportType {
        public boolean isSuspectedADR = false;
        public boolean isTherapeuticIneffectiveness = false;
        public boolean isInitialReport = false;
        public boolean isFollowUpReport = false;
    }

    public static class ProductCategory {
        public boolean isMedicinalProduct = false;
        public boolean isBloodProducts = false;
        public boolean isHerbalProduct = false;
        public boolean isCosmeceuticals = false;
        public String others = "";
    }

    public static class InstitutionDetails {
        public String institutionName = "";
        public String contactTelNo = "";
        public String facilityCode = "";
        public String county = "";
    }

    public static class PatientInfo {
        public String nameInitials = "";
        public String ipOpNo = "";
        public String dobAge = "";
        public String address = "";
        public String wardClinic = "";
        public boolean isMale = false;
        public boolean isFemale = false;
        public boolean hasAllergy = false;
        public String allergyDetails = "";
        public PregnancyStatus pregnancyStatus = new PregnancyStatus();
        public String weight = "";
        public String height = "";
    }

    public static class PregnancyStatus {
        public boolean notApplicable = false;
        public boolean notPregnant = false;
        public boolean firstTrimester = false;
        public boolean secondTrimester = false;
        public boolean thirdTrimester = false;
    }

    public static class AdverseReaction {
        public String onsetDate = "";
        public String description = "";
    }

    public static class CurrentMedication {
        public boolean isSuspected = false;
        public String innGenericName = "";
        public String brandName = "";
        public String batchLotNo = "";
        public String manufacturer = "";
        public String dose = "";
        public String route = "";
        public String frequency = "";
        public String startDate = "";
        public String stopDate = "";
        public String indication = "";
    }

    public static class PastMedication {
        public String innGenericName = "";
        public String brandName = "";
        public String batchLotNo = "";
        public String manufacturer = "";
        public String dose = "";
        public String route = "";
        public String frequency = "";
        public String startDate = "";
        public String stopDate = "";
        public String indication = "";
    }

    public static class DechallengeRechallenge {
        public String reactionResolvedAfterStopped = "";
        public String reactionReappearedAfterReintroduced = "";
    }

    public static class ReactionGrading {
        public String severity = ""; // Mild, Moderate, Severe, Fatal, Unknown
        public boolean isSerious = false;
        public SeriousnessCriteria seriousnessCriteria = new SeriousnessCriteria();
        public String actionTaken = "";
        public String outcome = "";
    }

    public static class SeriousnessCriteria {
        public boolean hospitalization = false;
        public boolean disability = false;
        public boolean congenitalAnomaly = false;
        public boolean lifeThreatening = false;
        public boolean death = false;
    }

    public static class ReporterDetails {
        public String initialReporterName = "";
        public String cadreDesignation = "";
        public String mobileNo = "";
        public String email = "";
        public String reportDate = "";
        public String submitterName = "";
        public String submitterCadre = "";
        public String submitterMobile = "";
        public String submitterEmail = "";
        public String submissionDate = "";
    }

    // Font and color constants
    private PdfFont normalFont;
    private PdfFont boldFont;
    private PdfFont titleFont;
    private DeviceRgb yellowBackground;

    public ADRReportingFormGenerator() throws IOException {
        initializeFonts();
    }

    private void initializeFonts() throws IOException {
        normalFont = PdfFontFactory.createFont();
        boldFont = PdfFontFactory.createFont();
        titleFont = PdfFontFactory.createFont();
        yellowBackground = new DeviceRgb(255, 242, 0);
    }

    public void generatePDF(String filePath) throws IOException {
        generatePDF(filePath, new ADRFormData());
    }

    public void generatePDF(String filePath, ADRFormData formData) throws IOException {
        PdfWriter writer = new PdfWriter(filePath);
        PdfDocument pdfDoc = new PdfDocument(writer);
        addYellowBackgroundWithEventHandler(pdfDoc);
        Document document = new Document(pdfDoc, PageSize.A4);

        // Set yellow background for all pages
        document.setBackgroundColor(yellowBackground);
        addYellowBackground(pdfDoc);

        // Add content
        addHeader(document);
        addTitle(document, formData);
        addReportType(document, formData);
        addProductCategory(document, formData);
        addInstitutionDetails(document, formData);
        addPatientInformation(document, formData);
        addAdverseReaction(document, formData);
        addMedicalHistory(document, formData);
        addCurrentMedications(document, formData);
        addPastMedications(document, formData);
        addDechallengeRechallenge(document, formData);
        addLabInvestigations(document, formData);
        addReactionGrading(document, formData);
        addOtherComments(document, formData);
        addReporterDetails(document, formData);
        addFooterSection(document);

        document.close();
    }

    private void addYellowBackground(PdfDocument pdfDoc) {

        // This will be called after all content is added
        int numberOfPages = pdfDoc.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {
            PdfCanvas canvas = new PdfCanvas(pdfDoc.getPage(i).newContentStreamBefore(),
                    pdfDoc.getPage(i).getResources(),
                    pdfDoc);
            Rectangle pageSize = pdfDoc.getPage(i).getPageSize();
            canvas.setFillColor(yellowBackground)
                    .rectangle(0, 0, pageSize.getWidth(), pageSize.getHeight())
                    .fill();
        }
    }

    private void addYellowBackgroundWithEventHandler(PdfDocument pdfDoc) {
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new IEventHandler() {
            @Override
            public void handleEvent(Event event) {
                PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
                PdfDocument pdfDoc = docEvent.getDocument();
                PdfPage page = docEvent.getPage();

                PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(),
                        page.getResources(),
                        pdfDoc);
                Rectangle pageSize = page.getPageSize();
                canvas.setFillColor(yellowBackground)
                        .rectangle(0, 0, pageSize.getWidth(), pageSize.getHeight())
                        .fill();
            }
        });
    }

    private void addHeader(Document document) {
        document.add(new Paragraph("(FOM001/MIP/PMS/SOP/001)")
                .setFont(normalFont).setFontSize(8).setFontColor(new DeviceRgb(0, 0, 0))
                .setTextAlignment(TextAlignment.LEFT));

        document.add(new Paragraph("MINISTRY OF HEALTH")
                .setFont(boldFont).setFontSize(12).setFontColor(new DeviceRgb(0, 0, 0))
                .setTextAlignment(TextAlignment.CENTER).setBold());

        document.add(new Paragraph("PHARMACY AND POISONS BOARD")
                .setFont(boldFont).setFontSize(12).setFontColor(new DeviceRgb(0, 0, 0))
                .setTextAlignment(TextAlignment.CENTER).setBold());

        document.add(new Paragraph("P.O. Box 27663-00506 NAIROBI")
                .setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0))
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("Tel: (020)-3562107 Ext 114, 0720 608811, 0733 884411 Fax: (020) 2713431/2713409")
                .setFont(normalFont).setFontSize(8).setFontColor(new DeviceRgb(0, 0, 0))
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("Email: pv@pharmacyboardkenya.org")
                .setFont(normalFont).setFontSize(8).setFontColor(new DeviceRgb(0, 0, 255))
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("\n"));
    }

    private void addTitle(Document document, ADRFormData formData) {
        document.add(new Paragraph("SUSPECTED ADVERSE DRUG REACTION REPORTING FORM")
                .setFont(titleFont).setFontSize(14).setFontColor(new DeviceRgb(255, 0, 0)).setBold()
                .setTextAlignment(TextAlignment.CENTER));

        Paragraph reportTitle = new Paragraph();
        reportTitle.add(
                new Text("REPORT TITLE: ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        reportTitle.add(new Text(formData.reportTitle).setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)).setUnderline());
        document.add(reportTitle);

        document.add(new Paragraph("\n"));
    }

    private void addReportType(Document document, ADRFormData formData) {
        document.add(new Paragraph("The report is on: ").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));

        Paragraph reportOn = new Paragraph();
        reportOn.add(createCheckbox(formData.reportType.isSuspectedADR));
        reportOn.add(new Text(" Suspected adverse drug reaction ").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));
        reportOn.add(createCheckbox(formData.reportType.isTherapeuticIneffectiveness));
        reportOn.add(new Text(" Therapeutic ineffectiveness ").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));
        document.add(reportOn);

        document.add(new Paragraph("Report Type: ").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));

        Paragraph reportType = new Paragraph();
        reportType.add(createCheckbox(formData.reportType.isInitialReport));
        reportType.add(
                new Text(" Initial Report ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        reportType.add(createCheckbox(formData.reportType.isFollowUpReport));
        reportType.add(new Text(" Follow Up Report ").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));
        document.add(reportType);

        document.add(new Paragraph("\n"));
    }

    private void addProductCategory(Document document, ADRFormData formData) {
        document.add(new Paragraph("Product category (Tick appropriate box)")
                .setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)).setBold());

        Paragraph productCat1 = new Paragraph();
        productCat1.add(createCheckbox(formData.productCategory.isMedicinalProduct));
        productCat1.add(new Text(" Medicinal product     ").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));
        productCat1.add(createCheckbox(formData.productCategory.isBloodProducts));
        productCat1.add(new Text(" Blood and blood products     ").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));
        productCat1.add(createCheckbox(formData.productCategory.isHerbalProduct));
        productCat1.add(
                new Text(" Herbal product").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        document.add(productCat1);

        Paragraph productCat2 = new Paragraph();
        productCat2.add(createCheckbox(formData.productCategory.isCosmeceuticals));
        productCat2.add(new Text(" Cosmeceuticals     ").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));
        productCat2.add(createCheckbox(!formData.productCategory.others.isEmpty()));
        productCat2.add(new Text(" Others: ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        productCat2.add(new Text(formData.productCategory.others).setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)).setUnderline());
        document.add(productCat2);

        document.add(new Paragraph("\n"));
    }

    private void addInstitutionDetails(Document document, ADRFormData formData) {
        document.add(new Paragraph("Institution details").setFont(boldFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)).setBold());

        Table institutionTable = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();

        institutionTable.addCell(new Cell().add(new Paragraph("Name of Institution").setFont(normalFont).setFontSize(9))
                .setFontColor(new DeviceRgb(0, 0, 0)).setBorder(Border.NO_BORDER));
        institutionTable.addCell(new Cell().add(new Paragraph("Contact/Tel No.").setFont(normalFont).setFontSize(9))
                .setFontColor(new DeviceRgb(0, 0, 0)).setBorder(Border.NO_BORDER));
        institutionTable.addCell(new Cell().add(new Paragraph("Facility Code:").setFont(normalFont).setFontSize(9))
                .setFontColor(new DeviceRgb(0, 0, 0)).setBorder(Border.NO_BORDER));
        institutionTable.addCell(new Cell().add(new Paragraph("County:").setFont(normalFont).setFontSize(9))
                .setFontColor(new DeviceRgb(0, 0, 0)).setBorder(Border.NO_BORDER));

        institutionTable.addCell(new Cell()
                .add(new Paragraph(formData.institutionDetails.institutionName).setFont(normalFont).setFontSize(9))
                .setFontColor(new DeviceRgb(0, 0, 0)).setBorderBottom(new SolidBorder(1)).setBorderTop(Border.NO_BORDER)
                .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER));
        institutionTable.addCell(new Cell()
                .add(new Paragraph(formData.institutionDetails.contactTelNo).setFont(normalFont).setFontSize(9))
                .setFontColor(new DeviceRgb(0, 0, 0)).setBorderBottom(new SolidBorder(1)).setBorderTop(Border.NO_BORDER)
                .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER));
        institutionTable.addCell(new Cell()
                .add(new Paragraph(formData.institutionDetails.facilityCode).setFont(normalFont).setFontSize(9))
                .setFontColor(new DeviceRgb(0, 0, 0)).setBorderBottom(new SolidBorder(1)).setBorderTop(Border.NO_BORDER)
                .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER));
        institutionTable.addCell(new Cell()
                .add(new Paragraph(formData.institutionDetails.county).setFont(normalFont).setFontSize(9))
                .setFontColor(new DeviceRgb(0, 0, 0)).setBorderBottom(new SolidBorder(1)).setBorderTop(Border.NO_BORDER)
                .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER));

        document.add(institutionTable);
        document.add(new Paragraph("\n"));
    }

    private void addPatientInformation(Document document, ADRFormData formData) {
        document.add(new Paragraph("1. Patient Information").setFont(boldFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)).setBold());

        // Patient details table
        Table patientTable = new Table(UnitValue.createPercentArray(new float[] { 3, 2, 2, 3 })).useAllAvailableWidth();

        // Row 1
        patientTable.addCell(createUnderlinedCell("Patient name/initials: " + formData.patientInfo.nameInitials));
        patientTable.addCell(createUnderlinedCell("IP/OP. No: " + formData.patientInfo.ipOpNo));
        patientTable.addCell(createUnderlinedCell("D.O.B/Age: " + formData.patientInfo.dobAge));
        patientTable.addCell(createUnderlinedCell("Patient address: " + formData.patientInfo.address));

        // Row 2
        patientTable.addCell(createUnderlinedCell("WARD/CLINIC: " + formData.patientInfo.wardClinic));
        patientTable.addCell(new Cell().add(new Paragraph("(NAME/NUMBER)").setFont(normalFont).setFontSize(8))
                .setBorder(Border.NO_BORDER));
        patientTable.addCell(new Cell().setBorder(Border.NO_BORDER));
        patientTable.addCell(new Cell().setBorder(Border.NO_BORDER));

        document.add(patientTable);

        // Gender and allergy
        Paragraph genderAllergy = new Paragraph();
        genderAllergy
                .add(new Text("Gender: ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        genderAllergy.add(createCheckbox(formData.patientInfo.isMale));
        genderAllergy.add(new Text(" Male  ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        genderAllergy.add(createCheckbox(formData.patientInfo.isFemale));
        genderAllergy.add(new Text(" Female     Any known allergy: ").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));
        genderAllergy.add(createCheckbox(!formData.patientInfo.hasAllergy));
        genderAllergy.add(new Text(" No  ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        genderAllergy.add(createCheckbox(formData.patientInfo.hasAllergy));
        genderAllergy.add(
                new Text(" Yes (specify): ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        genderAllergy
                .add(new Text(formData.patientInfo.allergyDetails).setFont(normalFont).setFontSize(10).setUnderline());
        document.add(genderAllergy);

        // Pregnancy status
        document.add(new Paragraph("Pregnancy status").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));

        Paragraph pregnancy1 = new Paragraph();
        pregnancy1.add(createCheckbox(formData.patientInfo.pregnancyStatus.notApplicable));
        pregnancy1.add(new Text(" Not Applicable     ").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));
        pregnancy1.add(createCheckbox(formData.patientInfo.pregnancyStatus.notPregnant));
        pregnancy1.add(
                new Text(" Not pregnant").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        document.add(pregnancy1);

        Paragraph pregnancy2 = new Paragraph();
        pregnancy2.add(createCheckbox(formData.patientInfo.pregnancyStatus.firstTrimester));
        pregnancy2.add(new Text(" 1st Trimester     ").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));
        pregnancy2.add(createCheckbox(formData.patientInfo.pregnancyStatus.secondTrimester));
        pregnancy2.add(new Text(" 2nd Trimester     ").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));
        pregnancy2.add(createCheckbox(formData.patientInfo.pregnancyStatus.thirdTrimester));
        pregnancy2.add(
                new Text(" 3rd Trimester").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        document.add(pregnancy2);

        // Weight and Height
        Paragraph weightHeight = new Paragraph();
        weightHeight.add(new Text("Weight: ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        weightHeight.add(new Text(formData.patientInfo.weight + " kg     ").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)).setUnderline());
        weightHeight.add(new Text("Height: ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        weightHeight.add(new Text(formData.patientInfo.height + " cm").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)).setUnderline());
        document.add(weightHeight);

        document.add(new Paragraph("\n"));
    }

    private void addAdverseReaction(Document document, ADRFormData formData) {
        document.add(new Paragraph("2. Suspected Adverse Reaction").setFont(boldFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)).setBold());

        Paragraph onsetDate = new Paragraph();
        onsetDate.add(new Text("Date of onset of reaction: ").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));
        onsetDate.add(new Text(formData.adverseReaction.onsetDate).setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)).setUnderline());
        document.add(onsetDate);

        Paragraph description = new Paragraph();
        description.add(new Text("Brief description of reaction: ").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));
        description.add(new Text(formData.adverseReaction.description).setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)).setUnderline());
        document.add(description);

        document.add(new Paragraph("\n"));
    }

    private void addMedicalHistory(Document document, ADRFormData formData) {
        document.add(new Paragraph(
                "3. Medical History. (Other relevant history including pre-existing medical conditions e.g. allergies, smoking, alcohol use, hepatic/ renal dysfunction etc)")
                .setFont(boldFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)).setBold());

        document.add(new Paragraph(formData.medicalHistory).setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));
        // document.add(new
        // Paragraph("_".repeat(100)).setFont(normalFont).setFontSize(10));
        // document.add(new
        // Paragraph("_".repeat(100)).setFont(normalFont).setFontSize(10));
        // document.add(new
        // Paragraph("_".repeat(100)).setFont(normalFont).setFontSize(10));

        document.add(new Paragraph("\n"));
    }

    private void addCurrentMedications(Document document, ADRFormData formData) {
        document.add(new Paragraph(
                "4. List all medicines being currently used by the patient including OTC, and herbal products (*** Tick the suspected medicine)")
                .setFont(boldFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)).setBold());

        Table medicineTable = new Table(
                UnitValue.createPercentArray(new float[] { 1, 2, 2, 1.5f, 1.5f, 1, 1, 1, 2, 1.5f }))
                .useAllAvailableWidth();

        // Headers
        medicineTable.addHeaderCell(new Cell().add(new Paragraph("Tick").setFont(boldFont).setFontSize(8))
                .setFontColor(new DeviceRgb(0, 0, 0)));
        medicineTable.addHeaderCell(new Cell().add(new Paragraph("INN/Generic Name").setFont(boldFont).setFontSize(8)
                .setFontColor(new DeviceRgb(0, 0, 0))));
        medicineTable.addHeaderCell(new Cell().add(new Paragraph("Brand Name").setFont(boldFont).setFontSize(8))
                .setFontColor(new DeviceRgb(0, 0, 0)));
        medicineTable.addHeaderCell(new Cell().add(new Paragraph("Batch/Lot No.").setFont(boldFont).setFontSize(8))
                .setFontColor(new DeviceRgb(0, 0, 0)));
        medicineTable.addHeaderCell(new Cell().add(new Paragraph("Manufacturer").setFont(boldFont).setFontSize(8))
                .setFontColor(new DeviceRgb(0, 0, 0)));
        medicineTable.addHeaderCell(new Cell().add(new Paragraph("Dose").setFont(boldFont).setFontSize(8))
                .setFontColor(new DeviceRgb(0, 0, 0)));
        medicineTable.addHeaderCell(new Cell().add(new Paragraph("Route").setFont(boldFont).setFontSize(8))
                .setFontColor(new DeviceRgb(0, 0, 0)));
        medicineTable.addHeaderCell(new Cell().add(new Paragraph("Frequency").setFont(boldFont).setFontSize(8))
                .setFontColor(new DeviceRgb(0, 0, 0)));
        medicineTable.addHeaderCell(new Cell().add(new Paragraph("Treatment Period").setFont(boldFont).setFontSize(8))
                .setFontColor(new DeviceRgb(0, 0, 0)));
        medicineTable.addHeaderCell(new Cell().add(new Paragraph("Indication").setFont(boldFont).setFontSize(8))
                .setFontColor(new DeviceRgb(0, 0, 0)));

        // Add medication data or empty rows
        int rowsToAdd = Math.max(3, formData.currentMedications.size());
        for (int i = 0; i < rowsToAdd; i++) {
            if (i < formData.currentMedications.size()) {
                CurrentMedication med = formData.currentMedications.get(i);
                medicineTable.addCell(new Cell().add(new Paragraph(med.isSuspected ? "✓" : "").setFont(normalFont)
                        .setFontSize(8).setFontColor(new DeviceRgb(0, 0, 0))));
                medicineTable.addCell(new Cell().add(new Paragraph(med.innGenericName).setFont(normalFont)
                        .setFontSize(8).setFontColor(new DeviceRgb(0, 0, 0))));
                medicineTable.addCell(new Cell().add(new Paragraph(med.brandName).setFont(normalFont).setFontSize(8)
                        .setFontColor(new DeviceRgb(0, 0, 0))));
                medicineTable.addCell(new Cell().add(new Paragraph(med.batchLotNo).setFont(normalFont).setFontSize(8)
                        .setFontColor(new DeviceRgb(0, 0, 0))));
                medicineTable.addCell(new Cell().add(new Paragraph(med.manufacturer).setFont(normalFont).setFontSize(8)
                        .setFontColor(new DeviceRgb(0, 0, 0))));
                medicineTable.addCell(new Cell().add(new Paragraph(med.dose).setFont(normalFont).setFontSize(8)
                        .setFontColor(new DeviceRgb(0, 0, 0))));
                medicineTable.addCell(new Cell().add(new Paragraph(med.route).setFont(normalFont).setFontSize(8)
                        .setFontColor(new DeviceRgb(0, 0, 0))));
                medicineTable.addCell(new Cell().add(new Paragraph(med.frequency).setFont(normalFont).setFontSize(8)
                        .setFontColor(new DeviceRgb(0, 0, 0))));
                medicineTable.addCell(new Cell().add(new Paragraph(med.startDate + " - " + med.stopDate)
                        .setFont(normalFont).setFontSize(8).setFontColor(new DeviceRgb(0, 0, 0))));
                medicineTable.addCell(new Cell().add(new Paragraph(med.indication).setFont(normalFont).setFontSize(8)
                        .setFontColor(new DeviceRgb(0, 0, 0))));
            } else {
                // Empty rows
                for (int j = 0; j < 10; j++) {
                    medicineTable.addCell(new Cell().add(new Paragraph(" ").setFont(normalFont).setFontSize(8))
                            .setFontColor(new DeviceRgb(0, 0, 0)));
                }
            }
        }

        document.add(medicineTable);
        document.add(new Paragraph("\n"));
    }

    private void addPastMedications(Document document, ADRFormData formData) {
        document.add(new Paragraph(
                "5. Past medication history (List all medicines used in the last 3 months including OTC, herbals, if pregnant indicate medicines used in the 1st trimester)")
                .setFont(boldFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)).setBold());

        Table pastMedicineTable = new Table(
                UnitValue.createPercentArray(new float[] { 2, 2, 1.5f, 1.5f, 1, 1, 1, 2, 1.5f }))
                .useAllAvailableWidth();

        // Headers
        pastMedicineTable.addHeaderCell(new Cell().add(new Paragraph("INN/Generic Name").setFont(boldFont)
                .setFontSize(8).setFontColor(new DeviceRgb(0, 0, 0))));
        pastMedicineTable.addHeaderCell(new Cell().add(
                new Paragraph("Brand Name").setFont(boldFont).setFontSize(8).setFontColor(new DeviceRgb(0, 0, 0))));
        pastMedicineTable.addHeaderCell(new Cell().add(
                new Paragraph("Batch/Lot No.").setFont(boldFont).setFontSize(8).setFontColor(new DeviceRgb(0, 0, 0))));
        pastMedicineTable.addHeaderCell(new Cell().add(
                new Paragraph("Manufacturer").setFont(boldFont).setFontSize(8).setFontColor(new DeviceRgb(0, 0, 0))));
        pastMedicineTable.addHeaderCell(new Cell()
                .add(new Paragraph("Dose").setFont(boldFont).setFontSize(8).setFontColor(new DeviceRgb(0, 0, 0))));
        pastMedicineTable.addHeaderCell(new Cell()
                .add(new Paragraph("Route").setFont(boldFont).setFontSize(8).setFontColor(new DeviceRgb(0, 0, 0))));
        pastMedicineTable.addHeaderCell(new Cell()
                .add(new Paragraph("Frequency").setFont(boldFont).setFontSize(8).setFontColor(new DeviceRgb(0, 0, 0))));
        pastMedicineTable.addHeaderCell(new Cell().add(new Paragraph("Treatment Period").setFont(boldFont)
                .setFontSize(8).setFontColor(new DeviceRgb(0, 0, 0))));
        pastMedicineTable.addHeaderCell(new Cell().add(
                new Paragraph("Indication").setFont(boldFont).setFontSize(8).setFontColor(new DeviceRgb(0, 0, 0))));

        // Add past medication data or empty rows
        int rowsToAdd = Math.max(3, formData.pastMedications.size());
         for (int i = 0; i < rowsToAdd; i++) {
            if (i < formData.pastMedications.size()) {
                PastMedication med = formData.pastMedications.get(i);
                pastMedicineTable.addCell(new Cell().add(new Paragraph(med.innGenericName).setFont(normalFont)
                        .setFontSize(8).setFontColor(new DeviceRgb(0, 0, 0))));
                pastMedicineTable.addCell(new Cell().add(new Paragraph(med.brandName).setFont(normalFont).setFontSize(8)
                        .setFontColor(new DeviceRgb(0, 0, 0))));
                pastMedicineTable.addCell(new Cell().add(new Paragraph(med.batchLotNo).setFont(normalFont).setFontSize(8)
                        .setFontColor(new DeviceRgb(0, 0, 0))));
                pastMedicineTable.addCell(new Cell().add(new Paragraph(med.manufacturer).setFont(normalFont).setFontSize(8)
                        .setFontColor(new DeviceRgb(0, 0, 0))));
                pastMedicineTable.addCell(new Cell().add(new Paragraph(med.dose).setFont(normalFont).setFontSize(8)
                        .setFontColor(new DeviceRgb(0, 0, 0))));
                pastMedicineTable.addCell(new Cell().add(new Paragraph(med.route).setFont(normalFont).setFontSize(8)
                        .setFontColor(new DeviceRgb(0, 0, 0))));
                pastMedicineTable.addCell(new Cell().add(new Paragraph(med.frequency).setFont(normalFont).setFontSize(8)
                        .setFontColor(new DeviceRgb(0, 0, 0))));
                pastMedicineTable.addCell(new Cell().add(new Paragraph(med.startDate + " - " + med.stopDate)
                        .setFont(normalFont).setFontSize(8).setFontColor(new DeviceRgb(0, 0, 0))));
                pastMedicineTable.addCell(new Cell().add(new Paragraph(med.indication).setFont(normalFont).setFontSize(8)
                        .setFontColor(new DeviceRgb(0, 0, 0))));
            } else {
                // Empty rows
                for (int j = 0; j < 10; j++) {
                    pastMedicineTable.addCell(new Cell().add(new Paragraph(" ").setFont(normalFont).setFontSize(8))
                            .setFontColor(new DeviceRgb(0, 0, 0)));
                }
            }
        }


        document.add(pastMedicineTable);
        document.add(new Paragraph("\n"));

    }

    private void addDechallengeRechallenge(Document document, ADRFormData formData) {
        document.add(new Paragraph("6. Dechallenge/Rechallenge").setFont(boldFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)).setBold());

        document.add(new Paragraph("Did the reaction resolve after the drug was stopped or when the dose was reduced?")
                .setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));

        Paragraph dechallenge = new Paragraph();
        dechallenge.add(createCheckbox("Yes".equals(formData.dechallengeRechallenge.reactionResolvedAfterStopped)));
        dechallenge.add(new Text(" Yes     ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        dechallenge.add(createCheckbox("No".equals(formData.dechallengeRechallenge.reactionResolvedAfterStopped)));
        dechallenge.add(new Text(" No     ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        dechallenge.add(createCheckbox("Unknown".equals(formData.dechallengeRechallenge.reactionResolvedAfterStopped)));
        dechallenge.add(
                new Text(" Unknown     ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        dechallenge.add(
                createCheckbox("Not Applicable".equals(formData.dechallengeRechallenge.reactionResolvedAfterStopped)));
        dechallenge.add(
                new Text(" Not Applicable  ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        document.add(dechallenge);

        document.add(new Paragraph("Did the reaction reappear after the drug was reintroduced?")
                .setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));

        Paragraph rechallenge = new Paragraph();
        rechallenge
                .add(createCheckbox("Yes".equals(formData.dechallengeRechallenge.reactionReappearedAfterReintroduced)));
        rechallenge.add(new Text(" Yes     ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        rechallenge
                .add(createCheckbox("No".equals(formData.dechallengeRechallenge.reactionReappearedAfterReintroduced)));
        rechallenge.add(new Text(" No     ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        rechallenge.add(
                createCheckbox("Unknown".equals(formData.dechallengeRechallenge.reactionReappearedAfterReintroduced)));
        rechallenge.add(
                new Text(" Unknown     ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        rechallenge.add(createCheckbox(
                "Not Applicable".equals(formData.dechallengeRechallenge.reactionReappearedAfterReintroduced)));
        rechallenge.add(
                new Text(" Not Applicable ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        document.add(rechallenge);

        document.add(new Paragraph("\n"));
    }

    private void addLabInvestigations(Document document, ADRFormData formData) {
        document.add(new Paragraph("7. Any lab investigations and results")
                .setFont(boldFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)).setBold());

        document.add(new Paragraph(formData.labInvestigations).setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));
        // document.add(new
        // Paragraph("_".repeat(100)).setFont(normalFont).setFontSize(10));

        document.add(new Paragraph("\n"));
    }

    private void addReactionGrading(Document document, ADRFormData formData) {
        document.add(new Paragraph("8. Grading of the reaction").setFont(boldFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)).setBold());

        // Severity
        Paragraph severity = new Paragraph();
        severity.add(new Text("Severity: ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        severity.add(createCheckbox("Mild".equals(formData.reactionGrading.severity)));
        severity.add(new Text(" Mild     ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        severity.add(createCheckbox("Moderate".equals(formData.reactionGrading.severity)));
        severity.add(
                new Text(" Moderate     ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        severity.add(createCheckbox("Severe".equals(formData.reactionGrading.severity)));
        severity.add(new Text(" Severe     ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        severity.add(createCheckbox("Fatal".equals(formData.reactionGrading.severity)));
        severity.add(new Text(" Fatal     ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        severity.add(createCheckbox("Unknown".equals(formData.reactionGrading.severity)));
        severity.add(new Text(" Unknown").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        document.add(severity);

        // Seriousness
        Paragraph seriousness = new Paragraph();
        seriousness.add(new Text("Is the reaction serious? ").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));
        seriousness.add(createCheckbox(formData.reactionGrading.isSerious));
        seriousness.add(new Text(" Yes     ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        seriousness.add(createCheckbox(!formData.reactionGrading.isSerious));
        seriousness.add(new Text(" No").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        document.add(seriousness);

        if (formData.reactionGrading.isSerious) {
            document.add(new Paragraph("If serious, tick criteria that apply:")
                    .setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));

            Paragraph criteria = new Paragraph();
            criteria.add(createCheckbox(formData.reactionGrading.seriousnessCriteria.hospitalization));
            criteria.add(new Text(" Hospitalization     ").setFont(normalFont).setFontSize(10)
                    .setFontColor(new DeviceRgb(0, 0, 0)));
            criteria.add(createCheckbox(formData.reactionGrading.seriousnessCriteria.disability));
            criteria.add(new Text(" Disability     ").setFont(normalFont).setFontSize(10)
                    .setFontColor(new DeviceRgb(0, 0, 0)));
            criteria.add(createCheckbox(formData.reactionGrading.seriousnessCriteria.congenitalAnomaly));
            criteria.add(new Text(" Congenital anomaly     ").setFont(normalFont).setFontSize(10)
                    .setFontColor(new DeviceRgb(0, 0, 0)));
            criteria.add(createCheckbox(formData.reactionGrading.seriousnessCriteria.lifeThreatening));
            criteria.add(new Text(" Life threatening     ").setFont(normalFont).setFontSize(10)
                    .setFontColor(new DeviceRgb(0, 0, 0)));
            criteria.add(createCheckbox(formData.reactionGrading.seriousnessCriteria.death));
            criteria.add(new Text(" Death").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
            document.add(criteria);
        }

        // Action taken - split into multiple lines for better readability
        document.add(new Paragraph("Action taken: ").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));

        Paragraph actionTaken1 = new Paragraph();
        actionTaken1.add(createCheckbox("Drug withdrawn".equals(formData.reactionGrading.actionTaken)));
        actionTaken1.add(new Text(" Drug withdrawn     ").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));
        actionTaken1.add(createCheckbox("Dose reduced".equals(formData.reactionGrading.actionTaken)));
        actionTaken1.add(new Text(" Dose reduced     ").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));
        actionTaken1.add(createCheckbox("Dose increased".equals(formData.reactionGrading.actionTaken)));
        actionTaken1.add(
                new Text(" Dose increased").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        document.add(actionTaken1);

        Paragraph actionTaken2 = new Paragraph();
        actionTaken2.add(createCheckbox("Drug Changed".equals(formData.reactionGrading.actionTaken)));
        actionTaken2.add(new Text(" Drug Changed     ").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));
        actionTaken2.add(createCheckbox("Dose not Changed".equals(formData.reactionGrading.actionTaken)));
        actionTaken2.add(new Text(" Dose not Changed     ").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));
        actionTaken2.add(createCheckbox("Unknown".equals(formData.reactionGrading.actionTaken)));
        actionTaken2.add(
                new Text(" Unknown     ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        actionTaken2.add(createCheckbox("Not Applicable".equals(formData.reactionGrading.actionTaken)));
        actionTaken2.add(
                new Text(" Not Applicable").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        document.add(actionTaken2);

        // Outcome - split into multiple lines
        document.add(
                new Paragraph("Outcome: ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));

        Paragraph outcome1 = new Paragraph();
        outcome1.add(createCheckbox("Recovered".equals(formData.reactionGrading.outcome)));
        outcome1.add(
                new Text(" Recovered     ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        outcome1.add(createCheckbox("Not recovered".equals(formData.reactionGrading.outcome)));
        outcome1.add(new Text(" Not recovered     ").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));
        outcome1.add(createCheckbox("Recovering".equals(formData.reactionGrading.outcome)));
        outcome1.add(new Text(" Recovering").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        document.add(outcome1);

        Paragraph outcome2 = new Paragraph();
        outcome2.add(createCheckbox("Recovered with sequelae".equals(formData.reactionGrading.outcome)));
        outcome2.add(new Text(" Recovered with sequelae     ").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));
        outcome2.add(createCheckbox("Fatal".equals(formData.reactionGrading.outcome)));
        outcome2.add(new Text(" Fatal     ").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        outcome2.add(createCheckbox("Unknown".equals(formData.reactionGrading.outcome)));
        outcome2.add(new Text(" Unknown").setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)));
        document.add(outcome2);

        document.add(new Paragraph("\n"));
    }

    private void addOtherComments(Document document, ADRFormData formData) {
        document.add(new Paragraph("9. Any other comment").setFont(boldFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)).setBold());

        document.add(new Paragraph(formData.otherComments).setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)));
        // document.add(new
        // Paragraph("_".repeat(100)).setFont(normalFont).setFontSize(10));

        document.add(new Paragraph("\n"));
    }

    private void addReporterDetails(Document document, ADRFormData formData) {
        document.add(new Paragraph("10. Reporter Details").setFont(boldFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)).setBold());

        // Initial reporter
        document.add(new Paragraph("Initial Reporter:").setFont(normalFont).setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0)).setBold());

        Table reporterTable = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();

        reporterTable.addCell(createUnderlinedCell("Name: " + formData.reporterDetails.initialReporterName));
        reporterTable.addCell(createUnderlinedCell("Cadre/Designation: " + formData.reporterDetails.cadreDesignation));
        reporterTable.addCell(createUnderlinedCell("Mobile No: " + formData.reporterDetails.mobileNo));
        reporterTable.addCell(createUnderlinedCell("Email: " + formData.reporterDetails.email));
        reporterTable.addCell(createUnderlinedCell("Date: " + formData.reporterDetails.reportDate));
        reporterTable.addCell(new Cell().setBorder(Border.NO_BORDER));

        document.add(reporterTable);

        // Submitter
        document.add(new Paragraph("\nSubmitter (if different from initial reporter):")
                .setFont(normalFont).setFontSize(10).setFontColor(new DeviceRgb(0, 0, 0)).setBold());

        Table submitterTable = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();

        submitterTable.addCell(createUnderlinedCell("Name: " + formData.reporterDetails.submitterName));
        submitterTable.addCell(createUnderlinedCell("Cadre/Designation: " + formData.reporterDetails.submitterCadre));
        submitterTable.addCell(createUnderlinedCell("Mobile No: " + formData.reporterDetails.submitterMobile));
        submitterTable.addCell(createUnderlinedCell("Email: " + formData.reporterDetails.submitterEmail));
        submitterTable.addCell(createUnderlinedCell("Date: " + formData.reporterDetails.submissionDate));
        submitterTable.addCell(new Cell().setBorder(Border.NO_BORDER));

        document.add(submitterTable);
    }

    private void addFooterSection(Document document) {
        // Add some spacing
        document.add(new Paragraph("\n"));

        // First - the motivational message (appears at the top in the PDF)
        document.add(new Paragraph("You need not be certain….. just be suspicious!")
                .setFont(boldFont)
                .setFontSize(10)
                .setFontColor(new DeviceRgb(255, 0, 0))
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setItalic());

        document.add(new Paragraph("\n"));

        // Appreciation message
        document.add(new Paragraph("Your support towards the National Pharmacovigilance system is appreciated")
                .setFont(normalFont)
                .setFontSize(9)
                .setFontColor(new DeviceRgb(255, 0, 0))
                .setTextAlignment(TextAlignment.CENTER));

        // Disclaimer paragraphs - as continuous text without extra spacing
        document.add(new Paragraph(
                "Submission of a report does not constitute an admission that medical personnel or manufacturer or the product caused or contributed to the event.")
                .setFont(normalFont)
                .setFontSize(9)
                .setFontColor(new DeviceRgb(0, 0, 0))
                .setTextAlignment(TextAlignment.JUSTIFIED));

        document.add(new Paragraph(
                "Patient's identity is held in strict confidence and program staff is not is not expected to and will not disclose reporter's identity in response to any public request.")
                .setFont(normalFont)
                .setFontSize(9)
                .setFontColor(new DeviceRgb(0, 0, 0))
                .setTextAlignment(TextAlignment.JUSTIFIED));

        document.add(new Paragraph(
                "Information supplied by you will contribute to the improvement of drug safety and therapy in Kenya. Once completed please send to:")
                .setFont(normalFont)
                .setFontSize(9)
                .setFontColor(new DeviceRgb(0, 0, 0))
                .setTextAlignment(TextAlignment.JUSTIFIED));

        document.add(new Paragraph("The Pharmacy and Poisons Board on the above address")
                .setFont(boldFont)
                .setFontSize(9)
                .setFontColor(new DeviceRgb(0, 0, 0))
                .setTextAlignment(TextAlignment.CENTER)
                .setBold());

        document.add(new Paragraph("\n"));

        // Official use section
        document.add(new Paragraph("FOR OFFICIAL (PPB) USE ONLY")
                .setFont(boldFont)
                .setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0))
                .setTextAlignment(TextAlignment.CENTER)
                .setBold());

        // Official use table
        Table officialTable = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();

        officialTable.addCell(createUnderlinedCell("ADR Report No: ……./………./………."));
        officialTable.addCell(createUnderlinedCell("Date Received ……./………./………."));
        officialTable.addCell(createUnderlinedCell("Vigiflow Entry Number…………………………………."));
        officialTable.addCell(createUnderlinedCell("Date Committed ……./………./………."));

        document.add(officialTable);
    }

    // Helper methods
    private Text createCheckbox(boolean isChecked) {
        String checkboxSymbol;
        if (isChecked) {
            checkboxSymbol = "[X] ";
        } else {
            checkboxSymbol = "[ ]";
        }

        return new Text(checkboxSymbol + " ")
                .setFont(normalFont)
                .setFontSize(10)
                .setFontColor(new DeviceRgb(0, 0, 0))
                .setBold();
    }

    private Cell createUnderlinedCell(String text) {
        return new Cell()
                .add(new Paragraph(text).setFont(normalFont).setFontSize(9).setFontColor(new DeviceRgb(0, 0, 0)))
                .setBorderBottom(new SolidBorder(1))
                .setBorderTop(Border.NO_BORDER)
                .setBorderLeft(Border.NO_BORDER)
                .setBorderRight(Border.NO_BORDER);
    }

    // Sample data creation method for demonstration
    public static ADRFormData createSampleFormData(String patientUuid) {
        String adrFormUuid = "cc27af13-69ee-49e2-8a43-e1b1926403c1";
        String adrEncounterTypeUuid = "d18d6d8a-4be2-4115-ac7e-86cc0ec2b263";

        PatientCalculationContext context = Context.getService(PatientCalculationService.class)
                .createCalculationContext();
        context.setNow(new Date());

        Patient patient = Context.getPatientService().getPatientByUuid(patientUuid);
        if (patient == null) {
            return null;
        }
        Encounter lastAdrEncounter = EmrUtils.lastEncounter(patient, Context
                .getEncounterService().getEncounterTypeByUuid(adrEncounterTypeUuid),
                Context
                        .getFormService().getFormByUuid(adrFormUuid));

        ADRFormData data = new ADRFormData();

        // Basic form info
        data.reportTitle = "Reaction to ARV medication";
        data.reportType.isSuspectedADR = true;
        data.reportType.isInitialReport = true;
        data.productCategory.isMedicinalProduct = true;

        // Institution details
        Location facility = Context.getService(KenyaEmrService.class).getDefaultLocation();

        data.institutionDetails.institutionName = facility != null ? facility.getName() : "";
        data.institutionDetails.contactTelNo = "";
        data.institutionDetails.facilityCode = "";
        data.institutionDetails.county = facility != null ? facility.getCountyDistrict() : "";

        // Patient information
        data.patientInfo.nameInitials = patient.getGivenName() + " " + patient.getFamilyName();
        data.patientInfo.ipOpNo = "";
        data.patientInfo.dobAge = patient.getAge() + "years";
        data.patientInfo.address = "";
        data.patientInfo.wardClinic = "";
        if (patient.getGender() != null) {
            if (patient.getGender().equalsIgnoreCase("M")) {
                data.patientInfo.isMale = true;
                data.patientInfo.pregnancyStatus.notApplicable = true;

            } else if (patient.getGender().equalsIgnoreCase("F")) {
                data.patientInfo.isFemale = true;
            }
        }
        data.patientInfo.hasAllergy = true;
        // TODO: Fetch from relevant obs if available
        data.patientInfo.allergyDetails = "";

        // height
        CalculationResultMap latestHeight = Calculations.lastObs(Dictionary.getConcept(Dictionary.HEIGHT_CM),
                Arrays.asList(patient.getId()), context);
        Obs heightValue = (latestHeight != null)
                ? EmrCalculationUtils.obsResultForPatient(latestHeight, patient.getPatientId())
                : null;
        String height = (heightValue != null && heightValue.getValueNumeric() != null)
                ? heightValue.getValueNumeric().toString()
                : "";
        // weight
        CalculationResultMap latestWeight = Calculations.lastObs(Dictionary.getConcept(Dictionary.WEIGHT_KG),
                Arrays.asList(patient.getId()), context);
        Obs weightValue = (latestWeight != null)
                ? EmrCalculationUtils.obsResultForPatient(latestWeight, patient.getPatientId())
                : null;
        String weight = (weightValue != null && weightValue.getValueNumeric() != null)
                ? weightValue.getValueNumeric().toString()
                : "";
        data.patientInfo.weight = weight;
        data.patientInfo.height = height;

        // Adverse reaction 
        //TODO: Fetch from relevant obs if available
        data.adverseReaction.onsetDate = "";
        data.adverseReaction.description = "";

        // Medical history  
        //TODO: Fetch from relevant obs if available
        data.medicalHistory = "";

        // Current medications
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd — MMM — yyyy");
        MedicationDispenseService medDispenseService = Context.getService(MedicationDispenseService.class);
        MedicationDispenseCriteria criteria = new MedicationDispenseCriteria();
        criteria.setPatient(patient);
        criteria.setIncludeVoided(false);
        List<MedicationDispense> dispenses = medDispenseService.getMedicationDispenseByCriteria(criteria);
        if (dispenses.size() > 0) {
            for (MedicationDispense dispense : dispenses) {
                if(dispense.getDrugOrder() != null && dispense.getDrugOrder().getAutoExpireDate() != null && dispense.getDrugOrder().getAutoExpireDate().after(new Date())) {
                    CurrentMedication med = new CurrentMedication();
                med.innGenericName = dispense.getDrug() != null ? dispense.getDrug().getName() : "";
                med.brandName = "";
                med.batchLotNo = "";
                med.manufacturer = "";
                med.dose = dispense.getDose() != null ? dispense.getDose().toString() : "";
                med.route = dispense.getRoute() != null
                        ? Context.getConceptService().getConcept(dispense.getRoute().getId()).getName().toString()
                        : "";
                med.frequency = dispense.getFrequency() != null ? dispense.getFrequency().getName() : "";
                med.startDate = dispense.getDrugOrder().getDateActivated() != null ? dateFormatter.format(dispense.getDrugOrder().getDateActivated()) : "";
                med.stopDate = dispense.getDrugOrder().getAutoExpireDate() != null ? dateFormatter.format(dispense.getDrugOrder().getAutoExpireDate()) : ""; // Placeholder, as stop date isn't typically in MedicationDispense
                med.indication = dispense.getDrugOrder().getOrderReasonNonCoded() != null ? dispense.getDrugOrder().getOrderReasonNonCoded() : "";
                data.currentMedications.add(med);
                }
                
            }

        }

        // Past medications

        if (dispenses.size() > 0) {
            // Calculate the date 3 months ago
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -3);
            Date threeMonthsAgo = cal.getTime();

            for (MedicationDispense dispense : dispenses) {
                if (dispense.getDrugOrder() != null && 
                dispense.getDrugOrder().getAutoExpireDate() != null && 
                dispense.getDrugOrder().getAutoExpireDate().before(new Date()) &&
                dispense.getDrugOrder().getDateActivated() != null &&
                dispense.getDrugOrder().getDateActivated().after(threeMonthsAgo)) {
                
                PastMedication med = new PastMedication();
                med.innGenericName = dispense.getDrug() != null ? dispense.getDrug().getName() : "";
                med.brandName = "";
                med.batchLotNo = "";
                med.manufacturer = "";
                med.dose = dispense.getDose() != null ? dispense.getDose().toString() : "";
                med.route = dispense.getRoute() != null
                        ? Context.getConceptService().getConcept(dispense.getRoute().getId()).getName().toString()
                        : "";
                med.frequency = dispense.getFrequency() != null ? dispense.getFrequency().getName() : "";
                med.startDate = dispense.getDrugOrder().getDateActivated() != null ? dateFormatter.format(dispense.getDrugOrder().getDateActivated()) : "";
                med.stopDate = dispense.getDrugOrder().getAutoExpireDate() != null ? dateFormatter.format(dispense.getDrugOrder().getAutoExpireDate()) : "";
                med.indication = dispense.getDrugOrder().getOrderReasonNonCoded() != null ? dispense.getDrugOrder().getOrderReasonNonCoded() : "";
                data.pastMedications.add(med);
                }
           }
        }

        // Lab investigations
        // TODO: Fetch from relevant obs if available
        data.labInvestigations = "";

        // Reaction grading
        if (lastAdrEncounter != null) {
            lastAdrEncounter.getEncounterProviders().forEach(ep -> {
                if (ep.getProvider() != null && ep.getProvider().getName() != null) {
                    data.reporterDetails.initialReporterName = ep.getProvider().getName();
                    data.reporterDetails.cadreDesignation = "";
                    data.reporterDetails.mobileNo = ep.getProvider().getPerson().getAttribute(
                            Context.getPersonService()
                                    .getPersonAttributeTypeByUuid("b2c38640-2603-4629-aebd-3b54f33f1e3a")) != null
                                            ? ep.getProvider().getPerson().getAttribute(Context.getPersonService()
                                                    .getPersonAttributeTypeByUuid(
                                                            "b2c38640-2603-4629-aebd-3b54f33f1e3a"))
                                                    .getValue()
                                            : "";
                    data.reporterDetails.email = ep.getProvider().getPerson().getAttribute(
                            Context.getPersonService()
                                    .getPersonAttributeTypeByUuid("b8d0b331-1d2d-4a9a-b741-1816f498bdb6")) != null
                                            ? ep.getProvider().getPerson().getAttribute(Context.getPersonService()
                                                    .getPersonAttributeTypeByUuid(
                                                            "b8d0b331-1d2d-4a9a-b741-1816f498bdb6"))
                                                    .getValue()
                                            : "";
                    data.reporterDetails.reportDate = ep.getDateCreated().toString();
                }
            });

            for (Obs obs : lastAdrEncounter.getAllObs()) {
                String conceptUuid = obs.getConcept().getUuid();
                Integer valueCodedId = obs.getValueCoded() != null ? obs.getValueCoded().getConceptId() : null;

                // --- Severity ---
                if ("162760AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA".equals(conceptUuid)) {

                    if (valueCodedId == 1500) {
                        data.reactionGrading.severity = "Severe";
                    } else if (valueCodedId == 1499) {
                        data.reactionGrading.severity = "Moderate";
                    } else if (valueCodedId == 1498) {
                        data.reactionGrading.severity = "Mild";
                    } else if (valueCodedId == 162819) {
                        data.reactionGrading.severity = "Fatal";
                    } else if (valueCodedId == 1067) {
                        data.reactionGrading.severity = "Unknown";
                    }
                }

                // --- Seriousness ---
                if ("162867AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA".equals(conceptUuid)) {
                    if (valueCodedId == 1065) {
                        data.reactionGrading.isSerious = true;
                    } else if (valueCodedId == 1066) {
                        data.reactionGrading.isSerious = false;
                    }
                }

                // --- Seriousness Criteria ---
                if ("ff5188f3-098b-40e9-be37-f248a41160d0".equals(conceptUuid)) {
                    if (valueCodedId == 5485) {
                        data.reactionGrading.seriousnessCriteria.hospitalization = true;
                    } else if (valueCodedId == 162558) {
                        data.reactionGrading.seriousnessCriteria.disability = true;
                    } else if (valueCodedId == 119975) {
                        data.reactionGrading.seriousnessCriteria.congenitalAnomaly = true;
                    } else if (valueCodedId == 1000184) {
                        data.reactionGrading.seriousnessCriteria.death = true;
                    } else if (valueCodedId == 162693) {
                        data.reactionGrading.seriousnessCriteria.lifeThreatening = true;
                    }
                }

                // --- Action Taken ---
                if ("1255AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA".equals(conceptUuid)) {
                    if (valueCodedId == 1260) {
                        data.reactionGrading.actionTaken = "Drug withdrawn";
                    } else if (valueCodedId == 1259) {
                        data.reactionGrading.actionTaken = "Dose reduced";
                    } else if (valueCodedId == 1257) {
                        data.reactionGrading.actionTaken = "Dose increased";
                    } else if (valueCodedId == 1258) {
                        data.reactionGrading.actionTaken = "Drug Changed";
                    } else if (valueCodedId == 1107) {
                        data.reactionGrading.actionTaken = "Unknown";
                    } else if (valueCodedId == 5622) {
                        data.reactionGrading.actionTaken = "Not Applicable";
                    } else if (valueCodedId == 981) {
                        data.reactionGrading.actionTaken = "Dose Changed";
                    } else if (valueCodedId == 166188) {
                        data.reactionGrading.actionTaken = "Dose not Changed";
                    }
                }

                // --- Outcome ---
                if ("163105AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA".equals(conceptUuid)) {
                    if (valueCodedId == 159791) {
                        data.reactionGrading.outcome = "Recovered";
                    } else if (valueCodedId == 164381) {
                        data.reactionGrading.outcome = "Not recovered";
                    } else if (valueCodedId == 1067) {
                        data.reactionGrading.outcome = "Unknown";
                    } else if (valueCodedId == 1000184) {
                        data.reactionGrading.outcome = "Fatal";
                    } else if (valueCodedId == 162677) {
                        data.reactionGrading.outcome = "Recovering";
                    } else if (valueCodedId == 162873) {
                        data.reactionGrading.outcome = "Recovered with sequelae";
                    }
                }

                // Dechallenge/Rechallenge
                if ("6097AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA".equals(conceptUuid)) {
                    if (valueCodedId == 1065) {
                        data.dechallengeRechallenge.reactionResolvedAfterStopped = "Yes";
                    } else if (valueCodedId == 1066) {
                        data.dechallengeRechallenge.reactionResolvedAfterStopped = "No";
                    } else if (valueCodedId == 1067) {
                        data.dechallengeRechallenge.reactionResolvedAfterStopped = "Unknown";
                    } else if (valueCodedId == 1175) {
                        data.dechallengeRechallenge.reactionResolvedAfterStopped = "Not Applicable";
                    }

                }
                if ("159924AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA".equals(conceptUuid)) {
                    if (valueCodedId == 1065) {
                        data.dechallengeRechallenge.reactionReappearedAfterReintroduced = "Yes";
                    } else if (valueCodedId == 1066) {
                        data.dechallengeRechallenge.reactionReappearedAfterReintroduced = "No";
                    } else if (valueCodedId == 1067) {
                        data.dechallengeRechallenge.reactionReappearedAfterReintroduced = "Unknown";
                    } else if (valueCodedId == 1175) {
                        data.dechallengeRechallenge.reactionReappearedAfterReintroduced = "Not Applicable";
                    }

                }

            }
        }
        // TODO: Fetch from relevant obs if available
        data.otherComments = "";

        // Reporter details

        data.reporterDetails.submitterName = "";
        data.reporterDetails.submitterCadre = "";
        data.reporterDetails.submitterMobile = "";
        data.reporterDetails.submitterEmail = "";
        data.reporterDetails.submissionDate = "";

        return data;
    }

}
