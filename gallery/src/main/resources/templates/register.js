document.getElementById('registerForm').addEventListener('submit', async function(event) {
    event.preventDefault();
    
    try {
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        const response = await fetch('http://localhost:8080/api/users/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include',
            body: JSON.stringify({ 
                username: username, 
                password: password 
            })
        });

        if (response.ok) {
            alert('Registration successful! Please login.');
            window.location.href = 'login.html';
        } else {
            const errorData = await response.text();
            throw new Error(errorData || 'Registration failed. Please try again.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert(error.message || 'Registration failed. Please try again.');
    }
}); 