document.getElementById('loginForm').addEventListener('submit', async function(event) {
    event.preventDefault();
    
    try {
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        const formData = new URLSearchParams();
        formData.append('username', username);
        formData.append('password', password);

        const response = await fetch('http://localhost:8080/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            credentials: 'include',
            body: formData.toString()
        });

        if (response.ok) {
            window.location.href = 'gallery.html';
        } else {
            throw new Error('Invalid username or password');
        }
    } catch (error) {
        console.error('Error:', error);
        alert(error.message || 'Login failed. Please try again.');
    }
});
