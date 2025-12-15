const form = document.getElementById('registerForm');
const messageDiv = document.getElementById('registerMessage');

form.addEventListener('submit', function (e) {
    e.preventDefault();
    const formData = new FormData(form);
    const params = new URLSearchParams(formData);

    fetch('/api/registration', {
        method: 'POST',
        body: params
    })
    .then(response => response.text().then(text => ({status: response.status, text})))
    .then(res => {
        if (res.status === 200) {
            messageDiv.style.color = 'green';
            messageDiv.textContent = res.text;
            form.reset();
        } else {
            messageDiv.style.color = 'red';
            messageDiv.textContent = res.text;
        }
    })
    .catch(err => {
        messageDiv.style.color = 'red';
        messageDiv.textContent = 'Ошибка регистрации. Попробуйте позже.';
    });
});
