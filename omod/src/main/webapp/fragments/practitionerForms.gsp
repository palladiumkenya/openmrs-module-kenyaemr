<div class="ke-panel-frame">
    <div class="ke-panel-heading">Practitioner Forms</div>
    <div class="ke-panel-content"></div>
    <% if (practitionerForms) { %>
    <div class="ke-panel-footer">
    <% practitionerForms.each { form -> %>
        ${ui.includeFragment("kenyaui", "widget/button", [
                iconProvider: form.iconProvider,
                icon: form.icon,
                label: form.name,
                extra: "Edit form",
                href: ui.pageLink("kenyaemr", "editPatientForm", [
                    appId: "kenyaemr.medicalEncounter",
                    patientId: person.id,
                    formUuid : form.formUuid,
                    returnUrl: ui.thisUrl()
                ])
        ]) }
    <% } %>
    </div>
    <% } %>
</div>