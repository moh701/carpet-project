document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('producer-login-form');
    const errorContainer = document.getElementById('error');

    loginForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const emailInput = document.getElementById('email');
        const passwordInput = document.getElementById('password');
        let errorMessage = '';

        if (!validateEmail(emailInput.value)) {
            errorMessage += 'Please enter a valid email address.\n';
        }

        if (passwordInput.value.trim() === '') {
            errorMessage += 'Please enter a password.\n';
        }

        if (errorMessage !== '') {
            showError(errorMessage);
            return;
        }

        loginForm.submit();
    });

    function validateEmail(email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }

    function showError(message) {
        errorContainer.textContent = message;
    }
});
