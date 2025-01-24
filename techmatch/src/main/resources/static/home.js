document.addEventListener('DOMContentLoaded', function () {
    // Select all event cards
    const cards = document.querySelectorAll('.card[data-bs-toggle="modal"]');

    cards.forEach(card => {
        card.addEventListener('click', function () {
            const eventId = this.getAttribute('data-event-id');

            // Fetch event data via AJAX (assuming you have an endpoint like /events/{id})
            fetch(`/events/${eventId}`)
            .then(response => response.json())
            .then(event => {
                document.getElementById('eventName').textContent = event.name;
                document.getElementById('eventDate').textContent = `${event.startDate} to ${event.endDate}`;
                document.getElementById('eventDescription').textContent = event.description;
                document.getElementById('eventLocation').textContent = event.location;
                document.getElementById('eventImage').src = `data:image/jpeg;base64,${event.base64Image}`;
                modal.show();
            })
            .catch(error => console.error('Error fetching event details:', error));
        
        
        });
    });
});
