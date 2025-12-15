document.addEventListener("DOMContentLoaded", () => {
    const tabPatients = document.getElementById('tabPatients');
    const tabAddPatient = document.getElementById('tabAddPatient');
    const patientsBlock = document.getElementById('patientsBlock');
    const addPatientBlock = document.getElementById('addPatientBlock');

    function loadPatientsTable() {
        fetch("/doctor/patients")
            .then(response => response.json())
            .then(data => {
                const table = document.getElementById("patientsTable");
                table.innerHTML = "";
                data.forEach(p => {
                    const row = `
                        <tr>
                            <td>${p.petName}</td>
                            <td>${p.species}</td>
                            <td>${p.owners}</td>
                            <td>${p.record}</td>
                        </tr>`;
                    table.insertAdjacentHTML("beforeend", row);
                });
            });
    }

    function loadPatientsSelect() {
        fetch("/doctor/patients")
            .then(response => response.json())
            .then(data => {
                const select = document.getElementById("petSelect");
                select.innerHTML = '<option value="">-- Выберите питомца --</option>';
                data.forEach(p => {
                    const option = document.createElement("option");
                    option.value = p.petId;
                    option.textContent = `${p.petName} (${p.species})`;
                    select.appendChild(option);
                });
            });
    }

    tabPatients.addEventListener('click', (e) => {
        e.preventDefault();
        patientsBlock.style.display = 'block';
        addPatientBlock.style.display = 'none';
        loadPatientsTable();
    });

    tabAddPatient.addEventListener('click', (e) => {
        e.preventDefault();
        patientsBlock.style.display = 'none';
        addPatientBlock.style.display = 'block';
        loadPatientsSelect();
    });

    const addPatientForm = document.getElementById('addPatientForm');
    if (addPatientForm) {
        addPatientForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const petId = document.getElementById('petSelect').value;
            const diagnosis = document.getElementById('diagnosis').value.trim();
            if (!petId) {
                alert("Выберите питомца!");
                return;
            }
            try {
                const response = await fetch(`/doctor/update-diagnosis/${petId}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'X-CSRF-TOKEN': document.querySelector('input[name="_csrf"]').value
                    },
                    body: JSON.stringify({ recordText: diagnosis })
                });
                if (response.ok) {
                    alert("Диагноз сохранен!");
                    addPatientForm.reset();
                } else {
                    alert("Ошибка при сохранении диагноза");
                }
            } catch (error) {
                console.error(error);
                alert("Ошибка при сохранении диагноза");
            }
        });
    }

    tabPatients.click();
});
