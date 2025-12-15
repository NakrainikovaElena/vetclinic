document.addEventListener("DOMContentLoaded", () => {
    const tabPets = document.getElementById("tabPets");
    const tabAddPet = document.getElementById("tabAddPet");
    const tabAddOwner = document.getElementById("tabAddOwner");
    const petsBlock = document.getElementById("petsBlock");
    const addPetBlock = document.getElementById("addPetBlock");
    const addOwnerBlock = document.getElementById("addOwnerBlock");

    function showBlock(block) {
        petsBlock.style.display = "none";
        addPetBlock.style.display = "none";
        addOwnerBlock.style.display = "none";
        block.style.display = "block";
    }

    async function loadPetsTable() {
        const response = await fetch("/api/client/pets");
        const pets = await response.json();
        const table = document.getElementById("petsTable");
        table.innerHTML = "";
        pets.forEach(p => {
            const row = `<tr>
                <td>${p.petName}</td>
                <td>${p.species}</td>
                <td>${p.specialistName}</td>
                <td>${p.specialistSpecialization}</td>
                <td>${p.specialistPhone}</td>
            </tr>`;
            table.insertAdjacentHTML("beforeend", row);
        });
    }

    async function loadSpecialists() {
        const response = await fetch("/api/client/specialists");
        const specialists = await response.json();
        const select = document.getElementById("specialistSelect");
        select.innerHTML = '<option value="">-- Выберите врача --</option>';
        specialists.forEach(s => {
            const option = document.createElement("option");
            option.value = s.specialistId;
            option.textContent = `${s.userFullName} — ${s.specialization}`;
            select.appendChild(option);
        });
    }

    async function loadPetsSelect() {
        const response = await fetch("/api/client/my-pets");
        const pets = await response.json();
        const select = document.getElementById("petSelect");
        select.innerHTML = '<option value="">-- Выберите питомца --</option>';
        pets.forEach(p => {
            const option = document.createElement("option");
            option.value = p.petId;
            option.textContent = `${p.petName} (${p.species})`;
            select.appendChild(option);
        });
    }

    tabPets.addEventListener("click", e => {
        e.preventDefault();
        showBlock(petsBlock);
        loadPetsTable();
    });

    tabAddPet.addEventListener("click", e => {
        e.preventDefault();
        showBlock(addPetBlock);
        loadSpecialists();
    });

    tabAddOwner.addEventListener("click", e => {
        e.preventDefault();
        showBlock(addOwnerBlock);
        loadPetsSelect();
    });

    // POST-запрос для добавления питомца
    const addPetForm = document.getElementById("addPetForm");
    addPetForm.addEventListener("submit", async e => {
        e.preventDefault();

        const petName = document.getElementById("petName").value.trim();
        const species = document.getElementById("species").value.trim();
        const specialistId = document.getElementById("specialistSelect").value;

        if (!petName || !species || !specialistId) {
            alert("Пожалуйста, заполните все поля!");
            return;
        }

        const data = {
            petName: petName,
            species: species,
            specialistId: parseInt(specialistId)
        };

        const csrfTokenInput = document.querySelector('input[name="_csrf"]');
        const csrfToken = csrfTokenInput ? csrfTokenInput.value : null;

        try {
            const response = await fetch("/api/client/pets", {
                method: "POST",
                headers: { "Content-Type": "application/json", "X-CSRF-TOKEN": csrfToken },
                body: JSON.stringify(data)
            });

            if (response.ok) {
                const addedPet = await response.json();
                alert(`Питомец "${addedPet.petName}" успешно добавлен!`);
                addPetForm.reset();
                tabPets.click();
            } else {
                const errorText = await response.text();
                alert("Ошибка при добавлении питомца: " + errorText);
            }
        } catch (err) {
            console.error(err);
            alert("Произошла ошибка при отправке запроса.");
        }
    });

    // POST-запрос для добавления владельца
    const addOwnerForm = document.getElementById("addOwnerForm");
    addOwnerForm.addEventListener("submit", async e => {
        e.preventDefault();
        const data = {
            petId: document.getElementById("petSelect").value,
            ownerLogin: document.getElementById("ownerLogin").value
        };
        const csrfToken = document.querySelector('input[name="_csrf"]').value;

        const response = await fetch("/api/client/owners", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "X-CSRF-TOKEN": csrfToken
            },
            body: JSON.stringify(data)
        });

        const messageDiv = document.getElementById("ownerMessage");
        const text = await response.text();

        messageDiv.textContent = text;
        messageDiv.style.color = "yellow";
        addOwnerForm.reset();
    });

    tabPets.click();
});
