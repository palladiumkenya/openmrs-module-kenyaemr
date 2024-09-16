
SET FOREIGN_KEY_CHECKS=0;
delete from patient_appointment_audit;
delete from patient_appointment where appointment_service_id is null or (appointment_service_id in (1,2,3,4,5,6,7,8,9,13,11,12));
SET FOREIGN_KEY_CHECKS=1;
    
    
INSERT INTO patient_appointment (
    patient_id,
    start_date_time,
    end_date_time,
    appointment_service_id,
    status,
    location_id,
    appointment_kind,
    appointment_number,
    uuid,
    date_created,
    creator
)   
SELECT 
    e.patient_id,
        CONCAT(DATE(o.value_datetime),
            ' 07:00:00') AS start_date_time,
    CONCAT(DATE(o.value_datetime),
            ' 17:00:00') AS end_date_time,
    (case  
    when ef.uuid = '23b4ebbd-29ad-455e-be0e-04aa6bc30798' and o.concept_id=5096 then 1 -- Greencard form
    when ef.uuid = '23b4ebbd-29ad-455e-be0e-04aa6bc30798' and o.concept_id=162549 then 2 -- Greencard form
    when ef.uuid = '22c68f86-bbf0-49ba-b2d1-23fa7ccf0259' and o.concept_id=5096 then 1 -- HIV summary form
    when ef.uuid = '22c68f86-bbf0-49ba-b2d1-23fa7ccf0259' and o.concept_id=162549 then 2 -- HIV summary form
    when ef.uuid = '1bfb09fc-56d7-4108-bd59-b2765fd312b8' then 7 -- prep initial
    when ef.uuid = 'ee3e2017-52c0-4a54-99ab-ebb542fb8984' then 8 -- prep followup
    when ef.uuid = '291c03c8-a216-11e9-a2a3-2a2ae2dbcce4' then 9 -- prep monthly refill
    when ef.uuid = '2daabb77-7ad6-4952-864b-8d23e109c69d' then 6 -- TB followup
    when ef.uuid = '92e041ac-9686-11e9-bc42-526af7764f64' then 3 -- KP clinical visit
    when ef.uuid = '755b59e6-acbb-4853-abaf-be302039f902' then 13  -- HEI followup
    when ef.uuid = 'e8f98494-af35-4bb8-9fc7-c409c8fed843' then 4 -- antenatal visit
    when ef.uuid = '72aa78e0-ee4b-47c3-9073-26f3b9ecc4a7' then 5  -- postnatal visit
    when ef.uuid = '496c7cc3-0eea-4e84-a04c-2292949e2f7f' then 5-- delivery visit. This is the first postnatal appointment after deliver
    else null 
    end) AS appointment_service_id,
    'Scheduled' AS status,
    e.location_id,
    'Scheduled' AS appointment_kind,
    '0000' AS appointment_number,
    uuid() AS uuid,
    e.encounter_datetime AS date_created, -- we want to pick the encounter datetime as the date created
    (select user_id from users where username='admin') AS creator
FROM
    encounter e 
    inner join form ef on ef.form_id = e.form_id and ef.uuid in ('1bfb09fc-56d7-4108-bd59-b2765fd312b8','291c03c8-a216-11e9-a2a3-2a2ae2dbcce4','2daabb77-7ad6-4952-864b-8d23e109c69d','92e041ac-9686-11e9-bc42-526af7764f64','755b59e6-acbb-4853-abaf-be302039f902','e8f98494-af35-4bb8-9fc7-c409c8fed843','72aa78e0-ee4b-47c3-9073-26f3b9ecc4a7','496c7cc3-0eea-4e84-a04c-2292949e2f7f','23b4ebbd-29ad-455e-be0e-04aa6bc30798', '22c68f86-bbf0-49ba-b2d1-23fa7ccf0259','ee3e2017-52c0-4a54-99ab-ebb542fb8984')
    inner join obs o on o.encounter_id = e.encounter_id and o.concept_id in (5096,162549) and o.voided = 0;
    
    
-- update appointment statuses
-- HIV followup appointments
update patient_appointment apt
inner join encounter e on e.patient_id = apt.patient_id and date(e.encounter_datetime) between date_sub(date(apt.start_date_time), interval 5 day) and date(apt.start_date_time)
inner join form f on f.form_id = e.form_id and f.uuid in ('23b4ebbd-29ad-455e-be0e-04aa6bc30798','22c68f86-bbf0-49ba-b2d1-23fa7ccf0259')
set apt.status='Completed'
where apt.appointment_service_id = 1;

-- ART drug refill
update patient_appointment apt
inner join encounter e on e.patient_id = apt.patient_id and date(e.encounter_datetime) between date_sub(date(apt.start_date_time), interval 5 day) and date(apt.start_date_time)
inner join form f on f.form_id = e.form_id and f.uuid in ('83fb6ab2-faec-4d87-a714-93e77a28a201')
set apt.status='Completed'
where apt.appointment_service_id = 2;

-- prep initial
update patient_appointment apt
inner join encounter e on e.patient_id = apt.patient_id and date(e.encounter_datetime) between date_sub(date(apt.start_date_time), interval 5 day) and date(apt.start_date_time)
inner join form f on f.form_id = e.form_id and f.uuid in ('1bfb09fc-56d7-4108-bd59-b2765fd312b8')
set apt.status='Completed'
where apt.appointment_service_id = 7;

-- prep followup
update patient_appointment apt
inner join encounter e on e.patient_id = apt.patient_id and date(e.encounter_datetime) between date_sub(date(apt.start_date_time), interval 5 day) and date(apt.start_date_time)
inner join form f on f.form_id = e.form_id and f.uuid in ('ee3e2017-52c0-4a54-99ab-ebb542fb8984')
set apt.status='Completed'
where apt.appointment_service_id = 8;

-- prep monthly
update patient_appointment apt
inner join encounter e on e.patient_id = apt.patient_id and date(e.encounter_datetime) between date_sub(date(apt.start_date_time), interval 5 day) and date(apt.start_date_time)
inner join form f on f.form_id = e.form_id and f.uuid in ('291c03c8-a216-11e9-a2a3-2a2ae2dbcce4')
set apt.status='Completed'
where apt.appointment_service_id = 9;

-- TB follow-up
update patient_appointment apt
inner join encounter e on e.patient_id = apt.patient_id and date(e.encounter_datetime) between date_sub(date(apt.start_date_time), interval 5 day) and date(apt.start_date_time)
inner join form f on f.form_id = e.form_id and f.uuid in ('2daabb77-7ad6-4952-864b-8d23e109c69d')
set apt.status='Completed'
where apt.appointment_service_id = 6;

-- KP clinic visit
update patient_appointment apt
inner join encounter e on e.patient_id = apt.patient_id and date(e.encounter_datetime) between date_sub(date(apt.start_date_time), interval 5 day) and date(apt.start_date_time)
inner join form f on f.form_id = e.form_id and f.uuid in ('92e041ac-9686-11e9-bc42-526af7764f64')
set apt.status='Completed'
where apt.appointment_service_id = 3;

-- HEI followup
update patient_appointment apt
inner join encounter e on e.patient_id = apt.patient_id and date(e.encounter_datetime) between date_sub(date(apt.start_date_time), interval 5 day) and date(apt.start_date_time)
inner join form f on f.form_id = e.form_id and f.uuid in ('755b59e6-acbb-4853-abaf-be302039f902')
set apt.status='Completed'
where apt.appointment_service_id = 13;

-- Ante-natal visit appointment
update patient_appointment apt
inner join encounter e on e.patient_id = apt.patient_id and date(e.encounter_datetime) between date_sub(date(apt.start_date_time), interval 5 day) and date(apt.start_date_time)
inner join form f on f.form_id = e.form_id and f.uuid in ('e8f98494-af35-4bb8-9fc7-c409c8fed843')
set apt.status='Completed'
where apt.appointment_service_id = 4;

-- post-natal visit appointment
update patient_appointment apt
inner join encounter e on e.patient_id = apt.patient_id and date(e.encounter_datetime) between date_sub(date(apt.start_date_time), interval 5 day) and date(apt.start_date_time)
inner join form f on f.form_id = e.form_id and f.uuid in ('72aa78e0-ee4b-47c3-9073-26f3b9ecc4a7')
set apt.status='Completed'
where apt.appointment_service_id = 5;

update patient_appointment apt set apt.status='Missed' where date(apt.start_date_time) < curdate() and apt.status='Scheduled';



    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    