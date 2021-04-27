<%
	ui.decorateWith("kenyaui", "panel", [heading: "MCH PS Care"])

	def dataPoints = []

	dataPoints << [label: "Enrollment Date", value: calculations.enrollmentDate]
	dataPoints << [label: "Last Survey Date", value: calculations.lastSurveyDate]

%>

<div class="ke-stack-item">
	<% dataPoints.each { print ui.includeFragment("kenyaui", "widget/dataPoint", it) } %>
</div>