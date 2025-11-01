// Get the HTML elements using their IDs
const profileTable = document.getElementById('profile-data');
const toggleButton = document.getElementById('edit-toggle-button');
// NEW: Get elements for profile picture editing
const profilePic = document.getElementById('profile-pic');
const profilePicInput = document.getElementById('profile-pic-input');

// State variable to track the current mode (false = View, true = Edit)
let isEditing = false;

/**
 * Toggles the profile display between static text and editable input fields.
 */
function toggleEditMode() {
    // Basic check to prevent errors if elements aren't loaded
    if (!profileTable || !toggleButton || !profilePic || !profilePicInput) {
        console.error("Required HTML elements not found.");
        return;
    }

    // Flip the state
    isEditing = !isEditing;

    if (isEditing) {
        // --- EDIT MODE: Switch to Input Fields ---
        
        // 1. Change button text and color for visual feedback
        toggleButton.textContent = 'Save Changes';
        toggleButton.style.backgroundColor = '#28a745'; // Green color for saving

        // 2. Show the profile picture input and pre-fill it with the current URL
        profilePicInput.style.display = 'block';
        profilePicInput.value = profilePic.src;
        
        // 3. Select all data cells that hold editable content (based on the data-field attribute)
        const dataCells = profileTable.querySelectorAll('td[data-field]');

        dataCells.forEach(cell => {
            const currentValue = cell.textContent.trim();
            
            // Create the input element
            const input = document.createElement('input');
            input.type = 'text';
            input.value = currentValue;
            input.classList.add('profile-edit-input'); 
            
            // Set the data-field-name attribute for potential saving logic later
            input.setAttribute('data-field-name', cell.getAttribute('data-field'));
            
            // Replace the text content with the input field
            cell.textContent = '';
            cell.appendChild(input);
            // Remove the specific text color in edit mode for consistency with input styles
            cell.style.color = 'initial'; 
        });

    } else {
        // --- VIEW MODE: Save Changes and Switch back to Text ---
        
        // 1. Change button text and color back
        toggleButton.textContent = 'Edit Profile';
        toggleButton.style.backgroundColor = '#007BFF'; // Blue color for editing
        
        // 2. Hide the profile picture input and update the image
        const newPicUrl = profilePicInput.value.trim();
        if (newPicUrl) {
            // Update the image source if a URL was entered
            profilePic.src = newPicUrl;
            // Handle image loading error gracefully if the URL is bad
            profilePic.onerror = () => {
                profilePic.src = 'https://placehold.co/100x100/FF0000/ffffff?text=Error';
                console.error("Failed to load image from URL:", newPicUrl);
                // Reset onerror to prevent infinite loops
                profilePic.onerror = null; 
            };
        }
        profilePicInput.style.display = 'none';

        // 3. Select all data cells
        const dataCells = profileTable.querySelectorAll('td[data-field]');

        // Placeholder object for collecting updated data for a server call
        const updatedData = {
            profilePicUrl: profilePic.src
        };

        dataCells.forEach(cell => {
            // Find the input element inside the cell
            const input = cell.querySelector('input[type="text"]');
            
            if (input) {
                const newValue = input.value.trim();
                const fieldName = input.getAttribute('data-field-name');

                // Update the DOM with the new text value
                cell.textContent = newValue;
                // Restore the original text color
                cell.style.color = '#526981';

                // Collect the data to simulate sending it to a server
                updatedData[fieldName] = newValue;
            }
        });
        
        // 4. Log the data to the console (simulating server submission)
        console.log('--- Data Saved Locally ---');
        console.log(JSON.stringify(updatedData, null, 2));
        console.log('--------------------------');
    }
}

// Attach the toggle function to the button's click event
// Wait until the DOM is fully loaded to ensure the button exists
document.addEventListener('DOMContentLoaded', () => {
    if (toggleButton) {
        toggleButton.addEventListener('click', toggleEditMode);
    }
});
