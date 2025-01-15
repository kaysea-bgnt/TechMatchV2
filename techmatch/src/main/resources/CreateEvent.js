document.addEventListener('DOMContentLoaded', function() {
    // DOM Elements
    const eventForm = document.getElementById('eventForm');
    const ticketTypeBtn = document.getElementById('ticketType');
    const imageInput = document.getElementById('eventImage');
    const uploadArea = document.querySelector('.upload-area');
    const startDate = document.getElementById('startDate');
    const startTime = document.getElementById('startTime');
    const endDate = document.getElementById('endDate');
    const endTime = document.getElementById('endTime');
    const eventCapacitySelect = document.getElementById('eventCapacity');

    // Topic Selection Elements
    const topicSelectHeader = document.querySelector('.select-header');
    const topicCustomSelect = document.querySelector('.custom-select');
    const topicSelectedText = document.querySelector('.selected-text');
    const topicSelectedCount = document.querySelector('.selected-count');
    const topicCheckboxes = document.querySelectorAll('.option input[type="checkbox"]');

    // Event Type Selection Elements
    const eventTypeSelectHeader = document.querySelector('.event-type-wrapper .select-header');
    const eventTypeCustomSelect = document.querySelector('.event-type-wrapper .custom-select');
    const eventTypeSelectedText = document.querySelector('.event-type-wrapper .selected-text');
    const eventTypeRadioInputs = document.querySelectorAll('.event-type-wrapper .option input[type="radio"]');

    // Elements for modal and capacity handling
    const modal = document.getElementById('customCapacityModal');
    const closeButton = document.querySelector('.close-button');
    const customCapacityInput = document.getElementById('customCapacityInput');
    const confirmCustomCapacity = document.getElementById('confirmCustomCapacity');

    let isFreeEvent = true;

    // Image Upload Handling
    function handleImageUpload(e) {
        if (e.target.files && e.target.files[0]) {
            const reader = new FileReader();
            reader.onload = function(e) {
                uploadArea.style.backgroundImage = `url(${e.target.result})`;
                uploadArea.style.backgroundSize = 'cover';
                uploadArea.style.backgroundPosition = 'center';
                uploadArea.querySelector('.camera-icon').style.display = 'none';
            }
            reader.readAsDataURL(e.target.files[0]);
        }
    }

    // Date and Time Validation
    function validateDateTime() {
        const start = new Date(`${startDate.value} ${startTime.value}`);
        const end = new Date(`${endDate.value} ${endTime.value}`);

        if (end <= start) {
            alert('End date/time must be after start date/time');
            return false;
        }
        return true;
    }

    // Topic Selection Handling
    function handleTopicSelection() {
        const selected = Array.from(topicCheckboxes)
          .filter(checkbox => checkbox.checked)
          .map(checkbox => checkbox.nextElementSibling.textContent);
      
        const count = selected.length;
      
        if (count === 0) {
          topicSelectedText.textContent = 'Select Topics';
          topicSelectedCount.textContent = '';
        } else {
          topicSelectedText.textContent = selected.join(', '); 
      
          // Check if the text content overflows
          if (topicSelectedText.scrollWidth > topicSelectedText.offsetWidth) { 
            // Truncate the text and add ellipsis
            topicSelectedText.textContent = topicSelectedText.textContent.slice(0, 20) + "..."; 
          }
      
          topicSelectedCount.textContent = `(${count} selected)`;
        }
      }

    // Event Type Selection Handling
    function handleEventTypeSelection() {
        const selectedRadio = document.querySelector('.event-type-wrapper .option input[type="radio"]:checked');
        if (selectedRadio) {
            eventTypeSelectedText.textContent = selectedRadio.nextElementSibling.textContent;
        } else {
            eventTypeSelectedText.textContent = 'Select Event Type';
        }
    }

    // Handle dropdown change for "Other" (custom capacity)
    eventCapacitySelect.addEventListener('change', () => {
        if (eventCapacitySelect.value === 'custom') {
            // Open modal for custom capacity
            modal.style.display = 'flex';
            customCapacityInput.value = ''; // Clear previous input
        }
    });

    // Handle input changes in the customCapacityInput
    customCapacityInput.addEventListener('input', () => {
        const inputValue = parseInt(customCapacityInput.value); 
        if (inputValue <= 0) { 
        customCapacityInput.value = 1; // Set the value to 1 if it's 0 or less
        }
    });

    // Close the modal
    closeButton.addEventListener('click', () => {
        modal.style.display = 'none';
    });

    // Confirm custom capacity and update dropdown
    confirmCustomCapacity.addEventListener('click', () => {
        const customCapacity = customCapacityInput.value;
    
        if (customCapacity && !isNaN(customCapacity) && customCapacity > 0) { 
        // Check if capacity is greater than 0
        // Create a new option and select it
        const newOption = document.createElement('option');
        newOption.value = customCapacity;
        newOption.textContent = customCapacity;
        eventCapacitySelect.appendChild(newOption);
        eventCapacitySelect.value = customCapacity;
    
        // Close the modal
        modal.style.display = 'none';
        } else {
        alert('Please enter a valid positive number for capacity.'); 
        }
    });
  

    // Close modal when clicking outside of it
    window.addEventListener('click', (e) => {
        if (e.target === modal) {
            modal.style.display = 'none';
        }
    });

    // Form Submission Handling
    async function handleFormSubmit(e) {
        e.preventDefault();

        if (!validateDateTime()) {
            return;
        }

        const formData = new FormData();
        
        // Collect form data
        const formFields = {
            'name': 'eventName',
            'startDate': 'startDate',
            'startTime': 'startTime',
            'endDate': 'endDate',
            'endTime': 'endTime',
            'location': 'location',
            'eventType': 'eventType',
            'description': 'description',
            'topic': 'topic',
            'capacity': 'capacity'
        };

        // Append form fields to FormData
        Object.entries(formFields).forEach(([key, id]) => {
            const element = document.getElementById(id);
            if (element) {
                formData.append(key, element.value);
            }
        });

        // Append additional fields
        formData.append('isFree', isFreeEvent);
        formData.append('requireApproval', document.getElementById('requireApproval')?.checked || false);

        // Append image if exists
        const imageFile = imageInput.files[0];
        if (imageFile) {
            formData.append('image', imageFile);
        }

        // Check if any topics are selected
        const selectedTopics = Array.from(topicCheckboxes).filter(checkbox => checkbox.checked);
        if (selectedTopics.length === 0) {
            alert('Please select at least one event topic.');
            return;
        }

        // Check if an event type is selected
        const selectedEventType = document.querySelector('.event-type-wrapper .option input[type="radio"]:checked');
        if (!selectedEventType) {
            alert('Please select an event type.');
            return;
        }

        try {
            const response = await fetch('/api/events', {
                method: 'POST',
                body: formData
            });

            if (!response.ok) {
                throw new Error('Failed to create event');
            }

            const result = await response.json();
            console.log('Event created successfully:', result);
            alert('Event created successfully!');
            window.location.href = `/events/${result.id}`;
        } catch (error) {
            console.error('Error creating event:', error);
            alert('Failed to create event. Please try again.');
        }
        
        const capacity = eventCapacitySelect.value;

        if (capacity === 'other') {
            formData.append('capacity', customCapacityInput.value);
        } else {
            formData.append('capacity', capacity);
        }
    }

    // Event Listeners
    function initializeEventListeners() {
        // Form submission
        eventForm.addEventListener('submit', handleFormSubmit);

        // Image upload
        imageInput.addEventListener('change', handleImageUpload);

        // Date/Time validation
        [startDate, startTime, endDate, endTime].forEach(input => {
            input.addEventListener('change', validateDateTime);
        });

        // Ticket type toggle
        let isFreeEvent = true; 

        ticketTypeBtn.addEventListener('click', function() {
            isFreeEvent = !isFreeEvent; 
            this.textContent = isFreeEvent ? 'Free' : 'Paid'; 
        });


        // Topic Selection
        topicSelectHeader.addEventListener('click', () => {
            topicCustomSelect.classList.toggle('open');
        });

        // Event Type Selection
        eventTypeSelectHeader.addEventListener('click', () => {
            eventTypeCustomSelect.classList.toggle('open');
        });

        // Close dropdowns when clicking outside
        document.addEventListener('click', (e) => {
            if (!topicCustomSelect.contains(e.target)) {
                topicCustomSelect.classList.remove('open');
            }
            if (!eventTypeCustomSelect.contains(e.target)) {
                eventTypeCustomSelect.classList.remove('open');
            }
        });

        // Topic checkbox changes
        topicCheckboxes.forEach(checkbox => {
            checkbox.addEventListener('change', handleTopicSelection);
        });

        // Event type radio changes
        eventTypeRadioInputs.forEach(radio => {
            radio.addEventListener('change', () => {
                handleEventTypeSelection();
                eventTypeCustomSelect.classList.remove('open');
            });
        });
    }

    // Initialize all event listeners
    initializeEventListeners();
});