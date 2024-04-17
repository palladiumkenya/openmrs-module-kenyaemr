INSERT INTO global_property (`property`, `property_value`, `description`, `uuid`)
VALUES ('bedManagement.sqlGet.patientListForAdmission',
        "select
    patient_identifier.identifier AS idNumber,
    CONCAT(pn.given_name, '' '', pn.family_name)  AS name,
    TIMESTAMPDIFF(YEAR, p.birthdate, CURDATE()) AS age,
    p.gender gender,
    el.visit_type visitType,
    cast(el.date_started as datetime) visitStartTime,
    el.admission_encounter_uuid admissionEncounterUuid,
    p.uuid patientUuid
from person p
         INNER JOIN person_name pn ON pn.person_id = p.person_id AND pn.voided IS FALSE
         LEFT OUTER JOIN patient_identifier ON patient_identifier.patient_id = p.person_id AND patient_identifier.voided IS FALSE
         INNER JOIN
     (select v.patient_id, bpam.patient_id activeBedAssignment, vt.name visit_type, v.date_started, e.uuid admission_encounter_uuid
      from visit v
               inner join encounter e on e.patient_id = v.patient_id
               inner join form f on e.form_id = f.form_id and f.uuid = 'e958f902-64df-4819-afd4-7fb061f59308'
               inner join visit_type vt
                          on vt.visit_type_id = v.visit_type_id and vt.uuid = 'a73e2ac6-263b-47fc-99fc-e0f2c09fc914'
               LEFT JOIN bed_patient_assignment_map bpam ON bpam.patient_id = v.patient_id AND bpam.date_stopped IS NULL
      where v.date_stopped is null and (e.encounter_datetime <= v.date_started and e.encounter_datetime>=date_sub(v.date_started, interval 2 day ))
      having activeBedAssignment is null) el on el.patient_id = p.person_id;",
        'Sql query to get patients waiting for admission',
        uuid());