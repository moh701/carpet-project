document.addEventListener('DOMContentLoaded', function() {
    const fpForm = document.getElementById('producer-forgetpassword-form');
    const errorContainer = document.getElementById('error');

    fpForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const emailInput = document.getElementById('email');
        let errorMessage = '';

        if (!validateEmail(emailInput.value)) {
            errorMessage += 'Please enter a valid email address.\n';
        }

        if (errorMessage !== '') {
            showError(errorMessage);
            return;
        }

        fpForm.submit();
    });

    function validateEmail(email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }

    function showError(message) {
        errorContainer.textContent = message;
    }
});
