document.addEventListener('DOMContentLoaded', function() {
    const registrationForm = document.getElementById('producer-registration-form');
    const errorContainer = document.getElementById('error');

    registrationForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const emailInput = document.getElementById('email');
        const nameInput = document.getElementById('name');
        const surnameInput = document.getElementById('surname');
        const passwordInput = document.getElementById('password');
        const repasswordInput = document.getElementById('repassword');
        const mobileNumberInput = document.getElementById('mobileNumber');
        const bankAccountInput = document.getElementById('bankAccount');
        const brandInput = document.getElementById('brand');
        const checkboxInput = document.getElementById('gridCheck');
        let errorMessage = '';

        if (!validateEmail(emailInput.value)) {
            errorMessage += 'Please enter a valid email address.\n';
        } else if (passwordInput.value.trim() === '') {
            errorMessage += 'Please enter a password.\n';
        }else if (nameInput.value.trim() === '') {
            errorMessage += 'Please enter a firstname.\n';
        }else if (surnameInput.value.trim() === '') {
            errorMessage += 'Please enter a lastnames.\n';
        } else if (passwordInput.value !== repasswordInput.value) {
            errorMessage += 'Passwords do not match.\n';
        } else if (!validateMobileNumber(mobileNumberInput.value)) {
            errorMessage += 'Please enter a valid mobile number.\n';
        } else if (!validateBankAccount(bankAccountInput.value)) {
            errorMessage += 'Please enter a valid bank account number.\n';
        } else if (!validateBrand(brandInput.value)) {
            errorMessage += 'Please enter a brand.\n';
        } else if (!checkboxInput.checked) {
            errorMessage += 'Please agree to the terms and conditions.\n';
        }

        if (errorMessage !== '') {
            showError(errorMessage);
            return;
        }

        registrationForm.submit();
    });

    function validateEmail(email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }

    function validateMobileNumber(mobileNumber) {
        const mobileNumberRegex = /^[0-9]{13}$/;
        return mobileNumberRegex.test(mobileNumber);
    }

    function validateBankAccount(bankAccount) {
        const bankAccountRegex = /^[0-9]{16}$/;
        return bankAccountRegex.test(bankAccount);
    }

    function validateBrand(brand) {
        return brand.trim() !== '';
    }

    function showError(message) {
        errorContainer.textContent = message;
    }
});
