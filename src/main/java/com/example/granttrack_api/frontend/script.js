const API_BASE_URL = "http://localhost:8080/api";


// =====================================================
// PAGE NAVIGATION
// =====================================================

function showSection(sectionId) {

    const sections = document.querySelectorAll(".content-section");

    sections.forEach(section => {
        section.classList.remove("active-section");
    });

    const selectedSection = document.getElementById(sectionId);

    if (selectedSection) {
        selectedSection.classList.add("active-section");
    }


    const navItems = document.querySelectorAll(".nav-item");

    navItems.forEach(item => {
        item.classList.remove("active");
    });


    navItems.forEach(item => {

        const onclickValue = item.getAttribute("onclick");

        if (onclickValue &&
            onclickValue.includes("'" + sectionId + "'")) {

            item.classList.add("active");
        }

    });


    // Load data based on section
    if (sectionId === "dashboard") {
        loadDashboard();
    }

    if (sectionId === "faculty") {
        loadFaculty();
    }

    if (sectionId === "applications") {
        loadApplications();
    }

    if (sectionId === "budget") {
        loadBudget();
    }

    if (sectionId === "expenditure") {
        loadExpenditures();
    }

    if (sectionId === "deadlines") {
        loadDeadlines();
    }

    if (sectionId === "reports") {
        loadReports();
    }
}


// =====================================================
// DASHBOARD
// =====================================================

async function loadDashboard() {

    try {

        const response =
            await fetch(`${API_BASE_URL}/dashboard`);

        if (!response.ok) {
            throw new Error("Dashboard API failed");
        }

        const data = await response.json();


        document.getElementById("applicationCount").textContent =
            data.applicationCount ?? 0;

        document.getElementById("submittedCount").textContent =
            data.submittedCount ?? 0;

        document.getElementById("underReviewCount").textContent =
            data.underReviewCount ?? 0;

        document.getElementById("approvedCount").textContent =
            data.approvedCount ?? 0;

        document.getElementById("rejectedCount").textContent =
            data.rejectedCount ?? 0;

        document.getElementById("facultyCount").textContent =
            data.facultyCount ?? 0;


        document.getElementById("approvedGrant").textContent =
            formatCurrency(data.approvedGrant);

        document.getElementById("totalExpenditure").textContent =
            formatCurrency(data.totalExpenditure);

        document.getElementById("remainingGrant").textContent =
            formatCurrency(data.remainingGrant);


        // Calculate utilization percentage
        const approvedGrant =
            Number(data.approvedGrant || 0);

        const totalExpenditure =
            Number(data.totalExpenditure || 0);

        let utilization = 0;

        if (approvedGrant > 0) {

            utilization =
                (totalExpenditure / approvedGrant) * 100;
        }


        document.getElementById("utilizationProgress").style.width =
            `${Math.min(utilization, 100)}%`;

        document.getElementById("utilizationText").textContent =
            `${utilization.toFixed(1)}%`;


    } catch (error) {

        console.error("Dashboard Error:", error);

        showError(
            "Dashboard data load panna mudiyala. Backend running-ah irukka check pannunga."
        );
    }
}


// =====================================================
// FACULTY
// =====================================================

async function loadFaculty() {

    try {

        const response =
            await fetch(`${API_BASE_URL}/faculty`);

        if (!response.ok) {
            throw new Error("Faculty API failed");
        }

        const facultyList =
            await response.json();


        const tableBody =
            document.getElementById("facultyTableBody");

        tableBody.innerHTML = "";


        if (facultyList.length === 0) {

            tableBody.innerHTML = `
                <tr>
                    <td colspan="4" class="no-data">
                        No faculty records found
                    </td>
                </tr>
            `;

            return;
        }


        facultyList.forEach(faculty => {

            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${faculty.id}</td>

                <td>${escapeHtml(faculty.name)}</td>

                <td>${escapeHtml(faculty.email)}</td>

                <td>${escapeHtml(faculty.department)}</td>
            `;

            tableBody.appendChild(row);

        });


    } catch (error) {

        console.error("Faculty Error:", error);

        showError("Faculty data load panna mudiyala.");
    }
}


// =====================================================
// GRANT APPLICATIONS
// =====================================================

async function loadApplications() {

    try {

        const response =
            await fetch(`${API_BASE_URL}/grant-applications`);

        if (!response.ok) {
            throw new Error("Applications API failed");
        }

        const applications =
            await response.json();


        const tableBody =
            document.getElementById("applicationTableBody");

        tableBody.innerHTML = "";


        if (applications.length === 0) {

            tableBody.innerHTML = `
                <tr>
                    <td colspan="6" class="no-data">
                        No grant applications found
                    </td>
                </tr>
            `;

            return;
        }


        applications.forEach(application => {

            const facultyName =
                application.faculty
                    ? application.faculty.name
                    : "-";


            const row =
                document.createElement("tr");


            row.innerHTML = `

                <td>${application.id}</td>

                <td>
                    ${escapeHtml(application.title)}
                </td>

                <td>
                    ${escapeHtml(facultyName)}
                </td>

                <td>
                    ${formatCurrency(application.requestedAmount)}
                </td>

                <td>
                    ${createStatusBadge(application.status)}
                </td>

                <td>
                    ${formatDate(application.utilizationDeadline)}
                </td>

            `;


            tableBody.appendChild(row);

        });


    } catch (error) {

        console.error("Applications Error:", error);

        showError("Grant applications load panna mudiyala.");
    }
}


// =====================================================
// GRANT BUDGET
// =====================================================

async function loadBudget() {

    try {

        // Current application ID = 1
        const applicationId = 1;

        const response =
            await fetch(
                `${API_BASE_URL}/grant-budgets/application/${applicationId}`
            );


        if (!response.ok) {

            throw new Error("Budget API failed");
        }


        const budget =
            await response.json();


        document.getElementById("budgetApproved").textContent =
            formatCurrency(budget.approvedAmount);


        document.getElementById("budgetExpenditure").textContent =
            formatCurrency(budget.totalExpenditure);


        document.getElementById("budgetRemaining").textContent =
            formatCurrency(budget.remainingAmount);


    } catch (error) {

        console.error("Budget Error:", error);

        showError("Budget data load panna mudiyala.");
    }
}


// =====================================================
// EXPENDITURES
// =====================================================

async function loadExpenditures() {

    try {

        const response =
            await fetch(`${API_BASE_URL}/expenditures`);

        if (!response.ok) {
            throw new Error("Expenditure API failed");
        }

        const expenditures =
            await response.json();


        const tableBody =
            document.getElementById("expenditureTableBody");

        tableBody.innerHTML = "";


        if (expenditures.length === 0) {

            tableBody.innerHTML = `
                <tr>
                    <td colspan="4" class="no-data">
                        No expenditure records found
                    </td>
                </tr>
            `;

            return;
        }


        expenditures.forEach(expenditure => {

            const applicationId =
                expenditure.application
                    ? expenditure.application.id
                    : "-";


            const row =
                document.createElement("tr");


            row.innerHTML = `

                <td>${expenditure.id}</td>

                <td>
                    ${escapeHtml(expenditure.description)}
                </td>

                <td>
                    ${formatCurrency(expenditure.amount)}
                </td>

                <td>
                    Application #${applicationId}
                </td>

            `;


            tableBody.appendChild(row);

        });


    } catch (error) {

        console.error("Expenditure Error:", error);

        showError("Expenditure data load panna mudiyala.");
    }
}


// =====================================================
// UTILIZATION DEADLINES
// =====================================================

async function loadDeadlines() {

    await loadUpcomingDeadlines();

    await loadExpiredDeadlines();
}


// -----------------------------------------------------
// Upcoming
// -----------------------------------------------------

async function loadUpcomingDeadlines() {

    try {

        const response =
            await fetch(
                `${API_BASE_URL}/utilization-deadlines/upcoming`
            );


        if (!response.ok) {
            throw new Error("Upcoming deadline API failed");
        }


        const applications =
            await response.json();


        const container =
            document.getElementById("upcomingDeadlines");


        container.innerHTML = "";


        if (applications.length === 0) {

            container.innerHTML = `
                <div class="no-data">
                    No upcoming deadlines
                </div>
            `;

            return;
        }


        applications.forEach(application => {

            const card =
                document.createElement("div");


            card.className =
                "deadline-card";


            card.innerHTML = `

                <h4>
                    ${escapeHtml(application.title)}
                </h4>

                <p>
                    Faculty:
                    ${escapeHtml(
                        application.faculty
                            ? application.faculty.name
                            : "-"
                    )}
                </p>

                <p>
                    Deadline:
                    <strong>
                        ${formatDate(application.utilizationDeadline)}
                    </strong>
                </p>

                <p>
                    Status:
                    ${createStatusBadge(application.status)}
                </p>

            `;


            container.appendChild(card);

        });


    } catch (error) {

        console.error(
            "Upcoming Deadline Error:",
            error
        );

        showError("Upcoming deadlines load panna mudiyala.");
    }
}


// -----------------------------------------------------
// Expired
// -----------------------------------------------------

async function loadExpiredDeadlines() {

    try {

        const response =
            await fetch(
                `${API_BASE_URL}/utilization-deadlines/expired`
            );


        if (!response.ok) {
            throw new Error("Expired deadline API failed");
        }


        const applications =
            await response.json();


        const container =
            document.getElementById("expiredDeadlines");


        container.innerHTML = "";


        if (applications.length === 0) {

            container.innerHTML = `
                <div class="no-data">
                    No expired deadlines
                </div>
            `;

            return;
        }


        applications.forEach(application => {

            const card =
                document.createElement("div");


            card.className =
                "deadline-card expired-card";


            card.innerHTML = `

                <h4>
                    ${escapeHtml(application.title)}
                </h4>

                <p>
                    Faculty:
                    ${escapeHtml(
                        application.faculty
                            ? application.faculty.name
                            : "-"
                    )}
                </p>

                <p>
                    Deadline:
                    <strong>
                        ${formatDate(application.utilizationDeadline)}
                    </strong>
                </p>

                <p>
                    Status:
                    ${createStatusBadge(application.status)}
                </p>

            `;


            container.appendChild(card);

        });


    } catch (error) {

        console.error(
            "Expired Deadline Error:",
            error
        );

        showError("Expired deadlines load panna mudiyala.");
    }
}


// =====================================================
// REPORTS
// =====================================================

async function loadReports() {

    try {

        const response =
            await fetch(`${API_BASE_URL}/reports/grants`);


        if (!response.ok) {
            throw new Error("Reports API failed");
        }


        const report =
            await response.json();


        document.getElementById(
            "reportTotalApplications"
        ).textContent =
            report.totalApplications ?? 0;


        document.getElementById(
            "reportSubmitted"
        ).textContent =
            report.submittedApplications ?? 0;


        document.getElementById(
            "reportUnderReview"
        ).textContent =
            report.underReviewApplications ?? 0;


        document.getElementById(
            "reportApproved"
        ).textContent =
            report.approvedApplications ?? 0;


        document.getElementById(
            "reportRejected"
        ).textContent =
            report.rejectedApplications ?? 0;


        document.getElementById(
            "reportApprovedGrant"
        ).textContent =
            formatCurrency(report.totalApprovedGrant);


        document.getElementById(
            "reportExpenditure"
        ).textContent =
            formatCurrency(report.totalExpenditure);


        document.getElementById(
            "reportRemaining"
        ).textContent =
            formatCurrency(report.remainingGrant);


    } catch (error) {

        console.error("Reports Error:", error);

        showError("Reports data load panna mudiyala.");
    }
}


// =====================================================
// FORM PLACEHOLDER FUNCTIONS
// =====================================================

function openFacultyForm() {

    alert(
        "Faculty form next step-la implement pannuvom."
    );
}


function openApplicationForm() {

    alert(
        "Grant application form next step-la implement pannuvom."
    );
}


// =====================================================
// UTILITY FUNCTIONS
// =====================================================

function formatCurrency(value) {

    const number =
        Number(value || 0);


    return number.toLocaleString(
        "en-IN",
        {
            style: "currency",
            currency: "INR",
            maximumFractionDigits: 2
        }
    );
}


function formatDate(dateValue) {

    if (!dateValue) {
        return "-";
    }


    const date =
        new Date(dateValue);


    return date.toLocaleDateString(
        "en-IN",
        {
            day: "2-digit",
            month: "2-digit",
            year: "numeric"
        }
    );
}


function createStatusBadge(status) {

    if (!status) {
        return "-";
    }


    let className =
        "status-badge";


    if (status === "SUBMITTED") {

        className +=
            " status-submitted";

    } else if (status === "UNDER_REVIEW") {

        className +=
            " status-review";

    } else if (status === "APPROVED") {

        className +=
            " status-approved";

    } else if (status === "REJECTED") {

        className +=
            " status-rejected";
    }


    return `
        <span class="${className}">
            ${status.replace("_", " ")}
        </span>
    `;
}


// =====================================================
// HTML SAFETY
// =====================================================

function escapeHtml(value) {

    if (value === null || value === undefined) {
        return "";
    }


    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}


// =====================================================
// ERROR MESSAGE
// =====================================================

function showError(message) {

    console.error(message);

    alert(message);
}


// =====================================================
// INITIAL LOAD
// =====================================================

document.addEventListener(
    "DOMContentLoaded",
    function () {

        loadDashboard();

    }
);