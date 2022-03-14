let loginForm = document.querySelector('.app__login-wrapper');

document.querySelector('.app-login-switch__login').addEventListener('click', function() {
    if (loginForm.classList.contains('app-login-switch__register--active')) {
        loginForm.classList.remove('app-login-switch__register--active');
    }
    if (!loginForm.classList.contains('app-login-switch__login--active')) {
        loginForm.classList.add('app-login-switch__login--active');
    }
});

document.querySelector('.app-login-switch__register').addEventListener('click', function() {
    if (loginForm.classList.contains('app-login-switch__login--active')) {
        loginForm.classList.remove('app-login-switch__login--active');
    }
    if (!loginForm.classList.contains('app-login-switch__register--active')) {
        loginForm.classList.add('app-login-switch__register--active');
    }
});