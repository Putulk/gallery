function loadImages() {
    const gallery = document.getElementById('gallery');
    gallery.innerHTML = '<p>Loading images...</p>';

    fetch('http://localhost:8080/api/images', {
        method: 'GET',
        credentials: 'include',
        headers: {
            'Accept': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            if (response.status === 401) {
                // If unauthorized, redirect to login
                window.location.href = 'login.html';
                return;
            }
            throw new Error('Failed to load images');
        }
        return response.json();
    })
    .then(images => {
        if (!images || images.length === 0) {
            gallery.innerHTML = '<p>No images found. Upload some images to get started!</p>';
            return;
        }

        gallery.innerHTML = '';
        images.forEach(image => {
            const imageCard = document.createElement('div');
            imageCard.className = 'image-card';

            const img = document.createElement('img');
            img.src = `http://localhost:8080/api/images/${image.id}`;
            img.alt = image.title || 'Gallery Image';
            
            // Add loading indicator and error handling for individual images
            img.onerror = function() {
                this.src = 'https://via.placeholder.com/200x200?text=Failed+to+load+image';
            };

            const imageInfo = document.createElement('div');
            imageInfo.className = 'image-info';
            
            const title = document.createElement('div');
            title.className = 'image-title';
            title.textContent = image.title || image.fileName;

            imageInfo.appendChild(title);
            imageCard.appendChild(img);
            imageCard.appendChild(imageInfo);
            gallery.appendChild(imageCard);
        });
    })
    .catch(error => {
        console.error('Error loading images:', error);
        gallery.innerHTML = '<p>Failed to load images. Please try again later.</p>';
    });
}

document.getElementById('uploadForm').addEventListener('submit', async function(event) {
    event.preventDefault();
    
    const submitButton = this.querySelector('button[type="submit"]');
    const originalButtonText = submitButton.textContent;
    submitButton.disabled = true;
    submitButton.textContent = 'Uploading...';
    
    try {
        const fileInput = document.getElementById('fileInput');
        const file = fileInput.files[0];
        if (!file) {
            alert('Please select a file to upload');
            return;
        }

        const formData = new FormData();
        formData.append('file', file);
        formData.append('title', file.name.split('.')[0]); // Use filename as title

        const response = await fetch('http://localhost:8080/api/images/upload', {
            method: 'POST',
            credentials: 'include',
            body: formData
        });

        if (response.ok) {
            fileInput.value = ''; // Clear the file input
            loadImages(); // Refresh the gallery
            alert('Image uploaded successfully!');
        } else {
            if (response.status === 401) {
                window.location.href = 'login.html';
                return;
            }
            const errorData = await response.text();
            throw new Error(errorData || 'Failed to upload image');
        }
    } catch (error) {
        console.error('Error:', error);
        alert(error.message || 'Failed to upload image. Please try again.');
    } finally {
        submitButton.disabled = false;
        submitButton.textContent = originalButtonText;
    }
});

function logout() {
    fetch('http://localhost:8080/logout', {
        method: 'POST',
        credentials: 'include'
    })
    .then(() => {
        window.location.href = 'login.html';
    })
    .catch(error => {
        console.error('Error logging out:', error);
        window.location.href = 'login.html'; // Redirect anyway
    });
}

// Load images when the page loads
window.onload = loadImages; 