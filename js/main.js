// Login Form Handling
document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    const loginButton = document.getElementById('loginButton');
    
    // Event handler function
    function handleLogin() {
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        if (username === 'admin' && password === 'password') {
            // Get current URL without query params
            const url = window.location.href.split('?')[0];
            // Get base directory path
            const basePath = url.substring(0, url.lastIndexOf('/') + 1);
            // Create home page URL
            const homePage = basePath + 'home.html';
            console.log('Redirecting to:', homePage);
            window.location.href = homePage;
            return false;
        } else {
            const errorMessage = document.getElementById('errorMessage');
            errorMessage.textContent = 'Invalid username or password';
            errorMessage.style.display = 'block';
            return false;
        }
    }
    
    if (loginForm && loginButton) {
        // Handle both form submit and button click
        loginForm.onsubmit = handleLogin;
        loginButton.onclick = handleLogin;
    }
});
